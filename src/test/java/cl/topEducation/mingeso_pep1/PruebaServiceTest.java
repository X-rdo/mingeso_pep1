package cl.topEducation.mingeso_pep1;


import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import cl.topEducation.mingeso_pep1.repositories.PruebaRepository;
import cl.topEducation.mingeso_pep1.services.PruebaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
public class PruebaServiceTest {

    @Mock
    PruebaRepository pruebaRepository;

    @InjectMocks
    PruebaService pruebaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void separarDDMMYYTest1(){
        String[] diaMesAnho = {"29","12","2000"};

        String[] fechaSeparada = pruebaService.separarDDMMYY("29-12-2000");
        assertEquals(diaMesAnho[0],fechaSeparada[0]);
        assertEquals(diaMesAnho[1],fechaSeparada[1]);
        assertEquals(diaMesAnho[2],fechaSeparada[2]);
    }


    @Test
    public void getAllFiles(){
        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2000,12,29));

        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);

        when(pruebaRepository.findAll()).thenReturn(pruebasMock);

        //Si hay un servicio que usa otros repositorio poner whens a todos

        ArrayList<PruebaEntity> pruebas = pruebaService.getAllFiles();
        assertEquals(1,pruebas.size());
    }

    @Test
    public void obtenerPorRuTest(){
        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2000,12,29));

        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);

        when(pruebaRepository.findByRut(anyString())).thenReturn(pruebasMock);
        ArrayList<PruebaEntity> pruebas = pruebaService.getPorRut("20.655.745-1");
        assertEquals(1,pruebas.size());
    }

    @Test
    public void filtrarPorAnhoActualTest1(){
        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2023,12,29));

        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);

        ArrayList<PruebaEntity> pruebas = pruebaService.filtrarPorAnhoActual(pruebasMock);
        assertEquals(950,pruebas.get(0).getPuntaje());

    }

    @Test
    public void obtenerPromedioTest(){
        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2023,12,29));

        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);

        int puntaje = pruebaService.obtenerPromedio(pruebasMock);

        assertEquals(950,puntaje);
    }



}
