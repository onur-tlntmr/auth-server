package com.example.springjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    @NotNull
    @Size(min = 32, max = 32, message = "Token must length is 32 character")
    @Column(name = "token", nullable = false, unique = true, length = 32)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;


}
