package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginateChallengeResponse {
    List<ChallengeResponse> results;
    int pageNo;
    int pageSize;
    int totalElements;
    int totalPages;
}
