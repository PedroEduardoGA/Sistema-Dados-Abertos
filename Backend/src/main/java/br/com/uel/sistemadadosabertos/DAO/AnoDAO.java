package br.com.uel.sistemadadosabertos.DAO;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.Ano;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

@Slf4j
@Repository
public class AnoDAO implements DAO<Ano>{
//Classe responsavel por manipular dados no banco na tabela de ano

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_ANO = "INSERT INTO registro_crimes.ano (ano) VALUES (?)";
    private static final String SQL_GET_ANO = "SELECT * FROM registro_crimes.ano WHERE ano = ?";
    private static final String SQL_GET_ALL_ANO = "SELECT * FROM registro_crimes.ano";
    private static final String SQL_UPDATE_ANO = "UPDATE registro_crimes.ano SET ano=? WHERE ano = ?";
    private static final String SQL_DELETE_ANO = "DELETE FROM registro_crimes.ano WHERE ano = ?";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String ANO_BAD_REQUEST = "Falha ao inserir ano (%d), ano ja existente na tabela de anos!";
    private static final String ANO_NOT_FOUND = "Ano %d nao existe na tabela!";
    
    @Override
    public int create(Ano ano) {
        try {
            return jdbc.update(SQL_CREATE_ANO, new Object[] { ano.getAno() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(ANO_BAD_REQUEST, ano.getAno());
            log.error("[AnoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public Ano get(Object key) {
        try {
            return jdbc.queryForObject(SQL_GET_ANO, BeanPropertyRowMapper.newInstance(Ano.class), key);
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(ANO_NOT_FOUND, key);
            log.error("[AnoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<Ano> getAll() {
        return jdbc.query(SQL_GET_ALL_ANO,  BeanPropertyRowMapper.newInstance(Ano.class));
    }

    @Override
    public int update(Ano ano) {
        try {
            return jdbc.update(SQL_UPDATE_ANO, new Object[] { ano.getAno(), ano.getAno()});
        } catch (DuplicateKeyException e) {
            String erro = String.format(ANO_BAD_REQUEST, ano.getAno());
            log.error("[AnoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        log.info("[AnoDAO] Deletando ano " +key);
        return jdbc.update(SQL_DELETE_ANO, key);
    }
    
}
