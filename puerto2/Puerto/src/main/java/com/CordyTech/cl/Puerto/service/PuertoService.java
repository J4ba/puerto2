package com.CordyTech.cl.Puerto.service;

import com.CordyTech.cl.Puerto.model.Puerto;
import com.CordyTech.cl.Puerto.repository.PuertoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PuertoService {

    @Autowired
    private PuertoRepository puertoRepository;

    public List<Puerto> listarPuertos() {
        return puertoRepository.findAll();
    }

    public Puerto guardarPuerto(Puerto puerto) {
        return puertoRepository.save(puerto);
    }

    public Optional<Puerto> obtenerPuertoPorId(int idPuerto) {
        return puertoRepository.findById(idPuerto);
    }

    public Optional<Puerto> obtenerPuertoPorNombre(String nombrePuerto) {
        return puertoRepository.findByNombrePuerto(nombrePuerto);
    }

    public void eliminarPuerto(int id) {
        puertoRepository.deleteById(id);
    }

    public List<Puerto> getDisponibles() {
        return puertoRepository.findByDispoTrue();
    }

    public List<Map<String, Object>> getTarifas() {
        List<Puerto> puertos = puertoRepository.findAll();
                return puertos.stream().map(p -> {
                    Map<String, Object> tarifas = new HashMap<>();
                    tarifas.put("Nombre Puerto", p.getNombrePuerto());
                    tarifas.put("Tarifa Por Hora", p.getTarifaHora());
                    tarifas.put("Tarifa Por Eslora", p.getTarifaEslora());
                    return tarifas;
                }).collect(Collectors.toList());

    }

    public Map<String, Object> getTarifasById(int idPuerto) {
        Puerto puerto = puertoRepository.findById(idPuerto).orElseThrow(()-> new RuntimeException("Puerto no encontrado"));
            Map<String, Object> tarifas = new HashMap<>();
            tarifas.put("Nombre Puerto", puerto.getNombrePuerto());
            tarifas.put("Tarifa Por Hora", puerto.getTarifaHora());
            tarifas.put("Tarifa Por Eslora", puerto.getTarifaEslora());
            return tarifas;

    }
}
