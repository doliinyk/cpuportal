package ua.lpnu.denysoliinyk.cpuportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ua.lpnu.denysoliinyk.cpuportal.entity.BaseEntity;

import java.util.UUID;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {
}