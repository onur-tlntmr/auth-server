package com.example.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.CascadeType.*;


@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotEmpty
    @Size(max = 64)
    @Column(name = "user_name", unique = true, nullable = false, length = 64)
    private String userName;

    @NotEmpty
    @Size(max = 64)
    @Column(name = "full_name", unique = true, nullable = false, length = 64)
    private String fullName;

    @NotEmpty
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @Column(length = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreatedDate
    @CreationTimestamp
    private LocalDate createdDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {MERGE, PERSIST, REFRESH, DETACH})
    private Collection<Role> roles = new ArrayList<>();

    public User(String userName, String fullName, String email, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}
