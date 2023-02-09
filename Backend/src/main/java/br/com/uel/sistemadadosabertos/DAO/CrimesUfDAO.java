package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.CrimesUf;

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
public class CrimesUfDAO implements DAO<CrimesUf> {
//Classe responsavel por manipular dados no banco na tabela de crimes de uma uf

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_CRIMEUF = "INSERT INTO registro_crimes.uf_tem_ocorrencias (nome_est, tipo, mes, ano, quantidade_ocorrencias) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_GET_CRIMEUF = "SELECT * FROM registro_crimes.uf_tem_ocorrencias WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ?";
    private static final String SQL_GET_ALL_CRIMEUF = "SELECT * FROM registro_crimes.uf_tem_ocorrencias";
    private static final String SQL_UPDATE_CRIMEUF = "UPDATE registro_crimes.uf_tem_ocorrencias SET quantidade_ocorrencias=? WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ?";
    private static final String SQL_DELETE_CRIMEUF = "DELETE FROM registro_crimes.uf_tem_ocorrencias WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ?";

    private static final String SQL_EXISTS_DADO = "SELECT EXISTS (SELECT * FROM registro_crimes.uf_tem_ocorrencias WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ?)";
    private static final String SQL_SUM_CRIMES_BY_ESTADO = "SELECT SUM (CASE  WHEN nome_est = ? AND ano = ? THEN quantidade_ocorrencias ELSE 0 END) FROM registro_crimes.uf_tem_ocorrencias";
    private static final String SQL_SUM_CRIMES_BY_TIPO = "SELECT SUM (CASE  WHEN nome_est = ? AND ano = ? AND tipo = ? THEN quantidade_ocorrencias ELSE 0 END) FROM registro_crimes.uf_tem_ocorrencias";
    private static final String SQL_SUM_CRIMES_BY_REGIAO = "SELECT SUM (CASE  WHEN ano = ? AND nome_reg = ? THEN quantidade_ocorrencias ELSE 0 END) FROM registro_crimes.uf_tem_ocorrencias crimes INNER JOIN registro_crimes.estado est ON crimes.nome_est = est.nome";
    private static final String SQL_SUM_ALLCRIMES_BY_ANO = "SELECT SUM (CASE  WHEN ano = ? THEN quantidade_ocorrencias ELSE 0 END) FROM registro_crimes.uf_tem_ocorrencias";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String CRIMEUF_BAD_REQUEST = "Falha ao inserir crime (%s, %s, %d, %d), dados ja existente na tabela de crimes de UF!";
    private static final String CRIMEUF_NOT_FOUND = "Crime de UF (%s, %s, %d, %d) nao existe na tabela!";
    private static final String NOME_EST_NOT_FOUND = "Falha ao atualizar crime, estado %s nao existe na tabela de estados!";

    @Override
    public int create(CrimesUf crimeUf) {
        try {
            return jdbc.update(SQL_CREATE_CRIMEUF, new Object[] { crimeUf.getNome_est(), crimeUf.getTipo(), crimeUf.getMes(), crimeUf.getAno(), crimeUf.getQuantidade_ocorrencias() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(CRIMEUF_BAD_REQUEST, crimeUf.getNome_est(), crimeUf.getTipo(), crimeUf.getMes(), crimeUf.getAno());
            log.error("[CrimesUfDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public CrimesUf get(Object key) {
        CrimesUf crime = (CrimesUf) key;
        try {
            return jdbc.queryForObject(SQL_GET_CRIMEUF, BeanPropertyRowMapper.newInstance(CrimesUf.class), crime.getNome_est(), crime.getTipo(), crime.getMes(), crime.getAno());
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(CRIMEUF_NOT_FOUND, crime.getNome_est(), crime.getTipo(), crime.getMes(), crime.getAno());
            log.error("[CrimesUfDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<CrimesUf> getAll() {
        return jdbc.query(SQL_GET_ALL_CRIMEUF,  BeanPropertyRowMapper.newInstance(CrimesUf.class));
    }

    @Override
    public int update(CrimesUf crimeUf) {
        try {
            return jdbc.update(SQL_UPDATE_CRIMEUF, new Object[] { crimeUf.getQuantidade_ocorrencias(), crimeUf.getNome_est(), crimeUf.getTipo(), crimeUf.getMes(), crimeUf.getAno() });
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(NOME_EST_NOT_FOUND, crimeUf.getNome_est());
            log.error("[CrimesUfDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        CrimesUf crime = (CrimesUf) key;
        log.info("[CrimesUfDAO] Deletando crime '{}' ocorrido em '{}' no mes '{}' de '{}' !",crime.getTipo(), crime.getNome_est(), crime.getMes(), crime.getAno());
        return jdbc.update(SQL_DELETE_CRIMEUF, crime.getNome_est(), crime.getTipo(), crime.getMes(), crime.getAno());
    }

    //--MetodosEspecificos-----
    public boolean exists(String nome_est, String tipo, int mes, int ano){
        return jdbc.queryForObject(SQL_EXISTS_DADO, Boolean.class, nome_est, tipo, mes, ano);
    }

    public int sumByEstadoAndAno(String nome_est, int ano){
        return jdbc.queryForObject(SQL_SUM_CRIMES_BY_ESTADO, Integer.class, nome_est, ano);
    }

    public int sumByEstadoAndAnoAndTipo(String nome_est, int ano, String tipo){
        return jdbc.queryForObject(SQL_SUM_CRIMES_BY_TIPO, Integer.class, nome_est, ano, tipo);
    }

    public int sumByRegiaoAndAno(String nome_reg, int ano){
        return jdbc.queryForObject(SQL_SUM_CRIMES_BY_REGIAO, Integer.class, ano, nome_reg);
    }

    public int sumByAno(int ano){
        return jdbc.queryForObject(SQL_SUM_ALLCRIMES_BY_ANO, Integer.class, ano);
    }
    
}
