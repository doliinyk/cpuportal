package ua.lpnu.denysoliinyk.cpuportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sockets")
public class Socket extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;
}
