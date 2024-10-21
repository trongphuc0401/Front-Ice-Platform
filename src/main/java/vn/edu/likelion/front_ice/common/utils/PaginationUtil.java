package vn.edu.likelion.front_ice.common.utils;

import org.springframework.data.domain.Page;
import vn.edu.likelion.front_ice.dto.response.challenge.ResultPaginationResponse;

public class PaginationUtil {
    /**
     * Create Meta for result pagination
     *
     * @param page Page .
     * @return Meta .
     */
    public static ResultPaginationResponse.Meta createPaginationMeta(Page<?> page) {
        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPageNo(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setTotalElements((int) page.getTotalElements());
        meta.setTotalPages(page.getTotalPages());
        return meta;
    }
}
