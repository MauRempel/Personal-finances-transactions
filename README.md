# 💰 Personal Finance Transactions API

API REST desenvolvida com Java e Spring Boot para gerenciamento de transações financeiras pessoais, permitindo controle de receitas, despesas, categorização e cálculo automático de saldo.

---

# 📌 Sobre o Projeto

Este projeto foi desenvolvido como aplicação de portfólio backend com foco em evolução prática no ecossistema Java/Spring Boot, aplicando conceitos utilizados em aplicações reais de mercado.

A API foi inspirada em planilhas pessoais de controle financeiro e tem como objetivo praticar:

- arquitetura em camadas
- desenvolvimento de APIs REST
- boas práticas com DTOs
- validação de requisições
- tratamento global de exceções
- persistência de dados com JPA/Hibernate
- versionamento de banco com Flyway
- documentação automática com Swagger/OpenAPI
- separação de ambientes com Spring Profiles

---

# 🚀 Funcionalidades

## ✅ Transações Financeiras

- Criar transações
- Listar todas as transações
- Buscar transação por ID
- Atualizar transações
- Remover transações

## ✅ Controle Financeiro

- Controle de receitas (`INCOME`)
- Controle de despesas (`EXPENSE`)
- Cálculo automático de saldo
- Categorias financeiras via Enum

## ✅ Qualidade e Arquitetura

- DTO Pattern
- Bean Validation
- Tratamento global de exceções
- Respostas padronizadas de erro
- HTTP Status Codes consistentes
- Swagger/OpenAPI

## ✅ Persistência e Infraestrutura

- PostgreSQL
- H2 Database para dev/testes
- Flyway para migrations
- Profiles separados por ambiente

---

# 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 25 | Linguagem principal |
| Spring Boot 4 | Framework backend |
| Spring Web MVC | Construção da API REST |
| Spring Data JPA | Persistência de dados |
| Hibernate | ORM |
| PostgreSQL | Banco principal |
| H2 Database | Banco para dev/testes |
| Flyway | Versionamento do banco |
| Spring Validation | Validação de requests |
| Springdoc OpenAPI | Swagger/OpenAPI |
| Maven | Gerenciamento de dependências |

---

# 📚 Documentação da API

Após iniciar a aplicação:

```bash
http://localhost:8080/swagger-ui.html
```

A documentação Swagger permite:

- visualizar endpoints
- testar requisições
- validar payloads
- consultar respostas HTTP
- entender contratos da API

---

# 🔗 Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/transactions` | Lista transações com filtros opcionais por categoria, tipo e período |
| GET | `/transactions/{id}` | Busca transação por ID |
| POST | `/transactions` | Cria uma transação |
| PUT | `/transactions/{id}` | Atualiza uma transação |
| DELETE | `/transactions/{id}` | Remove uma transação |
| GET | `/transactions/balance` | Retorna o saldo atual |

## Filtros disponíveis em `GET /transactions`
Os filtros são opcionais ao usar o GET e podem ser combinados entre si. Quando combinados os filtros de tempo (`start` e `end`), `start` não pode ser maior que `end`.

- category
- type
- start
- end

---

# 🧾 Exemplo de Requisição

## POST `/transactions`

```json
{
  "amount": 1000.00,
  "type": "INCOME",
  "category": "SALARY",
  "timestamp": "2026-05-09T09:00:00",
  "description: Salário mensal"
}
```

## GET `/transactions`
* Por padrão, as transações são retornadas com um tamanho de página de 10, ordenadas por timestamp descendente.

```text
GET /transactions?category=FOOD
GET /transactions?type=EXPENSE
GET /transactions?start=2026-05-01T00:00:00&end=2026-05-31T23:59:59
GET /transactions?category=FOOD&type=EXPENSE
GET /transactions?page=0&size=10&sort=timestamp,desc
```


---

# 🧱 Estrutura do Projeto

```text
src/main/java/com/MauRempel/personalFinance/budget
│
├── controller
│   └── Endpoints HTTP da aplicação
│
├── service
│   └── Regras de negócio e orquestração
│
├── repository
│   └── Camada de acesso a dados (JPA)
│
├── model
│   └── Entidades e enums do domínio
│
├── dto
│   └── Objetos de request/response
│
├── exception
│   └── Tratamento global de exceções
│
├── config
│   └── Configurações do Swagger/OpenAPI
│
└── resources
    └── Profiles e migrations Flyway
```

