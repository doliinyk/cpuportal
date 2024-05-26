package ua.lpnu.denysoliinyk.cpuportal.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.lpnu.denysoliinyk.cpuportal.entity.Socket;

@Repository
public interface SocketRepository extends BaseEntityRepository<Socket>, JpaSpecificationExecutor<Socket> {
    static Specification<Socket> nameContains(String name) {
        return (socket, cq, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }

            return cb.like(cb.lower(socket.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}