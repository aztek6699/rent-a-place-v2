package com.example.reviewservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_owner_id", nullable = false)
    private Long propertyOwnerId;

    @Column(name = "check_in_time")
    private Time checkInTime;
    @Column(name = "check_out_time")
    private Time checkOutTime;
    @Column(name = "guest_capacity")
    private int guestCapacity;

    private String amenities;
    private String description;
    private String picture;
    private float price;
    private String type;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PropertyLocation location;
}
