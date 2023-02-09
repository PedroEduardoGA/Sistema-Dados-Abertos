CREATE SCHEMA registro_crimes;

CREATE TABLE registro_crimes.cargas(
    nome VARCHAR(50),
    data timestamp,
    numero_tuplas INT,
    CONSTRAINT pk_cargas PRIMARY KEY (nome, data)
);

CREATE TABLE registro_crimes.regiao(
	nome VARCHAR(20),
	CONSTRAINT pk_nome PRIMARY KEY(nome)
);

CREATE TABLE registro_crimes.estado(
	nome_reg VARCHAR(20) NOT NULL,
	nome VARCHAR(20),
	sigla CHAR(2),
	CONSTRAINT pk_nome_reg PRIMARY KEY(nome),
	CONSTRAINT fk_nome_reg FOREIGN KEY(nome_reg) REFERENCES registro_crimes.regiao(nome)
);

CREATE TABLE registro_crimes.ano(
	ano INT,
	CONSTRAINT pk_ano PRIMARY KEY(ano)
);

CREATE TABLE registro_crimes.mes(
    mes VARCHAR(20),
	num_mes INT,
	CONSTRAINT pk_mes PRIMARY KEY(num_mes)
);

CREATE TABLE registro_crimes.uf_tem_poptotal(
	ano INT NOT NULL,
	nome_est VARCHAR(20) NOT NULL,
	pop_total INT,
	CONSTRAINT fk_nome_est FOREIGN KEY(nome_est) REFERENCES registro_crimes.estado(nome),
	CONSTRAINT fk_ano FOREIGN KEY(ano) REFERENCES registro_crimes.ano(ano),
    CONSTRAINT pk_keys PRIMARY KEY(ano, nome_est)
);

CREATE TABLE registro_crimes.vitima(
	sexo CHAR(2),
	descricao VARCHAR(15),
	CONSTRAINT pk_sexo PRIMARY KEY(sexo)
);

CREATE TABLE registro_crimes.ocorrencias_crime(
	tipo VARCHAR(40),
	CONSTRAINT pk_tipo PRIMARY KEY(tipo)
);

CREATE TABLE registro_crimes.uf_tem_ocorrencias(
	nome_est VARCHAR(20) NOT NULL,
	tipo VARCHAR(40) NOT NULL,
	mes INT NOT NULL,
	ano INT NOT NULL,
	quantidade_ocorrencias INT,
	CONSTRAINT fk_nome_est FOREIGN KEY(nome_est) REFERENCES registro_crimes.estado(nome),
	CONSTRAINT fk_tipo FOREIGN KEY(tipo) REFERENCES registro_crimes.ocorrencias_crime(tipo),
	CONSTRAINT fk_mes FOREIGN KEY(mes) REFERENCES registro_crimes.mes(num_mes),
	CONSTRAINT fk_ano FOREIGN KEY(ano) REFERENCES registro_crimes.ano(ano),
	CONSTRAINT pk_chaves PRIMARY KEY(nome_est, tipo, mes, ano)
);


CREATE TABLE registro_crimes.uf_tem_crimes_morte(
	nome_est VARCHAR(20) NOT NULL,
	tipo VARCHAR(50) NOT NULL,
	mes INT NOT NULL,
	ano INT NOT NULL,
	sexo CHAR(2) NOT NULL,
	quantidade_vitimas INT,
	CONSTRAINT fk_nome_est FOREIGN KEY(nome_est) REFERENCES registro_crimes.estado(nome),
	CONSTRAINT fk_tipo FOREIGN KEY(tipo) REFERENCES registro_crimes.ocorrencias_crime(tipo),
	CONSTRAINT fk_mes FOREIGN KEY(mes) REFERENCES registro_crimes.mes(num_mes),
	CONSTRAINT fk_ano FOREIGN KEY(ano) REFERENCES registro_crimes.ano(ano),
	CONSTRAINT fk_sexo FOREIGN KEY(sexo) REFERENCES registro_crimes.vitima(sexo),
	CONSTRAINT pk_chaves_crimes PRIMARY KEY(nome_est, tipo, mes, ano, sexo)
);

--Inseridos sexo das vitimas
INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES ('M ', 'Masculino');
INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES ('F ', 'Feminino');
INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES ('NI', 'Não Informado');

-- Inserindo os meses
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

-- Inserindo os anos
INSERT INTO registro_crimes.ano(ano) VALUES (2015);
INSERT INTO registro_crimes.ano(ano) VALUES (2016);
INSERT INTO registro_crimes.ano(ano) VALUES (2017);
INSERT INTO registro_crimes.ano(ano) VALUES (2018);
INSERT INTO registro_crimes.ano(ano) VALUES (2019);
INSERT INTO registro_crimes.ano(ano) VALUES (2020);
INSERT INTO registro_crimes.ano(ano) VALUES (2021);
INSERT INTO registro_crimes.ano(ano) VALUES (2022);

-- Inserindo as regiões
INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Norte');
INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Nordeste');
INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Centro-Oeste');
INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Sudeste');
INSERT INTO registro_crimes.regiao(nome) VALUES ('Região Sul');
INSERT INTO registro_crimes.regiao(nome) VALUES ('Brasil');




-- INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Brasil', 'Brasil', 'BR');
-- Inserindo os estados
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Acre', 'AC');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Amazonas', 'AM');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Roraima', 'RR');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Rondônia', 'RO');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Pará', 'PA');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Tocantins', 'TO');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Norte', 'Amapá', 'AP');

INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Maranhão', 'MA');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Piauí', 'PI');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Ceará', 'CE');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Rio Grande do Norte', 'RN');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Pernambuco', 'PE');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Paraíba', 'PB');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Sergipe', 'SE');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Alagoas', 'AL');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Nordeste', 'Bahia', 'BA');

INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Centro-Oeste', 'Mato Grosso', 'MT');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Centro-Oeste', 'Mato Grosso do Sul', 'MS');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Centro-Oeste', 'Goiás', 'GO');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Centro-Oeste', 'Distrito Federal', 'DF');

INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sudeste', 'São Paulo', 'SP');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sudeste', 'Rio de Janeiro', 'RJ');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sudeste', 'Espírito Santo', 'ES');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sudeste', 'Minas Gerais', 'MG');

INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sul', 'Paraná', 'PR');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sul', 'Rio Grande do Sul', 'RS');
INSERT INTO registro_crimes.estado(nome_reg, nome, sigla) VALUES ('Região Sul', 'Santa Catarina', 'SC');

-- Inserindo os tipos de crime
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Homicídio doloso');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Lesão corporal seguida de morte');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Roubo a instituição financeira');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Roubo de carga');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Roubo seguido de morte (latrocínio)');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Tentativa de homicídio');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Estupro');
INSERT INTO registro_crimes.ocorrencias_crime(tipo) VALUES ('Furto de veiculo');
