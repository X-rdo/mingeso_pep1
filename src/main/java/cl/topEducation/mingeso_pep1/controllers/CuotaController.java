package cl.topEducation.mingeso_pep1.controllers;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.services.CuotaService;
import cl.topEducation.mingeso_pep1.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RequestMapping("/cuota")
@Controller
public class CuotaController {
    @Autowired
    CuotaService cuotaService;
    @Autowired
    EstudianteService estudianteService;

    @GetMapping("/generar-cuotas")
    public String guardarCuotas() {
        return "agregarCuotas";
    }


    @PostMapping("/generar-cuotas")
    public String generarCuotas(@RequestParam(name = "rut") String rut,
                                @RequestParam(name = "tipoPago") int tipoPago,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        //te tira de vuelta diciendo que no existe el estudiante o opcion
        if (estudianteService.verificarEstudiante(rut) && !cuotaService.hayCuotasActuales(rut)) {
            //verificas si existe el estudiante y si tiene cuotas de este año
            int tipoColegio = estudianteService.verificarEstablecimiento(rut);
            if (tipoPago == 1) {
                //El pago al contado se considera como una sola cuota "especial"
                cuotaService.generarCuotas(rut,-1);
                return "main_menu"; //
            } else if (tipoPago == 2) {
                //Se redirecciona por el tipo de colegio
                if(tipoColegio == 1){
                    redirectAttributes.addFlashAttribute("rut", rut);
                    return "redirect:/cuota/cuotaMunicipal";
                } else if (tipoColegio == 2) {
                    redirectAttributes.addFlashAttribute("rut", rut);
                    return "redirect:/cuota/cuotaSubencionado";
                } else {
                    //generarCuotas()
                    redirectAttributes.addFlashAttribute("rut", rut);
                    return "redirect:/cuota/cuotaPrivado";
                }
            }
        }
        //se redirecciona diciendo que no se encontró al usuario con determinado rut o que ya se generaron las cuotas
        // falta el alert y todo eso
        return "main_menu";
    }

    @GetMapping("/cuotaSubencionado")
    public String guardarCuotasSubencionado() {
        return "cuota/subencionado";
    }


    @PostMapping("/cuotaSubencionado")
    public String cuotasSubvencionado (@ModelAttribute("rut") String rut, @RequestParam int cantCuotas, Model model){
        cuotaService.generarCuotas(rut, cantCuotas);
        return "redirect:/";
    }

    @GetMapping("/cuotaMunicipal")
    public String guardarCuotasMunicipal() {
        return "cuota/municipal";
    }


    @PostMapping("/cuotaMunicipal")
    public String cuotasMunicipal (@ModelAttribute("rut") String rut, @RequestParam int cantCuotas, Model model){
        cuotaService.generarCuotas(rut, cantCuotas);
        return "redirect:/";
    }

    @GetMapping("/cuotaPrivado")
    public String guardarCuotasPrivado() {
        return "cuota/privado";
    }


    @PostMapping("/cuotaPrivado")
    public String cuotasPrivado (@ModelAttribute("rut") String rut, @RequestParam int cantCuotas, Model model){
        cuotaService.generarCuotas(rut, cantCuotas);
        return "redirect:/";
    }

    @GetMapping("/rut-estudiante-listar")
    public String rutEstudianteListar() {
        return "rut-estudiante-listar";
    }

    @PostMapping("/rut-estudiante-listar-cuota")
    public String listar(@RequestParam String rut, Model model){
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCutoasByRut(rut);
        model.addAttribute("cuotas",cuotas);
        return "listarCuotasEstudiante";
    }
}
