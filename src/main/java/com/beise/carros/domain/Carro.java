package com.beise.carros.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private String descricao;
    private String urlFoto;
    private String urlVideo;
    private String latitude;
    private String longitude;

//    TODO: PRA FUNCIONAR INJETAR DADOS VIA MOCK
//    public Carro(){}
//    public Carro(Long id, String nome) {
//        this.id = id;
//        this.nome = nome;
//    }
}
