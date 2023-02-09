package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.Regiao;

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
public class RegiaoDAO implements DAO<Regiao> {
//Classe responsavel por manipular dados no banco na tabela de regiao

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_REGIAO = "INSERT INTO registro_crimes.regiao (nome) VALUES (?)";
    private static final String SQL_GET_REGIAO = "SELECT * FROM registro_crimes.regiao WHERE nome = ?";
    private static final String SQL_GET_ALL_REGIAO = "SELECT * FROM registro_crimes.regiao";
    private static final String SQL_UPDATE_REGIAO = "UPDATE registro_crimes.regiao SET nome=? WHERE nome = ?";
    private static final String SQL_DELETE_REGIAO = "DELETE FROM registro_crimes.regiao WHERE nome = ?";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String REGIAO_BAD_REQUEST = "Falha ao inserir regiao (%s), nome ja existente na tabela de regioes!";
    private static final String REGIAO_NOT_FOUND = "Regiao %s nao existe na tabela!";
    
    @Override
    public int create(Regiao regiao) {
        try {
            return jdbc.update(SQL_CREATE_REGIAO, new Object[] { regiao.getNome() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(REGIAO_BAD_REQUEST, regiao.getNome());
            log.error("[RegiaoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public Regiao get(Object key) {
        try {
            return jdbc.queryForObject(SQL_GET_REGIAO, BeanPropertyRowMapper.newInstance(Regiao.class), key);
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(REGIAO_NOT_FOUND, key);
            log.error("[RegiaoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<Regiao> getAll() {
        return jdbc.query(SQL_GET_ALL_REGIAO,  BeanPropertyRowMapper.newInstance(Regiao.class));
    }

    @Override
    public int update(Regiao regiao) {
        try {
            return jdbc.update(SQL_UPDATE_REGIAO, new Object[] { regiao.getNome() });
        } catch (IncorrectResultSizeDataAccessException e) {
            String erro = String.format(REGIAO_NOT_FOUND, regiao.getNome());
            log.error("[RegiaoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        log.info("[RegiaoDAO] Deletando regiao " +key);
        return jdbc.update(SQL_DELETE_REGIAO, key);
    }
    
}
