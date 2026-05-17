ALTER TABLE farmacias
ADD CONSTRAINT uk_farmacia_nome_endereco
UNIQUE (nome, endereco)
