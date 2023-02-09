# Descrição do projeto:
Sistema fullstack que visa disponibilizar a opção de upload de planilhas com conjuntos de dados que automaticamente-os insere e/ou os atualiza no banco de dados. Frontend desenvolvido utilizando Angular que se comunica com backend via API REST, backend é uma API desenvolvida utilizando o framework do Spring Boot do Java.

### Dados escolhidos:
  - Selecionamos dados do [DadosGov](https://dados.gov.br/dataset/sistema-nacional-de-estatisticas-de-seguranca-publica) que disponibilizada duas tabelas, uma de ocorrências que mostra a quantidade de cada tipo de crime por Tipo, UF, Ano e mês.
  - Selecionamos dados do [IBGE](https://www.ibge.gov.br/estatisticas/sociais/populacao/9103-estimativas-de-populacao.html?=&t=downloads) que disponibiliza varias tabelas, selecionamos as de 2015 até 2021, as tabelas demonstram o crescimento da população do Brasil por estado e região.
 
### Relatórios gerados:
	- Quantidade de crimes por estado
	- Quantidade de crimes por tipo e por estado
	- Diferença da quantidade de crimes por estado
	- Diferença da quantidade de crimes por regiões do Brasil
	- Percentual da população que foi vítima de um crime envolvendo morte
	- Diferença na quantidade entre vítimas de diferentes sexos por crime
	- Crescimento na ocorrência de crimes por ano
	- Crescimento na ocorrencia de crimes envolvendo mortes violentas
	- Crescimento da população de determinado estado por ano
	- Crescimento da população de determinada região por ano
