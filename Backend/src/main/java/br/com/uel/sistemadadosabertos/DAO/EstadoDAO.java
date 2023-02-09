package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.Estado;

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
public class EstadoDAO implements DAO<Estado>{
//Classe responsavel por manipular dados no banco na tabela de estado

    @Autowired
    JdbcTemplate jdbc;
    
    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_ESTADO = "INSERT INTO registro_crimes.estado (nome_reg, nome, sigla) VALUES (?, ?, ?)";
    private static final String SQL_GET_ESTADO = "SELECT * FROM registro_crimes.estado WHERE nome = ?";
    private static final String SQL_GET_ALL_ESTADO = "SELECT * FROM registro_crimes.estado";
    private static final String SQL_UPDATE_ESTADO = "UPDATE registro_crimes.estado SET nome_reg=?, sigla=? WHERE nome = ?";
    private static final String SQL_DELETE_ESTADO = "DELETE FROM registro_crimes.estado WHERE nome = ?";

    private static final String SQL_EXISTS_ESTADO = "SELECT EXISTS (SELECT * FROM registro_crimes.estado WHERE nome = ?)";
    private static final String SQL_GET_ESTADOS_BY_REGIAO = "SELECT * FROM registro_crimes.estado WHERE nome_reg= ?";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String ESTADO_BAD_REQUEST = "Falha ao inserir estado (%s), nome ja existente na tabela de estados!";
    private static final String ESTADO_NOT_FOUND = "Estado %s nao existe na tabela!";
    private static final String REGIAO_EST_NOT_FOUND = "Falha ao atualizar estado, regiao %s nao existe na tabela de regioes!";

    @Override
    public int create(Estado estado) {
        try {
            return jdbc.update(SQL_CREATE_ESTADO, new Object[] { estado.getNome_reg(), estado.getNome(), estado.getSigla() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(ESTADO_BAD_REQUEST, estado.getNome());
            log.error("[EstadoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,erro);
        }
    }

    @Override
    public Estado get(Object key) {
        try {
            return jdbc.queryForObject(SQL_GET_ESTADO, BeanPropertyRowMapper.newInstance(Estado.class), key);
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(ESTADO_NOT_FOUND, key);
            log.error("[EstadoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<Estado> getAll() {
        return jdbc.query(SQL_GET_ALL_ESTADO,  BeanPropertyRowMapper.newInstance(Estado.class));
    }

    @Override
    public int update(Estado estado) {
        try {
            return jdbc.update(SQL_UPDATE_ESTADO, new Object[] { estado.getNome_reg(), estado.getSigla(), estado.getNome()});
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(REGIAO_EST_NOT_FOUND, estado.getNome_reg());
            log.error("[EstadoDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        log.info("[EstadoDAO] Deletando estado " +key);
        return jdbc.update(SQL_DELETE_ESTADO, key);
    }
    
    //--MetodosEspecificos-----
    public boolean exists(String nome){
        return jdbc.queryForObject(SQL_EXISTS_ESTADO, Boolean.class, nome);
    }

    public List<Estado> getAllByRegiao(String nome_reg) {
        return jdbc.query(SQL_GET_ESTADOS_BY_REGIAO,  BeanPropertyRowMapper.newInstance(Estado.class), nome_reg);
    } 
}
