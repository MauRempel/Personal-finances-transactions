# 💰 API de Transações de Finanças Pessoais

API REST desenvolvida com **Spring Boot** para gerenciamento de transações financeiras pessoais, incluindo receitas e despesas, com categorização e cálculo automático de saldo.

---

## 🎯 Objetivo

Este projeto foi criado para simular um **sistema backend real de controle financeiro**, inspirado em uma planilha de orçamento pessoal.

O foco é aplicar boas práticas de desenvolvimento backend, como:

* Arquitetura em camadas
* Uso de DTOs para transferência de dados
* Validação de entrada
* Tratamento global de exceções
* Design de API REST
* Documentação com Swagger (OpenAPI)

---

## 🚀 Funcionalidades

* Criar transações financeiras (receitas e despesas)
* Categorizar transações (alimentação, salário, etc.)
* Cálculo automático do saldo
* Validação de dados (valor, tipo, categoria)
* Tratamento global de erros
* Endpoints REST organizados
* Documentação interativa com Swagger
* Ambiente de testes isolado com banco em memória

---

## 🛠️ Tecnologias

* Java 25
* Spring Boot
* Spring Web
* Maven
* H2 Database (para testes)
* Swagger (Springdoc OpenAPI)

---

## 📄 Documentação da API

A documentação interativa está disponível em:

👉 http://localhost:8080/swagger-ui.html

Através dela, é possível visualizar e testar todos os endpoints da API diretamente pelo navegador.

---

## 📌 Endpoints

| Método | Endpoint                | Descrição                 |
| ------ | ----------------------- | ------------------------- |
| GET    | `/transactions`         | Lista todas as transações |
| POST   | `/transactions`         | Cria uma nova transação   |
| GET    | `/transactions/balance` | Retorna o saldo atual     |

---

## 📥 Exemplo de Requisição

**POST /transactions**

```json id="br1"
{
  "amount": 100.50,
  "type": "EXPENSE",
  "category": "FOOD",
  "timestamp": "2026-04-16T09:00:00"
}
```

---

## 🧩 Arquitetura

O projeto segue uma **arquitetura em camadas**:

* **Controller** → Responsável pelas requisições HTTP
* **Service** → Contém as regras de negócio
* **Model** → Representa as entidades do domínio
* **DTO** → Objetos de transferência de dados (entrada/saída)
* **Exception** → Tratamento global de erros
* **Config** → Configurações da aplicação (Swagger/OpenAPI)

---

## 🧪 Testes

* Testes unitários para a camada de serviço
* Configuração separada para testes utilizando banco em memória (H2)
* Execução rápida e isolada dos testes

---

## ▶️ Como executar o projeto

### 1. Clonar o repositório

```bash id="br2"
git clone https://github.com/MauRempel/Personal-finances-transactions.git
cd Personal-finances-transactions
```

### 2. Executar a aplicação

```bash id="br3"
./mvnw spring-boot:run
```

### 3. Acessar a API

* API: http://localhost:8080
* Swagger UI: http://localhost:8080/swagger-ui.html

---

## ⚠️ Observações

* Arquivos de banco de dados não são versionados (ignorados via `.gitignore`)
* Os testes utilizam banco em memória (H2)
* O valor (`amount`) deve ser **positivo**
* O tipo da transação (INCOME / EXPENSE) define o impacto no saldo

---

## 🔮 Melhorias Futuras

* Persistência com banco de dados (PostgreSQL)
* Implementar GET por ID, DELETE e UPDATE
* Paginação e filtros
* Autenticação (JWT)
* Relatórios financeiros (mensal, por categoria, etc.)

---

## 👨‍💻 Autor

Maurício Rempel
Desenvolvedor Backend (Java / Spring Boot)

* GitHub: https://github.com/MauRempel
* LinkedIn: https://www.linkedin.com/in/mauricio-rempel-back-end/

---
