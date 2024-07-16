package com.quantacomit.practical_test.models;

import com.quantacomit.practical_test.Enums.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,name = "name")
    private ERole name;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private int isActive ;

    public Role(ERole name, int isActive) {
        this.name = name;
        this.isActive = isActive;
    }
}
