package com.CordyTech.cl.Puerto.controller;

import com.CordyTech.cl.Puerto.model.Puerto;
import com.CordyTech.cl.Puerto.service.PuertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/puertos")
public class PuertoController {

    @Autowired
    private PuertoService puertoService;

    @GetMapping
    public List<Puerto> listar() {
        return puertoService.listarPuertos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Puerto> obtenerPorId(@PathVariable int id) {
        return puertoService.obtenerPuertoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombres")
    public ResponseEntity<Puerto> obtenerPuertoPorNombre(@PathVariable String nombrePuerto) {
        return puertoService.obtenerPuertoPorNombre(nombrePuerto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Puerto> createPuerto(@RequestBody Puerto puerto) {
        Puerto nuevoPuerto = puertoService.saveBuque(puerto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPuerto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        puertoService.eliminarPuerto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public List<Puerto> getPuertosDisponibles() {
        return puertoService.getDisponibles();
    }

    @GetMapping("/tarifas")
    public List<Map<String, Object>> getTarifasPuertos(){
        return puertoService.getTarifas();
    }

    @GetMapping("/{id}/tarifas")
    public Map<String, Object> getTarifasById(@PathVariable int id){
        return puertoService.getTarifasById(id);
    }
}
