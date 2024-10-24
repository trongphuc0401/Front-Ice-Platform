package vn.edu.likelion.front_ice.service.challenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.common.enums.Level;
import vn.edu.likelion.front_ice.common.enums.LevelTest;
import vn.edu.likelion.front_ice.common.enums.ScoreAnswer;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.challenger.ChallengerDTO;
import vn.edu.likelion.front_ice.dto.request.follow.FollowRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.CreateChallengerRequest;
import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.challenger.NextLevelResponse;
import vn.edu.likelion.front_ice.dto.response.follow.FollowResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ChallengerMapper;
import vn.edu.likelion.front_ice.repository.*;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.FollowEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.FollowRepository;
import vn.edu.likelion.front_ice.repository.RecruiterRepository;
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
    private RecruiterRepository recruiterRepository;
    @Autowired
    private ChallengerRepository challengerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ChallengerMapper challengerMapper;
    @Autowired
    private AccessChallengeRepository accessChallengeRepository;

    @Override
    public Optional<ChallengerEntity> create(CreateChallengerRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<ChallengerEntity> updateInfo(String id, UpdateChallengerRequest i) {
        return Optional.empty();
    }

    @Override
    public List<ChallengerEntity> saveAll(List<ChallengerEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public ChallengerEntity findById(String id) {
        return null;
    }

    @Override
    public List<ChallengerEntity> findAll() {
        return List.of();
    }

    public Optional<FollowResponse> follow(FollowRequest t) {
        AccountEntity challenger;
        RecruiterEntity recruiter;

        challenger = accountRepository.findById(
                        challengerRepository.findById(t.getChallengerId())
                                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST))
                                .getAccount().getId())
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
    public Optional<ChallengerResponse> getDetailsProfile(String accessToken) {

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        AccountEntity account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challenger = challengerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

        LevelEntity level = Optional.ofNullable(challenger.getLevelId())
                .map(levelId -> levelRepository.findById(levelId)
                        .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXIST)))
                .orElse(null);

        ChallengerResponse response = challengerMapper.toChallengerResponse(account, challenger, level);

        // lấy totalJoinedChallenge và totalSubmittedChallenge
        challenger.setTotalJoinedChallenge(accessChallengeRepository
                .findByChallengerIdAndStatus(challenger.getId(), ChallengeAccessStatus.JOINED).size());
        challenger.setTotalSubmittedChallenge(accessChallengeRepository
                .findByChallengerIdAndStatus(challenger.getId(), ChallengeAccessStatus.SUBMITTED).size());

        // lấy nextLevel
        int scoreNextLevel = level.getMaxScore() - challenger.getScore();
        AtomicReference<String> nextRank = new AtomicReference<>();
        levelRepository.findById(level.getNextLevelId()).ifPresentOrElse(
                n -> nextRank.set(n.getTitle()),
                () -> nextRank.set("not found")
        );

        response.setNextLevel(NextLevelResponse.builder()
                .score(scoreNextLevel)
                .rank(nextRank.get())
                .build());

        return Optional.of(response);
    }

    public void updateScore(LevelTest levelTest, ChallengerDTO challengerDTO, ChallengerEntity challengerEntity) {
        switch (levelTest) {
            case EASY:
                challengerEntity = ChallengerEntity.builder()
                        .score(addScore(challengerDTO.getLevelChallenger(), challengerDTO.getScore(), "easy"))
                        .build();
                upLevel(challengerEntity, "newbie");
                challengerRepository.save(challengerEntity);
                break;

            case MEDIUM:
                challengerEntity = ChallengerEntity.builder()
                        .score(addScore(challengerDTO.getLevelChallenger(), challengerDTO.getScore(), "medium"))
                        .build();
                upLevel(challengerEntity, "newbie");
                challengerRepository.save(challengerEntity);
                break;

            case HARD:
                challengerEntity = ChallengerEntity.builder()
                        .score(addScore(challengerDTO.getLevelChallenger(), challengerDTO.getScore(), "medium"))
                        .build();
                upLevel(challengerEntity, "newbie");
                challengerRepository.save(challengerEntity);
                break;

            default:
                break;
        }
    }

    public int addScore(Level levelChallenger, int score, String levelAnwser) {
        switch (levelChallenger) {
            case NEWBIE:
                if (levelAnwser.equals("easy")) {

                    score = ScoreAnswer.NEW_BIE_EASY.getScore() + score;
                } else if (levelAnwser.equals("medium")) {

                    score = ScoreAnswer.NEW_BIE_MEDIUM.getScore() + score;
                } else {

                    score = ScoreAnswer.NEW_BIE_HARD.getScore() + score;
                }
                break;

            case SILVER:
                if (levelAnwser.equals("easy")) {

                    score = ScoreAnswer.SILVER_EASY.getScore() + score;
                } else if (levelAnwser.equals("medium")) {

                    score = ScoreAnswer.SILVER_MEDIUM.getScore() + score;
                } else {

                    score = ScoreAnswer.SILVER_HARD.getScore() + score;
                }

                break;

            case GOLD:
                if (levelAnwser.equals("easy")) {

                    score = ScoreAnswer.GOLD_EASY.getScore() + score;
                } else if (levelAnwser.equals("medium")) {

                    score = ScoreAnswer.GOLD_MEDIUM.getScore() + score;
                } else {

                    score = ScoreAnswer.GOLD_HARD.getScore() + score;
                }

                break;

            default:
                break;
        }

        return score;
    }

    public void upLevel(ChallengerEntity challengerEntity, String levelChallenger) {
        if (levelChallenger.equals("newbie") && challengerEntity.getScore() >= 150) {
            challengerEntity.setLevelId("silver");
        } else if (levelChallenger.equals("silver") && challengerEntity.getScore() >= 450) {
            challengerEntity.setLevelId("gold");
        } else if (levelChallenger.equals("gold") && challengerEntity.getScore() >= 1050) {
            challengerEntity.setLevelId("diamond");
        }
    }


}
