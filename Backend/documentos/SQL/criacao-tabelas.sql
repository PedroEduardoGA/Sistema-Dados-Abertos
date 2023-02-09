CREATE SCHEMA registro_crimes;

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
	CONSTRAINT uk_ano UNIQUE(ano),
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
	tipo VARCHAR(50) NOT NULL,
	mes VARCHAR(10) NOT NULL,
	ano INT NOT NULL,
	quantidade_ocorrencias INT,
	CONSTRAINT fk_nome_est FOREIGN KEY(nome_est) REFERENCES registro_crimes.estado(nome),
	CONSTRAINT fk_tipo FOREIGN KEY(tipo) REFERENCES registro_crimes.ocorrencias_crime(tipo),
	CONSTRAINT fk_mes FOREIGN KEY(mes) REFERENCES registro_crimes.mes(mes),
	CONSTRAINT fk_ano FOREIGN KEY(ano) REFERENCES registro_crimes.ano(ano),
	CONSTRAINT pk_chaves PRIMARY KEY(tipo, mes, ano)
);


CREATE TABLE registro_crimes.uf_tem_crimes_morte(
	nome_est VARCHAR(20) NOT NULL,
	tipo VARCHAR(50) NOT NULL,
	mes VARCHAR(10) NOT NULL,
	ano INT NOT NULL,
	sexo CHAR(2) NOT NULL,
	quantidade_vitimas INT,
	CONSTRAINT fk_nome_est FOREIGN KEY(nome_est) REFERENCES registro_crimes.estado(nome),
	CONSTRAINT fk_tipo FOREIGN KEY(tipo) REFERENCES registro_crimes.ocorrencias_crime(tipo),
	CONSTRAINT fk_mes FOREIGN KEY(mes) REFERENCES registro_crimes.mes(mes),
	CONSTRAINT fk_ano FOREIGN KEY(ano) REFERENCES registro_crimes.ano(ano),
	CONSTRAINT fk_sexo FOREIGN KEY(sexo) REFERENCES registro_crimes.vitima(sexo),
	CONSTRAINT pk_chaves_crimes PRIMARY KEY(tipo, mes, ano, sexo)
);

CREATE TABLE registro_crimes.cargas(
    nome VARCHAR(50),
    data timestamp,
    numero_tuplas INT,
    CONSTRAINT pk_cargas PRIMARY KEY (nome, data)
);
