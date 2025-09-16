# 🏥 API de Gestão de Clínicas

Este projeto foi desenvolvido como parte do **Trabalho de Desenvolvimento de Exercício (TDE)** da disciplina de Programação Backend.  
O sistema implementa uma **API REST** em **Java (Spring Boot)** para a **gestão de clínicas médicas**, com autenticação via **JWT** e persistência em banco de dados relacional.

## ⚙️ Tecnologias Utilizadas
- **Java 22**  
- **Spring Boot** (Tomcat embutido)  
- **Spring Security + JWT**  
- **Spring Data JPA / Hibernate**  
- **Banco de Dados Relacional** (MySQL)
- **Maven**  

---

## 🔑 Funcionalidades

### 🔐 Gestão de Usuários
- Cadastro de usuários (apenas **admin**)  
- Atualização dos próprios dados  
- Redefinição de senha (com senha anterior)  
- Reset de senha por **admin**  
- Exclusão de usuários (restrições quando há atendimentos vinculados)  
- Login e autenticação via **JWT**  

### 👩‍⚕️ Gestão de Pacientes
- Cadastro, atualização e exclusão de pacientes  
- Validações: CPF e e-mail únicos, responsável obrigatório para menores de idade  

### 📋 Gestão de Procedimentos
- Cadastro, atualização e exclusão (**apenas admin**)  
- Preços diferenciados: **plano** ou **particular**  

### 🗂 Gestão de Atendimentos
- Registro de atendimentos com paciente, procedimentos e tipo (plano/particular)  
- Cálculo automático do valor total  
- Alteração/remoção apenas pelo criador ou **admin**  

### 🔎 Consultas
- Busca de usuários, pacientes, procedimentos e atendimentos (por ID ou lista paginada)  
- Listagem de atendimentos entre duas datas  

---