---

# ⚙️ Profiles da Aplicação

O projeto utiliza ambientes separados via Spring Profiles:

| Profile | Descrição |
|---|---|
| `dev` | Banco H2 local persistente |
| `test` | H2 em memória para testes |
| `postgres` | PostgreSQL + Flyway |

Atualmente, o profile padrão da aplicação é o postgres.
O profile dev permanece disponível como fallback local com H2.

Configuração principal:

```text
src/main/resources/application.properties
```

---

# 🗄️ Estratégia de Banco de Dados

O projeto não força um perfil padrão. 
Escolha o perfil explicitamente ao executar a aplicação.
## Desenvolvimento Local

- Profile `dev` utiliza H2
- Profile `test` utiliza H2 em memória

## Persistência Real

- Profile `postgres` utiliza PostgreSQL

## Versionamento de Schema

As alterações do banco são controladas pelo Flyway:

```text
src/main/resources/db/migration
```

Migrations atuais:
```text
V1__create_transaction_table.sql
V2__add_description_to_transaction.sql
V3__add_transaction_filter_indexes.sql
```

Hibernate configurado com:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Isso significa que:

- Flyway gerencia criação/evolução do banco
- Hibernate valida compatibilidade do schema
- Hibernate não altera automaticamente o banco PostgreSQL

---

# ▶️ Como Executar o Projeto

## 1️⃣ Clonar o repositório

```bash
git clone https://github.com/MauRempel/Personal-finances-transactions.git
cd Personal-finances-transactions
```

---

## 2️⃣ Executar aplicação localmente

```bash
./mvnw "-Dspring-boot.run.profiles=dev" spring-boot:run
```

---

## 3️⃣ Executar com PostgreSQL

Crie um banco chamado:

```text
personal_finances
```

### Variáveis de ambiente do PostgreSQL

- DB_URL
- DB_USERNAME
- DB_PASSWORD

```text
DB_URL=jdbc:postgresql://localhost:5432/personal_finances
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
```

### Windows PowerShell 
Defina suas variáveis:
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/personal_finances"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="sua_senha"

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
- Toda transação possui:
  - valor
  - categoria
  - tipo
  - timestamp
- O tipo da transação define o impacto no saldo:
  - `INCOME` → adiciona saldo
  - `EXPENSE` → reduz saldo

---

# 🔍 Conceitos Aplicados

## Arquitetura

- Layered Architecture
- DTO Pattern
- Repository Pattern

## API Design

- RESTful endpoints
- HTTP Status Codes corretos
- Tratamento global de exceções
- Responses padronizadas

## Persistência

- ORM com JPA/Hibernate
- Migrations com Flyway
- Profiles separados por ambiente
- Filtros dinâmicos com Spring Data JPA Specifications
- Cálculo de saldo via query agregada no banco

## Qualidade

- Bean Validation
- Código desacoplado
- Separação de responsabilidades
- Documentação OpenAPI

---

# 🔍 Melhorias Implementadas

- CRUD completo de transações
- Separação entre Entity e DTO
- Tratamento global de exceções
- Retorno padronizado de erros
- Respostas HTTP consistentes (`400`, `404`, `500`)
- Documentação Swagger/OpenAPI
- Versionamento de banco com Flyway
- Configuração separada por profiles
- Migração de H2 para PostgreSQL
- Versionamento de schema com Flyway
- Cálculo de saldo com query no repositório
- Filtros dinâmicos com Specifications
- Resposta tipada para saldo
- Perfis separados por ambiente
- Paginação

---

# 🚧 Melhorias Futuras

- PATCH parcial
- Dashboard financeiro
- Relatórios por categoria
- Testes de integração
- Cobertura de testes
- Dockerização
- Deploy em cloud
- CI/CD
- Autenticação JWT
- Controle de usuários

---

## 👨‍💻 Autor

### Mauricio Rempel

Backend Developer focado em Java e Spring Boot.

- GitHub:
  https://github.com/MauRempel

- LinkedIn:
  https://www.linkedin.com/in/mauricio-rempel-back-end/

---

