package com.example.propertyservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Property {

    @Id
    private Long id;

    @Column(name = "property_owner_id", nullable = false)
    private Long propertyOwnerId;

    private String amenities;
    private String description;
    private Time check_in_time;
    private Time check_out_time;
    private int guest_capacity;
    private String picture;
    private float price;
    private String type;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PropertyLocation location;
}
