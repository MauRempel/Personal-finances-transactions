# Personal Finance Transactions API

API REST desenvolvida com Spring Boot e Java para gerenciamento de transações financeiras pessoais, permitindo controle de receitas, despesas, categorização e cálculo de saldo.

---

## 📌 Objetivo do Projeto

Este projeto foi desenvolvido como aplicação de portfólio backend com foco em prática de:

- arquitetura em camadas
- desenvolvimento de APIs REST
- boas práticas com DTOs
- validação de dados
- tratamento global de exceções
- persistência de dados com JPA
- versionamento de banco de dados com Flyway
- documentação com Swagger / OpenAPI

---

## 🚀 Funcionalidades

✅ Criar transações financeiras  
✅ Listar todas as transações  
✅ Buscar transação por ID  
✅ Atualizar transações  
✅ Remover transações  
✅ Calcular saldo atual  
✅ Validação de payloads com Bean Validation  
✅ Tratamento padronizado de erros  
✅ Documentação interativa com Swagger/OpenAPI  
✅ Perfis separados para desenvolvimento, testes e PostgreSQL

---

## 🛠️ Tecnologias Utilizadas

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- H2 Database
- Flyway
- Springdoc OpenAPI
- Maven

---

## 📚 Documentação da API

Após iniciar a aplicação, a documentação Swagger estará disponível em:

```bash
http://localhost:8080/swagger-ui.html
```

---

# 🔗 Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/transactions` | Lista todas as transações |
| GET | `/transactions/{id}` | Busca transação por ID |
| POST | `/transactions` | Cria uma nova transação |
| PUT | `/transactions/{id}` | Atualiza uma transação |
| DELETE | `/transactions/{id}` | Remove uma transação |
| GET | `/transactions/balance` | Retorna o saldo atual |

---

## 🧾 Exemplo de Requisição

### POST `/transactions`

```json
{
  "amount": 1000.00,
  "type": "INCOME",
  "category": "SALARY",
  "timestamp": "2026-05-09T09:00:00"
}
```

---

## 🧱 Estrutura do Projeto

```text
src/main/java/com/MauRempel/personalFinance/budget
│
├── controller   → Endpoints HTTP
├── service      → Regras de negócio
├── repository   → Camada de acesso a dados
├── model        → Entidades e enums
├── dto          → Objetos de request/response
├── exception    → Tratamento global de exceções
└── config       → Configurações da aplicação
```

---

## ⚙️ Profiles da Aplicação

O projeto utiliza profiles separados por ambiente:

| Profile | Descrição |
|---|---|
| `dev` | Banco H2 local |
| `test` | Banco H2 em memória para testes |
| `postgres` | PostgreSQL com Flyway |

As configurações principais ficam em:

```text
src/main/resources/application.properties
```

---

## 🗄️ Estratégia de Banco de Dados

### Desenvolvimento Local

- Profile `dev` utiliza H2
- Profile `postgres` utiliza PostgreSQL

### Versionamento de Schema

As alterações de schema são gerenciadas pelo Flyway através das migrations em:

```text
src/main/resources/db/migration
```

O Hibernate está configurado com:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Isso significa que:

- Flyway gerencia a criação/evolução do banco
- Hibernate valida compatibilidade do schema
- Hibernate não altera automaticamente o banco PostgreSQL

---

## ▶️ Como Executar

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/MauRempel/Personal-finances-transactions.git
cd Personal-finances-transactions
```

---

### 2️⃣ Executar com profile padrão (`dev`)

```bash
./mvnw spring-boot:run
```

---

### 3️⃣ Executar com PostgreSQL

Crie um banco chamado:

```text
personal_finances
```

Depois execute:

```bash
./mvnw "-Dspring-boot.run.profiles=postgres" spring-boot:run
```

### Windows PowerShell

```powershell
.\mvnw.cmd "-Dspring-boot.run.profiles=postgres" spring-boot:run
```

---

## 4️⃣ Executar testes

```bash
./mvnw test
```

---

# 📌 Regras de Negócio

- O valor da transação deve ser positivo
- O tipo da transação define o impacto no saldo:
    - `INCOME` → adiciona ao saldo
    - `EXPENSE` → subtrai do saldo

---

## 🔍 Melhorias Implementadas

- CRUD completo de transações
- Separação entre Entity e DTO
- Tratamento global de exceções
- Retorno padronizado de erros
- Respostas HTTP consistentes (`400`, `404`, etc.)
- Documentação Swagger/OpenAPI
- Versionamento de banco com Flyway
- Configuração separada por profiles

---

# 🚧 Melhorias Futuras

- Paginação de resultados
- Filtros por categoria e período
- Otimização do cálculo de saldo via query SQL
- Testes de integração
- Autenticação e autorização
- Deploy em nuvem
- CI/CD

---

## 👨‍💻 Autor

### Mauricio Rempel

- GitHub: https://github.com/MauRempel
- LinkedIn: https://www.linkedin.com/in/mauricio-rempel-back-end/