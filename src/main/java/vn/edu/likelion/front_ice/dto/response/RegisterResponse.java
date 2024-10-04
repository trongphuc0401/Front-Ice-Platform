package vn.edu.likelion.front_ice.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {
    String firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;

}
