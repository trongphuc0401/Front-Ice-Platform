package vn.edu.likelion.front_ice.dto.request.challenger;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.front_ice.common.enums.Gender;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * UpdateChallengerRequest -
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
public class UpdateProfileChallengerRequest {


    // image
    MultipartFile banner;
    MultipartFile avatar;

    // profile
    String firstName;
    String lastName;
    String phone;

    Integer gender; // nghiên cứu thêm xem là Integer hay Gender

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Định dạng ISO-8601 cho timestamp
    LocalDateTime birthday; // nghiên cứu thêm
    String bio;


    //url
    String urlGithub;
    MultipartFile urlCV;
    String urlPortfolio;
    String urlCodepen;
    String urlGitLab;
    String urlStackOverflow;
    String urlLinkedIn;


}
