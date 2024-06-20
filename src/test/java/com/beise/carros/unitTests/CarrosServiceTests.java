package com.beise.carros.unitTests;

import com.beise.carros.api.exception.ObjectNotFoundException;
import com.beise.carros.domain.Carro;
import com.beise.carros.domain.CarroService;
import com.beise.carros.domain.DTO.CarroDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarrosServiceTests {

	@Autowired
	private CarroService service;

	@Test
	@DisplayName("Inserir e deletar carro")
	public void test1() {
		Carro carro = new Carro();
		carro.setNome("tempra turbo 1.8");
		carro.setTipo("esportivos");

		CarroDTO c = service.insert(carro);

		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		c = service.getCarroById(id);
		assertNotNull(c);

		assertEquals("tempra turbo 1.8", c.getNome());
		assertEquals("esportivos", c.getTipo());

		service.delete(id);

		try{
			assertNull(service.getCarroById(id));
			fail("O carro não foi excluído");
		}catch (ObjectNotFoundException e){
			//TODO EXCEPTION
		}
	}
	@Test
	@DisplayName("Listar carros")
	public void test2(){
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30,carros.size());
	}

	@Test
	@DisplayName("Buscar carro por id")
	public void test3(){
		CarroDTO c = service.getCarroById(11L);
		assertNotNull(c);

		assertEquals("Ferrari FF", c.getNome());
		assertEquals("esportivos", c.getTipo());
	}

	@Test
	@DisplayName("Listar carros por tipo")
	public void test4(){
		assertEquals(10, service.getCarroByTipo("classicos").size());
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());
		assertEquals(0, service.getCarroByTipo("inexistentes").size());
	}
}
