package vn.edu.likelion.front_ice.service.firebase;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.dto.response.UploadAvatarResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.DesignImageResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;

import java.io.File;
import java.io.IOException;

/**
 * FirebaseService -
 *
 * @param
 * @return
 * @throws
 */
public interface FirebaseService {
    UploadAvatarResponse uploadChallengerAvatar(MultipartFile file);

    UploadAvatarResponse uploadManagerAvatar(MultipartFile file);

    UploadAvatarResponse uploadMentorAvatar(MultipartFile file);

    UploadAvatarResponse uploadAdminAvatar(MultipartFile file);

    UploadAvatarResponse uploadRecruiterAvatar(MultipartFile file);

    DesignImageResponse uploadDesignImage(String label,String challengeId,MultipartFile file);
}
