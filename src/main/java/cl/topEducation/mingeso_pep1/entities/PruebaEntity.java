package cl.topEducation.mingeso_pep1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prueba")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PruebaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que se genere los valores de forma incremental
    @Column(unique = true, nullable = false) //valores unicos y que estos no sean falsos
    private Long id;
    private String rut;
    private LocalDate fecha_examen;
    private int puntaje;
}
