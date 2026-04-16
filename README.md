# 💰 Personal Finances Transactions API

API backend desenvolvida para gerenciamento de transações financeiras pessoais, com foco em regras de negócio, consistência de dados e organização de domínio.

## 📌 Sobre o Projeto

Este projeto simula um sistema real de controle financeiro, permitindo o registro e processamento de transações como entradas e saídas, com cálculo automático de saldo.

A aplicação foi construída com foco em:

- Modelagem de domínio
- Regras de negócio bem definidas
- Boas práticas de backend com Java

---

## 🚀 Funcionalidades

- ✅ Registro de transações financeiras
- ✅ Classificação por tipo (INCOME / EXPENSE)
- ✅ Cálculo automático de saldo
- ✅ Validação de dados de entrada
- ✅ Estrutura preparada para API REST

---

## 🧱 Arquitetura

O projeto segue uma organização em camadas:
```java
📦 src/main/java
┣ 📂 controller → Entrada da aplicação (HTTP)
┣ 📂 service → Regras de negócio
┣ 📂 model → Entidades do domínio
┣ 📂 DTO → Transferência de dados
```
---

### 🔹 Separação de responsabilidades

- **Controller**
    - Recebe requisições e delega para o service

- **Service**
    - Contém lógica central (ex: cálculo de saldo)

- **Model**
    - Representa as entidades e regras básicas

- **DTO**
    - Isola entrada/saída da API

---

## 🧠 Regra de Negócio

O saldo é calculado com base nas transações:

- Entradas (**INCOME**) → somam ao saldo
- Saídas (**EXPENSE**) → subtraem do saldo

Exemplo:

```java
public BigDecimal calculateBalance(List<Transaction> transactions) {
    if (transactions == null) {
        throw new IllegalArgumentException("Transactions must not be null");
    }

    BigDecimal balance = BigDecimal.ZERO;

    for (Transaction t : transactions) {
        if (t.getType().equals("INCOME")) {
            balance = balance.add(t.getValue());
        } else {
            balance = balance.subtract(t.getValue());
        }
    }

    return balance;
}
```
---

### ⚠️ Decisões Técnicas
💡 Uso de BigDecimal

Para evitar problemas de precisão em cálculos financeiros, foi utilizado:
```java
BigDecimal
```

Evita erros comuns com double em operações monetárias.

---

### 💡 Validação de Entrada
Proteção contra listas nulas
Preparação para validações mais robustas (Bean Validation)
---

### 💡 Camada de Serviço

A lógica foi centralizada na camada de serviço para:

- Facilitar testes
- Evitar lógica no controller
- Melhorar manutenção
---

## 🛠️ Tecnologias
- Java 17+
- Spring Boot (estrutura base)
- Maven
- REST API
- BigDecimal (precisão financeira)
---

### 📬 Como Executar
```bash
# Clonar repositório

git clone https://github.com/MauRempel/Personal-finances-transactions.git

# Entrar no projeto
cd Personal-finances-transactions

# Rodar aplicação
./mvnw spring-boot:run
```
## 🔎 Testando a API

Exemplo de requisição:
```json

POST /transactions
Content-Type: application/json

{
"description": "Salário",
"value": 3000,
"type": "INCOME"
}
```
---
### 🧪 Possíveis Evoluções
- Persistência com banco de dados (PostgreSQL + JPA) - em andamento
- Autenticação (JWT + Spring Security)
- Testes unitários (JUnit / Mockito)
- Documentação com Swagger
- Paginação e filtros
- Dockerização
---
### 📊 Contexto

Sistemas de controle financeiro são amplamente utilizados para acompanhar receitas e despesas, permitindo melhor organização e tomada de decisão financeira.

### 👨‍💻 Autor

**Maurício Rempel Carmignan**

Desenvolvedor Backend Java

🔗 [Linkedin](https://www.linkedin.com/in/mauricio-rempel-back-end/)
💻 [Github](https://github.com/MauRempel)