package com.prog4.libraryManagement.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.prog4.libraryManagement.repository.types.PostgresEnumType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"author\"")
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(name = "birth_date")
    private Date birthDate;
    public enum Sex {
        M, F
    }
}
