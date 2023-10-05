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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@RequestMapping("/cuota")
@Controller
public class CuotaController {
    @Autowired
    CuotaService cuotaService;


    //verificar establecimiento
    @GetMapping("/generear-cuotas")
    public String generarCuotas(Model model){
        ArrayList<CuotaEntity> cuotas = new ArrayList<CuotaEntity>();
        model.addAttribute("cuotas", cuotas);
        return "agregarCuotas";
    }
    @GetMapping("/guardar-cuotas")
    public String guardarCuotas(Model model){
        ArrayList<CuotaEntity> cuotas = new ArrayList<CuotaEntity>();
        model.addAttribute("cuotas", cuotas);
        return "agregarCuotas";
    }

    @GetMapping
    public String ejemplo(@RequestParam(name= "rut") String rut,
                          @RequestParam(name = "tipoPago") int tipoPago,
                          Model model){
        //Logica del controlador

        if(tipoPago == 1){ //contado
            return "main_menu"; //
        }else{//buscar por rut el tipo de colegio

            // ver que tipos de vista van a llegar

            /*
            if(tipoColegio){
                //redireccionamos a las paginas correspondientes
            }
            */

            return "main_menu";

        }


    }

    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<CuotaEntity> cuotas=cuotaService.obtenerCuotas();
        model.addAttribute("cuota",cuotas);
        return "index";
    }
}
