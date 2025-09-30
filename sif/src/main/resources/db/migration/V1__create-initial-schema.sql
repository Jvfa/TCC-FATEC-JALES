-- Criação da tabela de usuários
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(255) NOT NULL
);

-- Criação da tabela de pacientes
CREATE TABLE pacientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nome_mae VARCHAR(255),
    data_nascimento DATE,
    cpf VARCHAR(255) UNIQUE,
    rg VARCHAR(255),
    cns VARCHAR(255),
    endereco VARCHAR(255),
    telefone VARCHAR(255),
    peso NUMERIC(5, 2),
    altura NUMERIC(3, 2),
    cor VARCHAR(255),
    status_cadastro VARCHAR(255)
);

-- Criação da tabela de medicamentos
CREATE TABLE medicamentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    dosagem VARCHAR(255),
    forma_farmaceutica VARCHAR(255)
);

-- Criação da tabela de processos
CREATE TABLE processos (
    id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_abertura DATE,
    mes_inicio_validade INT,
    mes_fim_validade INT,
    cid VARCHAR(255),
    status VARCHAR(255),
    observacoes TEXT,
    CONSTRAINT fk_processos_pacientes FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    -- RESTRIÇÃO CORRIGIDA, INCLUINDO 'VENCIDO'
    CONSTRAINT processos_status_check CHECK (status IN ('EM_ABERTO', 'ENCERRADO', 'CANCELADO', 'VENCIDO'))
);

-- Criação da tabela de retiradas
CREATE TABLE retiradas (
    id BIGSERIAL PRIMARY KEY,
    processo_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    data_retirada DATE,
    nome_retirou VARCHAR(255),
    quantidade_dispensada INT,
    CONSTRAINT fk_retiradas_processos FOREIGN KEY (processo_id) REFERENCES processos(id),
    CONSTRAINT fk_retiradas_usuarios FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Criação da tabela de junção processo <-> medicamento
CREATE TABLE processo_medicamentos (
    id BIGSERIAL PRIMARY KEY,
    processo_id BIGINT,
    medicamento_id BIGINT,
    quantidade INT NOT NULL,
    CONSTRAINT fk_pm_processo FOREIGN KEY (processo_id) REFERENCES processos(id),
    CONSTRAINT fk_pm_medicamento FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id)
);
