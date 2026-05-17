-- Escalas oficiais de plantão de Maio/2026 e Junho/2026.
-- Observação dos PDFs oficiais: plantão de 24 horas inicia às 07:00 e termina às 07:00 do dia seguinte.

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'MEDFARMA' AND endereco = 'AV. MARECHAL RONDON, 2197'),
    DATE '2026-05-01',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA POPULAR' AND endereco = 'RUA MONTE CASTELO, 394'),
    DATE '2026-05-02',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'ULTRAPOPULAR' AND endereco = 'AV. MARECHAL RONDON, 2177'),
    DATE '2026-05-03',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PAGUE MENOS' AND endereco = 'AV. MARECHAL RONDON, 2142'),
    DATE '2026-05-04',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PRESIDENCIAL' AND endereco = 'RUA 31 DE MARÇO, 1624'),
    DATE '2026-05-05',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'RUA MONTE CASTELO, 164'),
    DATE '2026-05-06',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO ULTRA BAIXO' AND endereco = 'RUA 31 DE MARÇO, 927'),
    DATE '2026-05-07',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA ULTRA POPULAR' AND endereco = 'AV. MARECHAL RONDON, 2050'),
    DATE '2026-05-08',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA SUPER POPULAR' AND endereco = 'AV. CASTELO BRANCO, 1330'),
    DATE '2026-05-09',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO JUSTO' AND endereco = 'RUA JASMIM, 2500'),
    DATE '2026-05-10',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA DUTRAFARMA' AND endereco = 'RUA DOS MINEIROS, 198 - PRÓXIMO À RODOVIÁRIA'),
    DATE '2026-05-11',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. MARECHAL RONDON, 1783'),
    DATE '2026-05-12',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGASIL' AND endereco = 'AV. MARECHAL RONDON, 1912'),
    DATE '2026-05-13',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA JI MED' AND endereco = 'RUA DOS MINEIROS, 260 - EM FRENTE À RODOVIÁRIA'),
    DATE '2026-05-14',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'BIO FARMA' AND endereco = 'RUA MATO GROSSO, 2815'),
    DATE '2026-05-15',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA MONTE CASTELO' AND endereco = 'RUA MONTE CASTELO, 314'),
    DATE '2026-05-16',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA REAL' AND endereco = 'RUA DOS MINEIROS, 298 - PRÓXIMO À RODOVIÁRIA'),
    DATE '2026-05-17',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA GOIAS' AND endereco = 'AV. MARECHAL RONDON, 2230'),
    DATE '2026-05-18',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'MEDFARMA' AND endereco = 'AV. MARECHAL RONDON, 2197'),
    DATE '2026-05-19',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA POPULAR' AND endereco = 'RUA MONTE CASTELO, 394'),
    DATE '2026-05-20',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'ULTRAPOPULAR' AND endereco = 'AV. MARECHAL RONDON, 2177'),
    DATE '2026-05-21',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PAGUE MENOS' AND endereco = 'AV. MARECHAL RONDON, 2142'),
    DATE '2026-05-22',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PRESIDENCIAL' AND endereco = 'RUA 31 DE MARÇO, 1624'),
    DATE '2026-05-23',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'RUA MONTE CASTELO, 164'),
    DATE '2026-05-24',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO ULTRA BAIXO' AND endereco = 'RUA 31 DE MARÇO, 927'),
    DATE '2026-05-25',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA ULTRA POPULAR' AND endereco = 'AV. MARECHAL RONDON, 2050'),
    DATE '2026-05-26',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA SUPER POPULAR' AND endereco = 'AV. CASTELO BRANCO, 1330'),
    DATE '2026-05-27',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO JUSTO' AND endereco = 'RUA JASMIM, 2500'),
    DATE '2026-05-28',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA DUTRAFARMA' AND endereco = 'RUA DOS MINEIROS, 198 - PRÓXIMO À RODOVIÁRIA'),
    DATE '2026-05-29',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. MARECHAL RONDON, 1783'),
    DATE '2026-05-30',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGASIL' AND endereco = 'AV. MARECHAL RONDON, 1912'),
    DATE '2026-05-31',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA RONDOMINAS' AND endereco = 'AV. BRASIL, 1659'),
    DATE '2026-05-01',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA ULTRA POPULAR' AND endereco = 'AV. BRASIL, 453'),
    DATE '2026-05-02',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'SAÚDE POPULAR' AND endereco = 'RUA IPÊ, 2435, T-17'),
    DATE '2026-05-03',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA RENASCER' AND endereco = 'AV. ÉDSON LIMA DO NASCIMENTO, 3038'),
    DATE '2026-05-04',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PASSARELA' AND endereco = 'AV. BRASIL, 2093'),
    DATE '2026-05-05',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA VIDA FARMA' AND endereco = 'AV. EDSON LIMA DO NASCIMENTO, 5782'),
    DATE '2026-05-06',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. BRASIL, 1838'),
    DATE '2026-05-07',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PAGUE MENOS' AND endereco = 'RUA AMAZONAS, 219'),
    DATE '2026-05-08',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA ULTRA POPULAR' AND endereco = 'RUA AMAZONAS, 229'),
    DATE '2026-05-09',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMAIS' AND endereco = 'AV. DAS SERINGUEIRAS, 580, T-14'),
    DATE '2026-05-10',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'REDE MASTER FARMA T 14' AND endereco = 'AV. DAS SERINGUEIRAS, 144, T-14'),
    DATE '2026-05-11',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO BAIXO' AND endereco = 'RUA MARTINS COSTA, 299'),
    DATE '2026-05-12',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. MARECHAL RONDON, 1783'),
    DATE '2026-05-13',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA BRASIL FARMA' AND endereco = 'RUA MARACATIARA, 1803'),
    DATE '2026-05-14',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA VOCE' AND endereco = 'AV. GOV. JORGE TEIXEIRA, 2588, T-23'),
    DATE '2026-05-15',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGASIL' AND endereco = 'RUA LUIZ MUZAMBINHO, 1745, T-06'),
    DATE '2026-05-16',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'SAÚDE POPULAR' AND endereco = 'RUA IPÊ, 2435, T-17'),
    DATE '2026-05-17',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO BAIXO' AND endereco = 'AV. BRASIL, 1833, T-15'),
    DATE '2026-05-18',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA JIFARMA' AND endereco = 'AV. BRASIL, 722, T-06'),
    DATE '2026-05-19',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PREÇO BAIXO' AND endereco = 'AV. BRASIL, 753, T-06'),
    DATE '2026-05-20',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA POPULAR' AND endereco = 'AV. BRASIL, 1593, T-13'),
    DATE '2026-05-21',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RONDOMIL' AND endereco = 'AV. BRASIL, 738'),
    DATE '2026-05-22',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIAS TEIXEIRA' AND endereco = 'AV. BRASIL, 3524'),
    DATE '2026-05-23',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'TRIP FARMA POPULAR' AND endereco = 'AV. BRASIL, 162'),
    DATE '2026-05-24',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA CENTRO POPULAR' AND endereco = 'AV. DOUTORA ELAINE ALTAFIN, 1891, SALA 02'),
    DATE '2026-05-25',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA MAIS' AND endereco = 'AV. DAS SERINGUEIRAS, 1358, T-14'),
    DATE '2026-05-26',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'RUA MONTE CASTELO, 164'),
    DATE '2026-05-27',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA PREÇO POPULAR' AND endereco = 'AV. DAS SERINGUEIRAS, 97, T-14'),
    DATE '2026-05-28',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA RONDOMINAS' AND endereco = 'AV. BRASIL, 1659'),
    DATE '2026-05-29',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA ULTRA POPULAR' AND endereco = 'AV. BRASIL, 453'),
    DATE '2026-05-30',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'SAÚDE POPULAR' AND endereco = 'RUA IPÊ, 2435, T-17'),
    DATE '2026-05-31',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 05/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA JI MED' AND endereco = 'RUA DOS MINEIROS, 260 - EM FRENTE À RODOVIÁRIA'),
    DATE '2026-06-01',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'BIO FARMA' AND endereco = 'RUA MATO GROSSO, 2815'),
    DATE '2026-06-02',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA MONTE CASTELO' AND endereco = 'RUA MONTE CASTELO, 314'),
    DATE '2026-06-03',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA REAL' AND endereco = 'RUA DOS MINEIROS, 298 - PRÓXIMO À RODOVIÁRIA'),
    DATE '2026-06-04',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA GOIAS' AND endereco = 'AV. MARECHAL RONDON, 2230'),
    DATE '2026-06-05',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'MEDFARMA' AND endereco = 'AV. MARECHAL RONDON, 2197'),
    DATE '2026-06-06',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA POPULAR' AND endereco = 'RUA MONTE CASTELO, 394'),
    DATE '2026-06-07',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'ULTRAPOPULAR' AND endereco = 'AV. MARECHAL RONDON, 2177'),
    DATE '2026-06-08',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PAGUE MENOS' AND endereco = 'AV. MARECHAL RONDON, 2142'),
    DATE '2026-06-09',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PRESIDENCIAL' AND endereco = 'RUA 31 DE MARÇO, 1624'),
    DATE '2026-06-10',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'RUA MONTE CASTELO, 164'),
    DATE '2026-06-11',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO ULTRA BAIXO' AND endereco = 'RUA 31 DE MARÇO, 927'),
    DATE '2026-06-12',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA ULTRA POPULAR' AND endereco = 'AV. MARECHAL RONDON, 2050'),
    DATE '2026-06-13',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA SUPER POPULAR' AND endereco = 'AV. CASTELO BRANCO, 1330'),
    DATE '2026-06-14',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO JUSTO' AND endereco = 'RUA JASMIM, 2500'),
    DATE '2026-06-15',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA DUTRAFARMA' AND endereco = 'RUA DOS MINEIROS, 198 - PRÓXIMO À RODOVIÁRIA'),
    DATE '2026-06-16',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. MARECHAL RONDON, 1783'),
    DATE '2026-06-17',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGASIL' AND endereco = 'AV. MARECHAL RONDON, 1912'),
    DATE '2026-06-18',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA JI MED' AND endereco = 'RUA DOS MINEIROS, 260 - EM FRENTE À RODOVIÁRIA'),
    DATE '2026-06-19',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'BIO FARMA' AND endereco = 'RUA MATO GROSSO, 2815'),
    DATE '2026-06-20',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA MONTE CASTELO' AND endereco = 'RUA MONTE CASTELO, 314'),
    DATE '2026-06-21',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA REAL' AND endereco = 'RUA DOS MINEIROS, 298 - PRÓXIMO À RODOVIÁRIA'),
    DATE '2026-06-22',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA GOIAS' AND endereco = 'AV. MARECHAL RONDON, 2230'),
    DATE '2026-06-23',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'MEDFARMA' AND endereco = 'AV. MARECHAL RONDON, 2197'),
    DATE '2026-06-24',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA POPULAR' AND endereco = 'RUA MONTE CASTELO, 394'),
    DATE '2026-06-25',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'ULTRAPOPULAR' AND endereco = 'AV. MARECHAL RONDON, 2177'),
    DATE '2026-06-26',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PAGUE MENOS' AND endereco = 'AV. MARECHAL RONDON, 2142'),
    DATE '2026-06-27',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PRESIDENCIAL' AND endereco = 'RUA 31 DE MARÇO, 1624'),
    DATE '2026-06-28',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'RUA MONTE CASTELO, 164'),
    DATE '2026-06-29',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO ULTRA BAIXO' AND endereco = 'RUA 31 DE MARÇO, 927'),
    DATE '2026-06-30',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Primeiro Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PAGUE MENOS' AND endereco = 'RUA AMAZONAS, 219'),
    DATE '2026-06-01',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA ULTRA POPULAR' AND endereco = 'RUA AMAZONAS, 229'),
    DATE '2026-06-02',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMAIS' AND endereco = 'AV. DAS SERINGUEIRAS, 580, T-14'),
    DATE '2026-06-03',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'REDE MASTER FARMA T 14' AND endereco = 'AV. DAS SERINGUEIRAS, 144, T-14'),
    DATE '2026-06-04',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO BAIXO' AND endereco = 'RUA MARTINS COSTA, 299'),
    DATE '2026-06-05',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGASIL' AND endereco = 'RUA LUIZ MUZAMBINHO, 1745, T-06'),
    DATE '2026-06-06',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA BRASIL FARMA' AND endereco = 'RUA MARACATIARA, 1803'),
    DATE '2026-06-07',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA VOCE' AND endereco = 'AV. GOV. JORGE TEIXEIRA, 2588, T-23'),
    DATE '2026-06-08',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. MARECHAL RONDON, 1783'),
    DATE '2026-06-09',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'SAÚDE POPULAR' AND endereco = 'RUA IPÊ, 2435, T-17'),
    DATE '2026-06-10',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO BAIXO' AND endereco = 'AV. BRASIL, 1833, T-15'),
    DATE '2026-06-11',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA JIFARMA' AND endereco = 'AV. BRASIL, 722, T-06'),
    DATE '2026-06-12',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PREÇO BAIXO' AND endereco = 'AV. BRASIL, 753, T-06'),
    DATE '2026-06-13',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMA POPULAR' AND endereco = 'AV. BRASIL, 1593, T-13'),
    DATE '2026-06-14',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RONDOMIL' AND endereco = 'AV. BRASIL, 738'),
    DATE '2026-06-15',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIAS TEIXEIRA' AND endereco = 'AV. BRASIL, 3524'),
    DATE '2026-06-16',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'TRIP FARMA POPULAR' AND endereco = 'AV. BRASIL, 162'),
    DATE '2026-06-17',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA RONDOMINAS' AND endereco = 'AV. BRASIL, 1659'),
    DATE '2026-06-18',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGARIA ULTRA POPULAR' AND endereco = 'AV. BRASIL, 453'),
    DATE '2026-06-19',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'SAÚDE POPULAR' AND endereco = 'RUA IPÊ, 2435, T-17'),
    DATE '2026-06-20',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA RENASCER' AND endereco = 'AV. ÉDSON LIMA DO NASCIMENTO, 3038'),
    DATE '2026-06-21',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PASSARELA' AND endereco = 'AV. BRASIL, 2093'),
    DATE '2026-06-22',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA VIDA FARMA' AND endereco = 'AV. EDSON LIMA DO NASCIMENTO, 5782'),
    DATE '2026-06-23',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'RD FARMA' AND endereco = 'AV. BRASIL, 1838'),
    DATE '2026-06-24',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMACIA PAGUE MENOS' AND endereco = 'RUA AMAZONAS, 219'),
    DATE '2026-06-25',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA ULTRA POPULAR' AND endereco = 'RUA AMAZONAS, 229'),
    DATE '2026-06-26',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMAIS' AND endereco = 'AV. DAS SERINGUEIRAS, 580, T-14'),
    DATE '2026-06-27',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'REDE MASTER FARMA T 14' AND endereco = 'AV. DAS SERINGUEIRAS, 144, T-14'),
    DATE '2026-06-28',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'FARMÁCIA PREÇO BAIXO' AND endereco = 'RUA MARTINS COSTA, 299'),
    DATE '2026-06-29',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);

INSERT INTO escala_plantao (farmacia_id, data_plantao, inicia_as, termina_as, observacoes)
VALUES (
    (SELECT id FROM farmacias WHERE nome = 'DROGASIL' AND endereco = 'RUA LUIZ MUZAMBINHO, 1745, T-06'),
    DATE '2026-06-30',
    TIME '07:00:00',
    TIME '07:00:00',
    'Escala oficial 06/2026 - Segundo Distrito'
);
