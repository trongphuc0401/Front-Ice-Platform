package vn.edu.likelion.front_ice.service.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.StatusSolution;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.solution.CreateSolutionRequest;
import vn.edu.likelion.front_ice.dto.request.solution.UpdateSolutionRequest;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.SolutionMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.SolutionRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SolutionMapper solutionMapper;

    @Autowired
    private ChallengerRepository challengerRepository;

    @Override
    public Optional<SolutionEntity> create(CreateSolutionRequest t) {
        if (t.getChallengeId() == null) throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);

        String email = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        ChallengerEntity challengerEntity = challengerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGER_NOT_EXIST));

//        Optional<SolutionEntity> solutionEntity = solutionRepository
//                .findByChallengeIdAndChallengerId(t.getChallengeId(), challengerEntity.getId());

        Optional<SolutionEntity> solutionEntity = null;
        if (solutionEntity.isPresent()) {
            throw new AppException(ErrorCode.YOU_HAVE_ALREADY_JOINED);
        }

        solutionEntity = Optional.of(solutionMapper.toSolution(t));
//        solutionEntity.get().setChallengerId(challengerEntity.getId());
//        solutionEntity.get().setStatusSolution(StatusSolution.EMPTY);

        return Optional.of(solutionRepository.save(solutionEntity.get()));
    }

    @Override
    public Optional<SolutionEntity> updateInfo(String id, UpdateSolutionRequest i) {
        return Optional.empty();
    }

    @Override
    public List<SolutionEntity> saveAll(List<SolutionEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public SolutionEntity findById(String id) {
        return null;
    }

    @Override
    public List<SolutionEntity> findAll() {
        return List.of();
    }
}
