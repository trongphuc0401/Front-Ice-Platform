package vn.edu.likelion.front_ice.service.staff;

import vn.edu.likelion.front_ice.dto.response.*;
import vn.edu.likelion.front_ice.entity.StaffEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

public interface StaffService extends BaseService<StaffEntity, RegisterResponse, RecruiterResponse> {
    Optional<StaffResponse> getDetailsProfile(String accountId);
}
