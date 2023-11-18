package com.ds.Assignement1.Assignement1.Model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date birthDate;
    private String address;
    private String email;
    private String phoneNumber;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] profilePicture;

    @OneToOne(cascade = CascadeType.MERGE)
    private Role role;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Device> devices;
}