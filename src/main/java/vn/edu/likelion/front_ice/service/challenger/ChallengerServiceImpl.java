package vn.edu.likelion.front_ice.service.challenger;

import vn.edu.likelion.front_ice.dto.request.challenger.UpdateChallengerRequest;
import vn.edu.likelion.front_ice.dto.response.challenger.ChallengerResponse;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;

import java.util.List;
import java.util.Optional;

/**
 * ChallengerServiceImpl -
 *
 * @param
 * @return
 * @throws
 */
public class ChallengerServiceImpl implements ChallengerService {


    @Override public Optional<ChallengerResponse> create(UpdateChallengerRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<ChallengerResponse> updateInfo(String id, UpdateChallengerRequest updateChallengerRequest) {
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
}
