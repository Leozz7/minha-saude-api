CREATE DATABASE IF NOT EXISTS minhasaude;
USE minhasaude;

-- Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    tipo ENUM('ADMIN', 'USER') DEFAULT 'USER',
    senha VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Responsáveis
CREATE TABLE IF NOT EXISTS responsaveis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(50),
    data_nascimento DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Pacientes
CREATE TABLE IF NOT EXISTS pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(50),
    data_nascimento DATE NOT NULL,
    estado VARCHAR(100) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    responsavel_id BIGINT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_paciente_responsavel FOREIGN KEY (responsavel_id)
        REFERENCES responsaveis(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Procedimentos
CREATE TABLE IF NOT EXISTS procedimentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT NOT NULL,
    valor_plano DECIMAL(10,2) NOT NULL,
    valor_particular DECIMAL(10,2) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Atendimentos
CREATE TABLE IF NOT EXISTS atendimentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    tipo ENUM('PLANO','PARTICULAR') NOT NULL,
    numero_carteira VARCHAR(255),
    data_atendimento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valor_total DECIMAL(10,2) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_atendimento_paciente FOREIGN KEY (paciente_id)
        REFERENCES pacientes(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_atendimento_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Atendimento <-> Procedimentos
CREATE TABLE IF NOT EXISTS atendimento_procedimentos (
    atendimento_id BIGINT NOT NULL,
    procedimento_id BIGINT NOT NULL,
    PRIMARY KEY (atendimento_id, procedimento_id),
    CONSTRAINT fk_ap_atendimento FOREIGN KEY (atendimento_id)
        REFERENCES atendimentos(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ap_procedimento FOREIGN KEY (procedimento_id)
        REFERENCES procedimentos(id) ON DELETE RESTRICT ON UPDATE CASCADE
);
