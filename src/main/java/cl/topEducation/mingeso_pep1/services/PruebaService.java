package cl.topEducation.mingeso_pep1.services;

import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import cl.topEducation.mingeso_pep1.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

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

    public String[] separarDDMMYY(String diaMesAnho) {
        String[] valores = diaMesAnho.split("-");
        return valores;
    }

    public ArrayList<PruebaEntity> getAllFiles() {
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }

    public ArrayList<PruebaEntity> getPorRut(String rut){
        return (ArrayList<PruebaEntity>) pruebaRepository.findByRut(rut);
    }

    public ArrayList<PruebaEntity> filtrarPorAnhoActual(ArrayList<PruebaEntity> pruebasEstudiante){
        ArrayList<PruebaEntity> pruebasAnhoActual = new ArrayList<PruebaEntity>();
        Calendar calendario = Calendar.getInstance();
        int anhoActual = calendario.get(Calendar.YEAR);
        for (PruebaEntity prueba: pruebasEstudiante) {
            if(prueba.getFecha_examen().getYear() == anhoActual){
                pruebasAnhoActual.add(prueba);
            }
        }
        return pruebasAnhoActual;
    }

    public int obtenerPromedio(ArrayList<PruebaEntity> pruebas){
        int sumaPuntajes= 0;
        int cantidadPruebas = 0;
        for (PruebaEntity prueba: pruebas) {
            sumaPuntajes += prueba.getPuntaje();
            cantidadPruebas +=1;
        }
        if(cantidadPruebas == 0){
            cantidadPruebas =1;
        }
        return (sumaPuntajes / cantidadPruebas);
    }

    //Se obtienen pruebas del año actual de determinado estudiante
    public ArrayList<PruebaEntity> obtenerPruebasEstudiante(String rut){
        return filtrarPorAnhoActual(getPorRut(rut));
    }

    public int cantExamenesRendidos(String rut){
        ArrayList<PruebaEntity> pruebasEstudiante = obtenerPruebasEstudiante(rut);
        if (pruebasEstudiante.isEmpty()){
            return 0;
        }else {
            return pruebasEstudiante.size();
        }
    }



}