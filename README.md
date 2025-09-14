# 🚗 Borracharia Backend

Sistema de gestão para uma borracharia, desenvolvido em **Java Spring Boot** com **MongoDB**.  
Permite o cadastro de clientes, abertura de ordens de serviço (OS), controle de pagamentos e autenticação com papéis (Admin/Func).

---

## ⚙️ Tecnologias

- [Java 17+](https://adoptium.net/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Security + JWT](https://spring.io/projects/spring-security)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Lombok](https://projectlombok.org/)
- [Swagger / OpenAPI](https://springdoc.org/)

---

## 🛠️ Funcionalidades

### 🔐 Autenticação & Autorização
- Login via **JWT**
- **Admin**
    - Pode visualizar, criar, editar e deletar qualquer registro
    - Pode gerenciar usuários (em breve via `UserController`)
- **Func**
    - Só pode visualizar/editar/deletar registros que ele mesmo criou
    - Não enxerga dados criados por outros usuários

### 👥 Clientes
- Cadastro, edição, listagem e exclusão de clientes
- Controle de ownership (cada funcionário só vê os clientes criados por ele)

### 🧾 Ordens de Serviço (Work Orders)
- Cadastro de serviços realizados
- Registro da **placa do carro**
- Data do serviço
- Lista de serviços executados (descrição, quantidade, preço unitário)
- Cálculo automático do **total da OS**
- Pagamento via:
    - Crédito
    - Débito
    - Pix
    - Voucher
- Controle de **status da OS**: `OPEN`, `PAID`, `CANCELED`

---

## 📂 Estrutura de Pacotes

com.borracharia
├── auth # Autenticação (AuthController)
├── common # Classes comuns (ex.: auditoria)
├── config # Configurações (JWT, Security, Swagger, Auditoria)
├── customer # Cliente (controller, service, entity, repository)
├── user # Usuário (controller, service, entity, repository, enums)
└── workorder # Ordem de Serviço (controller, service, entity, repository, enums)


---

## 🚀 Executando o projeto

### Pré-requisitos
- Java 17+
- MongoDB rodando localmente (padrão: `mongodb://localhost:27017/borracharia`)

### Usando `.env`
Crie o arquivo `.env` na raiz do projeto:

```env
SERVER_PORT=8089
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/borracharia

JWT_ISSUER=borracharia-app
JWT_SECRET=b26f8c9e3d91a7cfa2459d2e8d917e3f4c5b6a7d8e9f1029384756abcdef1234
JWT_EXPIRATION=86400


./gradlew bootRun


A aplicação será iniciada em:
👉 http://localhost:8089

📖 Documentação da API

Swagger UI:
👉 http://localhost:8089/swagger-ui/index.html

Spec OpenAPI:

JSON: http://localhost:8089/v3/api-docs

YAML: http://localhost:8089/v3/api-docs.yaml

👤 Usuários padrão (DataSeeder)

Admin

username: admin

password: admin123

Funcionário

username: func

password: func123

⚠️ Esses usuários são criados automaticamente na primeira execução.

🔑 Exemplos de uso
1. Login (gera token JWT)
curl -X POST http://localhost:8089/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
  
 Resposta:
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
