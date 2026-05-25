CREATE TABLE conversas (

    id BIGSERIAL PRIMARY KEY,
    telefone_usuario VARCHAR(20) NOT NULL,
    mensagem_usuario TEXT NOT NULL,
    resposta_enviada TEXT NOT NULL,
    intencao_detectada VARCHAR(100),
    distrito_detectado VARCHAR(100),
    data_referencia DATE,
    origem VARCHAR(50) NOT NULL,
    criada_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_conversas_telefone_usuario
    ON conversas (telefone_usuario);

CREATE INDEX idx_conversas_telefone_criada_em
    ON conversas(telefone_usuario, criada_em DESC);

