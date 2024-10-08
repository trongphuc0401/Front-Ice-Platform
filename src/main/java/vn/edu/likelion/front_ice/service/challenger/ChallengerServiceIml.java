package vn.edu.likelion.front_ice.service.challenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.FollowRequest;
import vn.edu.likelion.front_ice.dto.response.ChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.FollowResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ChallengerMapper;
import vn.edu.likelion.front_ice.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengerServiceIml implements ChallengerService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private ChallengerRepository challengerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ChallengerMapper challengerMapper;

    @Override
    public Optional<FollowResponse> create(FollowRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<FollowResponse> updateInfo(String id, FollowRequest followRequest) {
        return Optional.empty();
    }

    @Override
    public List<FollowResponse> saveAll(List<ChallengerEntity> ts) {
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

    public Optional<FollowResponse> follow(FollowRequest t) {
        AccountEntity challenger;
        RecruiterEntity recruiter;

        challenger = accountRepository.findById(
                        challengerRepository.findById(t.getChallengerId())
                                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST))
                                .getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        recruiter = recruiterRepository.findById(t.getRecruiterId())
                .orElseThrow(() -> new AppException(ErrorCode.RECRUITER_NOT_EXIST));

        // check follow
        followRepository.findByChallengerIdAndRecruiterId(t.getChallengerId(), t.getRecruiterId())
//                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST))
                .ifPresent(follow -> {
                    throw new AppException(ErrorCode.CHALLENGER_HAS_FOLLOWED_RECRUITER);
                })
        ;

        FollowEntity followEntity = FollowEntity.builder()
                .challengerId(t.getChallengerId())
                .recruiterId(t.getRecruiterId())
                .build();

        followRepository.save(followEntity);
        FollowResponse response = new FollowResponse(challenger.getFirstName()
                + " " + challenger.getLastName(), recruiter.getName());

        return Optional.of(response);
    }

    public Optional<List<RecruiterEntity>> getFollow(String challengerId) {
        List<RecruiterEntity> listResponse = new ArrayList<>();

        List<FollowEntity> listFollow = followRepository.findByChallengerId(challengerId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        for (FollowEntity followEntity : listFollow) {
            listResponse.add(recruiterRepository.findById(followEntity.getRecruiterId()).get());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ChallengerResponse> getDetailsProfile(String accountId) {

        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challenger = challengerRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        LevelEntity level = Optional.ofNullable(challenger.getLevelId())
                .map(levelId -> levelRepository.findById(levelId)
                        .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXIST)))
                .orElse(null);

        return Optional.of(challengerMapper.toChallengerResponse(account, challenger, level));
    }

}
