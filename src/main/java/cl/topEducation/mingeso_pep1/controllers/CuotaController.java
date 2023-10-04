package cl.topEducation.mingeso_pep1.controllers;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.services.CuotaService;
import cl.topEducation.mingeso_pep1.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping("/cuota")
@Controller
public class CuotaController {
    @Autowired
    CuotaService cuotaService;

    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<CuotaEntity> cuotas=cuotaService.obtenerCuotas();
        model.addAttribute("cuota",cuotas);
        return "index";
    }
}
