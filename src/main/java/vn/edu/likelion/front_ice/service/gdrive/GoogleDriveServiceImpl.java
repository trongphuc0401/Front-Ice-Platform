package vn.edu.likelion.front_ice.service.gdrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.utils.HelperUtil;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.dto.response.resource.AssetsResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.DesignImageResponse;
import vn.edu.likelion.front_ice.dto.response.resource.DownloadResourceResponse;
import vn.edu.likelion.front_ice.dto.response.resource.FigmaResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ResourceMapper;
import vn.edu.likelion.front_ice.projection.challenge.TypeChallengeProjection;
import vn.edu.likelion.front_ice.projection.challenger.IsPremiumProjection;
import vn.edu.likelion.front_ice.projection.resource.AssetsUrlProjection;
import vn.edu.likelion.front_ice.projection.resource.FigmaUrlProjection;
import vn.edu.likelion.front_ice.repository.*;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
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
    private final ResourceMapper resourceMapper;


    public GoogleDriveServiceImpl(AccountRepository accountRepository, ChallengerRepository challengerRepository,
                                  ChallengeRepository challengeRepository, ResourceRepository resourceRepository,
                                  SecurityUtil securityUtil, PreviewRepository previewRepository,
                                  ResourceMapper resourceMapper) {
        this.accountRepository = accountRepository;
        this.challengerRepository = challengerRepository;
        this.challengeRepository = challengeRepository;
        this.resourceRepository = resourceRepository;
        this.securityUtil = securityUtil;
        this.previewRepository = previewRepository;
        this.resourceMapper = resourceMapper;
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

    private String uploadFileToDrive(MultipartFile file, String folderId, String newFileName) throws IOException, GeneralSecurityException {
        // Tạo dịch vụ Google Drive
        Drive drive = createDriveService();

        // Lưu file tạm thời với tên mới
        java.io.File tempFile = java.io.File.createTempFile("temp-", "-" + newFileName);
        file.transferTo(tempFile);

        // Đổi tên file
        java.io.File renamedFile = new java.io.File(tempFile.getParent(), newFileName);
        if (!tempFile.renameTo(renamedFile)) {
            throw new IOException("Failed to rename file to " + newFileName);
        }

        try {
            // Metadata cho file Drive
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(newFileName);
            fileMetaData.setParents(Collections.singletonList(folderId));

            // Nội dung file
            FileContent mediaContent = new FileContent(file.getContentType(), renamedFile);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();

            // Gán quyền truy cập công khai
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            // Trả về URL file trên Google Drive
            return "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
        } finally {
            // Đảm bảo xóa file tạm ngay cả khi có lỗi
            renamedFile.delete();
        }
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

   @Override
   @Async
    public CompletableFuture<String> uploadCV(MultipartFile file) throws GeneralSecurityException, IOException {

        String folderId = "1sM4AJtU45u3Mg2X9Z0ZZozHXv7aNXIyi";
        String fileName = file.getOriginalFilename();

        return CompletableFuture.completedFuture(uploadFileToDrive(file, folderId, fileName));
    }


    @Override
    public AssetsResponse uploadAssets(Long challengeId, File file) {

        if (file == null || !file.exists() || file.length() == 0) {
            throw new AppException(ErrorCode.ASSETS_UPLOAD_FAILED);
        }

        AssetsResponse response = new AssetsResponse();

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        try {

            String folderId = "1CUsNkMqDiH04F71zGUbdtgUosxPSQBpw";

            Drive drive = createDriveService();

            String originalFileName = file.getName();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(originalFileName);
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent("application/zip", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id,size")
                    .execute();

            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            long fileSize = uploadedFile.getSize();

            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            if (!file.delete()) {
                throw new AppException(ErrorCode.DELETE_FAILED);
            }

            String assetsId = HelperUtil.getThreadLocalRandomId();

            response.setAssetsId(assetsId);
            response.setAssetsName(originalFileName);
            response.setAssetsSize(fileSize);

            resourceEntity.setAssetsId(assetsId);
            resourceEntity.setAssetsUrl(fileUrl);
            resourceEntity.setAssetsName(originalFileName);
            resourceEntity.setAssetsSize(fileSize);



            resourceRepository.save(resourceEntity);

        } catch (IOException e) {
            throw new AppException(ErrorCode.GOOGLE_DRIVE_ERROR);
        } catch (GeneralSecurityException e) {
            throw new AppException(ErrorCode.GENERAL_SECURITY_ERROR);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return response;
    }

    @Override
    public FigmaResponse uploadFigma(Long challengeId, File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            throw new AppException(ErrorCode.ASSETS_UPLOAD_FAILED);
        }

        FigmaResponse response = new FigmaResponse();
        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        try {
            String folderId = "1TGDETb1gH0JACUMCbuXYHISKJerodAki";
            Drive drive = createDriveService();

            String originalFileName = file.getName();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(originalFileName);
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent("application/zip", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id,size")
                    .execute();

            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            long fileSize = uploadedFile.getSize();

            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            if (!file.delete()) {
                throw new AppException(ErrorCode.DELETE_FAILED);
            }

            String figmaId = HelperUtil.getThreadLocalRandomId();

            response.setFigmaId(figmaId);
            response.setFigmaName(originalFileName);
            response.setFigmaSize(fileSize);

            resourceEntity.setFigmaId(figmaId);
            resourceEntity.setFigmaUrl(fileUrl);
            resourceEntity.setFigmaName(originalFileName);
            resourceEntity.setFigmaSize(fileSize);


            resourceRepository.save(resourceEntity);

        } catch (IOException e) {
            throw new AppException(ErrorCode.GOOGLE_DRIVE_ERROR);
        } catch (GeneralSecurityException e) {
            throw new AppException(ErrorCode.GENERAL_SECURITY_ERROR);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return response;
    }

    public InputStream downloadAssets(String assetsId) throws IOException, GeneralSecurityException {

        String assetsUrl = resourceRepository.findAssetsUrlByAssetsId(assetsId)
                .map(AssetsUrlProjection::getAssetsUrl)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_EXIST));

        String fileId = assetsUrl.replace("https://drive.google.com/uc?export=view&id=", "");

        Drive drive = createDriveService();

        InputStream inputStream;
        inputStream = drive.files().get(fileId).executeMediaAsInputStream();

        return inputStream;
    }

    public InputStream downloadFigma(String figmaId) throws IOException, GeneralSecurityException {

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_EXIST));


        boolean isPremium = challengerRepository.findIsPremiumProjectionByAccountEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));


        TypeChallenge typeChallenge = challengeRepository.findTypeChallengeByFigmaId(figmaId)
                .map(TypeChallengeProjection::getTypeChallenge)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        boolean isAllowedChallenge = typeChallenge == TypeChallenge.PREMIUM && isPremium
                || typeChallenge == TypeChallenge.FREE_PLUS_PLUS;

        if (!isAllowedChallenge) {
            throw new AppException(ErrorCode.CHALLENGER_AND_CHALLENGE_NOT_PREMIUM);
        }


        String figmaUrl = resourceRepository.findFigmaUrlByFigmaId(figmaId)
                .map(FigmaUrlProjection::getFigmaUrl)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_EXIST));

        String fileId = figmaUrl.replace("https://drive.google.com/uc?export=view&id=", "");

        Drive drive = createDriveService();

        InputStream inputStream;
        inputStream = drive.files().get(fileId).executeMediaAsInputStream();

        return inputStream;
    }

    @Override public DownloadResourceResponse downloadResource(Long challengeId) {

       String email =  SecurityUtil.getCurrentUserLogin().orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        if (!challengerRepository.checkChallengeIsJoined(email, challengeId)) {
            throw new AppException(ErrorCode.CHALLENGER_NOT_JOIN_CHALLENGE);
        }

        DownloadResourceResponse downloadResourceResponse = new DownloadResourceResponse();

        ResourceEntity resourceEntity = resourceRepository.findByChallengeId(challengeId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_EXIST)
        );
        AssetsResponse assetsResponse = resourceMapper.toAssetsResponse(resourceEntity);
        FigmaResponse figmaResponse = resourceMapper.toFigmaResponse(resourceEntity);

        downloadResourceResponse.setAssets(assetsResponse);
        downloadResourceResponse.setFigma(figmaResponse);

        return downloadResourceResponse;
    }
}
