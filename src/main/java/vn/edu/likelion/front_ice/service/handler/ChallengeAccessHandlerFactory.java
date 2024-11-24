package vn.edu.likelion.front_ice.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChallengeAccessHandlerFactory {

    private final Map<ChallengeAccessStatus, ChallengeAccessHandler> handlers = new HashMap<>();

    @Autowired
    public ChallengeAccessHandlerFactory(
            PublicAccessHandler publicAccessHandler,
            JoinedAccessHandler joinedAccessHandler,
            SubmittedAccessHandler submittedAccessHandler) {
        handlers.put(ChallengeAccessStatus.PUBLIC_ACCESS, publicAccessHandler);
        handlers.put(ChallengeAccessStatus.JOINED, joinedAccessHandler);
        handlers.put(ChallengeAccessStatus.SUBMITTED, submittedAccessHandler);
    }

    public ChallengeAccessHandler getHandler(ChallengeAccessStatus status) {
        return handlers.getOrDefault(status, (challenge, response) -> {
            throw new IllegalArgumentException("Unsupported status: " + status);
        });
    }
}