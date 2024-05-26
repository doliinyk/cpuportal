package ua.lpnu.denysoliinyk.cpuportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "producers", indexes = @Index(name = "name_description_idx", columnList = "name, description"))
public class Producer extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;
}