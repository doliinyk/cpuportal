package ua.lpnu.denysoliinyk.cpuportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "processors", indexes = {
        @Index(name = "idx_processor_properties",
                columnList = "model, cores, threads, core_clock, boost_clock, graphics, price")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "producer_id",
                "name"
        })
})
public class Processor extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @Column(name = "model", nullable = false)
    private String model;

    @ManyToOne(optional = false)
    @JoinColumn(name = "socket_id", nullable = false)
    private Socket socket;

    @Column(name = "cores", nullable = false)
    private int cores;

    @Column(name = "threads", nullable = false)
    private int threads;

    @Column(name = "core_clock", nullable = false)
    private double coreClock;

    @Column(name = "boost_clock")
    private Double boostClock;

    @Column(name = "graphics", nullable = false)
    private boolean graphics;

    @Column(name = "price", nullable = false)
    private double price;
}