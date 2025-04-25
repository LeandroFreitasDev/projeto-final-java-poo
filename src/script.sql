-- Criação do banco de dados
CREATE DATABASE projetofinal;

-- Tabela: funcionario
CREATE TABLE funcionario (
    codigo_funcionario SERIAL PRIMARY KEY,
    nome_funcionario VARCHAR(100),
    cpf_funcionario VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE,
    salario_bruto NUMERIC(10, 2)
);

-- Tabela: dependente
CREATE TABLE dependente (
    codigo_dependente SERIAL PRIMARY KEY,
    nome_dependente VARCHAR(100),
    cpf_dependente VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE,
    parentesco VARCHAR(20),
    cod_funcionario INTEGER,
    CONSTRAINT fk_funcionario_dependente
        FOREIGN KEY (cod_funcionario)
        REFERENCES funcionario (codigo_funcionario)
        ON DELETE CASCADE
);

-- Tabela: folha_de_pagamento
CREATE TABLE folha_de_pagamento (
    codigo_pagamento SERIAL PRIMARY KEY,
    data_pagamento DATE,
    desconto_inss NUMERIC(10, 2),
    desconto_ir NUMERIC(10, 2),
    salario_liquido NUMERIC(10, 2),
    cod_funcionario INTEGER,
    CONSTRAINT fk_funcionario_pagamento
        FOREIGN KEY (cod_funcionario)
        REFERENCES funcionario (codigo_funcionario)
        ON DELETE CASCADE
);
