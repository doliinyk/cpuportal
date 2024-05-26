package ua.lpnu.denysoliinyk.cpuportal.repository;

import org.springframework.stereotype.Repository;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseEntityRepository<User> {
    Optional<User> findByUuidOrUsername(UUID uuid, String username);
}