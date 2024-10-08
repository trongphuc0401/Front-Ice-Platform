package vn.edu.likelion.front_ice.service.gdrive;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;

import java.io.File;

/**
 * GoogleDriveService -
 *
 * @param
 * @return
 * @throws
 */
public interface GoogleDriveService{

    UploadAvatarResponse uploadAvatar(String accountChallengerId,File file);
}
