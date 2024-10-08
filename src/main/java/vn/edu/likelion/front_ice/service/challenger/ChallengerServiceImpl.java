package vn.edu.likelion.front_ice.service.challenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.FollowRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.CreationChallengerRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.FollowResponse;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.FollowEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.FollowRepository;
import vn.edu.likelion.front_ice.repository.RecruiterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengerServiceImpl implements ChallengerService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private ChallengerRepository challengerRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override public Optional<ChallengerResponse> create(CreationChallengerRequest t) {
        return Optional.empty();
    }

    @Override public Optional<ChallengerResponse> updateInfo(String id, UpdateChallengerRequest i) {
        return Optional.empty();
    }

    @Override public List<ChallengerResponse> saveAll(List<ChallengerEntity> ts) {
        return List.of();
    }

    @Override public void delete(String id) {

    }

    @Override public void deleteAll(List<String> listId) {

    }

    @Override public Optional<ChallengerResponse> findById(String id) {
        return Optional.empty();
    }

    @Override public List<ChallengerResponse> findAll() {
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



}
