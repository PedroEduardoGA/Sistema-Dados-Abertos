package br.com.uel.sistemadadosabertos.DAO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import br.com.uel.sistemadadosabertos.models.CrimesUfMorte;

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
public class CrimesUfMorteDAO implements DAO<CrimesUfMorte>{
//Classe responsavel por manipular dados no banco na tabela de crimes de uma uf com vitimas

    @Autowired
    JdbcTemplate jdbc;

    //--------------------------------SQL's--------------------------------
    private static final String SQL_CREATE_CRIMEUF_COM_MORTE = "INSERT INTO registro_crimes.uf_tem_crimes_morte (nome_est, tipo, mes, ano, sexo, quantidade_vitimas) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_CRIMEUF_COM_MORTE = "SELECT * FROM registro_crimes.uf_tem_crimes_morte WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ? AND sexo = ?";
    private static final String SQL_GET_ALL_CRIMEUF_COM_MORTE = "SELECT * FROM registro_crimes.uf_tem_crimes_morte";
    private static final String SQL_UPDATE_CRIMEUF_COM_MORTE = "UPDATE registro_crimes.uf_tem_crimes_morte SET quantidade_vitimas=? WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ? AND sexo = ?";
    private static final String SQL_DELETE_CRIMEUF_COM_MORTE = "DELETE FROM registro_crimes.uf_tem_crimes_morte WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ? AND sexo = ?";

    private static final String SQL_EXISTS_DADO = "SELECT EXISTS (SELECT * FROM registro_crimes.uf_tem_crimes_morte WHERE nome_est = ? AND tipo = ? AND mes = ? AND ano = ? AND sexo = ?)";
    private static final String SQL_SUM_CRIMES_BY_ESTADO = "SELECT SUM (CASE  WHEN nome_est = ? AND ano = ? THEN quantidade_vitimas ELSE 0 END) FROM registro_crimes.uf_tem_crimes_morte";
    private static final String SQL_SUM_CRIMES_BY_REGIAO = "SELECT SUM (CASE  WHEN ano = ? AND nome_reg = ? THEN quantidade_vitimas ELSE 0 END) FROM registro_crimes.uf_tem_crimes_morte crimes INNER JOIN registro_crimes.estado est ON crimes.nome_est = est.nome";
    private static final String SQL_SUM_ALLCRIMES_BY_ANO = "SELECT SUM (CASE  WHEN ano = ? THEN quantidade_vitimas ELSE 0 END) FROM registro_crimes.uf_tem_crimes_morte";
    private static final String SQL_SUM_ALLVITIMAS_BY_REGIAO = "SELECT ano, sexo, SUM (CASE  WHEN nome_reg = ? THEN quantidade_vitimas ELSE 0 END) AS quantidade_vitimas FROM registro_crimes.uf_tem_crimes_morte crimes INNER JOIN registro_crimes.estado est ON crimes.nome_est = est.nome GROUP BY sexo, ano ORDER BY ano, sexo ASC";
    private static final String SQL_SUM_ALLVITIMAS_BY_ESTADO = "SELECT ano, sexo, SUM (CASE  WHEN nome_est = ? THEN quantidade_vitimas ELSE 0 END) AS quantidade_vitimas FROM registro_crimes.uf_tem_crimes_morte GROUP BY sexo, ano ORDER BY ano, sexo ASC";
    private static final String SQL_SUM_ALLVITIMAS_BY_BRASIL = "SELECT ano, sexo, SUM (quantidade_vitimas) AS quantidade_vitimas FROM registro_crimes.uf_tem_crimes_morte GROUP BY sexo, ano ORDER BY ano, sexo ASC";

    //--------------------Mensagens de erros/exceptions--------------------
    private static final String CRIMEUF_COM_MORTE_BAD_REQUEST = "Falha ao inserir crime com vitima (%s, %s, %s, %d, %d), dados ja existente na tabela de crimes de UF com morte!";
    private static final String CRIMEUF_COM_MORTE_NOT_FOUND = "Crime de UF com vitima (%s, %s, %s, %d, %d) nao existe na tabela!";
    private static final String NOME_EST_NOT_FOUND = "Falha ao atualizar crime com vitima, estado %s nao existe na tabela de estados!";

