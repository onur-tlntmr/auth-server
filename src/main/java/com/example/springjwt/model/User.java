package com.example.springjwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private long id;

    @Column(length = 64)
    private String name;

    @Column(unique = true,nullable = false,length = 64)
    private String userName;

    private String email;

    @Column(length = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreatedDate
    private Date createdDate;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
