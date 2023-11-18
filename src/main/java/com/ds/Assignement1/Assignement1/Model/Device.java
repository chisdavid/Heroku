package com.ds.Assignement1.Assignement1.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String address;
    private Long maximumEnergyConsumption;
    private Long averageEnergyConsumption;
    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Sensor sensor;
}