    @Override
    public int create(CrimesUfMorte crimeUfMorte) {
        try {
            return jdbc.update(SQL_CREATE_CRIMEUF_COM_MORTE, new Object[] { crimeUfMorte.getNome_est(), crimeUfMorte.getTipo(), crimeUfMorte.getMes(), crimeUfMorte.getAno(), crimeUfMorte.getSexo(), crimeUfMorte.getQuantidade_vitimas() });
        } catch (DuplicateKeyException e) {
            String erro = String.format(CRIMEUF_COM_MORTE_BAD_REQUEST, crimeUfMorte.getNome_est(), crimeUfMorte.getTipo(), crimeUfMorte.getSexo(), crimeUfMorte.getMes(), crimeUfMorte.getAno());
            log.error("[CrimesUfMorteDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public CrimesUfMorte get(Object key) {
        CrimesUfMorte crimeUfMorte = (CrimesUfMorte) key;
        try {
            return jdbc.queryForObject(SQL_GET_CRIMEUF_COM_MORTE, BeanPropertyRowMapper.newInstance(CrimesUfMorte.class), crimeUfMorte.getNome_est(), crimeUfMorte.getTipo(), crimeUfMorte.getMes(), crimeUfMorte.getAno(), crimeUfMorte.getSexo());
        } catch (IncorrectResultSizeDataAccessException  e) {
            String erro = String.format(CRIMEUF_COM_MORTE_NOT_FOUND, crimeUfMorte.getNome_est(), crimeUfMorte.getTipo(), crimeUfMorte.getSexo(), crimeUfMorte.getMes(), crimeUfMorte.getAno());
            log.error("[CrimesUfMorteDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,erro);
        }
    }

    @Override
    public List<CrimesUfMorte> getAll() {
        return jdbc.query(SQL_GET_ALL_CRIMEUF_COM_MORTE,  BeanPropertyRowMapper.newInstance(CrimesUfMorte.class));
    }

    @Override
    public int update(CrimesUfMorte crimeUfMorte) {
        try {
            return jdbc.update(SQL_UPDATE_CRIMEUF_COM_MORTE, new Object[] { crimeUfMorte.getQuantidade_vitimas(), crimeUfMorte.getNome_est(), crimeUfMorte.getTipo(), crimeUfMorte.getMes(), crimeUfMorte.getAno(), crimeUfMorte.getSexo() });
        } catch (DataIntegrityViolationException e) {
            String erro = String.format(NOME_EST_NOT_FOUND, crimeUfMorte.getNome_est());
            log.error("[CrimesUfMorteDAO] " +erro);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, erro);
        }
    }

    @Override
    public int delete(Object key) {
        CrimesUfMorte crimeUfMorte = (CrimesUfMorte) key;
        log.info("[CrimesUfMorteDAO] Deletando crime com morte '{}' ocorrido em '{}' no mes '{}' de '{}' para vitimas do sexo '{}' !", crimeUfMorte.getTipo(), crimeUfMorte.getNome_est(), crimeUfMorte.getMes(), crimeUfMorte.getAno(), crimeUfMorte.getSexo());
        return jdbc.update(SQL_DELETE_CRIMEUF_COM_MORTE, crimeUfMorte.getNome_est(), crimeUfMorte.getTipo(), crimeUfMorte.getMes(), crimeUfMorte.getAno(), crimeUfMorte.getSexo());
    }

    //--MetodosEspecificos-----
    public boolean exists(String nome_est, String tipo, int mes, int ano, String sexo){
        return jdbc.queryForObject(SQL_EXISTS_DADO, Boolean.class, nome_est, tipo, mes, ano, sexo);
    }

    public int sumByEstadoAndAno(String nome_est, int ano){
        return jdbc.queryForObject(SQL_SUM_CRIMES_BY_ESTADO, Integer.class, nome_est, ano);
    }

    public int sumByRegiaoAndAno(String nome_reg, int ano){
        return jdbc.queryForObject(SQL_SUM_CRIMES_BY_REGIAO, Integer.class, ano, nome_reg);
    }

    public int sumByAno(int ano){
        return jdbc.queryForObject(SQL_SUM_ALLCRIMES_BY_ANO, Integer.class, ano);
    }

    public List<CrimesUfMorte> listByRegiaoAndAno(String nome_reg){
        return jdbc.query(SQL_SUM_ALLVITIMAS_BY_REGIAO, BeanPropertyRowMapper.newInstance(CrimesUfMorte.class), nome_reg);
    }

    public List<CrimesUfMorte> listByEstadoAndAno(String nome_est){
        return jdbc.query(SQL_SUM_ALLVITIMAS_BY_ESTADO, BeanPropertyRowMapper.newInstance(CrimesUfMorte.class), nome_est);
    }

    public List<CrimesUfMorte> listByBrasilAndAno(){
        return jdbc.query(SQL_SUM_ALLVITIMAS_BY_BRASIL, BeanPropertyRowMapper.newInstance(CrimesUfMorte.class));
    }
    
}
