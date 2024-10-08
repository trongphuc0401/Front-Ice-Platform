package vn.edu.likelion.front_ice.service.staff;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.account.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.recruiter.RecruiterResponse;
import vn.edu.likelion.front_ice.dto.response.account.RegisterResponse;
import vn.edu.likelion.front_ice.dto.response.staff.StaffResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.StaffEntity;
import vn.edu.likelion.front_ice.mapper.StaffMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.StaffRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    private final AccountRepository accountRepository;
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    public StaffServiceImpl(AccountRepository accountRepository, StaffRepository staffRepository, StaffMapper staffMapper) {
        this.accountRepository = accountRepository;
        this.staffRepository = staffRepository;
        this.staffMapper = staffMapper;
    }

    @Override public Optional<RecruiterResponse> create(RegisterRequest t) {
        return Optional.empty();
    }

    @Override public Optional<RecruiterResponse> updateInfo(String id, RegisterRequest i) {
        return Optional.empty();
    }

    @Override
    public List<RecruiterResponse> saveAll(List<StaffEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public Optional<RecruiterResponse> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<RecruiterResponse> findAll() {
        return List.of();
    }

    @Override
    public Optional<StaffResponse> getDetailsProfile(String accountId) {

        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        StaffEntity staff = staffRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        return Optional.of(staffMapper.toStaffResponse(account, staff));
    }
}
