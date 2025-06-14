
# API Auth

API de autenticação e feed de tweets desenvolvida com Spring Boot.

Este projeto demonstra uma implementação de autenticação baseada em token JWT e gestão de usuários, além de um controle simples de "tweets".

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Security
- Maven
- Docker / Docker Compose
- Banco de dados (ver `docker-compose.yml`) — PostgreSQL

## Estrutura

- `/controllers` — controladores REST
- `/dto` — Data Transfer Objects para requisições e respostas
- `/config` — configuração de segurança (Spring Security)
- `/docker` — arquivos de infraestrutura para executar com containers


## Como rodar o projeto

### Com Docker Compose

```bash
docker-compose up --build
```

Isso irá levantar o banco de dados e a aplicação.

### Sem Docker (localmente com Maven)

Pré-requisitos:

- Java 21
- Maven

```bash
mvn clean install
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Variáveis de ambiente

Configure suas variáveis de ambiente ou `.env` com:

- `JWT_SECRET` — segredo para geração e validação dos tokens
- `DATABASE_URL` — URL do banco de dados (ou configurar no `application.properties`)

## Licença

Este projeto está licenciado sob a licença MIT.


