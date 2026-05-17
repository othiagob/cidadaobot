ALTER TABLE escala_plantao
ADD CONSTRAINT uk_escala_plantao_data_farmacia
UNIQUE (data_plantao, farmacia_id);

CREATE INDEX idx_escala_plantao_data
ON escala_plantao (data_plantao);
