package vn.edu.likelion.front_ice.dto.response.challenge;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultPaginationResponse {
    Meta meta;
    Object result;

    @Getter
    @Setter
    public static class Meta {
        int pageNo;
        int pageSize;
        int totalElements;
        int totalPages;
    }
}
