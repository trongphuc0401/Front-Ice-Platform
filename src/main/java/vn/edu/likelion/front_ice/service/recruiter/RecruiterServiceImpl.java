package vn.edu.likelion.front_ice.service.recruiter;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.follow.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.follow.FollowResponse;
import vn.edu.likelion.front_ice.dto.response.recruiter.RecruiterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.mapper.RecruiterMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.RecruiterRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.util.List;
import java.util.Optional;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    private final AccountRepository accountRepository;
    private final RecruiterRepository recruiterRepository;
    private final RecruiterMapper recruiterMapper;

    public RecruiterServiceImpl(AccountRepository accountRepository, RecruiterRepository recruiterRepository, RecruiterMapper recruiterMapper) {
        this.accountRepository = accountRepository;
        this.recruiterRepository = recruiterRepository;
        this.recruiterMapper = recruiterMapper;
    }

    @Override
    public Optional<RecruiterResponse> getDetailsProfile(String accessToken) {

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        AccountEntity account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        RecruiterEntity recruiter = recruiterRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        return Optional.of(recruiterMapper.toRecruiterResponse(account, recruiter));
    }

    @Override
    public Optional<RecruiterEntity> create(FollowRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<RecruiterEntity> updateInfo(String id, FollowRequest i) {
        return Optional.empty();
    }

    @Override
    public List<RecruiterEntity> saveAll(List<RecruiterEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public RecruiterEntity findById(String id) {
        return null;
    }

    @Override
    public List<RecruiterEntity> findAll() {
        return List.of();
    }
}
