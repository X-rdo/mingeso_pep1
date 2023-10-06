package cl.topEducation.mingeso_pep1.controllers;



import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService;

    /*
    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<EstudianteEntity>estudiantes=estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiantes",estudiantes);
        return "index";
    }

     */

    //@requestParam string rur
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
        //return "redirect:/estudiante/listar";
    }




}
