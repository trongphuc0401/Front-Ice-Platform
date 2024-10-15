package vn.edu.likelion.front_ice.service.staff;


import vn.edu.likelion.front_ice.dto.request.account.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.recruiter.RecruiterResponse;
import vn.edu.likelion.front_ice.dto.response.staff.StaffResponse;
import vn.edu.likelion.front_ice.entity.StaffEntity;
import vn.edu.likelion.front_ice.service.BaseService;

import java.util.Optional;

public interface StaffService extends BaseService<StaffEntity, RegisterRequest, RegisterRequest> {
    Optional<StaffResponse> getDetailsProfile(String accountId);
}
