package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clientes.colaboradores.ColaboradoresProxy;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.model.controller.dtos.*;
import ar.edu.utn.dds.k3003.model.mappers.HeladeraMapper;
import ar.edu.utn.dds.k3003.model.mappers.RetiroMapper;
import ar.edu.utn.dds.k3003.repositories.AlertaRepository;
import ar.edu.utn.dds.k3003.repositories.HeladeraRepository;
import ar.edu.utn.dds.k3003.model.mappers.TemperaturaMapper;
import ar.edu.utn.dds.k3003.repositories.RetiroRepository;
import ar.edu.utn.dds.k3003.repositories.TemperaturaRepository;
import ar.edu.utn.dds.k3003.utils.MetricsRegistry;
import io.micrometer.core.instrument.Gauge;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Fachada implements FachadaHeladeras {

 private final HeladeraRepository heladerasRepository;
 private final HeladeraMapper heladeraMapper;

 private final TemperaturaRepository temperaturaRepository;
 private final TemperaturaMapper temperaturaMapper;

 private final AlertaRepository alertaRepository;
 private final RetiroRepository retiroRepository;
 private final RetiroMapper retiroMapper;

    private FachadaViandas fachadaViandas;
 private ColaboradoresProxy fachadaColaboradores;

    // Un mapa que contendrá las métricas de cantidad de viandas por heladeras
 private ConcurrentHashMap<Long, AtomicReference<Integer>> viandasPorHeladeras;
 private ConcurrentHashMap<Long, AtomicReference<Integer>> aperturasPorHeladeras;

 public Fachada() {
  this.heladerasRepository = new HeladeraRepository();
  this.heladeraMapper = new HeladeraMapper();
  this.temperaturaRepository= new TemperaturaRepository();
  this.temperaturaMapper = new TemperaturaMapper();
  this.alertaRepository = new AlertaRepository();
  this.retiroRepository = new RetiroRepository();
  this.retiroMapper = new RetiroMapper();
  inicializarCantidadViandasPorHeladeras();
  inicializarCantidadAperturasxHeladera();
 }

    public Fachada(HeladeraRepository heladerasRepository, HeladeraMapper heladeraMapper,
                   TemperaturaRepository temperaturaRepository, TemperaturaMapper temperaturaMapper,
                   AlertaRepository alertaRepository, RetiroRepository retiroRepository, RetiroMapper retiroMapper) {
        this.heladerasRepository = heladerasRepository;
        this.heladeraMapper = heladeraMapper;
        this.temperaturaRepository = temperaturaRepository;
        this.temperaturaMapper = temperaturaMapper;
        this.alertaRepository = alertaRepository;
        this.retiroRepository = retiroRepository;
        this.retiroMapper = retiroMapper;
    }

    private void inicializarCantidadAperturasxHeladera() {
        this.aperturasPorHeladeras = new ConcurrentHashMap<>();
        var heladeras = this.heladerasRepository.findAllwithCantAperturas();

        heladeras.forEach(heladera -> {
            Long heladeraId = (Long) heladera[0];
            int cantidadAperturas = 0;

            if (heladera[1] != null) {
                cantidadAperturas = (int) heladera[1];
            }

            actualizarMetricacantidadAperturasHeladera(heladeraId, cantidadAperturas);
        });
    }

    private void actualizarMetricacantidadAperturasHeladera(Long heladeraId, int cantidadAperturas) {
        aperturasPorHeladeras.computeIfAbsent(heladeraId, id -> {
            // Crear y registrar una nueva métrica si no existe para esta heladera
            AtomicReference<Integer> cantidadAperturasRef = new AtomicReference<>(cantidadAperturas);
            Gauge.builder("heladera.aperturas.actual", cantidadAperturasRef, AtomicReference::get)
                    .description("Cantidad de aperturas actuales de la heladera " + heladeraId)
                    .tag("heladeraId", String.valueOf(heladeraId))
                    .register(MetricsRegistry.getRegistry());
            return cantidadAperturasRef;
        }).set(cantidadAperturas);  // Actualizamos la temperatura si ya existe
    }

    private void inicializarCantidadViandasPorHeladeras() {

     this.viandasPorHeladeras = new ConcurrentHashMap<>();
     var heladeras = this.heladerasRepository.findAllwithCantViandas();

     heladeras.forEach(heladera -> {
         Long heladeraId = (Long) heladera[0];
         int cantidadViandas = (int) heladera[1];
         actualizarMetricacantidadViandasHeladera(heladeraId, cantidadViandas);
     });
    }
    // Registrar o actualizar la métrica para una heladera específica

    private void actualizarMetricacantidadViandasHeladera(Long heladeraId, int cantidadViandas) {
        viandasPorHeladeras.computeIfAbsent(heladeraId, id -> {
            // Crear y registrar una nueva métrica si no existe para esta heladera
            AtomicReference<Integer> cantidadViandasRef = new AtomicReference<>(cantidadViandas);
            Gauge.builder("heladera.viandas.actual", cantidadViandasRef, AtomicReference::get)
                    .description("Cantidad de viandas actual de la heladera " + heladeraId)
                    .tag("heladeraId", String.valueOf(heladeraId))
                    .register(MetricsRegistry.getRegistry());
            return cantidadViandasRef;
        }).set(cantidadViandas);  // Actualizamos la temperatura si ya existe
    }

    @Override public HeladeraDTO agregar(HeladeraDTO heladeraDTO){
    Heladera heladera= new Heladera(heladeraDTO.getNombre());
    heladera = this.heladerasRepository.save(heladera);
    return heladeraMapper.map(heladera);
    }

    @Override public void depositar(Integer heladeraId, String qrVianda) throws NoSuchElementException {
        Heladera heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));
       ViandaDTO vianda = this.fachadaViandas.buscarXQR(qrVianda);

