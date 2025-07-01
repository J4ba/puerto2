package com.CordyTech.cl.Puerto;

import com.CordyTech.cl.Puerto.model.Puerto;
import com.CordyTech.cl.Puerto.repository.PuertoRepository;
import com.CordyTech.cl.Puerto.service.PuertoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PuertoApplicationTests {

	private PuertoRepository puertoRepository;
	private PuertoService puertoService;

	@BeforeEach
	void setUp() {
		puertoRepository = mock(PuertoRepository.class);
		puertoService = new PuertoService();
		puertoService.setPuertoRepository(puertoRepository);
	}

	@Test
	void testListarPuertos() {
		List<Puerto> lista = List.of(new Puerto(1, "Valparaíso", 100, 20, true));
		when(puertoRepository.findAll()).thenReturn(lista);

		List<Puerto> resultado = puertoService.listarPuertos();

		assertEquals(1, resultado.size());
		assertEquals("Valparaíso", resultado.get(0).getNombrePuerto());
	}

	@Test
	void testGuardarPuerto() {
		Puerto puerto = new Puerto(1, "San Antonio", 120, 30, true);
		when(puertoRepository.save(puerto)).thenReturn(puerto);

		Puerto resultado = puertoService.guardarPuerto(puerto);

		assertEquals("San Antonio", resultado.getNombrePuerto());
		verify(puertoRepository, times(1)).save(puerto);
	}

	@Test
	void testObtenerPuertoPorId() {
		Puerto puerto = new Puerto(1, "Iquique", 90, 25, true);
		when(puertoRepository.findById(1)).thenReturn(Optional.of(puerto));

		Optional<Puerto> resultado = puertoService.obtenerPuertoPorId(1);

		assertTrue(resultado.isPresent());
		assertEquals("Iquique", resultado.get().getNombrePuerto());
	}

	@Test
	void testGetDisponibles() {
		List<Puerto> disponibles = List.of(
				new Puerto(1, "Puerto Montt", 110, 22, true),
				new Puerto(2, "Talcahuano", 105, 20, true)
		);
		when(puertoRepository.findByDispoTrue()).thenReturn(disponibles);

		List<Puerto> resultado = puertoService.getDisponibles();

		assertEquals(2, resultado.size());
		assertTrue(resultado.get(0).isDispo());
	}

	@Test
	void testGetTarifas() {
		List<Puerto> puertos = List.of(
				new Puerto(1, "Coquimbo", 95, 19, true)
		);
		when(puertoRepository.findAll()).thenReturn(puertos);

		List<Map<String, Object>> resultado = puertoService.getTarifas();

		assertEquals(1, resultado.size());
		assertEquals("Coquimbo", resultado.get(0).get("Nombre Puerto"));
		assertEquals(95.0f, resultado.get(0).get("Tarifa Por Hora"));
		assertEquals(19.0f, resultado.get(0).get("Tarifa Por Eslora"));
	}

	@Test
	void testGetTarifasById() {
		Puerto puerto = new Puerto(1, "Antofagasta", 130, 35, true);
		when(puertoRepository.findById(1)).thenReturn(Optional.of(puerto));

		Map<String, Object> tarifas = puertoService.getTarifasById(1);

		assertEquals("Antofagasta", tarifas.get("Nombre Puerto"));
		assertEquals(130.0f, tarifas.get("Tarifa Por Hora"));
		assertEquals(35.0f, tarifas.get("Tarifa Por Eslora"));
	}

	@Test
	void testGetTarifasById_PuertoNoExiste() {
		when(puertoRepository.findById(999)).thenReturn(Optional.empty());

		Exception ex = assertThrows(RuntimeException.class, () -> {
			puertoService.getTarifasById(999);
		});

		assertEquals("Puerto no encontrado", ex.getMessage());
	}

}
