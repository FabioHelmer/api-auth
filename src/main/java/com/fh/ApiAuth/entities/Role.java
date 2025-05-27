package com.fh.ApiAuth.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "role", schema = "tweeter")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;


    public enum Values{
        ADMIN(1L),
        BASIC(2L);

        long idRole;

        Values(long idRole){
            this.idRole = idRole;
        }

        public Long getIdRole() {
            return idRole;
        }

    }

}
