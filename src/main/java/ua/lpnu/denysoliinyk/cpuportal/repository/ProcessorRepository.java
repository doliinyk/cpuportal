package ua.lpnu.denysoliinyk.cpuportal.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.lpnu.denysoliinyk.cpuportal.entity.Processor;
import ua.lpnu.denysoliinyk.cpuportal.entity.User;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ProcessorRepository extends BaseEntityRepository<Processor>, JpaSpecificationExecutor<Processor> {
    Page<Processor> findByUser(User user, Pageable pageable);

    static Specification<Processor> hasProducer(Set<UUID> producerIds) {
        return (processor, cq, cb) -> {
            if (producerIds == null || producerIds.isEmpty()) {
                return cb.conjunction();
            }
            return cb.in(processor.get("producer").get("uuid")).value(producerIds);
        };
    }

    static Specification<Processor> hasModel(String model) {
        return (processor, cq, cb) -> {
            if (model == null || model.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(processor.get("model")), "%" + model.toLowerCase() + "%");
        };
    }

    static Specification<Processor> hasSocket(Set<UUID> socketIds) {
        return (processor, cq, cb) -> {
            if (socketIds == null || socketIds.isEmpty()) {
                return cb.conjunction();
            }
            return cb.in(processor.get("socket").get("uuid")).value(socketIds);
        };
    }

    static Specification<Processor> hasCores(Integer minCores, Integer maxCores) {
        return (processor, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minCores != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(processor.get("cores"), minCores));
            }
            if (maxCores != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(processor.get("cores"), maxCores));
            }
            return predicate;
        };
    }

    static Specification<Processor> hasThreads(Integer minThreads, Integer maxThreads) {
        return (processor, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minThreads != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(processor.get("threads"), minThreads));
            }
            if (maxThreads != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(processor.get("threads"), maxThreads));
            }
            return predicate;
        };
    }

    static Specification<Processor> hasCoreClock(Double minCoreClock, Double maxCoreClock) {
        return (processor, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minCoreClock != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(processor.get("coreClock"),
                                                                      minCoreClock));
            }
            if (maxCoreClock != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(processor.get("coreClock"), maxCoreClock));
            }
            return predicate;
        };
    }

    static Specification<Processor> hasBoostClock(Double minBoostClock, Double maxBoostClock) {
        return (processor, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minBoostClock != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(processor.get("boostClock"), minBoostClock));
            }
            if (maxBoostClock != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(processor.get("boostClock"), maxBoostClock));
            }
            return predicate;
        };
    }

    static Specification<Processor> hasGraphics(Boolean graphics) {
        return (processor, cq, cb) -> {
            if (graphics == null) {
                return cb.conjunction();
            }
            return cb.equal(processor.get("graphics"), graphics);
        };
    }

    static Specification<Processor> hasPrice(Double minPrice, Double maxPrice) {
        return (processor, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            if (minPrice != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(processor.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(processor.get("price"), maxPrice));
            }
            return predicate;
        };
    }
}