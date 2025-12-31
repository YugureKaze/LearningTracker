package com.yugurekaze.learningtracker.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//TODO add builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence_generator"
    )
    @SequenceGenerator(name = "user_sequence_generator",
            sequenceName = "users_id_seq",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;

    @Column(name = "email", unique = true, length = 255, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
}
