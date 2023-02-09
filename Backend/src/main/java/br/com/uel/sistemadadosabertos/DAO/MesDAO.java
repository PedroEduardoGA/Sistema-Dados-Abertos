package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.Mes;

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
public class MesDAO implements DAO<Mes> {
//Classe responsavel por manipular dados no banco na tabela de mes

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_MES = "INSERT INTO registro_crimes.mes (num_mes, mes) VALUES (?, ?)";
    private static final String SQL_GET_MES = "SELECT * FROM registro_crimes.mes WHERE num_mes = ?";
    private static final String SQL_GET_ALL_MES = "SELECT * FROM registro_crimes.mes";
    private static final String SQL_UPDATE_MES = "UPDATE registro_crimes.mes SET mes=? WHERE num_mes = ?";
    private static final String SQL_DELETE_MES = "DELETE FROM registro_crimes.mes WHERE num_mes = ?";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String MES_BAD_REQUEST = "Falha ao inserir mes (%d, %s), mes ja existente na tabela de meses!";
    private static final String MES_NOT_FOUND = "Mes %d nao existe na tabela!";

    @Override
    public int create(Mes mes) {
        try {
            return jdbc.update(SQL_CREATE_MES, new Object[] { mes.getNum_mes(), mes.getMes() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(MES_BAD_REQUEST, mes.getNum_mes(), mes.getMes());
            log.error("[MesDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public Mes get(Object key) {
        try {
            return jdbc.queryForObject(SQL_GET_MES, BeanPropertyRowMapper.newInstance(Mes.class), key);
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(MES_NOT_FOUND, key);
            log.error("[MesDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<Mes> getAll() {
        return jdbc.query(SQL_GET_ALL_MES,  BeanPropertyRowMapper.newInstance(Mes.class));
    }

    @Override
    public int update(Mes mes) {
        try {
            return jdbc.update(SQL_UPDATE_MES, new Object[] { mes.getMes(), mes.getNum_mes()});
        } catch (IncorrectResultSizeDataAccessException e) {
            String erro = String.format(MES_NOT_FOUND, mes.getNum_mes());
            log.error("[MesDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        log.info("[MesDAO] Deletando mes " +key);
        return jdbc.update(SQL_DELETE_MES, key);
    }
    
}
