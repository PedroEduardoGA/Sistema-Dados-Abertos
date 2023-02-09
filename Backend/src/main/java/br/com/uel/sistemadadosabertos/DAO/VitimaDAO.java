package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.Vitima;

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
public class VitimaDAO implements DAO<Vitima>{
//Classe responsavel por manipular dados no banco na tabela de vitimas

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_VITIMA = "INSERT INTO registro_crimes.vitima (sexo, descricao) VALUES (?, ?)";
    private static final String SQL_GET_VITIMA = "SELECT * FROM registro_crimes.vitima WHERE sexo = ?";
    private static final String SQL_GET_ALL_VITIMA = "SELECT * FROM registro_crimes.vitima";
    private static final String SQL_UPDATE_VITIMA = "UPDATE registro_crimes.vitima SET descricao=? WHERE sexo = ?";
    private static final String SQL_DELETE_VITIMA = "DELETE FROM registro_crimes.vitima WHERE sexo = ?";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String VITIMA_BAD_REQUEST = "Falha ao inserir vitima (%s), sexo ja existente na tabela de vitima!";
    private static final String VITIMA_NOT_FOUND = "Vitima (%s) nao existe na tabela!";
    
    @Override
    public int create(Vitima vitima) {
        try {
            return jdbc.update(SQL_CREATE_VITIMA, new Object[] { vitima.getSexo(), vitima.getDescricao() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(VITIMA_BAD_REQUEST, vitima.getSexo());
            log.error("[VitimaDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public Vitima get(Object key) {
        try {
            return jdbc.queryForObject(SQL_GET_VITIMA, BeanPropertyRowMapper.newInstance(Vitima.class), key);
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(VITIMA_NOT_FOUND, key);
            log.error("[VitimaDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<Vitima> getAll() {
        return jdbc.query(SQL_GET_ALL_VITIMA,  BeanPropertyRowMapper.newInstance(Vitima.class));
    }

    @Override
    public int update(Vitima vitima) {
        try {
            return jdbc.update(SQL_UPDATE_VITIMA, new Object[] { vitima.getDescricao(), vitima.getSexo() });
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(VITIMA_NOT_FOUND, vitima.getSexo());
            log.error("[VitimaDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        log.info("[VitimaDAO] Deletando vitima do sexo '{}' !", key);
        return jdbc.update(SQL_DELETE_VITIMA, key);
    }
    
}
