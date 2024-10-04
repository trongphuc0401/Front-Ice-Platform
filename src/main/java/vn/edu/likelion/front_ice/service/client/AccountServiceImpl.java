package vn.edu.likelion.front_ice.service.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.AccountType;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.LoginResponse;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.mapper.AccountMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;
import vn.edu.likelion.front_ice.security.SecurityUtil;

import java.util.List;
import java.util.Optional;

/**
 * AccountServiceImpl -
 *
 * @param
 * @return
 * @throws
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired SecurityUtil securityUtil;
    @Autowired private AccountMapper accountMapper;


    @Override public Optional<RegisterResponse> create(RegisterRequest registerRequest) {

        if(accountRepository.findByEmail(registerRequest.getEmail()).isEmpty()){
            AccountEntity accountEntity =  accountMapper.toAccount(registerRequest);
            accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
            accountEntity.setIsDeleted(0);
            accountEntity.setStatus(1);
            accountRepository.save(accountEntity);

            return Optional.of(accountMapper.toRegisterResponse(accountEntity));
        }else {
            throw new AppException(ErrorCode.ACCOUNT_EXIST);
        }


    }

    @Override public Optional<RegisterResponse> updateInfo(String id, RegisterRequest registerRequest) {
        return Optional.empty();
    }

    @Override public List<RegisterResponse> saveAll(List<AccountEntity> ts) {
        return List.of();
    }


    @Override public void delete(String id) {

    }

    @Override public void deleteAll(List<String> listId) {

    }

    @Override public Optional<RegisterResponse> findById(String id) {
        return Optional.empty();
    }

    @Override public List<RegisterResponse> findAll() {

        return List.of();
    }

    @Override public Optional<LoginResponse> login(LoginRequest loginRequest) {


        AccountEntity accountEntity = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_OR_PASSWORD_INCORRECT)); // customize lại ErrorCode

        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(securityUtil.createAccessToken(loginRequest.getEmail(),loginRequest))
                .account(accountEntity)
                .expiresIn(securityUtil.getExpirationTime())
                .build();


        return Optional.of(loginResponse);
    }
}
