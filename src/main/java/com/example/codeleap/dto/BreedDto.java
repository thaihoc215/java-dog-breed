package com.example.codeleap.dto;

import lombok.Data;

@Data
public class BreedDto {
    private WeightDto weight;
    private String id;
    private String name;
    private String cfa_url;
    private String vetstreet_url;
    private String vcahospitals_url;
    private String temperament;
    private String origin;
    private String country_codes;
    private String country_code;
    private String description;
    private String life_span;
    private Integer indoor;
    private Integer lap;
    private String alt_names;
    private Integer adaptability;
    private Integer affection_level;
    private Integer child_friendly;
    private Integer dog_friendly;
    private Integer energy_level;
    private Integer grooming;
    private Integer health_issues;
    private Integer intelligence;
    private Integer shedding_level;
    private Integer social_needs;
    private Integer stranger_friendly;
    private Integer vocalisation;
    private Integer experimental;
    private Integer hairless;
    private Integer natural;
    private Integer rare;
    private Integer rex;
    private Integer suppressed_tail;
    private Integer short_legs;
    private String wikipedia_url;
    private Integer hypoallergenic;
    private String reference_image_id;

    @Data
    public static class WeightDto {
        private String imperial;
        private String metric;
    }
}
