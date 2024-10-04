package vn.edu.likelion.front_ice.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.front_ice.common.enums.AccountType;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.mapper.AccountMapper;
import vn.edu.likelion.front_ice.repository.AccountRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceIml implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Optional<RegisterResponse> create(RegisterRequest t) {

        AccountEntity accountEntity =  accountMapper.toAccount(t);
        accountEntity.setAccountType(AccountType.CHALLENGE);
        accountRepository.save(accountEntity);

        return Optional.of(accountMapper.toRegisterResponse(accountEntity));
    }

    @Override
    public Optional<RegisterResponse> updateInfo(String id, RegisterRequest registerRequest) {
        return Optional.empty();
    }

    @Override
    public List<RegisterResponse> saveAll(List<AccountEntity> ts) {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll(List<String> listId) {

    }

    @Override
    public Optional<RegisterResponse> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<RegisterResponse> findAll() {
        return List.of();
    }
}
