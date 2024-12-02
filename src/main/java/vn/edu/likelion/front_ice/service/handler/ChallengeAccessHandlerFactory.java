package vn.edu.likelion.front_ice.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChallengeAccessHandlerFactory {

    private final Map<ChallengeAccessStatus, ChallengeAccessHandler> handlers = new HashMap<>();

    @Autowired
    public ChallengeAccessHandlerFactory(
            List<ChallengeAccessHandler> challengeAccessHandlers) {
        challengeAccessHandlers.forEach(handler -> {
            ChallengeAccessStatus status = getStatusFromHandler(handler);
            handlers.put(status, handler);
        });
    }

    private ChallengeAccessStatus getStatusFromHandler(ChallengeAccessHandler handler) {
        // Xác định status từ handler
        if (handler instanceof PublicAccessHandler) return ChallengeAccessStatus.PUBLIC_ACCESS;
        if (handler instanceof JoinedAccessHandler) return ChallengeAccessStatus.JOINED;
        if (handler instanceof NotJoinAccessHander) return ChallengeAccessStatus.NOT_JOINED;
        if (handler instanceof SubmittedAccessHandler) return ChallengeAccessStatus.SUBMITTED;
        return ChallengeAccessStatus.PUBLIC_ACCESS;
    }

    public ChallengeAccessHandler getHandler(ChallengeAccessStatus status) {
        return handlers.getOrDefault(status, new PublicAccessHandler());
    }
}