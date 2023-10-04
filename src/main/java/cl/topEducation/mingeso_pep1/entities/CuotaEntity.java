package cl.topEducation.mingeso_pep1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean estado_pago;
    private String rut;

}
