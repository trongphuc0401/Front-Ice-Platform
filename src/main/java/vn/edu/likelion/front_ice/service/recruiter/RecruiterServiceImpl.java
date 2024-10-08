package vn.edu.likelion.front_ice.service.recruiter;

import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.FollowResponse;
import vn.edu.likelion.front_ice.dto.response.RecruiterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.mapper.RecruiterMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.RecruiterRepository;

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
    public Optional<FollowResponse> create(FollowRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<FollowResponse> updateInfo(String id, FollowRequest followRequest) {
        return Optional.empty();
    }

    @Override
    public List<FollowResponse> saveAll(List<RecruiterEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public Optional<FollowResponse> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<FollowResponse> findAll() {
        return List.of();
    }

    @Override
    public Optional<RecruiterResponse> getDetailsProfile(String accountId) {

        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        RecruiterEntity recruiter = recruiterRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        return Optional.of(recruiterMapper.toRecruiterResponse(account, recruiter));
    }
}
