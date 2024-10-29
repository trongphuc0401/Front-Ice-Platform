package vn.edu.likelion.front_ice.service.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.DesignImageResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.PreviewEntity;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.ChallengeRepository;
import vn.edu.likelion.front_ice.repository.PreviewRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 * FirebaseServiceImpl is a service class that handles Firebase Storage operations
 * related to uploading avatar images for different user types.
 * <br><br>
 * This service provides methods for uploading and managing avatars for different user
 * roles, such as {@code Challengers}, {@code Managers}, {@code Mentors}, {@code Admins}, and {@code Recruiters}.
 * Each method ensures that any existing avatar for the user is replaced by the new one,
 * and the URL of the uploaded image is saved to the database.
 * <br><br>
 * Implements FirebaseService interface.
 */

@Service
public class FirebaseServiceImpl implements FirebaseService {

    private final AccountRepository accountRepository;
    private final PreviewRepository previewRepository;
    private final ChallengeRepository challengeRepository;

    public FirebaseServiceImpl(AccountRepository accountRepository, PreviewRepository previewRepository,
                               ChallengeRepository challengeRepository) {
        this.accountRepository = accountRepository;
        this.previewRepository = previewRepository;
        this.challengeRepository = challengeRepository;
    }

    /**
     * Uploads an avatar for a user of type Challenger.
     *
     * @param file MultipartFile containing the avatar file data to be uploaded
     * @return UploadAvatarResponse containing the public URL of the uploaded avatar
     */
    @Override
    public UploadAvatarResponse uploadChallengerAvatar(MultipartFile file) {
        AccountEntity accountEntity = getCurrentUserAccount();
        return uploadAvatar(file, accountEntity, "avatars/challenger");
    }

    /**
     * Uploads an avatar for a user of type Manager.
     *
     * @param file MultipartFile containing the avatar file data to be uploaded
     * @return UploadAvatarResponse containing the public URL of the uploaded avatar
     */
    @Override public UploadAvatarResponse uploadManagerAvatar(MultipartFile file) {
        AccountEntity accountEntity = getCurrentUserAccount();
        return uploadAvatar(file, accountEntity, "avatars/manager");
    }

    /**
     * Uploads an avatar for a user of type Mentor.
     *
     * @param file MultipartFile containing the avatar file data to be uploaded
     * @return UploadAvatarResponse containing the public URL of the uploaded avatar
     */
    @Override public UploadAvatarResponse uploadMentorAvatar(MultipartFile file) {
        AccountEntity accountEntity = getCurrentUserAccount();
        return uploadAvatar(file, accountEntity, "avatars/mentor");
    }

    /**
     * Uploads an avatar for a user of type Admin.
     *
     * @param file MultipartFile containing the avatar file data to be uploaded
     * @return UploadAvatarResponse containing the public URL of the uploaded avatar
     */
    @Override public UploadAvatarResponse uploadAdminAvatar(MultipartFile file) {
        AccountEntity accountEntity = getCurrentUserAccount();
        return uploadAvatar(file, accountEntity, "avatars/admin");
    }

    /**
     * Uploads an avatar for a user of type Recruiter.
     *
     * @param file MultipartFile containing the avatar file data to be uploaded
     * @return UploadAvatarResponse containing the public URL of the uploaded avatar
     */
    @Override public UploadAvatarResponse uploadRecruiterAvatar(MultipartFile file) {
        AccountEntity accountEntity = getCurrentUserAccount();
        return uploadAvatar(file, accountEntity, "avatars/recruiter");
    }

