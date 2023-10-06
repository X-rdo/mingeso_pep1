package cl.topEducation.mingeso_pep1.services;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.repositories.CuotaRepository;
import cl.topEducation.mingeso_pep1.repositories.EstudianteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;
    EstudianteRepository estudianteRepository;
    EstudianteService estudianteService;

    public ArrayList<CuotaEntity> obtenerCuotas(){
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
    }


    public void generarCuotas(String rut,int cantidadCuotas,boolean alContado){
        existenCuotas(rut);
        estudianteService.verificarEstudiante(rut);
        Optional<EstudianteEntity> estudiante;
        estudiante = estudianteRepository.findById(rut);
        //contado?
        EstudianteEntity estudiante1 = estudiante.get();
        if(estudiante.isPresent()){
                //cantidad cuotas?
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


    //metodo que ve si hay cuotas del año actual
    //Si encuentra cuotas del año actual true
    //Si no hay cuotas del año actual false
    public boolean hayCuotasActuales(String rut){
        ArrayList<CuotaEntity> cuotas = (ArrayList<CuotaEntity>) cuotaRepository.findByRut(rut);
        int diferencia;
        Calendar calendario = Calendar.getInstance();

        int anhoActual = calendario.get(Calendar.YEAR);

        for (CuotaEntity cuota: cuotas) {
             LocalDate fecha = cuota.getFecha_cuota();
             diferencia = anhoActual - fecha.getYear();
             if(diferencia != 0){
                 return true;
             }
        }
        return false;

    }


}
