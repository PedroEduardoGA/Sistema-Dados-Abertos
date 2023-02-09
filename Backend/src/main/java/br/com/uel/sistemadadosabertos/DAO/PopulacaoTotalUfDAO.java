package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.PopulacaoTotalUf;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

@Slf4j
@Repository
public class PopulacaoTotalUfDAO implements DAO<PopulacaoTotalUf>{
//Classe responsavel por manipular dados no banco na tabela de populacao total

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_POPTOTAL_UF = "INSERT INTO registro_crimes.uf_tem_poptotal (ano, nome_est, pop_total) VALUES (?, ?, ?)";
    private static final String SQL_GET_POPTOTAL_UF = "SELECT * FROM registro_crimes.uf_tem_poptotal WHERE ano = ? AND nome_est = ?";
    private static final String SQL_GET_ALL_POPTOTAL_UF = "SELECT * FROM registro_crimes.uf_tem_poptotal";
    private static final String SQL_UPDATE_POPTOTAL_UF = "UPDATE registro_crimes.uf_tem_poptotal SET pop_total=? WHERE ano = ? AND nome_est = ?";
    private static final String SQL_DELETE_POPTOTAL_UF = "DELETE FROM registro_crimes.uf_tem_poptotal WHERE ano = ? AND nome_est = ?";

    private static final String SQL_EXISTS_POPTOTAL = "SELECT EXISTS (SELECT * FROM registro_crimes.uf_tem_poptotal WHERE ano = ? AND nome_est = ?)";
    private static final String SQL_SUM_POPTOTAL_BY_REGIAO = "SELECT SUM (CASE  WHEN ano = ? AND nome_reg = ? THEN pop_total ELSE 0 END) FROM registro_crimes.uf_tem_poptotal INNER JOIN registro_crimes.estado ON registro_crimes.uf_tem_poptotal.nome_est = registro_crimes.estado.nome";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String POPTOTAL_UF_BAD_REQUEST = "Falha ao inserir populacao total (%s, %d), dados ja existentes na tabela de populacao total!";
    private static final String POPTOTAL_UF_NOT_FOUND = "Estado %s no ano de %s nao possui registro de populacao total na tabela!";
    private static final String NOME_EST_NOT_FOUND = "Falha ao atualizar populacao total, estado %s ou ano %d nao existem nas tabelas externas!";

    @Override
    public int create(PopulacaoTotalUf popTotalUf) {
        try {
            return jdbc.update(SQL_CREATE_POPTOTAL_UF, new Object[] { popTotalUf.getAno(), popTotalUf.getNome_est(), popTotalUf.getPop_total() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(POPTOTAL_UF_BAD_REQUEST, popTotalUf.getNome_est(), popTotalUf.getAno());
            log.error("[PopulacaoTotalDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(NOME_EST_NOT_FOUND, popTotalUf.getNome_est());
            log.error("[PopulacaoTotalDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public PopulacaoTotalUf get(Object key) {
        PopulacaoTotalUf keys = (PopulacaoTotalUf) key;
        try {
            return jdbc.queryForObject(SQL_GET_POPTOTAL_UF, BeanPropertyRowMapper.newInstance(PopulacaoTotalUf.class), keys.getAno(), keys.getNome_est());
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(POPTOTAL_UF_NOT_FOUND, keys.getNome_est(), keys.getAno());
            log.error("[PopulacaoTotalDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<PopulacaoTotalUf> getAll() {
        return jdbc.query(SQL_GET_ALL_POPTOTAL_UF,  BeanPropertyRowMapper.newInstance(PopulacaoTotalUf.class));
    }

    @Override
    public int update(PopulacaoTotalUf popTotalUf) {
        try {
            return jdbc.update(SQL_UPDATE_POPTOTAL_UF, new Object[] { popTotalUf.getPop_total(), popTotalUf.getAno(), popTotalUf.getNome_est() });
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(NOME_EST_NOT_FOUND, popTotalUf.getNome_est(), popTotalUf.getAno());
            log.error("[PopulacaoTotalDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        PopulacaoTotalUf keys = (PopulacaoTotalUf) key;
        log.info("[PopulacaoTotalDAO] Deletando registro de populacao total do estado '{}' no ano de '{}' !", keys.getNome_est(), keys.getAno());
        return jdbc.update(SQL_DELETE_POPTOTAL_UF, keys.getNome_est(), keys.getAno());
    }

    //--MetodosEspecificos-----
    public boolean exists(int ano, String nome_est){
        return jdbc.queryForObject(SQL_EXISTS_POPTOTAL, Boolean.class, ano, nome_est);
    }

    public int sumByRegiaoAndAno(String nome_reg, int ano){
        return jdbc.queryForObject(SQL_SUM_POPTOTAL_BY_REGIAO, Integer.class, ano, nome_reg);
    }
    
}
