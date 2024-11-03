package vn.edu.likelion.front_ice.dto.response.solution;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;

import java.util.List;

/**
 * PaginatedSolutionChallengerResponse -
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
public class PaginateSolutionChallengerResponse {
    List<SolutionChallengerResponse> results;
    int pageNo;
    int pageSize;
    int totalElements;
    int totalPages;
}
