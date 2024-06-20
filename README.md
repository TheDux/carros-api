# SPRING BOOT CRUD - API de carros
### Repositório inspirado no curso de Spring Boot do [Ricardo Lecheta](https://github.com/rlecheta)

Baixar a imagem:
- docker pull mysql/mysql-server:8.0.32

Para criar uma imagem mysql com port forwarding dentro do docker
- docker run -d -h localhost -p 3306:3306 --name=spring-db mysql/mysql-server

Configurar o banco de dados: acessar documentação [aqui](https://hub.docker.com/r/mysql/mysql-server)

para criar a tabela:
```bash
create table carro
    (id bigint not null auto_increment,
    nome varchar(255),
    descricao varchar(255),
    url_foto varchar(255),
    url_video varchar(255),
    latitude varchar(255),
    longitude varchar(255),
    tipo varchar(255), primary key (id) );
```
para popular o container: utilizar o script de insert no diretório Data na raíz do projeto

URL do Swagger:
```bash
http://localhost:8080/swagger-ui/index.html
```
Executar a suíte de testes integrados e unitários:
```bash
mvn test
```