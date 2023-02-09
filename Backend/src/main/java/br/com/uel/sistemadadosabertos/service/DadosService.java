package br.com.uel.sistemadadosabertos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uel.sistemadadosabertos.DAO.AnoDAO;
import br.com.uel.sistemadadosabertos.models.Ano;
import br.com.uel.sistemadadosabertos.models.Consulta;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DadosService {

    @Autowired
    AnoDAO anoDAO;

    @Autowired
    EstadoService estadoService;

    @Autowired
    CrimesService crimesService;

    public List<Object> consultaDadosCrimes(Consulta consulta, int opcao){
        switch (opcao) {
            case 1:
                if( consulta.getTipo().equals("Todos")){
                    log.info("[DadosService] Retornando registro de todos os estados e a quantidade total de crimes ocorridos em cada!");
                    return crimesService.listagemTotalCrimes(consulta.getAno());
                }else{
                    log.info("[DadosService] Retornando registro de todos os estados e a quantidade total de crimes ocorridos de '{}' em cada!", consulta.getTipo());
                    return crimesService.listagemTotalCrimesDoTipo(consulta.getTipo(), consulta.getAno());
                }
            case 2:
                if( !consulta.getRegiao().equals("Brasil") ){
                    log.info("[DadosService] Retornando quantidade de crimes dos estados da regiao " +consulta.getRegiao());
                    return crimesService.listagemTotalCrimesDosEstadosDaRegiao(consulta.getRegiao(), consulta.getAno());
                }else{
                    log.info("[DadosService] Retornando quantidade de crimes de todas as regioes no ano " +consulta.getAno());
                    return crimesService.listagemTotalCrimesDasRegioes(consulta.getAno());
                }
            case 3:
                log.info("[DadosService] Retornando percentual da populcao que foi vitima de crimes envolvendo mortes de todos estados!");
                return crimesService.listagemPercentualCrimesComMorte(consulta.getAno());
            case 4:
                if( consulta.getRegiao().equals("Brasil") && !consulta.getEstado().equals("Brasil") ){
                    log.info("[DadosService] Retornando quantidade total de cada sexo em todos os anos pro estado '{}'!",consulta.getEstado());
                    return crimesService.listagemTotalCrimesPorVitimaAndEstado(consulta.getEstado());
                }else if(consulta.getEstado().equals("Brasil")){
                    log.info("[DadosService] Retornando quantidade total de cada sexo em todos os anos pro Brasil!");
                    return crimesService.listagemTotalCrimesPorVitima();
                }else{
                    log.info("[DadosService] Retornando quantidade total de cada sexo em todos os anos pra '{}'!",consulta.getRegiao());
                    return crimesService.listagemTotalCrimesPorVitimaAndRegiao(consulta.getRegiao());
                }
            case 5:
                if( consulta.getOpcao().equals("crimes")){
                    if( consulta.getRegiao().equals("Brasil") ){
                        if( consulta.getEstado().equals("Brasil") ){
                            log.info("[DadosService] Retornando crescimento de crimes ao longo dos anos pro Brasil!");
                            return crimesService.listagemCrescimentoCrimesBrasil();
                        }
                        log.info("[DadosService] Retornando crescimento de crimes ao longo dos anos pro estado " +consulta.getEstado());
                        return crimesService.listagemCrescimentoCrimesPorEstado(consulta.getEstado());
                    }else{
                        log.info("[DadosService] Retornando crescimento de crimes ao longo dos anos pra " +consulta.getRegiao());
                        return crimesService.listagemCrescimentoCrimesPorRegiao(consulta.getRegiao());
                    }
                }

                if( consulta.getOpcao().equals("crimes-morte")){
                    if( consulta.getRegiao().equals("Brasil") ){
                        if( consulta.getEstado().equals("Brasil") ){
                            log.info("[DadosService] Retornando crescimento de crimes com morte ao longo dos anos pro Brasil!");
                            return crimesService.listagemCrescimentoCrimesMorteBrasil();
                        }
                        log.info("[DadosService] Retornando crescimento de crimes com morte ao longo dos anos pro estado " +consulta.getEstado());
                        return crimesService.listagemCrescimentoCrimesMortePorEstado(consulta.getEstado());
                    }else{
                        log.info("[DadosService] Retornando crescimento de crimes com morte ao longo dos anos pra " +consulta.getRegiao());
                        return crimesService.listagemCrescimentoCrimesMortePorRegiao(consulta.getRegiao());
                    }
                }

                if( consulta.getOpcao().equals("populacao")){
                    if( !consulta.getRegiao().equals("Brasil") ){
                        log.info("[DadosService] Retornando crescimento da populacao ao longo dos anos pra " +consulta.getRegiao());
                        return estadoService.listagemCrescimentoPopulacaoPorRegiao(consulta.getRegiao());
                    }else{
                        log.info("[DadosService] Retornando crescimento da populacao ao longo dos anos pro estado " +consulta.getEstado());
                        return estadoService.listagemCrescimentoPopulacaoPorEstado(consulta.getEstado());
                    }
                }
                
            default:
                log.error("[DadosService] Opcao de consulta invalida!");
                return null;
        }
    }

    public List<Ano> getAnos(){
        log.info("[DadosService] Retornando todos anos registrados!");
        return anoDAO.getAll();
    }

}
