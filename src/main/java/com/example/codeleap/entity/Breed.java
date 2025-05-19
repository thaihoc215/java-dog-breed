package com.example.codeleap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "breeds")
@Data
public class Breed {
    @Embedded
    private Weight weight;

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 64)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "cfa_url")
    private String cfa_url;

    @Column(name = "vetstreet_url")
    private String vetstreet_url;

    @Column(name = "vcahospitals_url")
    private String vcahospitals_url;

    @Column(name = "temperament")
    private String temperament;

    @Column(name = "origin")
    private String origin;

    @Column(name = "country_codes")
    private String country_codes;

    @Column(name = "country_code")
    private String country_code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "life_span")
    private String life_span;

    @Column(name = "indoor")
    private Integer indoor;

    @Column(name = "lap")
    private Integer lap;

    @Column(name = "alt_names")
    private String alt_names;

    @Column(name = "adaptability")
    private Integer adaptability;

    @Column(name = "affection_level")
    private Integer affection_level;

    @Column(name = "child_friendly")
    private Integer child_friendly;

    @Column(name = "dog_friendly")
    private Integer dog_friendly;

    @Column(name = "energy_level")
    private Integer energy_level;

    @Column(name = "grooming")
    private Integer grooming;

    @Column(name = "health_issues")
    private Integer health_issues;

    @Column(name = "intelligence")
    private Integer intelligence;

    @Column(name = "shedding_level")
    private Integer shedding_level;

    @Column(name = "social_needs")
    private Integer social_needs;

    @Column(name = "stranger_friendly")
    private Integer stranger_friendly;

    @Column(name = "vocalisation")
    private Integer vocalisation;

    @Column(name = "experimental")
    private Integer experimental;

    @Column(name = "hairless")
    private Integer hairless;

    @Column(name = "\"natural\"")
    private Integer natural;

    @Column(name = "rare")
    private Integer rare;

    @Column(name = "rex")
    private Integer rex;

    @Column(name = "suppressed_tail")
    private Integer suppressed_tail;

    @Column(name = "short_legs")
    private Integer short_legs;

    @Column(name = "wikipedia_url")
    private String wikipedia_url;

    @Column(name = "hypoallergenic")
    private Integer hypoallergenic;

    @Column(name = "reference_image_id")
    private String reference_image_id;

    @Data
    @Embeddable
    public static class Weight {
        private String imperial;
        private String metric;
    }
}
