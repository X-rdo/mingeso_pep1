package cl.topEducation.mingeso_pep1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    @Id
    @Column(unique = true, nullable = false) //valores unicos y que estos no sean falsos
    private String rut;
    private String apellidos;
    private String nombres;
    private LocalDate fecha_nacimiento;
    private int tipoColegio; //Se considera que este campo no puede ser NULL, debido a que no se indica nada de estudiantes los cuales no poseen colegio
    private String nombre_colegio;
    private int anho_egreso_colegio;


}
