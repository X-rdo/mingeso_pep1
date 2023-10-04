package cl.topEducation.mingeso_pep1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class VentanasController {

    @GetMapping
    public String vistaEstudiante(){
        return "main_menu";
    }

}
