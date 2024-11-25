package vn.edu.likelion.front_ice.service.challenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.likelion.front_ice.common.enums.Level;
import vn.edu.likelion.front_ice.common.enums.ScoreAnswer;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.follow.FollowRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.CreateChallengerRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.challenger.NextLevelResponse;
import vn.edu.likelion.front_ice.dto.response.challenger.OverviewResponse;
import vn.edu.likelion.front_ice.dto.response.follow.FollowResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ChallengerMapper;
import vn.edu.likelion.front_ice.projection.challenger.OverviewProjection;
import vn.edu.likelion.front_ice.repository.*;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.FollowEntity;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.FollowRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChallengerServiceImpl implements ChallengerService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private ChallengerRepository challengerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ChallengerMapper challengerMapper;
    @Autowired
    private SolutionRepository solutionRepository;

    @Override
    public Optional<ChallengerEntity> create(CreateChallengerRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<ChallengerEntity> updateInfo(Long id, UpdateChallengerRequest i) {
        return Optional.empty();
    }

    @Override
    public List<ChallengerEntity> saveAll(List<ChallengerEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll(List<Long> listId) {

    }

    @Override
    public ChallengerEntity findById(Long id) {
        return null;
    }

    @Override
    public List<ChallengerEntity> findAll() {
        return List.of();
    }

//    public Optional<FollowResponse> follow(FollowRequest t) {
//        AccountEntity challenger;
//
//        challenger = accountRepository.findById(
//                        challengerRepository.findById(t.getChallengerId())
//                                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST))
//                                .getAccount().getId())
//                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
//
//        // check follow
//        followRepository.findByChallengerIdAndRecruiterId(t.getChallengerId(), t.getRecruiterId())
////                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST))
//                .ifPresent(follow -> {
//                    throw new AppException(ErrorCode.CHALLENGER_HAS_FOLLOWED_RECRUITER);
//                })
//        ;
//
//        FollowEntity followEntity = FollowEntity.builder()
//                .challengerId(t.getChallengerId())
//                .recruiterId(t.getRecruiterId())
//                .build();
//
//        followRepository.save(followEntity);
//        FollowResponse response = new FollowResponse(challenger.getFirstName()
//                + " " + challenger.getLastName(), recruiter.getName());
//
//        return Optional.of(response);
//    }

//    public Optional<List<RecruiterEntity>> getFollow(Long challengerId) {
//        List<RecruiterEntity> listResponse = new ArrayList<>();
//
//        List<FollowEntity> listFollow = followRepository.findByChallengerId(challengerId)
//                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));
//
//        for (FollowEntity followEntity : listFollow) {
//            listResponse.add(recruiterRepository.findById(followEntity.getRecruiterId()).get());
//        }
//        return Optional.empty();
//    }

    @Override public Optional<ChallengerResponse> getDetailsProfile(String accessToken) {
        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challenger = accountRepository.findChallengerByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST)).getChallenger();
        upLevel(challenger,challenger.getLevelId());

            LevelEntity level = Optional.ofNullable(challenger.getLevelId())
                    .map(levelId -> levelRepository.findById(levelId)
                            .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXIST)))
                    .orElse(null);

        ChallengerResponse response = challengerMapper.toChallengerResponse(challenger.getAccount(),challenger,level);
        response.setIsPremium(challengerRepository.findIsPremiumProjectionByAccountEmail(email).orElse(false));

        // sau khi làm xong submit thì hãy bỏ này vào
        // challenger.setTotalJoinedChallenge(solutionRepository
        //         .findByChallengerAndIsJoined(challenger, true).size());
        // challenger.setTotalSubmittedChallenge(solutionRepository
        //         .findByChallengerAndIsSubmitted(challenger, true).size());

        // challengerRepository.save(challenger);

            return Optional.of(response);
    }

    @Transactional
    @Override public Optional<OverviewResponse> getOverviewProfile() {

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        OverviewProjection account = accountRepository.findOverviewByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challenger = challengerRepository.findByAccount_Id(account.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        upLevel(challenger,challenger.getLevelId());

        LevelEntity level = Optional.ofNullable(challenger.getLevelId())
                    .map(levelId -> levelRepository.findById(levelId)
                            .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXIST)))
                    .orElse(null);

        // sau khi làm xong submit thì hãy bỏ này vào
        // challenger.setTotalJoinedChallenge(solutionRepository
        //         .findByChallengerAndIsJoined(challenger, true).size());
        // challenger.setTotalSubmittedChallenge(solutionRepository
        //         .findByChallengerAndIsSubmitted(challenger, true).size());
        //     challengerRepository.save(challenger);

        OverviewResponse response = challengerMapper.toOverviewResponse(account,level);
        response.setIsPremium(challengerRepository.findIsPremiumProjectionByAccountEmail(email).orElse(false));
        response.setLevelId(challenger.getLevelId());
        response.setTotalJoinedChallenge(challenger.getTotalJoinedChallenge());
        response.setTotalSubmittedChallenge(challenger.getTotalSubmittedChallenge());
        challenger.setLevelId(challenger.getLevelId());
        challengerRepository.updateLevelId(challenger.getId(), challenger.getLevelId());

        double currentScore = challenger.getScore();

        assert level != null;
        Long nextLevelId = level.getNextLevelId();

        if (nextLevelId != null) {
            LevelEntity nextLevel = levelRepository.findById(nextLevelId)
                    .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXIST));

            double nextScore = nextLevel.getMaxScore() - currentScore ;

            nextScore = Math.max(0, nextScore);


            response.setNextLevel(NextLevelResponse.builder()
                    .score(nextScore)
                    .rank(nextLevel.getLevel().getValue()) // Rank kế tiếp
                    .build());
        } else {
            response.setNextLevel(NextLevelResponse.builder()
                    .score(0.0)
                    .rank("Diamond")
                    .build());
        }
        return Optional.of(response);
    }

   public void upLevel(ChallengerEntity challengerEntity, Long levelChallenger) {
       if (levelChallenger ==1 && challengerEntity.getScore() >= 10) {
           challengerEntity.setLevelId(2L);
       } else if (levelChallenger==2 && challengerEntity.getScore() >= 150) {
           challengerEntity.setLevelId(3L);
       } else if (levelChallenger==3 && challengerEntity.getScore() >= 450) {
           challengerEntity.setLevelId(4L);
       } else if (levelChallenger==4 && challengerEntity.getScore() >= 1050) {
           challengerEntity.setLevelId(5L);
       } else if(levelChallenger==5 && challengerEntity.getScore() >= 2100) {
           challengerEntity.setLevelId(6L);
       }
   }


}
