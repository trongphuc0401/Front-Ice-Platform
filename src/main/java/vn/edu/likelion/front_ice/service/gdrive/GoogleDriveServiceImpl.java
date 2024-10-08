package vn.edu.likelion.front_ice.service.gdrive;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.repository.AccountRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

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

    private static String SERVIEC_ACCOUNT_KEY_PATH = getPathToGoodleCredentials();
    private final AccountRepository accountRepository;

    public GoogleDriveServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private static String getPathToGoodleCredentials() {

        String currentDirectory = System.getProperty("user.dir");
        Path filePath= Paths.get(currentDirectory, "credentials.json");
        return filePath.toString();
    }


    @Override public UploadAvatarResponse uploadChallengerAvatar(String accountChallengerId,File file) {

        UploadAvatarResponse response = new UploadAvatarResponse();

        AccountEntity accountEntity = accountRepository.findById(accountChallengerId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        try {
            String folderId = "1dD24z_IAgyaY7bPbCPlx2m23jyQXELaQ";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            // Đặt quyền chia sẻ công khai cho tệp
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();
            file.delete();
            response.setUrl(imageUrl);
            accountEntity.setAvatar(imageUrl);
            accountRepository.save(accountEntity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return response;
    }

    @Override public UploadAvatarResponse uploadManagerAvatar(String accountChallengerId, File file) {
        UploadAvatarResponse response = new UploadAvatarResponse();

        AccountEntity accountEntity = accountRepository.findById(accountChallengerId)
                .orElseThrow(() -> new AppException(ErrorCode.MANAGER_NOT_EXIST));

        try {
            String folderId = "1vpYYN0SNilcKW89MF63ABvFr80HFQlyv";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            if (!file.getName().endsWith(".jpg") && !file.getName().endsWith(".jpeg")) {
                throw new AppException(ErrorCode.INVALID_IMAGE_FORMAT); // Ném lỗi định dạng ảnh không hợp lệ
            }
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            // Đặt quyền chia sẻ công khai cho tệp
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();
            file.delete();
            response.setUrl(imageUrl);
            accountEntity.setAvatar(imageUrl);
            accountRepository.save(accountEntity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return response;
    }

    @Override public UploadAvatarResponse uploadMentorAvatar(String accountChallengerId, File file) {
        UploadAvatarResponse response = new UploadAvatarResponse();

        AccountEntity accountEntity = accountRepository.findById(accountChallengerId)
                .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXIST));

        try {
            String folderId = "1H2NC6sEKLrFTMbA1GQjrUoHTdjS1gJEo";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            // Đặt quyền chia sẻ công khai cho tệp
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();
            file.delete();
            response.setUrl(imageUrl);
            accountEntity.setAvatar(imageUrl);
            accountRepository.save(accountEntity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return response;
    }

    @Override public UploadAvatarResponse uploadAdminAvatar(String accountChallengerId, File file) {

        // sprint 4 sẽ làm cái này
        return null;
    }

    @Override public UploadAvatarResponse uploadRecruiterAvatar(String accountChallengerId, File file)  {
        UploadAvatarResponse response = new UploadAvatarResponse();

        AccountEntity accountEntity = accountRepository.findById(accountChallengerId)
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_EXIST));

        try {
            String folderId = "1YysRUafhz5seAY9Oa_LzCATYQyA1ZXcn";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            // Đặt quyền chia sẻ công khai cho tệp
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();
            file.delete();
            response.setUrl(imageUrl);
            accountEntity.setAvatar(imageUrl);
            accountRepository.save(accountEntity);
        } catch (IOException | GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @Override
    public UploadAvatarResponse uploadCV(String accountChallengerId, File file) {
        UploadAvatarResponse response = new UploadAvatarResponse();

        // Kiểm tra xem tài khoản có tồn tại hay không
        AccountEntity accountEntity = accountRepository.findById(accountChallengerId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        // Kiểm tra file PDF

        try {
            // Khởi tạo Google Drive API
            String folderId = "1sM4AJtU45u3Mg2X9Z0ZZozHXv7aNXIyi";
            Drive drive = createDriveService();

            // Tạo metadata cho file
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            // Tạo FileContent với định dạng PDF
            FileContent mediaContent = new FileContent("application/pdf", file);

            // Upload file lên Google Drive
            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            // Tạo URL cho file PDF
            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("FILE URL: " + fileUrl);

            // Đặt quyền chia sẻ công khai cho file
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            // Xóa file tạm sau khi upload thành công
            file.delete();

            // Cập nhật URL cho file CV
            response.setUrl(fileUrl);
            // accountEntity.setCvUrl(fileUrl);  // Giả sử bạn có trường để lưu URL CV
            accountRepository.save(accountEntity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppException(ErrorCode.CV_UPLOAD_FAILED); // Ném lỗi nếu có lỗi xảy ra trong quá trình upload
        }

        return response;
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

}
