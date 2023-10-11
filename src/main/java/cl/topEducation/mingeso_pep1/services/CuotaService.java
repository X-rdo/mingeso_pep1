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
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    EstudianteService estudianteService;

    public ArrayList<CuotaEntity> obtenerCuotas(){
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
    }

    public ArrayList<CuotaEntity> obtenerCutoasByRut(String rut){
        ArrayList<CuotaEntity> cuotasTotales = (ArrayList<CuotaEntity>) cuotaRepository.findByRut(rut);
        ArrayList<CuotaEntity> cuotasAnhoActual = new ArrayList<CuotaEntity>();
        int diferencia;
        Calendar calendario = Calendar.getInstance();

        int anhoActual = calendario.get(Calendar.YEAR);

        for (CuotaEntity cuota: cuotasTotales) {
            LocalDate fecha = cuota.getFecha_cuota();
            diferencia = anhoActual - fecha.getYear();
            if(diferencia == 0){
                cuotasAnhoActual.add(cuota);
            }
        }
        return cuotasAnhoActual;

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
             if(diferencia == 0){
                 return true;
             }
        }
        return false;

    }

    //capaz puede retornar un 1 o -1 para ver si corrió perfectamente
    public void generarCuotas(String rut, int cantCuotas){
        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(rut);
        if(estudiante.isPresent()){
            CuotaEntity cuotaVariable;
            double arancelEstudiante = estudiante.get().getArancel();

            Calendar calendario = Calendar.getInstance();
            int anhoActual = calendario.get(Calendar.YEAR);
            int mesPartidaCuotas = 4;
            long cantidadPorCuota;

            if(cantCuotas == -1){
                cantidadPorCuota = (long) (arancelEstudiante*0.5);
            }else{
                 cantidadPorCuota= (long) (arancelEstudiante/cantCuotas);
            }
            for(int i = 1;i<cantCuotas + 1;++i){
                cuotaVariable = new CuotaEntity();
                cuotaVariable.setNumero_cuota(i);
                cuotaVariable.setMonto(cantidadPorCuota);
                cuotaVariable.setEstado_pago("No pagada"); //Recordar cambiar el estado a string para la visualizacion en
                cuotaVariable.setRut(rut);
                cuotaVariable.setFecha_cuota(LocalDate.of(anhoActual,mesPartidaCuotas,5));
                mesPartidaCuotas += 1;
                if (mesPartidaCuotas > 12){
                    mesPartidaCuotas = 1;
                }
                cuotaRepository.save(cuotaVariable);
            }
        }
    }


}
