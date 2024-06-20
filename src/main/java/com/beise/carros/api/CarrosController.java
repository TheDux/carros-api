package com.beise.carros.api;

import com.beise.carros.domain.Carro;
import com.beise.carros.domain.CarroService;
import com.beise.carros.domain.DTO.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService service;

    @GetMapping
    public ResponseEntity<List<CarroDTO>> get(){

        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> get(@PathVariable("id") Long id){

        CarroDTO carro = service.getCarroById(id);

        return ResponseEntity.ok(carro);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarroDTO>> get(@PathVariable("tipo") String tipo){
        List<CarroDTO> carros = service.getCarroByTipo(tipo);

        return carros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CarroDTO> post(@RequestBody Carro carro){
            CarroDTO c = service.insert(carro);

            URI location = getUri(c.getId());
            return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> put(@PathVariable("id") Long id, @RequestBody Carro carro){
        CarroDTO c = service.update(carro, id);

        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarroDTO> delete(@PathVariable("id")Long id){
        Optional<CarroDTO> op = Optional.ofNullable(service.getCarroById(id));
        if(op.isPresent()){
            service.delete(id);
        }
        return ResponseEntity.ok().build();
    }

//     TODO: ROTA DE MOCK
//        @GetMapping("/carros-mock")
//    public List<Carro> getMock(){
//
//        return service.getCarrosFake();
//    }

}
