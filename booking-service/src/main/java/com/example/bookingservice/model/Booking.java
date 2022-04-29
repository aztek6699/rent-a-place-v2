package com.example.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "booking")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;
    private Date endDate;
    private float totalCost;
    private Boolean complete;

    @Column(name = "renter_id")
    private Long renterId;

    @Column(name = "property_id")
    private Long propertyId;
}
