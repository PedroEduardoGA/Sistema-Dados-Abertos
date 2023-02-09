package br.com.uel.sistemadadosabertos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.uel.sistemadadosabertos.DAO.CrimesUfDAO;
import br.com.uel.sistemadadosabertos.DAO.CrimesUfMorteDAO;
import br.com.uel.sistemadadosabertos.DAO.OcorrenciasCrimesDAO;
import br.com.uel.sistemadadosabertos.models.Ano;
import br.com.uel.sistemadadosabertos.models.CrimesUf;
import br.com.uel.sistemadadosabertos.models.CrimesUfMorte;
import br.com.uel.sistemadadosabertos.models.Estado;
import br.com.uel.sistemadadosabertos.models.OcorrenciasCrimes;
import br.com.uel.sistemadadosabertos.models.Regiao;
import br.com.uel.sistemadadosabertos.models.response.DadosTotais;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrimesService {
    
    @Autowired
    OcorrenciasCrimesDAO tipoCrimesDAO;

    @Autowired
    CrimesUfDAO crimesUfDAO;

    @Autowired
    CrimesUfMorteDAO crimesUfMorteDAO;

    @Autowired
    CargasService cargasService;

    @Autowired
    EstadoService estadoService;

    @Autowired
    DadosService dadosService;

    public int insereCrimes(String filename, List<CrimesUf> crimes){
        log.info("[CrimesService] Inserindo nova carga de dados de crimes !");
        int inserted;
        int inseridos = 0;

        cargasService.insereCarga(filename, crimes.size());

        for (CrimesUf crime : crimes) {
            verificaTipo(crime.getTipo());

            if(crimesUfDAO.exists(crime.getNome_est(), crime.getTipo(), crime.getMes(), crime.getAno()))
                inserted = crimesUfDAO.update(crime);
            else
                inserted = crimesUfDAO.create(crime);

            inseridos+= inserted;
        }

        return inseridos;
    }

    public int insereCrimesVitimas(String filename, List<CrimesUfMorte> crimesMorte){
        log.info("[CrimesService] Inserindo nova carga de dados de crimes com vitimas !");
        int inserted;
        int inseridos = 0;

        cargasService.insereCarga(filename, crimesMorte.size());

        for (CrimesUfMorte crimeMorte : crimesMorte) {
            verificaTipo(crimeMorte.getTipo());

            if(crimesUfMorteDAO.exists(crimeMorte.getNome_est(), crimeMorte.getTipo(), crimeMorte.getMes(), crimeMorte.getAno(), crimeMorte.getSexo()))
                inserted = crimesUfMorteDAO.update(crimeMorte);
            else
                inserted = crimesUfMorteDAO.create(crimeMorte);

            inseridos+= inserted;
        }
        
        return inseridos;
    }

    public List<CrimesUf> getCrimesUf(){
        log.info("[CrimesService] Retornando todos crimes por UF registradas no banco!");
        return crimesUfDAO.getAll();
    }

    public List<OcorrenciasCrimes> getTiposCrimes(){
        log.info("[CrimesService] Retornando todos tipos de crimes registrados no banco!");
        return tipoCrimesDAO.getAll();
    }

    public int verificaTipo(String tipo){
        if(!tipoCrimesDAO.exists(tipo)) // Se na tabela de tipo de crimes nao existir o tipo vai inserir o tipo do crime na tabela
            return tipoCrimesDAO.create(new OcorrenciasCrimes(tipo));
        else
            return 1; // tipo ja existe na tabela
    }

    //Retorna lista com todos estados e sua quantidade total de crimes do tipo informado ocorridos no ano informado
    public List<Object> listagemTotalCrimesDoTipo(String tipo, int ano){
        List<Object> retorno = new ArrayList<>();

        List<Estado> estados = estadoService.getEstados();
        for (Estado estado : estados) {
            if( estado.getNome().equals("Brasil"))
                continue;

            DadosTotais dadosEst = new DadosTotais();

            dadosEst.setNome(estado.getNome());
            dadosEst.setQtdTotal(crimesUfDAO.sumByEstadoAndAnoAndTipo(estado.getNome(), ano, tipo));
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com todos estados e sua quantidade total de crimes ocorridos no ano informado
    public List<Object> listagemTotalCrimes(int ano){
        List<Object> retorno = new ArrayList<>();

        List<Estado> estados = estadoService.getEstados();
        for (Estado estado : estados) {
            if( estado.getNome().equals("Brasil"))
                continue;

            DadosTotais dadosEst = new DadosTotais();

            dadosEst.setNome(estado.getNome());
            dadosEst.setQtdTotal(crimesUfDAO.sumByEstadoAndAno(estado.getNome(), ano));
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com todos estados da região informada e sua quantidade total de crimes ocorridos no ano informado
    public List<Object> listagemTotalCrimesDosEstadosDaRegiao(String nome_reg, int ano){
        List<Object> retorno = new ArrayList<>();

        List<Estado> estados = estadoService.getEstadosByRegiao(nome_reg);
        for (Estado estado : estados) {
            DadosTotais dadosEst = new DadosTotais();

            dadosEst.setNome(estado.getNome());
            dadosEst.setQtdTotal(crimesUfDAO.sumByEstadoAndAno(estado.getNome(), ano));
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com todas regioes e sua quantidade total de crimes ocorridos no ano informado
    public List<Object> listagemTotalCrimesDasRegioes(int ano){
        List<Object> retorno = new ArrayList<>();

        List<Regiao> regioes = estadoService.getRegioes();
        for (Regiao regiao : regioes) {
            if( regiao.getNome().equals("Brasil") )
                continue;

            DadosTotais dadosReg = new DadosTotais();

            dadosReg.setNome(regiao.getNome());
            dadosReg.setQtdTotal( crimesUfDAO.sumByRegiaoAndAno(regiao.getNome(), ano) );
            retorno.add(dadosReg);
        }
        return retorno;
    }

    //Retorna lista com todos estados e o percentual da populacao que foi vitima de crimes com morte
    public List<Object> listagemPercentualCrimesComMorte(int ano){
        if( ano == 2022){
            log.error("[CrimesService] Falha ao retornar dados da consulta, a tabela de populacao total nao possui dados de 2022!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tabela de populacao total nao possui dados de 2022");
        }

        List<Object> retorno = new ArrayList<>();

        List<Estado> estados = estadoService.getEstados();
        for (Estado estado : estados) {
            if( estado.getNome().equals("Brasil"))
                continue;

            DadosTotais dadosEst = new DadosTotais();

            int quantidadeTotal = crimesUfMorteDAO.sumByEstadoAndAno(estado.getNome(), ano); //Obtem total de vitimas dos crimes do estado no ano em questao
            int populacaoTotal = estadoService.getPopulacaoTotalEstado(estado.getNome(), ano); //Obtem populacao total do estado no ano em questao

            if( populacaoTotal == 0 ){
                log.error("[CrimesService] Falha ao obter populacao total do estado " +estado.getNome());
                continue;
            }

            dadosEst.setNome(estado.getNome());
            dadosEst.setPercentual(((double) quantidadeTotal/populacaoTotal)*100);
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com crescimento de crimes do estado em questao ao longo dos anos
    public List<Object> listagemCrescimentoCrimesPorEstado(String nome_est){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            DadosTotais dadosEst = new DadosTotais();

            dadosEst.setNome( nome_est );
            dadosEst.setQtdTotal( crimesUfDAO.sumByEstadoAndAno(nome_est, ano.getAno()) ); //pega qtd total de crimes do estado no ano em questao
            dadosEst.setAno( ano.getAno() );
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com crescimento de crimes com morte do estado em questao ao longo dos anos
    public List<Object> listagemCrescimentoCrimesMortePorEstado(String nome_est){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            DadosTotais dadosEst = new DadosTotais();

            dadosEst.setNome( nome_est );
            dadosEst.setQtdTotal( crimesUfMorteDAO.sumByEstadoAndAno(nome_est, ano.getAno()) ); //pega qtd total de crimes com morte do estado no ano em questao
            dadosEst.setAno( ano.getAno() );
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com crescimento de crimes da regiao em questao ao longo dos anos
    public List<Object> listagemCrescimentoCrimesPorRegiao(String nome_reg){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            DadosTotais dadosReg = new DadosTotais();

            dadosReg.setNome(nome_reg);
            dadosReg.setQtdTotal( crimesUfDAO.sumByRegiaoAndAno(nome_reg, ano.getAno()) );
            dadosReg.setAno( ano.getAno() );
            retorno.add(dadosReg);
        }
        return retorno;
    }

    //Retorna lista com crescimento de crimes com morte da regiao em questao ao longo dos anos
    public List<Object> listagemCrescimentoCrimesMortePorRegiao(String nome_reg){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            DadosTotais dadosReg = new DadosTotais();

            dadosReg.setNome(nome_reg);
            dadosReg.setQtdTotal( crimesUfMorteDAO.sumByRegiaoAndAno(nome_reg, ano.getAno()) );
            dadosReg.setAno( ano.getAno() );
            retorno.add(dadosReg);
        }
        return retorno;
    }

    //Retorna lista com crescimento de crimes em todo Brasil ao longo dos anos
    public List<Object> listagemCrescimentoCrimesBrasil(){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            DadosTotais dadosReg = new DadosTotais();

            dadosReg.setNome("Brasil");
            dadosReg.setQtdTotal( crimesUfDAO.sumByAno(ano.getAno()) );
            dadosReg.setAno( ano.getAno() );
            retorno.add(dadosReg);
        }
        return retorno;
    }

    //Retorna lista com crescimento de crimes com morte em todo Brasil ao longo dos anos
    public List<Object> listagemCrescimentoCrimesMorteBrasil(){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            DadosTotais dadosReg = new DadosTotais();

            dadosReg.setNome("Brasil");
            dadosReg.setQtdTotal( crimesUfMorteDAO.sumByAno(ano.getAno()) );
            dadosReg.setAno( ano.getAno() );
            retorno.add(dadosReg);
        }
        return retorno;
    }

    //Retorna lista com quantidade de vitimas por sexo dos crimes com morte no estado ao longo dos anos
    public List<Object> listagemTotalCrimesPorVitimaAndEstado(String nome_est){
        List<Object> retorno = new ArrayList<>();
        List<CrimesUfMorte> listagem = crimesUfMorteDAO.listByEstadoAndAno(nome_est);
        retorno.addAll(listagem);
        return retorno;
    }

    //Retorna lista com quantidade de vitimas por sexo dos crimes com morte na regiao ao longo dos anos
    public List<Object> listagemTotalCrimesPorVitimaAndRegiao(String nome_reg){
        List<Object> retorno = new ArrayList<>();
        List<CrimesUfMorte> listagem = crimesUfMorteDAO.listByRegiaoAndAno(nome_reg);
        retorno.addAll(listagem);
        return retorno;
    }

    //Retorna lista com quantidade de vitimas por sexo dos crimes com morte no Brasil ao longo dos anos
    public List<Object> listagemTotalCrimesPorVitima(){
        List<Object> retorno = new ArrayList<>();
        List<CrimesUfMorte> listagem = crimesUfMorteDAO.listByBrasilAndAno();
        retorno.addAll(listagem);
        return retorno;
    }

}
