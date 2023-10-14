package cl.topEducation.mingeso_pep1.services;

import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import cl.topEducation.mingeso_pep1.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PruebaService {

    @Autowired
    PruebaRepository pruebaRepository;

    private final Path root = Paths.get("cargas");

    public void inicializacion() {
        try {
            Files.createDirectories((Path) root);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la carpeta cargas.");
        }
    }

    public void guardarArchivo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName != null){
            if(!file.isEmpty()){
                try {
                    inicializacion();
                    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    throw new RuntimeException("No se pudo guardar el archivo. Error: " + e.getMessage());
                }
            }
        }
    }


    public void leerCsv(String fileName) {
        String linea = "";
        String csvSplit = ";";
        String root = "cargas/";
        try {
            BufferedReader br = new BufferedReader(new FileReader(root + fileName));
            int primeraLinea = 1;
            int dia;
            int mes;
            int anho;

            String[] ddmmyy;
            while((linea = br.readLine()) != null) {
                if(primeraLinea == 1) {
                    String[] data = linea.split(csvSplit);
                    if(data[0].equals("Rut") || data[1].equals("Fecha") || data[2].equals("Puntaje")) {
                        primeraLinea += 1;
                    }else{
                        br.close();
                        throw new RuntimeException("El archivo no tiene el formato correcto.");
                    }
                } else {
                    String[] data = linea.split(csvSplit);
                    PruebaEntity prueba = new PruebaEntity();
                    prueba.setRut(data[0]);

                    ddmmyy = separarDDMMYY(data[1]);
                    dia = Integer.parseInt(ddmmyy[0]);
                    mes = Integer.parseInt(ddmmyy[1]);
                    anho = Integer.parseInt(ddmmyy[2]);

                    prueba.setFecha_examen(LocalDate.of(anho,mes,dia));
                    prueba.setPuntaje(Integer.parseInt(data[2]));
                    pruebaRepository.save(prueba);
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException("No se encontró el archivo. Error: " + e.getMessage());
        } finally {
            System.out.println("Lectura de archivo finalizada.");
        }
    }

    public static String[] separarDDMMYY(String diaMesAnho) {
        String[] valores = diaMesAnho.split("-");
        return valores;
    }

    public ArrayList<PruebaEntity> getAllFiles() {
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }
}
