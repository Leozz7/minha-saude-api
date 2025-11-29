# 🏥 API de Gestão de Clínicas

Este projeto foi desenvolvido como parte do **Trabalho de Desenvolvimento de Exercício (TDE)** da disciplina de **Programação Backend**.  
Ele implementa uma **API REST** em **Java (Spring Boot)** para a **gestão de clínicas médicas**, com autenticação via **JWT** e persistência em banco de dados relacional.


![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-green)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Swagger](https://img.shields.io/badge/Docs-Swagger-85EA2D)

---

## ⚙️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.3.4
* **Segurança:** Spring Security + JWT (JSON Web Token)
* **Banco de Dados:** MySQL
* **Persistência:** Spring Data JPA / Hibernate
* **Documentação:** SpringDoc OpenApi (Swagger UI)
* **Ferramentas:** Maven, Lombok, Dotenv 

---

## 🔑 Funcionalidades e Regras de Negócio

### 👥 Gestão de Usuários
* **Autenticação:** Login seguro retornando Token JWT (Bearer).
* **Permissões:** Sistema de perfis (`ADMIN` e `USER`).
* **CRUD:** Cadastro e atualização de dados cadastrais.
* **Segurança:** Senhas criptografadas com `BCrypt`.

### 👩‍⚕️ Gestão de Pacientes
* **Validações:** CPF e E-mail únicos no sistema.
* **Regra de Menor de Idade:** O sistema calcula a idade automaticamente. Se o paciente for menor de 18 anos, é **obrigatório** cadastrar um Responsável (que deve ser maior de idade).

### 🧾 Gestão de Procedimentos
* **Acesso Restrito:** Apenas administradores podem criar, atualizar ou remover procedimentos.
* **Precificação Dinâmica:** Suporte a dois valores distintos por procedimento: `valorPlano` e `valorParticular`.

### 📂 Gestão de Atendimentos
* **Cálculo Automático:** O valor total do atendimento é calculado somando os procedimentos, aplicando o valor correto com base no `TipoPagamento` escolhido.
* **Validação de Convênio:** Se o pagamento for via `PLANO`, o número da carteira é obrigatório.
* **Relatórios:** Listagem de atendimentos filtrada por período (data inicial e final).
* 
---
## 🚀 Como Rodar o Projeto

### 1. Clone o repositório
```bash
git clone <URL_DO_REPOSITORIO>
```

### 2. Configure as variáveis de ambiente
Crie um arquivo .env na raiz do projeto
```
DB_URL=jdbc:mysql://localhost:3306/minhasaude?useSSL=false&serverTimezone=UTC
DB_USER=exemplo
DB_PASSWORD=exemplo
JWT_SECRET=umaChaveSeguraComPeloMenos32Bytes!!!
JWT_EXPIRATION=1000000000
```

### 3. Rode o projeto com Maven
```
mvn spring-boot:run
```
### 4. Teste a API
Utilize ferramentas como Postman, Insomnia ou outro cliente HTTP.
💡 Há uma collection do Postman pronta para uso na pasta: backend/endpointTest/minha-saude-collection.json.

---
## 📘 Documentação com Swagger

A API possui documentação interativa gerada automaticamente pelo **Swagger**, permitindo visualizar e testar os endpoints diretamente pelo navegador.

Após iniciar o projeto, acesse:

- **http://localhost:8080/swagger-ui/index.html**

## 📝 Estrutura de Endpoints Principais

| **Método** | **Endpoint**                | **Descrição**                     | **Permissão**   |
|------------|------------------------------|-----------------------------------|------------------|
| POST       | `/api/usuarios/login`        | Gerar Token de Acesso             | Livre            |
| POST       | `/api/usuarios/criar`        | Criar novo usuário                | Livre            |
| GET        | `/api/pacientes/listar`      | Listar pacientes (paginado)       | Autenticado      |
| POST       | `/api/atendimentos/criar`    | Registrar atendimento             | Autenticado      |
| POST       | `/api/procedimentos/criar`   | Cadastrar procedimento            | Admin            |
