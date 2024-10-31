package vn.edu.likelion.front_ice.service.gdrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.AssetsResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.DesignImageResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.repository.*;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.function.Predicate;

/**
 * GoogleDriveServiceImpl -
 *
 * @param
 * @return
 * @throws
 */
@Service
public class GoogleDriveServiceImpl implements GoogleDriveService{

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static String SERVIEC_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();
    private final AccountRepository accountRepository;
    private final ChallengerRepository challengerRepository;
    private final ChallengeRepository challengeRepository;
    private final ResourceRepository resourceRepository;
    private final SecurityUtil securityUtil;
    private final PreviewRepository previewRepository;


    public GoogleDriveServiceImpl(AccountRepository accountRepository, ChallengerRepository challengerRepository,
                                  ChallengeRepository challengeRepository, ResourceRepository resourceRepository,
                                  SecurityUtil securityUtil, PreviewRepository previewRepository) {
        this.accountRepository = accountRepository;
        this.challengerRepository = challengerRepository;
        this.challengeRepository = challengeRepository;
        this.resourceRepository = resourceRepository;
        this.securityUtil = securityUtil;
        this.previewRepository = previewRepository;
    }

    private static String getPathToGoogleCredentials() {

        String currentDirectory = System.getProperty("user.dir");
        Path filePath= Paths.get(currentDirectory, "credentials.json");
        return filePath.toString();
    }

