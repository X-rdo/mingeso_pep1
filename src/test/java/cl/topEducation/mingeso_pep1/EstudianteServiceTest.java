package cl.topEducation.mingeso_pep1;

import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.repositories.EstudianteRepository;
import cl.topEducation.mingeso_pep1.services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
public class EstudianteServiceTest {
    @Mock
    EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void guardarEstudianteTest1(){
        EstudianteEntity estudianteMock = new EstudianteEntity();
        estudianteMock.setRut("20.655.745-1");
        estudianteMock.setNombres("Ricardo");
        estudianteMock.setApellidos("Avaca");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock.setTipoColegio(2);
        estudianteMock.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock.setAnho_egreso_colegio(2018);


        EstudianteEntity estudiante = estudianteService.guardarEstudiante(estudianteMock);
        assertEquals(1350000.0, estudiante.getArancel());
    }
    @Test
    public void guardarEstudianteTest2(){
        EstudianteEntity estudianteMock = new EstudianteEntity();
        estudianteMock.setRut("20.655.745-1");
        estudianteMock.setNombres("Ricardo");
        estudianteMock.setApellidos("Avaca");
        estudianteMock.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock.setTipoColegio(1);
        estudianteMock.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock.setAnho_egreso_colegio(2023);


        EstudianteEntity estudiante = estudianteService.guardarEstudiante(estudianteMock);
        assertEquals(1020000.0, estudiante.getArancel());
    }

    @Test
    public void obtenerEstudianteTest1(){

        ArrayList<EstudianteEntity> listaEstudiantes = new ArrayList<EstudianteEntity>();
        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2021);

        EstudianteEntity estudianteMock2 = new EstudianteEntity();
        estudianteMock2.setRut("20.655.745-2");
        estudianteMock2.setNombres("Nicolas");
        estudianteMock2.setApellidos("Retamal");
        estudianteMock2.setFecha_nacimiento(LocalDate.of(1999, 12, 19));
        estudianteMock2.setTipoColegio(1);
        estudianteMock2.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock2.setAnho_egreso_colegio(2020);

        listaEstudiantes.add(estudianteMock1);
        listaEstudiantes.add(estudianteMock2);

        when(estudianteRepository.findAll()).thenReturn(listaEstudiantes);

        ArrayList<EstudianteEntity> estudiantesPrueba = estudianteService.obtenerEstudiantes();
        assertEquals(2, estudiantesPrueba.size());
    }

    @Test
    public void obtenerPorIdTest1(){
        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2022);

        when(estudianteRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock1));
        Optional<EstudianteEntity> estudiante = estudianteService.obtenerPorId("20.655.745-1");
        assertEquals("20.655.745-1",estudiante.get().getRut());
    }


    @Test
    public void verificarEstablecimientoTest1(){

        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2022);

        when(estudianteRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock1));
        int tipoColegio = estudianteService.verificarEstablecimiento("20.655.745-1");
        assertEquals(2, tipoColegio);
    }

    @Test
    public void verificarEstablecimientoTest2(){

        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2022);

        when(estudianteRepository.findById(anyString())).thenReturn(Optional.empty());
        int tipoColegio = estudianteService.verificarEstablecimiento("20.655.745-1");
        assertEquals(-1, tipoColegio);
    }


    @Test
    public void verificarEstudianteTest1(){

        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2022);

        when(estudianteRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock1));
        boolean estudianteEsta = estudianteService.verificarEstudiante("20.655.745-1");
        assertEquals(true, estudianteEsta);
    }

    @Test
    public void verificarEstudianteTest2(){

        when(estudianteRepository.findById(anyString())).thenReturn(Optional.empty());
        boolean estudianteEsta = estudianteService.verificarEstudiante("20.655.745-1");
        assertEquals(false, estudianteEsta);
    }



}
