package cl.topEducation.mingeso_pep1;



import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import cl.topEducation.mingeso_pep1.repositories.CuotaRepository;
import cl.topEducation.mingeso_pep1.repositories.EstudianteRepository;
import cl.topEducation.mingeso_pep1.services.CuotaService;
import cl.topEducation.mingeso_pep1.services.EstudianteService;
import cl.topEducation.mingeso_pep1.services.PruebaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
public class CuotaServiceTest {


    @Mock
    EstudianteRepository estudianteRepository;
    @Mock
    CuotaRepository cuotaRepository;
    @Mock
    EstudianteService estudianteService;
    @Mock
    PruebaService pruebaService;
    @InjectMocks
    private CuotaService cuotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void obtenerCuotasByRutTest(){

        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023,10,14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();

        cuotasMock.add(cuota);
        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);

        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCutoasByRut("20.655745-1");
        assertEquals(200339L, cuotas.get(0).getMonto());
    }

    @Test
    public void hayCuotasActualesTest1(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023,10,14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();

        cuotasMock.add(cuota);
        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        boolean hayCuotas = cuotaService.hayCuotasActuales("20.655.745-1");
        assertTrue(hayCuotas);
    }

    //                       20.655.745-1
    @Test
    public void separaNumeroYrutTest(){
        String[] numeroYRutMock = {"1","20.655.745-1"};

        String[] numeroYRut = cuotaService.separaNumeroYrut("1,20.655.745-1");
        assertEquals(numeroYRutMock[0],numeroYRut[0]);
        assertEquals(numeroYRutMock[1],numeroYRut[1]);
    }

    @Test
    public void separaRutTest(){
        String rut = cuotaService.separarRut("1,20.655.745-1");
        assertEquals("20.655.745-1",rut);
    }

    @Test
    public void cuotasNoPagadasTest(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023,10,14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        ArrayList<CuotaEntity> cuotas = cuotaService.cuotasNoPagadas(cuotasMock);
        assertEquals(1,cuotas.size());
    }


    @Test
    public void aplicarInteresTest1() {
        Long monto = 200000L;
        int mesAtraso = 0;
        Long montoFinal = cuotaService.aplicarInteres(mesAtraso,monto);
        assertEquals(200000L,montoFinal);
    }

    @Test
    public void aplicarInteresTest2() {
        Long monto = 200000L;
        int mesAtraso = 1;
        Long montoFinal = cuotaService.aplicarInteres(mesAtraso,monto);
        assertEquals(206000L,montoFinal);
    }

    @Test
    public void aplicarInteresTest3() {
        Long monto = 200000L;
        int mesAtraso = 2;
        Long montoFinal = cuotaService.aplicarInteres(mesAtraso,monto);
        assertEquals(212000L,montoFinal);
    }

    @Test
    public void aplicarInteresTest4() {
        Long monto = 200000L;
        int mesAtraso = 3;
        Long montoFinal = cuotaService.aplicarInteres(mesAtraso,monto);
        assertEquals(218000L,montoFinal);
    }

    @Test
    public void aplicarInteresTest5() {
        Long monto = 200000L;
        int mesAtraso = 4;
        Long montoFinal = cuotaService.aplicarInteres(mesAtraso,monto);
        assertEquals(229999L,montoFinal);
    }

    @Test
    public void aplicarDescuentoTest1() {
        Long monto = 200000L;
        int puntaje = 960;
        Long montoFinal = cuotaService.aplicarDescuento(puntaje,monto);
        assertEquals(180000L,montoFinal);
    }
    @Test
    public void aplicarDescuentoTest2() {
        Long monto = 200000L;
        int puntaje = 930;
        Long montoFinal = cuotaService.aplicarDescuento(puntaje,monto);
        assertEquals(190000L,montoFinal);
    }

    @Test
    public void aplicarDescuentoTest3() {
        Long monto = 200000L;
        int puntaje = 855;
        Long montoFinal = cuotaService.aplicarDescuento(puntaje,monto);
        assertEquals(196000L,montoFinal);
    }

    @Test
    public void aplicarDescuentoTest4() {
        Long monto = 200000L;
        int puntaje = 0;
        Long montoFinal = cuotaService.aplicarDescuento(puntaje,monto);
        assertEquals(200000L,montoFinal);
    }

    @Test
    public void montoTotalArancel() {
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023,10,14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        Long montoFinal = cuotaService.montoTotalArancel("20.655.745-1");
        assertEquals(200339L,montoFinal);
    }


    @Test
    public void hayCuotasActualesTest2() {
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2022, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);

        boolean hayCuotas = cuotaService.hayCuotasActuales("20.655.745-1");

        assertEquals(false,hayCuotas);
    }

    @Test
    public void cantidadCuotasTest() {
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        int cantCuotas = cuotaService.cantidadCuotas("20.655.745-1");

        assertEquals(1,cantCuotas);
    }

    @Test
    public void cantCuotasPagadasTest1(){

        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);
        int cantCuotas = cuotaService.cantidadCuotasPagadas("20.655.745-1");
        assertEquals(0,cantCuotas);


    }

    @Test
    public void cantCuotasPagadasTest2(){

        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);
        int cantCuotas = cuotaService.cantidadCuotasPagadas("20.655.745-1");
        assertEquals(1,cantCuotas);
    }

    @Test
    public void montoPagadoTest1(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        Long montoPagado = cuotaService.montoPagado("20.655.745-1");
        assertEquals(200339L,montoPagado);
    }

    @Test
    public void montoPorPagadarTest1(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        Long montoPagado = cuotaService.montoPorPagar("20.655.745-1");
        assertEquals(200339L,montoPagado);
    }

    @Test
    public void ultimoPagoTest(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(LocalDate.of(2023, 10, 14));
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        LocalDate ultimoPago = cuotaService.ultimoPago("20.655.745-1");
        assertEquals(LocalDate.of(2023, 10, 14),ultimoPago);
    }

    @Test
    public void cuotasRetrasadasTest1(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("No pagada");
        cuota.setFecha_pago(null);
        cuota.setFecha_cuota(LocalDate.of(2023, 4, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);

        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        int cantCuotasRetrasadas = cuotaService.cuotasRetrasadas("20.655.745-1");
        assertEquals(1,cantCuotasRetrasadas);
    }

    @Test
    public void datosEnterosTest(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(LocalDate.of(2023, 10, 14));
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);


        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2000,12,29));
        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);

        when(pruebaService.cantExamenesRendidos(anyString())).thenReturn(1);
        when(pruebaService.obtenerPruebasEstudiante(anyString())).thenReturn(pruebasMock);
        when(pruebaService.obtenerPromedio(any())).thenReturn(pruebaMock.getPuntaje());
        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        ArrayList<Integer> datosEnteros = cuotaService.datosEnteros("20.655.745-1");
        assertEquals((950),datosEnteros.get(1));
    }

    @Test
    public void datosLongTest(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(LocalDate.of(2023, 10, 14));
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);
        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        ArrayList<Long> datosLong = cuotaService.datosLong("20.655.745-1");
        assertEquals((200339L),datosLong.get(0));
    }


    @Test
    public void verificarYActualizarMontosTest(){
        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(LocalDate.of(2023, 10, 14));
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);


        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2000,12,29));
        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);

        when(pruebaService.cantExamenesRendidos(anyString())).thenReturn(1);
        when(pruebaService.obtenerPruebasEstudiante(anyString())).thenReturn(pruebasMock);
        when(pruebaService.obtenerPromedio(any())).thenReturn(pruebaMock.getPuntaje());
        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        cuotaService.verificarYActualizarMontos("20.655.745-1");
    }
    @Test
    public void cambiarTipoPago(){

        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2021);



        CuotaEntity cuota = new CuotaEntity();
        cuota.setId(1L);
        cuota.setNumero_cuota(1);
        cuota.setRut("20.655.745-1");
        cuota.setMonto(200339L);
        cuota.setMonto_variable(200339L);
        cuota.setEstado_pago("Pagado");
        cuota.setFecha_pago(LocalDate.of(2023, 10, 14));
        cuota.setFecha_cuota(LocalDate.of(2023, 10, 14));
        ArrayList<CuotaEntity> cuotasMock = new ArrayList<CuotaEntity>();
        cuotasMock.add(cuota);


        PruebaEntity pruebaMock = new PruebaEntity();
        pruebaMock.setId(1L);
        pruebaMock.setRut("20.655.745-1");
        pruebaMock.setPuntaje(950);
        pruebaMock.setFecha_examen(LocalDate.of(2000,12,29));
        ArrayList<PruebaEntity> pruebasMock = new ArrayList<PruebaEntity>();
        pruebasMock.add(pruebaMock);


        when(estudianteRepository.findById(anyString())).thenReturn(Optional.of(estudianteMock1));
        when(estudianteService.obtenerPorId(anyString())).thenReturn(Optional.of(estudianteMock1));
        when(pruebaService.cantExamenesRendidos(anyString())).thenReturn(1);
        when(pruebaService.obtenerPruebasEstudiante(anyString())).thenReturn(pruebasMock);
        when(pruebaService.obtenerPromedio(any())).thenReturn(pruebaMock.getPuntaje());
        when(cuotaRepository.findByRut(anyString())).thenReturn(cuotasMock);
        when(cuotaService.obtenerCutoasByRut(anyString())).thenReturn(cuotasMock);

        cuotaService.cambiarTipoPago("20.655.745-1",2);
    }

    @Test
    public void generarCuotaTest1(){

        EstudianteEntity estudianteMock1 = new EstudianteEntity();
        estudianteMock1.setRut("20.655.745-1");
        estudianteMock1.setNombres("Ricardo");
        estudianteMock1.setApellidos("Avaca");
        estudianteMock1.setFecha_nacimiento(LocalDate.of(2000, 12, 29));
        estudianteMock1.setTipoColegio(2);
        estudianteMock1.setNombre_colegio("Colegio San Francisco de Sales");
        estudianteMock1.setAnho_egreso_colegio(2021);
        estudianteMock1.setArancel(1500000L);


        when(estudianteService.obtenerPorId(anyString())).thenReturn(Optional.of(estudianteMock1));

        cuotaService.generarCuotas("20.655.745-1",3);

    }

}
