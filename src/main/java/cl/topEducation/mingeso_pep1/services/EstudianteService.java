package cl.topEducation.mingeso_pep1.services;

import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    public ArrayList<EstudianteEntity> obtenerEstudiantes(){
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }

    public EstudianteEntity guardarEstudiante(EstudianteEntity estudiante){
        estudiante.setArancel(generarArancel(estudiante));
        return estudianteRepository.save(estudiante);}

    public Optional<EstudianteEntity> obtenerPorId(String id){return estudianteRepository.findById(id);}

    public double generarArancel(EstudianteEntity estudiante) {
            double arancel = 1500000;
            //Aplica descuento por tipo de colegio
            if (estudiante.getTipoColegio() == 1){
                arancel = arancel * 0.8;
            } else if (estudiante.getTipoColegio() == 2){
                arancel = arancel * 0.9;
            }
            //Aplica descuento por años desde que egresó
            LocalDate current_date = LocalDate.now();
            int current_Year = current_date.getYear();
            int egreso = estudiante.getAnho_egreso_colegio();
            if(current_Year - egreso == 0){
                arancel = arancel * 0.85;
            } else if (current_Year - egreso < 3) {
                arancel = arancel * 0.92;
            } else if (current_Year - egreso < 5) {
                arancel = arancel*0.96;
            }
            return arancel;
    }

    public int verificarEstablecimiento(EstudianteEntity estudiante){
        if(1 == estudiante.getTipoColegio()){
            return 1;
        } else if (2 == estudiante.getTipoColegio()) {
            return 2;
        } else {
            return 3;
        }
    }


}
