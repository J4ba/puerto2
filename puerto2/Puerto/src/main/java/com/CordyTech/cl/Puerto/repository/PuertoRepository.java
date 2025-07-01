package com.CordyTech.cl.Puerto.repository;

import com.CordyTech.cl.Puerto.model.Puerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PuertoRepository extends JpaRepository<Puerto, Integer> {

    @Override
    List<Puerto> findAll();

    Optional<Puerto> findById(int idPuerto);

    Optional<Puerto> findByNombrePuerto(String nombrePuerto);

    List<Puerto> findByDispoTrue();

}
