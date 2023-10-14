package cl.topEducation.mingeso_pep1.services;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import cl.topEducation.mingeso_pep1.repositories.CuotaRepository;
import cl.topEducation.mingeso_pep1.repositories.EstudianteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.BreakIterator;
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
    @Autowired
    PruebaService pruebaService;

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
                cuotaVariable.setMonto_variable(cantidadPorCuota);
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

    //Siendo el numero de cuota el primer dato del string[] y el segundo rut
    public static String[] separaNumeroYrut(String numeroYRut) {
        String[] valores = numeroYRut.split(",");
        return valores;
    }

    public String separarRut(String numeroYRut) {
        String[] valores = separaNumeroYrut(numeroYRut);
        return valores[1];
    }

    public void cambiarEstadoPago(String numeroYRut){
        String[] valores = separaNumeroYrut(numeroYRut);
        int numeroCuota = Integer.parseInt(valores[0]);
        String rut = valores[1];

        ArrayList<CuotaEntity> cuotas = (ArrayList<CuotaEntity>) cuotaRepository.findByRut(rut);
        if (!cuotas.isEmpty()){
            for(CuotaEntity cuota: cuotas){

                if(cuota.getNumero_cuota() == numeroCuota){
                    cuota.setEstado_pago("Pagado");

                    cuotaRepository.save(cuota);
                    break;
                }

            }
        }
    }

    public ArrayList<CuotaEntity> cuotasNoPagadas(ArrayList<CuotaEntity> cuotasTotales){
        ArrayList<CuotaEntity> cuotasNoPagadas = new ArrayList<CuotaEntity>();
        for (CuotaEntity cuota: cuotasTotales) {
            if(cuota.getEstado_pago().equals("No pagada")){
                cuotasNoPagadas.add(cuota);
            }
        }
        return cuotasNoPagadas;
    }
    
    public Long aplicarInteres(int mesesAtraso, Long monto){
        if(mesesAtraso == 0 || mesesAtraso < 0){
            return monto;
        } else if (mesesAtraso == 1) {
            return (long) (monto * 1.03);
        } else if (mesesAtraso == 2) {
            return (long) (monto * 1.06);
        } else if (mesesAtraso == 3) {
            return (long) (monto * 1.09);
        }else{
            return (long) (monto * 1.15);
        }
    }

    public Long aplicarDescuento(int puntaje, Long monto){
        if(950 <= puntaje && puntaje<= 1000){
            return (long) (monto * 0.9);
        } else if (900 <= puntaje && puntaje<= 949) {
            return (long) (monto * 0.95);
        } else if (850 <= puntaje && puntaje<= 899) {
            return (long) (monto * 0.98);
        }else{
            return monto;
        }
    }


    public void verificarYActualizarMontos(String rut){
        ArrayList<CuotaEntity> cuotasEstudiante = cuotasNoPagadas(obtenerCutoasByRut(rut));

        //Intereses atraso
        int mesesAtraso;
        Calendar calendario = Calendar.getInstance();
        int mesActual = calendario.get(Calendar.MONTH);

        for (CuotaEntity cuota: cuotasEstudiante) {
            mesesAtraso = mesActual- cuota.getFecha_cuota().getMonthValue();
            cuota.setMonto_variable(aplicarInteres(mesesAtraso,cuota.getMonto()));
        }

        //aplicar descuentos

        ArrayList<PruebaEntity> pruebasEstudiante = pruebaService.obtenerPruebasEstudiante(rut);
        int promedio = pruebaService.obtenerPromedio(pruebasEstudiante);

        for (CuotaEntity cuota: cuotasEstudiante) {
            cuota.setMonto_variable(aplicarDescuento(promedio,cuota.getMonto_variable()));
            cuotaRepository.save(cuota);
        }
    }
}
