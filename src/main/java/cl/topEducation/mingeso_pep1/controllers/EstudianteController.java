package cl.topEducation.mingeso_pep1.controllers;



import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService;


    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<EstudianteEntity>estudiantes=estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiantes",estudiantes);
        return "index";
    }


    @GetMapping("/nuevo-estudiante")
    public String nuevoAlumno(Model model){
        EstudianteEntity estudiante = new EstudianteEntity();
        model.addAttribute("estudiante", estudiante);
        return "agregarEstudiante";
    }


    @PostMapping("/guardar-estudiante")
    public String guardarAlumno(@ModelAttribute("estudiante") EstudianteEntity estudiante, Model model){
        estudianteService.guardarEstudiante(estudiante);
        ArrayList<EstudianteEntity>estudiantes=estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiantes",estudiantes);
        return "main_menu";
    }


    //HU7
    /*
    @PostMapping("/resumen-estado-pago")
    public String resumenEstadoPago(@RequestParam String rut, Model model){
        Optional<EstudianteEntity> estudiante = estudianteService.obtenerPorId(rut);
        model.addAttribute("estudiante",estudiante);
        return "por definir";
    }


    @PostMapping("/resumen-estado-pago-datos")
    public String listarEstadoPago(@ModelAttribute("rut") EstudianteEntity estudiante, Model model){



        //ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCutoasByRut(cuotaService.separarRut(numeroYRut));
        //model.addAttribute("cuotas",cuotas);
        return "listarCuotasEstudiantePago";
    }
    */


}
