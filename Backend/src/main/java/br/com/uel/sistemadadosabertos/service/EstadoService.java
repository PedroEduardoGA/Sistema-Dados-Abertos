package br.com.uel.sistemadadosabertos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uel.sistemadadosabertos.DAO.EstadoDAO;
import br.com.uel.sistemadadosabertos.DAO.PopulacaoTotalUfDAO;
import br.com.uel.sistemadadosabertos.DAO.RegiaoDAO;
import br.com.uel.sistemadadosabertos.models.Ano;
import br.com.uel.sistemadadosabertos.models.Estado;
import br.com.uel.sistemadadosabertos.models.PopulacaoTotalUf;
import br.com.uel.sistemadadosabertos.models.Regiao;
import br.com.uel.sistemadadosabertos.models.response.DadosTotais;
import br.com.uel.sistemadadosabertos.utils.Utilities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadoService {
    
    @Autowired
    EstadoDAO estadoDAO;

    @Autowired
    RegiaoDAO regiaoDAO;

    @Autowired
    PopulacaoTotalUfDAO popTotalUfDAO;

    @Autowired
    CargasService cargasService;

    @Autowired
    DadosService dadosService;

    public int inserePopulacaoTotal(String filename, List<PopulacaoTotalUf> popTotal){
        log.info("[EstadoService] Inserindo nova carga de dados de populacao total !");
        String anoStr = filename.substring( filename.indexOf("2"), filename.indexOf("2")+4);
        int ano = Integer.parseInt(anoStr);
        int inserted;
        int inseridos = 0;

        cargasService.insereCarga(filename, popTotal.size()-5);

        for (PopulacaoTotalUf popTotalUf : popTotal) {
            if(popTotalUf.getNome_est().contains("Região")){
                continue;
            }
            popTotalUf.setAno(ano);
            verificaEstado(popTotalUf.getNome_est());   //Verifica se o estado do dado de popTotal ja existe na tabela de estados

            if(popTotalUfDAO.exists(popTotalUf.getAno(), popTotalUf.getNome_est()))
                inserted = popTotalUfDAO.update(popTotalUf);
            else
                inserted = popTotalUfDAO.create(popTotalUf);

            inseridos+= inserted;
        }

        return inseridos;
    }

    public boolean verificaEstado(String nome_est){
        if(!estadoDAO.exists(nome_est)){
            String[] dadosEst = Utilities.getSiglaAndRegiao(nome_est).split("&");

            Estado estado = Estado.builder()
                .nome(nome_est)
                .nome_reg(dadosEst[1])
                .sigla(dadosEst[0]).build();

            if(estadoDAO.create(estado) == 0)
                return false;       
        }
        return true;    
    }

    public int insereEstado(Estado estado){
        log.info("[EstadoService] Inserindo novo estado!");
        return estadoDAO.create(estado);
    }

    public Estado getEstado(String nome){
        log.info("[EstadoService] Retornando informacoes sobre '{}'!",nome);
        return estadoDAO.get(nome);
    }

    public List<Estado> getEstados(){
        log.info("[EstadoService] Retornando todos estados registrados!");
        return estadoDAO.getAll();
    }

    public List<Estado> getEstadosByRegiao(String nome_reg){
        log.info("[EstadoService] Retornando todos estados da '{}'!", nome_reg);
        return estadoDAO.getAllByRegiao(nome_reg);
    }

    public List<Regiao> getRegioes(){
        log.info("[EstadoService] Retornando todas regioes registradas!");
        return regiaoDAO.getAll();
    }

    public int atualizaEstado(Estado estado){
        log.info("[EstadoService] Atualizando informacoes sobre '{}'!",estado.getNome());
        return estadoDAO.update(estado);
    }
    
    public int removeEstado(String nome){
        log.info("[EstadoService] Revomendo estado: '{}'",nome);
        return estadoDAO.delete(nome);
    }

    public int getPopulacaoTotalEstado(String nome_est, int ano){
        log.info("[EstadoService] Retornando populacao total do estado '{}' no ano de '{}'!", nome_est, ano);
        return popTotalUfDAO.get(new PopulacaoTotalUf(ano, nome_est, 0)).getPop_total();
    }

    //Retorna lista com crescimento da populacao do estado em questao ao longo dos anos
    public List<Object> listagemCrescimentoPopulacaoPorEstado(String nome_est){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            if( ano.getAno() == 2022)
                continue;

            DadosTotais dadosEst = new DadosTotais();

            dadosEst.setNome( nome_est );
            dadosEst.setQtdTotal( popTotalUfDAO.get(new PopulacaoTotalUf(ano.getAno(), nome_est, 0)).getPop_total() ); //pega qtd total de populacao do estado no ano em questao
            dadosEst.setAno( ano.getAno() );
            retorno.add(dadosEst);
        }
        return retorno;
    }

    //Retorna lista com crescimento da populacao da regiao em questao ao longo dos anos
    public List<Object> listagemCrescimentoPopulacaoPorRegiao(String nome_reg){
        List<Object> retorno = new ArrayList<>();

        List<Ano> anos = dadosService.getAnos();
        for (Ano ano : anos) {
            if( ano.getAno() == 2022)
                continue;

            DadosTotais dadosReg = new DadosTotais();

            dadosReg.setNome(nome_reg);
            dadosReg.setQtdTotal( popTotalUfDAO.sumByRegiaoAndAno(nome_reg, ano.getAno()) );
            dadosReg.setAno( ano.getAno() );
            retorno.add(dadosReg);
        }
        return retorno;
    }
    
}
