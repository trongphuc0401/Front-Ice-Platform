package vn.edu.likelion.front_ice.service.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.exceptions.AppException;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.dto.request.LoginRequest;
import vn.edu.likelion.front_ice.dto.response.LoginResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
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


    @Override public Optional create(Object t) {
        return Optional.empty();
    }

    @Override public Optional updateInfo(String id, Object o) {
        return Optional.empty();
    }

    @Override public List saveAll(List ts) {
        return List.of();
    }

    @Override public void delete(String id) {

    }

    @Override public Optional findById(String id) {
        return Optional.empty();
    }

    @Override public List findAll() {
        return List.of();
    }

    @Override public void deleteAll(List listId) {

    }

    @Override public Optional<LoginResponse> login(LoginRequest loginRequest) {


        AccountEntity accountEntity = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_OR_PASSWORD_INCORRECT)); // customize lại ErrorCode

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
