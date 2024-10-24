package vn.edu.likelion.front_ice.dto.request.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.enums.Difficulty;
import vn.edu.likelion.front_ice.common.enums.Level;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;

import java.time.LocalDateTime;

/**
 * CreationChallengeRequest -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateChallengeRequest {


    // challenge

    String title;

    String description;

    LocalDateTime openDate;

    LocalDateTime closeDate;

    boolean isPremium;

    TypeChallenge typeChallenge;

    Double price;

    String message;

    String assets;

    String brief;

    //Technical
    String technicalId;

    // challenge point
    Difficulty difficulty;

    Level level;

    // resource
    MultipartFile assetsFile;

    MultipartFile figmaFile;

    // preview
    MultipartFile previewFile;
    String label;


}
