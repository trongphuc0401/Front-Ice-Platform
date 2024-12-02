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
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.query.SearchRequest;
import vn.edu.likelion.front_ice.common.query.SearchSpecification;
import vn.edu.likelion.front_ice.common.utils.PaginationUtil;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.request.challenge.UpdateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.challenge.*;
import vn.edu.likelion.front_ice.entity.*;
import vn.edu.likelion.front_ice.mapper.ChallengeMapper;
import vn.edu.likelion.front_ice.mapper.ResourceMapper;
import vn.edu.likelion.front_ice.repository.CategoryRepository;
import vn.edu.likelion.front_ice.repository.ChallengeRepository;
import vn.edu.likelion.front_ice.repository.ChallengerRepository;
import vn.edu.likelion.front_ice.repository.SolutionRepository;
import vn.edu.likelion.front_ice.service.gdrive.GoogleDriveService;
import vn.edu.likelion.front_ice.security.SecurityUtil;
import vn.edu.likelion.front_ice.service.client.AccountService;
import vn.edu.likelion.front_ice.service.handler.ChallengeAccessHandlerFactory;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ChallengeAccessService challengeAccessService;

    @Autowired
    private ChallengeAccessHandlerFactory challengeAccessHandlerFactory;
    @Autowired private ChallengerRepository challengerRepository;

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

    @Override public ResultPaginationResponse getPaginationJoinedChallenge(int pageNo, int pageSize) {

        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<ChallengeEntity> pageJoinedChallenge = challengeRepository.findAllJoinedChallenge(email,pageable);

        if (!pageJoinedChallenge.hasContent()) {
            throw new AppException(ErrorCode.CHALLENGE_NOT_EXIST);
        }

        return buildPaginationResponse(pageJoinedChallenge);
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

        DetailChallengeResponse response = challengeMapper.toChallengeDetailResponse(challenge);

        ParticipationSubmissionCount counts = solutionRepository.countParticipationAndSubmission(challengeId);
        response.setPeopleParticipated(counts.getPeopleParticipated());
        response.setPeopleSubmitted(counts.getPeopleSubmitted());

        Optional<String> email = SecurityUtil.getCurrentUserLogin();
        if (email.isEmpty() || SecurityConstants.ANONYMOUS_USER.equalsIgnoreCase(email.get())) {
            challengeAccessHandlerFactory.getHandler(ChallengeAccessStatus.PUBLIC_ACCESS)
                    .handleAccess(challenge, response);
            return response;
        }

        AccountEntity account = accountService.getAccountDetailsByEmail(email.get());
        ChallengeAccessStatus accessStatus = challengeAccessService.determineAccessStatus(account, challenge);

        challengeAccessHandlerFactory.getHandler(accessStatus).handleAccess(challenge, response);
        return response;
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
