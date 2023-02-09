INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (1, 'Janeiro');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (2, 'Fevereiro');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (3, 'Março');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (4, 'Abril');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (5, 'Maio');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (6, 'Junho');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (7, 'Julho');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (8, 'Agosto');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (9, 'Setembro');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (10, 'Outubro');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (11, 'Novembro');
INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (12, 'Dezembro');

INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES ('M', 'Masculino');
INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES ('F', 'Feminino');
INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES ('NI', 'Não Informado');

INSERT INTO registro_crimes.ano(ano) VALUES (2015);
INSERT INTO registro_crimes.ano(ano) VALUES (2016);
INSERT INTO registro_crimes.ano(ano) VALUES (2017);
INSERT INTO registro_crimes.ano(ano) VALUES (2018);
INSERT INTO registro_crimes.ano(ano) VALUES (2019);
INSERT INTO registro_crimes.ano(ano) VALUES (2020);
INSERT INTO registro_crimes.ano(ano) VALUES (2021);
INSERT INTO registro_crimes.ano(ano) VALUES (2022);

INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Norte');
INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Nordeste');

INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Acre', 'AC');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Amazonas', 'AM');

INSERT INTO registro_crimes.uf_tem_poptotal(ano, nome_est, pop_total) VALUES (2015, 'Acre', 803513);
INSERT INTO registro_crimes.uf_tem_poptotal(ano, nome_est, pop_total) VALUES (2016, 'Acre', 816687);

INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Homicídio doloso');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Furto de veiculo');

INSERT INTO registro_crimes.uf_tem_ocorrencias(nome_est, tipo, mes, ano, quantidade_ocorrencias) VALUES ('Acre', 'Homicidio doloso', 7, 2017, 10);
INSERT INTO registro_crimes.uf_tem_ocorrencias(nome_est, tipo, mes, ano, quantidade_ocorrencias) VALUES ('Acre', 'Furto de veiculo', 7, 2017, 39);


INSERT INTO registro_crimes.uf_tem_crimes_morte(nome_est, tipo, mes, ano, sexo, quantidade_vitimas) VALUES ('Acre', 'Homicidio doloso', 1, 2022, 'F', 2);
INSERT INTO registro_crimes.uf_tem_crimes_morte(nome_est, tipo, mes, ano, sexo, quantidade_vitimas) VALUES ('Acre', 'Homicidio doloso', 1, 2022, 'M', 8);
INSERT INTO registro_crimes.uf_tem_crimes_morte(nome_est, tipo, mes, ano, sexo, quantidade_vitimas) VALUES ('Acre', 'Homicidio doloso', 1, 2022, 'NI', 0);