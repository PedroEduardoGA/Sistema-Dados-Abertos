package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.OcorrenciasCrimes;

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
public class OcorrenciasCrimesDAO implements DAO<OcorrenciasCrimes> {
//Classe responsavel por manipular dados no banco na tabela de ocorrencias de crime

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_CRIME = "INSERT INTO registro_crimes.ocorrencias_crime (tipo) VALUES (?)";
    private static final String SQL_GET_CRIME = "SELECT * FROM registro_crimes.ocorrencias_crime WHERE tipo = ?";
    private static final String SQL_GET_ALL_CRIME = "SELECT * FROM registro_crimes.ocorrencias_crime";
    private static final String SQL_UPDATE_CRIME = "UPDATE registro_crimes.ocorrencias_crime SET tipo=? WHERE tipo = ?";
    private static final String SQL_DELETE_CRIME = "DELETE FROM registro_crimes.ocorrencias_crime WHERE tipo = ?";

    private static final String SQL_EXISTS_CRIME = "SELECT EXISTS (SELECT * FROM registro_crimes.ocorrencias_crime WHERE tipo = ?)";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String CRIME_BAD_REQUEST = "Falha ao inserir crime (%s), crime ja existente na tabela de ocorrencias de crime!";
    private static final String CRIME_NOT_FOUND = "Crime (%s) nao existe na tabela!";

    @Override
    public int create(OcorrenciasCrimes ocorrenciaCrime) {
        try {
            return jdbc.update(SQL_CREATE_CRIME, new Object[] { ocorrenciaCrime.getTipo() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(CRIME_BAD_REQUEST, ocorrenciaCrime.getTipo());
            log.error("[OcorrenciaCrimeDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public OcorrenciasCrimes get(Object key) {
        try {
            return jdbc.queryForObject(SQL_GET_CRIME, BeanPropertyRowMapper.newInstance(OcorrenciasCrimes.class), key);
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(CRIME_NOT_FOUND, key);
            log.error("[OcorrenciaCrimeDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<OcorrenciasCrimes> getAll() {
        return jdbc.query(SQL_GET_ALL_CRIME,  BeanPropertyRowMapper.newInstance(OcorrenciasCrimes.class));
    }

    @Override
    public int update(OcorrenciasCrimes ocorrenciaCrime) {
        try {
            return jdbc.update(SQL_UPDATE_CRIME, new Object[] { ocorrenciaCrime.getTipo(), ocorrenciaCrime.getTipo() });
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(CRIME_NOT_FOUND, ocorrenciaCrime.getTipo());
            log.error("[OcorrenciaCrimeDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        log.info("[OcorrenciaCrimeDAO] Deletando crime do tipo '{}' !", key);
        return jdbc.update(SQL_DELETE_CRIME, key);
    }

    //--MetodosEspecificos-----
    public boolean exists(String tipo){
        return jdbc.queryForObject(SQL_EXISTS_CRIME, Boolean.class, tipo);
    }
    
}