//        if(vianda.getEstado() != EstadoViandaEnum.PREPARADA
//                && vianda.getEstado()!=EstadoViandaEnum.EN_TRASLADO){
//            throw new RuntimeException("La Vianda "+qrVianda+ " no esta preparada ni en traslado, no se puede depositar");
//        }
        fachadaViandas.modificarEstado(vianda.getCodigoQR(), EstadoViandaEnum.DEPOSITADA);
        fachadaViandas.modificarHeladera(vianda.getCodigoQR(),heladeraId);
        heladera.depositarVianda();
        this.heladerasRepository.update(heladera);
        actualizarMetricacantidadViandasHeladera(heladera.getId(), heladera.getViandas());
        actualizarMetricacantidadAperturasHeladera(heladera.getId(),heladera.getCantidadAperturas());
    }

    @Override public Integer cantidadViandas(Integer heladeraId) throws NoSuchElementException{
        Heladera heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));
      return heladera.getViandas();
 }

    @Override public void retirar(RetiroDTO retiro) throws NoSuchElementException{

     Heladera heladera = this.heladerasRepository.findById(Long.valueOf(retiro.getHeladeraId()))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + retiro.getHeladeraId()));
        ViandaDTO vianda = this.fachadaViandas.buscarXQR(retiro.getQrVianda());

        if(vianda.getEstado()!=EstadoViandaEnum.DEPOSITADA){
            throw new RuntimeException("La Vianda " + retiro.getQrVianda() + " no esta depositada, no se puede retirar");
        }
        if(!Objects.equals(vianda.getHeladeraId(), retiro.getHeladeraId())){//la heladera de la que se quiere retirar la vianda es la heladera en la que realmente esta la vianda
            throw new RuntimeException("La Vianda " + retiro.getQrVianda() + " no esta depositada en esta heladera " + retiro.getHeladeraId());
        }
       
        try {
            heladera.retirarVianda();
            fachadaViandas.modificarEstado(vianda.getCodigoQR(), EstadoViandaEnum.RETIRADA);
            fachadaViandas.modificarHeladera(vianda.getCodigoQR(),-1);
            this.retiroRepository.save(new RegistroRetiro(vianda.getCodigoQR(), heladera));
            this.heladerasRepository.update(heladera);
            actualizarMetricacantidadViandasHeladera(heladera.getId(), heladera.getViandas());
            actualizarMetricacantidadAperturasHeladera(heladera.getId(),heladera.getCantidadAperturas());
        } catch (Exception e) {
            throw new RuntimeException("No hay viandas para retirar");
        }
    }

    @Override public void temperatura(TemperaturaDTO temperaturaDTO){
        Heladera heladera = this.heladerasRepository.findById(Long.valueOf(temperaturaDTO.getHeladeraId()))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + temperaturaDTO.getHeladeraId()));

        heladera.setUltimaConexion(LocalDateTime.now());
        var temperaturaMax = heladera.getTemperaturaMax();
        var temperaturaMin = heladera.getTemperaturaMin();
        float temperaturaActual = (float) temperaturaDTO.getTemperatura();

        if(temperaturaActual >= temperaturaMax){
            this.reportarAlerta(new AlertaDTO(Math.toIntExact(heladera.getId()), TipoAlerta.TEMPERATURA_ALTA));
        }
        if(temperaturaActual <= temperaturaMin){
            this.reportarAlerta(new AlertaDTO(Math.toIntExact(heladera.getId()), TipoAlerta.TEMPERATURA_BAJA));
        }

        Temperatura temperatura = new Temperatura(temperaturaDTO.getTemperatura(),heladera, LocalDateTime.now());
        this.temperaturaRepository.save(temperatura);
    }

    @Override public List<TemperaturaDTO> obtenerTemperaturas(Integer heladeraId){
        Heladera heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));

        List<Temperatura> temperaturas= temperaturaRepository.findAllById(heladera.getId());

        List<TemperaturaDTO> temperaturaDTOS = temperaturas.stream()
                .map(temperaturaMapper::map)
                .collect(Collectors.toList());

        Collections.reverse(temperaturaDTOS);
     return temperaturaDTOS;
    }

    @Override public void setViandasProxy(FachadaViandas viandas){
     this.fachadaViandas=viandas;
    }

    public void setColaboradoresProxy(ColaboradoresProxy colaboradores){
        this.fachadaColaboradores = colaboradores;
    }

    public HeladeraIdDTO obtenerHeladera(Integer id){
        Heladera heladera = this.heladerasRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + id));

        return heladeraMapper.mapId(heladera);
    }
    public Heladera obtenerHeladeraOrigin(Integer id){
        Heladera heladera = this.heladerasRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + id));
        return heladera;
    }
    public boolean clean() {
    temperaturaRepository.findAll().forEach(temperaturaRepository::delete);
     heladerasRepository.findAll().forEach(heladerasRepository::delete);
        return heladerasRepository.findAll().isEmpty() && temperaturaRepository.findAll().isEmpty();
    }

    public List<HeladeraDTO> obtenerHeladeras() {
        return heladeraMapper.originToListDTO(this.heladerasRepository.findAll());
    }

    public void repararHeladera(AlertaDTO alertaDTO) {
        var heladera = this.heladerasRepository.findById(Long.valueOf(alertaDTO.getHeladeraId()))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + alertaDTO.getHeladeraId()));

        var alerta = new Alerta(heladera, alertaDTO.getTipoAlerta());
        this.alertaRepository.save(alerta);
        heladera.reparar();

        this.heladerasRepository.update(heladera);
    }

    public void agregarSuscriptor(Integer id, SuscripcionDTO suscripcionDTO) {

     var heladera = this.heladerasRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + id));

        this.fachadaColaboradores.buscarXId(Long.valueOf(suscripcionDTO.colaboradorId));

        var colaboradorSuscrito = new ColaboradorSuscrito(suscripcionDTO.colaboradorId,
                suscripcionDTO.maximoViandas, suscripcionDTO.minimoViandas, suscripcionDTO.reportarIncidentes);

        heladera.agregarSuscriptor(colaboradorSuscrito);

        this.heladerasRepository.update(heladera);
    }

    public Heladera reportarAlerta(AlertaDTO alertaDTO) {


        var heladera = this.heladerasRepository.findById(Long.valueOf(alertaDTO.getHeladeraId()))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + alertaDTO.getHeladeraId()));

        var alerta = new Alerta(heladera, alertaDTO.getTipoAlerta());

        this.alertaRepository.save(alerta);

        List<ColaboradorSuscrito> colaboradores = heladera.getColaboradores();

        boolean heladeraActiva = true;

        switch (alertaDTO.getTipoAlerta()){
            case FALLA_TECNICA, SIN_CONEXION, TEMPERATURA_ALTA, TEMPERATURA_BAJA, MOVIMIENTO -> {

                if(heladera.isActiva()){
                    colaboradores = colaboradores.stream().filter(ColaboradorSuscrito::getReportarIncidente).toList();
                    heladera.falla(alertaDTO.getTipoAlerta());
                } else {
                    heladeraActiva = false;
                }
            }
            case MAXIMOVIANDAS -> {
                colaboradores = colaboradores.stream()
                        .filter(colaboradorSuscrito -> colaboradorSuscrito.getMaximoViandas() != null)
                        .filter(colaboradorSuscrito -> heladera.getViandas()  >= colaboradorSuscrito.getMaximoViandas()).toList();
            }
            case MINIMOVIANDAS ->{
                colaboradores = colaboradores.stream()
                        .filter(colaboradorSuscrito -> colaboradorSuscrito.getMinimoViandas() != null)
                        .filter(colaboradorSuscrito -> heladera.getViandas()  <= colaboradorSuscrito.getMinimoViandas()).toList();
            }
            default -> colaboradores.clear();
        }

        if(!colaboradores.isEmpty() && heladeraActiva){
            alertaDTO.setColaboradoresId(colaboradores.stream().map(ColaboradorSuscrito::getColaboradorId).toList());
            this.fachadaColaboradores.reportarAlerta(alertaDTO);
        }
        return this.heladerasRepository.update(heladera);
    }

    public AlertaHeladeraDTO obtenerAlertas(Integer heladeraId) {

        var heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));

        var registros = this.alertaRepository.findAllById(heladera.getId()).stream().map(Alerta::mapRegistro).toList();

        return new AlertaHeladeraDTO(Math.toIntExact(heladera.getId()), registros);
    }

    public void eliminarSuscriptor(Integer heladeraId, Integer colaboradorId) {

        var heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));

        heladera.eliminarColaborador(colaboradorId);

        this.heladerasRepository.update(heladera);
    }

    public List<RegistroRetiroDTO> obtenerRetirosDelDia(Integer heladeraId) {

        var heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));

     return this.retiroRepository.findRetirosByHeladeraAndToday(heladera.getId())
             .stream().map(this.retiroMapper::map).toList();
    }

    public MensajeCapacidadDTO obtenerMensajeCapacidad(Integer heladeraId) {
        var heladera = this.heladerasRepository.findById(Long.valueOf(heladeraId))
                .orElseThrow(() -> new NoSuchElementException("Heladera no encontrada id: " + heladeraId));

        return new MensajeCapacidadDTO(heladera.GetMensajeCapacidad());
    }
}
