package cl.topEducation.mingeso_pep1.controllers;

import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import cl.topEducation.mingeso_pep1.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RequestMapping
@Controller
public class PruebaController {

    @Autowired
    PruebaService pruebaService;

    @GetMapping("/cargar-archivo")
    public String cargarArchivo(){
        return "cargar-archivo";
    }

    @PostMapping("/cargar-archivo")
    public String cargarArchivoPost(
            @RequestParam("archivo") MultipartFile archivo,
            RedirectAttributes redirectAttributes) {
        pruebaService.guardarArchivo(archivo);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo Subido con éxito! " + archivo.getOriginalFilename() + "!");
        pruebaService.leerCsv(archivo.getOriginalFilename());
        return "redirect:/cargar-archivo";
    }

    @GetMapping("/archivo-info")
    public String archivoInfo(Model model) {
        ArrayList<PruebaEntity> archivo = pruebaService.getAllFiles();
        model.addAttribute("archivo", archivo);
        return "archivo-info";
    }

}