    private Drive createDriveService() throws GeneralSecurityException,IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVIEC_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }

    private DesignImageResponse uploadDesignImage( File file, String folderId, ErrorCode errorCode, String label) {
        DesignImageResponse response = new DesignImageResponse();

        PreviewEntity previewEntity = new PreviewEntity();

        try {
            Drive drive = createDriveService();
            String newFileName = System.currentTimeMillis() + ".jpeg";

            File renamedFile = new File(file.getParent(), newFileName);
            if (!file.renameTo(renamedFile)) {
                throw new IOException("Failed to rename file to " + newFileName);
            }

            // Chuẩn bị metadata cho file
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(newFileName);
            fileMetaData.setParents(Collections.singletonList(folderId));

            // Tạo FileContent với loại MIME là image/jpeg
            FileContent mediaContent = new FileContent("image/jpeg", renamedFile);

            // Tải file lên Google Drive
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();

            // Tạo URL công khai cho hình ảnh
            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);

            // Đặt quyền chia sẻ công khai cho tệp
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            // Xóa tệp cục bộ sau khi tải lên thành công
            renamedFile.delete();

            // Cập nhật URL vào AccountEntity và lưu lại
            previewEntity.setUrl(imageUrl);
            previewEntity.setLabel(label);
            previewRepository.save(previewEntity);
            response.setId(previewEntity.getId());
            response.setImageUrl(imageUrl);
            response.setLabel(label);


        } catch (IOException | GeneralSecurityException e) {
            System.err.println("Error uploading image: " + e.getMessage());
            throw new AppException(errorCode);
        }

        return response;
    }

    @Override public DesignImageResponse uploadImageDesktop(File file) {
        String folderId = "1Dd-E2PmIpnrtLgcJLs9ck3xncNUGuZdl";
        return uploadDesignImage(file, folderId,ErrorCode.IMAGE_DESKTOP,"desktop design");
    }
    @Override public DesignImageResponse uploadImageMobile(File file) {
        String folderId = "1OYmbANIPhLoaCnCs7NeYd0-zLXQbB0vm";
        return uploadDesignImage(file, folderId,ErrorCode.IMAGE_MOBILE,"mobile design");
    }
    @Override public DesignImageResponse uploadImageTablet(File file) {
        String folderId = "1Z-RIvXxqzM6Bk0wf7HLjFlR7joqLLXAF";
        return uploadDesignImage(file, folderId,ErrorCode.IMAGE_TABLET,"tablet design");
    }

    private String uploadFileToDrive(File file, String folderId, String newFileName) throws IOException, GeneralSecurityException {
        Drive drive = createDriveService();

        File renamedFile = new File(file.getParent(), newFileName);
        if (!file.renameTo(renamedFile)) {
            throw new IOException("Failed to rename file to " + newFileName);
        }

        com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
        fileMetaData.setName(newFileName);
        fileMetaData.setParents(Collections.singletonList(folderId));

        FileContent mediaContent = new FileContent("application/pdf", renamedFile);
        com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                .setFields("id").execute();

        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        drive.permissions().create(uploadedFile.getId(), permission).execute();

        renamedFile.delete();

        return "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
    }


    @Override
    public UploadAvatarResponse uploadCV(String accessToken , File file) {
        UploadAvatarResponse response = new UploadAvatarResponse();

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challengerEntity = challengerRepository.findByAccountEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        AccountEntity accountEntity = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        try {
            String folderId = "1sM4AJtU45u3Mg2X9Z0ZZozHXv7aNXIyi";
            String newFileName = "CV_" + accountEntity.getFirstName() + "_" + accountEntity.getLastName() + "_" + System.currentTimeMillis() + ".pdf";

            String fileUrl = uploadFileToDrive(file, folderId, newFileName);
            System.out.println("FILE URL: " + fileUrl);

            response.setUrl(fileUrl);
            challengerEntity.setUrlCV(fileUrl);
            challengerRepository.save(challengerEntity);

        } catch (IOException | GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }

        return response;
    }


    @Override public AssetsResponse uploadAssets(Long challengeId, File file) {

        AssetsResponse response = new AssetsResponse();

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        try {
            // ID của thư mục trên Google Drive
            String folderId = "1CUsNkMqDiH04F71zGUbdtgUosxPSQBpw";

            // Tạo dịch vụ Google Drive
            Drive drive = createDriveService();

            // Giữ nguyên tên file gốc
            String originalFileName = file.getName();

            // Chuẩn bị metadata cho file
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(originalFileName); // Giữ nguyên tên gốc
            fileMetaData.setParents(Collections.singletonList(folderId));

            // Định nghĩa file content với loại file là zip
            FileContent mediaContent = new FileContent("application/zip", file);

            // Upload file lên Google Drive
            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id,size")
                    .execute();

            // Tạo link trực tiếp đến file trên Google Drive
            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("File URL: " + fileUrl);

            long fileSize = uploadedFile.getSize(); // Lấy kích thước file (bytes)
            System.out.println("File Size: " + fileSize + " bytes");

            // Đặt quyền chia sẻ công khai cho file
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            // Xóa file cục bộ sau khi upload thành công (nếu cần)
            file.delete();
            response.setAssetsUrl(fileUrl);
            response.setAssetsName(originalFileName);
            response.setAssetsSize(fileSize);
            resourceEntity.setAssetsUrl(fileUrl);
            resourceEntity.setAssetsName(originalFileName);
            resourceEntity.setAssetsSize(fileSize);
            resourceRepository.save(resourceEntity);

            // Thiết lập URL vào response
        } catch (IOException | GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @Override public AssetsResponse uploadFigma(Long challengeId, File file) {
        AssetsResponse response = new AssetsResponse();

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        try {
            // ID của thư mục trên Google Drive
            String folderId = "1TGDETb1gH0JACUMCbuXYHISKJerodAki";

            // Tạo dịch vụ Google Drive
            Drive drive = createDriveService();

            // Giữ nguyên tên file gốc
            String originalFileName = file.getName();

            // Chuẩn bị metadata cho file
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(originalFileName); // Giữ nguyên tên gốc
            fileMetaData.setParents(Collections.singletonList(folderId));

            // Định nghĩa file content với loại file là zip
            FileContent mediaContent = new FileContent("application/zip", file);

            // Upload file lên Google Drive
            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id,size")
                    .execute();

            // Tạo link trực tiếp đến file trên Google Drive
            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("File URL: " + fileUrl);

            long fileSize = uploadedFile.getSize(); // Lấy kích thước file (bytes)
            System.out.println("File Size: " + fileSize + " bytes");

            // Đặt quyền chia sẻ công khai cho file
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            // Xóa file cục bộ sau khi upload thành công (nếu cần)
            file.delete();
            response.setAssetsUrl(fileUrl);
            response.setAssetsName(originalFileName);
            response.setAssetsSize(fileSize);
            resourceEntity.setFigmaUrl(fileUrl);
            resourceEntity.setFigmaName(originalFileName);
            resourceEntity.setFigmaSize(fileSize);
            resourceRepository.save(resourceEntity);

            // Thiết lập URL vào response
        } catch (IOException | GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }



    public InputStream downloadAssets(Long challengeId) throws IOException, GeneralSecurityException {

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId).orElseThrow(
                () -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        String fileId = resourceEntity.getAssetsUrl().replace("https://drive.google.com/uc?export=view&id=", "");

        Drive drive = createDriveService();

        InputStream inputStream;
        inputStream = drive.files().get(fileId).executeMediaAsInputStream();

        return inputStream;
    }

    public InputStream downloadFigma(Long challengeId) throws IOException, GeneralSecurityException {

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challengerEntity = challengerRepository.findByAccountEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        ChallengeEntity challengeEntity = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));


        Predicate<ChallengeEntity> isAllowedChallenge = challenge ->
        {
            TypeChallenge typeChallenge = challenge.getTypeChallenge();
            return typeChallenge == TypeChallenge.PREMIUM || typeChallenge == TypeChallenge.FREE_PLUS_PLUS;
        };

        if (challengerEntity.isPremium() || !isAllowedChallenge.test(challengeEntity)) {
            throw new AppException(ErrorCode.CHALLENGER_AND_CHALLENGE_NOT_PREMIUM);
        }

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId).orElseThrow(
                () -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));



        String fileId = resourceEntity.getFigmaUrl().replace("https://drive.google.com/uc?export=view&id=", "");

        Drive drive = createDriveService();

        InputStream inputStream;
        inputStream = drive.files().get(fileId).executeMediaAsInputStream();

        return inputStream;
    }
}
