# 🏥 API de Gestão de Clínicas

Este projeto foi desenvolvido como parte do **Trabalho de Desenvolvimento de Exercício (TDE)** da disciplina de **Programação Backend**.  
Ele implementa uma **API REST** em **Java (Spring Boot)** para a **gestão de clínicas médicas**, com autenticação via **JWT** e persistência em banco de dados relacional.

---

## ⚙️ Tecnologias Utilizadas
- **Java 21**  
- **Spring Boot**
- **Spring Security + JWT**  
- **Spring Data JPA / Hibernate**  
- **MySQL**  
- **Maven**  

---

## 🔑 Funcionalidades

### 👥 Gestão de Usuários
- Cadastro de usuários
- Atualização dos próprios dados  
- Alteração de senha com senha anterior  
- Reset de senha por **admin**  
- Exclusão de usuários (restrições quando há atendimentos vinculados)  
- Login e autenticação via **JWT**  

### 👩‍⚕️ Gestão de Pacientes
- Cadastro, atualização e exclusão de pacientes  
- Validações:
  - CPF e e-mail únicos  
  - Responsável obrigatório para menores de idade  

### 🧾 Gestão de Procedimentos
- Cadastro, atualização e exclusão (**somente admin**)  
- Preços diferenciados: **plano** ou **particular**  

### 📂 Gestão de Atendimentos
- Registro de atendimentos com paciente, procedimentos e tipo (plano/particular)  
- Cálculo automático do valor total  
- Alteração/remoção apenas pelo criador ou **admin**  

### 🔎 Consultas
- Busca de usuários, pacientes, procedimentos e atendimentos (por **ID** ou lista paginada)  
- Listagem de atendimentos entre duas datas  

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

---
## 📘 Documentação com Swagger

A API possui documentação interativa gerada automaticamente pelo **Swagger**, permitindo visualizar e testar os endpoints diretamente pelo navegador.

Após iniciar o projeto, acesse:

- **http://localhost:8080/swagger-ui/index.html**