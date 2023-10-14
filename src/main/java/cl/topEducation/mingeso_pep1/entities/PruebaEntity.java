package cl.topEducation.mingeso_pep1.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(unique = true, nullable = false) //valores unicos y que estos no sean falsos
    private String rut;
    private LocalDate fecha_examen;
    private int puntaje;
}
