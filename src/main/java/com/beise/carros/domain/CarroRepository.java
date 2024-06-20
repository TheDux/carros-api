package com.beise.carros.domain;

import com.beise.carros.domain.DTO.CarroDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CarroRepository extends JpaRepository<Carro,Long> {
    List<Carro> findByTipo(String tipo);

    void save(CarroDTO carro);
}
