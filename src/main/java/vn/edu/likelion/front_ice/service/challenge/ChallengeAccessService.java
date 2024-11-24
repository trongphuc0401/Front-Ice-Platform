package vn.edu.likelion.front_ice.service.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.ChallengeEntity;
import vn.edu.likelion.front_ice.entity.SolutionEntity;
import vn.edu.likelion.front_ice.repository.SolutionRepository;

import java.util.Optional;

@Service
public class ChallengeAccessService {
    @Autowired
    private SolutionRepository solutionRepository;

    public ChallengeAccessStatus determineAccessStatus(AccountEntity account, ChallengeEntity challenge) {
        boolean isPremiumRequired = challenge.getTypeChallenge() == TypeChallenge.PREMIUM;

        if (isPremiumRequired && !account.getChallenger().isPremium()) {
            return ChallengeAccessStatus.PREMIUM_REQUIRED;
        }

        Optional<SolutionEntity> solutionOpt = solutionRepository.findByChallenger_IdAndChallenge_Id(account.getChallenger().getId(), challenge.getId());
        if (solutionOpt.isEmpty()) {
            return ChallengeAccessStatus.NOT_JOINED;
        }

        SolutionEntity solution = solutionOpt.get();
        if (solution.isReported()) {
            return ChallengeAccessStatus.REPORTED;
        }

        if (solution.isSubmitted()) {
            return ChallengeAccessStatus.SUBMITTED;
        }

        return ChallengeAccessStatus.JOINED;
    }
}
