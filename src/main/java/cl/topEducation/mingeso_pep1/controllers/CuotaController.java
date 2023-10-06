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
    EstudianteService estudianteService;


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
    public String pedido(@RequestParam(name= "rut") String rut,
                          @RequestParam(name = "tipoPago") int tipoPago,
                          Model model){
        //Logica del controlador

        //Si Tiene cuotas
        //ver el tema de englobar todo en una option si no se cumple lo requerido
        //te tira de vuelta diciendo que no existe el estudiante o opcion

        if(estudianteService.verificarEstudiante(rut) ){//verificas si existe el estudiante
            if(tipoPago == 1 ){ //contado te deriva al arancel y todo
                pagoContado(rut) //Una cuota gigante pero con el arancel a la mitad
                return "main_menu"; //
            }else if(tipoPago == 2){
                //buscar por rut el tipo de colegio
                //if(tipoColegio)
                //return direccion para determinado tipo de colegio
                // ver que tipos de vista van a llegar

            /*
            if(tipoColegio){
                //redireccionamos a las paginas correspondientes
            }
            */
        }
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
