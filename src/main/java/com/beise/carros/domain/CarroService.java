package com.beise.carros.domain;

import com.beise.carros.domain.DTO.CarroDTO;
import com.beise.carros.api.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService{

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
        List<Carro> carros = rep.findAll();

        List<CarroDTO> list = carros.stream().map(CarroDTO::create).collect(Collectors.toList());
        return list;
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create)
                .orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {

        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível atualizar o registro!");
        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        Optional<Carro> optional = rep.findById(id);
        if (optional.isPresent()){
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            rep.save(db);
            return CarroDTO.create(db);
        }
        else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
            rep.deleteById(id);
    }

//    TODO: PRA INJETAR DADOS VIA MOCK
//    public List<Carro> getCarrosFake(){
//        List<Carro> carros = new ArrayList<>();
//
//        carros.add(new Carro(1L, "Fusca"));
//        carros.add(new Carro(2L, "Brasilia"));
//        carros.add(new Carro(3L, "Chevette"));
//
//        return carros;
//    }


}
