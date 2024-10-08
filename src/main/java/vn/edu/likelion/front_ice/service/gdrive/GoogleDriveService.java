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

    UploadAvatarResponse uploadChallengerAvatar(String accountChallengerId,File file);

    UploadAvatarResponse uploadManagerAvatar(String accountChallengerId,File file);

    UploadAvatarResponse uploadMentorAvatar(String accountChallengerId,File file);

    UploadAvatarResponse uploadAdminAvatar(String accountChallengerId,File file);

    UploadAvatarResponse uploadRecruiterAvatar(String accountChallengerId,File file);
}
