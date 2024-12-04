package vn.edu.likelion.front_ice.service.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.StatusSolution;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.utils.HelperUtil;
import vn.edu.likelion.front_ice.common.utils.PaginationUtil;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ResultPaginationResponse;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionChallengerResponse;
import vn.edu.likelion.front_ice.dto.response.solution.OtherSolutionResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.SolutionMapper;
import vn.edu.likelion.front_ice.repository.*;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.client.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SolutionMapper solutionMapper;

    @Autowired
    private ChallengerRepository challengerRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LevelRepository levelRepository;

    @Override
    public Optional<SolutionEntity> create(CreateSolutionRequest t) {
        if (t.getChallengeId() == null) throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);

        String email = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challengerEntity = accountRepository.findChallengerByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST))
                .getChallenger();

        Optional<SolutionEntity> solutionEntity = solutionRepository
                .findByChallengeIdAndChallengerIdAndIsJoined(t.getChallengeId(), challengerEntity.getId(), true);

        if (solutionEntity.isPresent()) {
            throw new AppException(ErrorCode.YOU_HAVE_ALREADY_JOINED);
        }

        solutionEntity = Optional.of(
                SolutionEntity.builder()
                        .challenge(
                                challengeRepository.findById(t.getChallengeId())
                                        .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST))
                        )
                        .challenger(challengerEntity)
                        .isJoined(true)
                        .statusSolution(StatusSolution.PROCESSING)
                        .solutionCode(HelperUtil.generateSolutionCode(t.getChallengeId(), solutionRepository))
                        .build()
        );

        challengerEntity.setTotalJoinedChallenge(challengerEntity.getTotalJoinedChallenge() + 1);
        challengerRepository.save(challengerEntity);

        return Optional.of(solutionRepository.save(solutionEntity.get()));
    }

    @Override
    public Optional<SolutionEntity> updateInfo(Long id, UpdateSolutionRequest i) {

        SolutionEntity solution = solutionRepository.findById(id)
                .map(solutionEntity -> solutionMapper.toSolutionUpdate(i, solutionEntity))
                .orElseThrow(() -> new AppException(ErrorCode.SOLUTION_NOT_EXIST));

        if (solution.getStatusSolution().equals(StatusSolution.APPROVED) && solution.isSubmitted()) {
            throw new AppException(ErrorCode.YOU_HAVE_ALREADY_SUBMITTED);
        }

        solution.setStatusSolution(StatusSolution.APPROVED);
        solution.setSubmitted(true);
        solution.getChallenger().setTotalSubmittedChallenge(solution.getChallenger().getTotalSubmittedChallenge() + 1);
        challengerRepository.save(solution.getChallenger());
        return Optional.of(solutionRepository.save(solution));
    }

    @Override
    public List<SolutionEntity> saveAll(List<SolutionEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll(List<Long> listId) {

    }

    @Override
    public SolutionEntity findById(Long id) {
        return null;
    }

    @Override
    public List<SolutionEntity> findAll() {
        return List.of();
    }

    @Override
    public ResultPaginationResponse getSolutionsOfOtherChallengers(int pageNo, int pageSize) {
        Optional<String> email = SecurityUtil.getCurrentUserLogin();
        AccountEntity account = accountService.getAccountDetailsByEmail(email.get());
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SolutionEntity> solutionsPage = solutionRepository.findSolutionsOfOtherChallengers(account.getChallenger().getId(), pageable);
        List<OtherSolutionChallengerResponse> responseList = solutionsPage.stream()
                .map(solution -> {
                    OtherSolutionChallengerResponse responseItem = solutionMapper.toOtherSolutionChallengerResponse(solution);

                    LevelEntity levelEntity = levelRepository.findById(solution.getChallenger().getLevelId()).orElse(null);
                    if (levelEntity != null) {
                        responseItem.setChallengerLevel(levelEntity.getLevel());
                    } else {
                        responseItem.setChallengerLevel(null);
                    }

                    return responseItem;
                })
                .collect(Collectors.toList());

        ResultPaginationResponse.Meta meta = PaginationUtil.createPaginationMeta(solutionsPage);
        ResultPaginationResponse result = new ResultPaginationResponse(meta, responseList);

        return result;
    }
}
