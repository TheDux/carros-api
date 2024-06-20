package com.beise.carros.domain.DTO;

import com.beise.carros.domain.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDTO {

    private Long id;
    private String nome;
    private String tipo;

    public static CarroDTO create(Carro c){
        ModelMapper modelmapper = new ModelMapper();
        return modelmapper.map(c, CarroDTO.class);
    }
}
