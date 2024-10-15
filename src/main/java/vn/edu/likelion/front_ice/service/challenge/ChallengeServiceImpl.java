package vn.edu.likelion.front_ice.service.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.challenge.CreationChallengeRequest;
import vn.edu.likelion.front_ice.dto.request.challenge.UpdateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.ChallengeResponse;
import vn.edu.likelion.front_ice.dto.response.challenge.PaginateChallengeResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ChallengeMapper;
import vn.edu.likelion.front_ice.mapper.ChallengerMapper;
import vn.edu.likelion.front_ice.repository.CategoryRepository;
import vn.edu.likelion.front_ice.repository.ChallengeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ChallengeMapper challengeMapper;


    @Override
    public Optional<ChallengeEntity> create(CreationChallengeRequest t) {
        return Optional.empty();
    }

    @Override
    public Optional<ChallengeEntity> updateInfo(String id, UpdateChallengeRequest i) {
        return Optional.empty();
    }

    @Override
    public List<ChallengeEntity> saveAll(List<ChallengeEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public Optional<ChallengeEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<ChallengeEntity> findAll() {
        return List.of();
    }

    @Override
    public PaginateChallengeResponse getPaginationChallengeByCategory(String category, int pageNo, int pagSize) {
        CategoryEntity categoryEntity = categoryRepository.findById(category)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));

        Pageable a = PageRequest.of(pageNo, pagSize, Sort.by("createAt").descending());
        Page<ChallengeEntity> challengeEntities = challengeRepository.findByCategoryId(categoryEntity.getId(), a);
        if (!challengeEntities.hasContent()) {
            throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);
        }
        List<ChallengeEntity> entities = challengeEntities.getContent();
        List<ChallengeResponse> data = entities.stream().map(challengeMapper::toChallengeResponse).toList();
        PaginateChallengeResponse response = new PaginateChallengeResponse();
        response.setResults(data);
        response.setPageNo(challengeEntities.getNumber());
        response.setPageSize(challengeEntities.getSize());
        response.setTotalElements(challengeEntities.getNumberOfElements());
        response.setTotalPages(challengeEntities.getTotalPages());

        return response;
    }
}
