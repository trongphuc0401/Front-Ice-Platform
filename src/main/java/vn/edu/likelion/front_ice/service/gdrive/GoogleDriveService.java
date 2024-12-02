package vn.edu.likelion.front_ice.service.gdrive;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.dto.response.resource.AssetsResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.DesignImageResponse;
import vn.edu.likelion.front_ice.dto.response.resource.DownloadResourceResponse;
import vn.edu.likelion.front_ice.dto.response.resource.FigmaResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.concurrent.CompletableFuture;

/**
 * GoogleDriveService -
 *
 * @param
 * @return
 * @throws
 */
public interface GoogleDriveService{

    UploadAvatarResponse uploadCV(String accountId, File file);

    AssetsResponse uploadAssets(Long challengeId , File file);

    FigmaResponse uploadFigma(Long challengeId, File file);

    DesignImageResponse uploadImageDesktop(File file);

    DesignImageResponse uploadImageMobile(File file);

    DesignImageResponse uploadImageTablet(File tempFile);

    DownloadResourceResponse downloadResource(Long challengeId);

    InputStream downloadAssets(String assetsId) throws IOException, GeneralSecurityException;

    InputStream downloadFigma(String figmaId) throws IOException, GeneralSecurityException;

    CompletableFuture<String> uploadCV(MultipartFile file) throws GeneralSecurityException, IOException;
}
