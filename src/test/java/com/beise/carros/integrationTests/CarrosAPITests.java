package com.beise.carros.integrationTests;

import com.beise.carros.CarrosApplication;
import com.beise.carros.domain.Carro;
import com.beise.carros.domain.DTO.CarroDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = CarrosApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CarrosAPITests {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<CarroDTO> getCarro(String url){
		return rest.getForEntity(url, CarroDTO.class);
	}

	private ResponseEntity<List<CarroDTO>> getCarros(String url){
		return rest.exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CarroDTO>>() {
				}
		);
	}

	@Test
	@DisplayName("Inserir e deletar carro via API")
	public void test1() {
		Carro carro = new Carro();
		carro.setNome("tempra turbo 1.8");
		carro.setTipo("esportivos");

		ResponseEntity response = rest
				.postForEntity(
				"/api/v1/carros",
						carro,
						null
		);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		String location = response.getHeaders().get("location").get(0);
		CarroDTO c = getCarro(location).getBody();

		assertNotNull(c);
		assertEquals("tempra turbo 1.8", c.getNome());
		assertEquals("esportivos", c.getTipo());

		rest.delete(location);

		assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
	}

	@Test
	@DisplayName("Listar carros via API")
	public void test2(){
		List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
		assertNotNull(carros);
		assertEquals(30, carros.size());
	}

	@Test
	@DisplayName("Listar por tipo via API")
	public void test3(){
		assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
		assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
		assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());

		assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
	}

	@Test
	@DisplayName("Buscar carro por id via API")
	public void test4() {

		ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		CarroDTO c = response.getBody();
		assertEquals("Ferrari FF", c.getNome());
	}

	@Test
	@DisplayName("Buscar carro inexistente por id via API")
	public void test5() {

		ResponseEntity response = getCarro("/api/v1/carros/1100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
