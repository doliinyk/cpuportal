package ua.lpnu.denysoliinyk.cpuportal.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    User user = new User();

    @Test
    void getAuthorities() {
        assertThat(user.getAuthorities()).isEmpty();
    }

    @Test
    void isAccountNonExpired() {
        assertThat(user.isAccountNonExpired()).isTrue();
    }

    @Test
    void isAccountNonLocked() {
        assertThat(user.isAccountNonLocked()).isTrue();
    }

    @Test
    void isCredentialsNonExpired() {
        assertThat(user.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void isEnabled() {
        assertThat(user.isEnabled()).isTrue();
    }
}