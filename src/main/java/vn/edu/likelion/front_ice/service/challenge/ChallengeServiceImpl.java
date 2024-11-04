package vn.edu.likelion.front_ice.service.challenge;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.constants.SecurityConstants;
import vn.edu.likelion.front_ice.common.enums.ChallengeAccessStatus;
import vn.edu.likelion.front_ice.common.enums.TypeChallenge;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.query.SearchRequest;
import vn.edu.likelion.front_ice.common.query.SearchSpecification;
import vn.edu.likelion.front_ice.common.utils.PaginationUtil;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.request.challenge.UpdateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.*;
import vn.edu.likelion.front_ice.dto.response.resource.ResourceResponse;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ChallengeMapper;
import vn.edu.likelion.front_ice.mapper.ResourceMapper;
import vn.edu.likelion.front_ice.repository.CategoryRepository;
import vn.edu.likelion.front_ice.repository.ChallengeRepository;
import vn.edu.likelion.front_ice.repository.ResourceRepository;
import vn.edu.likelion.front_ice.repository.SolutionRepository;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.client.AccountService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChallengeMapper challengeMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired private ResourceRepository resourceRepository;

    @Override
    @Transactional()
    public Optional<ChallengeEntity> create(CreateChallengeRequest createChallengeRequest) {
        try {
            ChallengeEntity challengeEntity = challengeMapper.toChallenge(createChallengeRequest);

            File tempFile = File.createTempFile("resource_"+challengeEntity
                    .getTitle()
                    .toLowerCase()
                    .replace(" ", "-")
                    +"_", ".zip");

            ChallengeEntity savedChallenge = challengeRepository.save(challengeEntity);
            googleDriveService.uploadAssets(challengeEntity.getId(),tempFile);

            return Optional.of(savedChallenge);
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ChallengeEntity> updateInfo(Long id, UpdateChallengeRequest i) {
        return Optional.empty();
    }

    @Override
    public List<ChallengeEntity> saveAll(List<ChallengeEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll(List<Long> listId) {

    }

    @Override
    public ChallengeEntity findById(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));
    }

    @Override
    public List<ChallengeEntity> findAll() {
        return List.of();
    }

    @Override
    public PaginateChallengeResponse getPaginationChallengeByCategory(Long category, int pageNo, int pagSize) {
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

    @Override
    public ResultPaginationResponse getPaginationChallenge(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<ChallengeEntity> pageChallenge = challengeRepository.findAllChallenges(pageable);

        if (!pageChallenge.hasContent()) {
            throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);
        }

        return buildPaginationResponse(pageChallenge);
    }

    @Override
    public ResultPaginationResponse searchChallenges(SearchRequest searchRequest) {
        Specification<ChallengeEntity> specification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPageNo() - 1, searchRequest.getPageSize());

        Page<ChallengeEntity> pageChallenge = challengeRepository.findAll(specification, pageable);

        if (!pageChallenge.hasContent()) {
            throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);
        }

        return buildPaginationResponse(pageChallenge);
    }

    @Override
    public Object getDetailChallenge(Long challengeId) {

        ChallengeEntity challenge = challengeRepository.findChallengeWithDetails(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.CHALLENGE_NOT_EXIST));

        ResourceEntity challengerEntity = resourceRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_EXIST));

        ResourceResponse resourceResponse = resourceMapper.toResourceResponse(challengerEntity);
        ChallengeDetailForChallengerResponse response = challengeMapper.toChallengeDetailResponse(challenge);
        response.setResource(resourceResponse);


        Optional<String> email = SecurityUtil.getCurrentUserLogin();
        if (email.isEmpty() || SecurityConstants.ANONYMOUS_USER.equalsIgnoreCase(email.get())) {
            setPublicAccess(response);
            return response;
        }

        AccountEntity account = accountService.getAccountDetailsByEmail(email.get());
        return handleAccessBasedOnRole(account, challenge, response);
    }

    private void setPublicAccess(ChallengeDetailForChallengerResponse response) {
        response.setAccessStatus(ChallengeAccessStatus.PUBLIC_ACCESS.getStatus());
        response.setAccessMessage(ChallengeAccessStatus.PUBLIC_ACCESS.getMessage());
        response.setResource(null);
    }

    private Object handleAccessBasedOnRole(AccountEntity account, ChallengeEntity challenge, ChallengeDetailForChallengerResponse response) {
        switch (account.getRole()) {
            case CHALLENGER -> handleChallengerAccess(account, challenge, response);
            case ADMIN, MANAGER, MENTOR, RECRUITER -> {
                break;
            }
            default -> throw new AppException(ErrorCode.USER_ROLE_NOT_SUPPORTED);
        }
        return response;
    }

    private void handleChallengerAccess(AccountEntity account, ChallengeEntity challenge, ChallengeDetailForChallengerResponse response) {
        ChallengeAccessStatus accessStatus = determineAccessStatus(account, challenge);

        response.setAccessStatus(accessStatus.getStatus());
        response.setAccessMessage(accessStatus.getMessage());

        if (accessStatus == ChallengeAccessStatus.JOINED || accessStatus == ChallengeAccessStatus.SUBMITTED) {
            response.setResource(response.getResource());
        } else {
            response.setResource(null);
        }
    }

    private ChallengeAccessStatus determineAccessStatus(AccountEntity account, ChallengeEntity challenge) {
        boolean isPremiumRequired = challenge.getTypeChallenge() == TypeChallenge.PREMIUM;
        Optional<SolutionEntity> solutionOpt = solutionRepository.findByChallenger_IdAndChallenge_Id(account.getChallenger().getId(), challenge.getId());

        if (isPremiumRequired && account.getChallenger() != null && !account.getChallenger().isPremium()) {
            return ChallengeAccessStatus.PREMIUM_REQUIRED;
        }

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

    private ResultPaginationResponse buildPaginationResponse(Page<ChallengeEntity> pageChallenge) {
        List<ChallengeResponse> challengeResponses = pageChallenge.getContent()
                .stream()
                .map(challengeMapper::toChallengeResponse)
                .toList();

        ResultPaginationResponse.Meta meta = PaginationUtil.createPaginationMeta(pageChallenge);

        ResultPaginationResponse response = new ResultPaginationResponse();
        response.setMeta(meta);
        response.setResult(challengeResponses);

        return response;
    }
}
