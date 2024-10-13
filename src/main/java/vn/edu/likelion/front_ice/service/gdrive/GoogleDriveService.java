package vn.edu.likelion.front_ice.service.gdrive;

import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.AssetsResponse;

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

    UploadAvatarResponse uploadCV(String accountId, File tempFile);

    AssetsResponse uploadAssets(String challengeId , File file);


}
