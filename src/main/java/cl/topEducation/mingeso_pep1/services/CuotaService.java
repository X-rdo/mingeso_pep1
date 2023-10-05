package cl.topEducation.mingeso_pep1.services;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.repositories.CuotaRepository;
import cl.topEducation.mingeso_pep1.repositories.EstudianteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
    EstudianteRepository estudianteRepository;

    public ArrayList<CuotaEntity> obtenerCuotas(){
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
    }


    public void generarCuotas(String rut,int cantidadCuotas,boolean alContado){
        existenCuotas(rut);
        Optional<EstudianteEntity> estudiante;
        estudiante = estudianteRepository.findById(rut);
        existenCuotas(rut);
        //contado?
        EstudianteEntity estudiante1 = estudiante.get();
        if(estudiante.isPresent()){
            if(alContado){
                estudiante1.setArancel(estudiante1.getArancel()*0.5);
            }else {

                //cantidad cuotas?
            }
        }else {
            throw new EntityExistsException("No existe un estudiante con el rut ingresado");
        }
    }

    public void existenCuotas(String rut){
        boolean existeCuota = cuotaRepository.existsByRut(rut);
        if(existeCuota){
            throw new EntityExistsException(rut + "El rut puesto ya se le han generado cuotas" );
        }
    }
}
