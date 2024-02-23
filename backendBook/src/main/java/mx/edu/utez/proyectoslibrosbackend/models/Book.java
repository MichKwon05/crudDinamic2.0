package mx.edu.utez.proyectoslibrosbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String nameBook;

    @Column(nullable = false)
    private String gener;

    @Column(nullable = false)
    private int agePubli;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
}
