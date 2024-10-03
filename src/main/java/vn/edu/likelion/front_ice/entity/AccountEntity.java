package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.likelion.front_ice.common.enums.AccountType;

import java.util.Collection;
import java.util.List;

/**
 * AccountEntity - Represents a user's account.
 */
@Entity
@Table(name = "tbl_account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity extends BaseEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AccountType accountType;

    @Column(nullable = false)
    int status;  // 0 = inactive, 1 = active

    // Implementing UserDetails methods properly

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + accountType.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // You can add logic if credentials expire
    }

    @Override
    public boolean isEnabled() {
        // The user is enabled only if status == 1
        return this.status == 1;
    }
}
