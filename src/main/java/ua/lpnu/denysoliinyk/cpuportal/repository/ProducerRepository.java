package ua.lpnu.denysoliinyk.cpuportal.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.lpnu.denysoliinyk.cpuportal.entity.Producer;

@Repository
public interface ProducerRepository extends BaseEntityRepository<Producer>, JpaSpecificationExecutor<Producer> {
    static Specification<Producer> nameContains(String name) {
        return (producer, cq, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }

            return cb.like(cb.lower(producer.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    static Specification<Producer> descriptionContains(String description) {
        return (socket, cq, cb) -> {
            if (description == null || description.isEmpty()) {
                return cb.conjunction();
            }

            return cb.like(cb.lower(socket.get("description")), "%" + description.toLowerCase() + "%");
        };
    }
}