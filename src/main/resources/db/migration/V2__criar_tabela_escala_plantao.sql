
CREATE TABLE escala_plantao (
    id BIGSERIAL PRIMARY KEY,
    farmacia_id BIGINT NOT NULL,
    data_plantao DATE NOT NULL,
    inicia_as TIME NOT NULL DEFAULT '19:00',
    termina_as TIME NOT NULL DEFAULT '07:00',
    observacoes VARCHAR(255),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_escala_plantao_farmacia
        FOREIGN KEY (farmacia_id)
        REFERENCES farmacias(id)

);