    /**
     * Uploads an design image for a user of challege.
     * @param label for know what is this.
     * @param challengeId challenge Id where store to display design image.
     * @param file MultipartFile containing the design image file data to be uploaded.
     * @return
     */
    @Override
    public DesignImageResponse uploadDesignImage(String label, String challengeId, MultipartFile file) {
        DesignImageResponse response = new DesignImageResponse();

        // Retrieve the ChallengeEntity
        ChallengeEntity challengeEntity = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        previewRepository.findByChallengeAndLabel(challengeEntity, label).ifPresent(existingPreview -> {
            throw new AppException(ErrorCode.DUPLICATE_PREVIEW_EXCEPTION);
        });
        PreviewEntity previewEntity = new PreviewEntity();

        try {
            // If an existing URL is present, delete the old file in Firebase
            if (previewEntity.getUrl() != null) {
                String oldUrl = previewEntity.getUrl();
                String oldFileName = oldUrl.substring(oldUrl.indexOf("/o/") + 3, oldUrl.indexOf("?alt=media"));
                oldFileName = oldFileName.replace("%2F", "/");

                Bucket bucket = StorageClient.getInstance().bucket();
                Blob oldBlob = bucket.get(oldFileName);
                if (oldBlob != null) {
                    oldBlob.delete();
                }
            }

            // Generate a new file name and token
            String fileName = UUID.randomUUID() + "-" + label + "-" + file.getOriginalFilename();
            String downloadToken = UUID.randomUUID().toString();

            // Upload the new file to Firebase
            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.create("design-image/" + fileName, file.getBytes(), file.getContentType(),
                            Bucket.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ))
                    .toBuilder()
                    .setMetadata(Collections.singletonMap("firebaseStorageDownloadTokens", downloadToken))
                    .build()
                    .update();

            // Generate the new URL
            String url = String.format(
                    "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
                    bucket.getName(),
                    blob.getName().replace("/", "%2F"),
                    downloadToken
            );

            // Update the PreviewEntity with the new URL and details
            previewEntity.setUrl(url);
            previewEntity.setLabel(label);
            previewEntity.setChallenge(challengeEntity);
            previewRepository.save(previewEntity);

            // Set the response details
            response.setId(previewEntity.getId());
            response.setImageUrl(url);
            response.setLabel(label);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
    }


    /**
     * Uploads an avatar for a user account, replacing the old image if it exists.
     *
     * @param file         MultipartFile containing the avatar file data to be uploaded
     * @param accountEntity The AccountEntity of the current user
     * @param folderName   The folder where the image will be stored on Firebase Storage (e.g., "avatars/challenger")
     * @return UploadAvatarResponse containing the public URL of the uploaded avatar
     * @throws AppException if any error occurs during file upload
     */
    public UploadAvatarResponse uploadAvatar(MultipartFile file, AccountEntity accountEntity, String folderName) {
        UploadAvatarResponse uploadAvatarResponse = new UploadAvatarResponse();
        try {
            if (accountEntity.getAvatar() != null) {
                String oldAvatarUrl = accountEntity.getAvatar();
                String oldFileName = oldAvatarUrl.substring(oldAvatarUrl.indexOf("/o/") + 3, oldAvatarUrl.indexOf("?alt=media"));
                oldFileName = oldFileName.replace("%2F", "/");

                Bucket bucket = StorageClient.getInstance().bucket();
                Blob oldBlob = bucket.get(oldFileName);
                if (oldBlob != null) {
                    oldBlob.delete();
                }
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Bucket bucket = StorageClient.getInstance().bucket();
            String downloadToken = UUID.randomUUID().toString();

            Blob blob = bucket.create(folderName + "/" + fileName, file.getBytes(), file.getContentType(),
                            Bucket.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ))
                    .toBuilder()
                    .setMetadata(Collections.singletonMap("firebaseStorageDownloadTokens", downloadToken))
                    .build()
                    .update();

            String url = String.format(
                    "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
                    bucket.getName(),
                    blob.getName().replace("/", "%2F"),
                    downloadToken
            );

            uploadAvatarResponse.setUrl(url);
            accountEntity.setAvatar(url);
            accountRepository.save(accountEntity);

            return uploadAvatarResponse;

        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.PHOTO_UPLOAD_FAILED);
        }
    }

    /**
     * Retrieves the AccountEntity of the current user based on the login token.
     *
     * @return AccountEntity containing the user account information
     * @throws AppException if the user does not exist or the token is invalid
     */
    private AccountEntity getCurrentUserAccount() {
        String email = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_JWT_TOKEN));

        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
    }


}
