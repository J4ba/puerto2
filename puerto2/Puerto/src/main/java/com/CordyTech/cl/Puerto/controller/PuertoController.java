package com.CordyTech.cl.Puerto.controller;

import com.CordyTech.cl.Puerto.model.Puerto;
import com.CordyTech.cl.Puerto.service.PuertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    public ResponseEntity<EntityModel<Puerto>> obtenerPorId(@PathVariable int id) {
        return puertoService.obtenerPuertoPorId(id)
                .map(puerto -> {
                    EntityModel<Puerto> recurso = EntityModel.of(puerto,
                            linkTo(methodOn(PuertoController.class).obtenerPorId(id)).withSelfRel(),
                            linkTo(methodOn(PuertoController.class).getTarifasById(id)).withRel("tarifas"),
                            linkTo(methodOn(PuertoController.class).listar()).withRel("puertos"));

                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombres")
    public ResponseEntity<Puerto> obtenerPuertoPorNombre(@PathVariable String nombrePuerto) {
        return puertoService.obtenerPuertoPorNombre(nombrePuerto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Puerto>> guardar(@RequestBody Puerto puerto) {
        Puerto guardado = puertoService.guardarPuerto(puerto);

        EntityModel<Puerto> recurso = EntityModel.of(guardado,
                linkTo(methodOn(PuertoController.class).obtenerPorId(guardado.getIdPuerto())).withSelfRel(),
                linkTo(methodOn(PuertoController.class).listar()).withRel("todos"));

        return ResponseEntity
                .created(linkTo(methodOn(PuertoController.class).obtenerPorId(guardado.getIdPuerto())).toUri())
                .body(recurso);
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
    public ResponseEntity<EntityModel<Map<String, Object>>> getTarifasById(@PathVariable int id) {
        Map<String, Object> tarifas = puertoService.getTarifasById(id);

        EntityModel<Map<String, Object>> recurso = EntityModel.of(tarifas,
                linkTo(methodOn(PuertoController.class).getTarifasById(id)).withSelfRel(),
                linkTo(methodOn(PuertoController.class).obtenerPorId(id)).withRel("puerto"));

        return ResponseEntity.ok(recurso);
    }

}