package com.example.petwebapplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data // Generates getters, setters, equals(), hashCode(), and toString() methods.
@NoArgsConstructor // Generates a no-args constructor.
@AllArgsConstructor
public class PetServiceRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "service_date", nullable = false)
    private Date serviceDate;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @Column(name = "details")
    private String details;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "next_scheduled_visit_date")
    private Date next_scheduled_visit;

    @Column(name = "next_scheduled_visit_reason")
    private String next_scheduled_visit_reason;

    @Column(name = "next_scheduled_visit_reason")
    private String next_scheduled_visit_location;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;
}
