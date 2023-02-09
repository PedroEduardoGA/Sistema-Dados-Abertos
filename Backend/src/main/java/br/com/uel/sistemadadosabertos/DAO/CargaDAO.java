package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.Carga;

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
public class CargaDAO implements DAO<Carga>{
//Classe responsavel por manipular dados no banco na tabela de cargas

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_CARGA = "INSERT INTO registro_crimes.cargas (nome, data, numero_tuplas) VALUES (?, ?, ?)";
    private static final String SQL_GET_CARGA = "SELECT * FROM registro_crimes.cargas WHERE nome = ? AND data = ?";
    private static final String SQL_GET_ALL_CARGA = "SELECT * FROM registro_crimes.cargas";
    private static final String SQL_DELETE_CARGA = "DELETE FROM registro_crimes.cargas WHERE nome = ? AND data = ?";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String CARGA_BAD_REQUEST = "Falha ao inserir carga (%s, %s, %d), dados ja existente na tabela de cargas!";
    private static final String CARGA_NOT_FOUND = "Carga (%s, %s, %d) nao existe na tabela!";
    
    @Override
    public int create(Carga carga) {
        try {
            return jdbc.update(SQL_CREATE_CARGA, new Object[] { carga.getNome(), carga.getData(), carga.getNumero_tuplas() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(CARGA_BAD_REQUEST, carga.getNome(), carga.getData().toString(), carga.getNumero_tuplas());
            log.error("[CargaDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public Carga get(Object key) {
        Carga carga = (Carga) key;
        try {
            return jdbc.queryForObject(SQL_GET_CARGA, BeanPropertyRowMapper.newInstance(Carga.class), carga.getNome(), carga.getData());
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(CARGA_NOT_FOUND, carga.getNome(), carga.getData().toString(), carga.getNumero_tuplas());
            log.error("[CargaDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<Carga> getAll() {
        return jdbc.query(SQL_GET_ALL_CARGA,  BeanPropertyRowMapper.newInstance(Carga.class));
    }

    @Deprecated
    @Override
    public int update(Carga carga) {
        // Metodo inutilizado neste DAO!
        return -1;
    }

    @Override
    public int delete(Object key) {
        Carga carga = (Carga) key;
        log.info("[CargaDAO] Deletando carga '{}' realizada em '{}' !",carga.getNome(), carga.getData());
        return jdbc.update(SQL_DELETE_CARGA, carga.getNome(), carga.getData());
    }
    
}
