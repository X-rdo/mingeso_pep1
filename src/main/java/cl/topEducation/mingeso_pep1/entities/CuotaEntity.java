package cl.topEducation.mingeso_pep1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que se genere los valores de forma incremental
    @Column(unique = true, nullable = false) //valores unicos y que estos no sean falsos
    private Long id;
    private int numero_cuota;
    private Long monto;
    private String estado_pago;
    private String rut;
    private LocalDate fecha_cuota; //Tiene que partir de 4 en la primera cuota

}
