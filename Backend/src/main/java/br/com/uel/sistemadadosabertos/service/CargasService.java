package br.com.uel.sistemadadosabertos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uel.sistemadadosabertos.DAO.CargaDAO;
import br.com.uel.sistemadadosabertos.models.Carga;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CargasService {
    
    @Autowired
    CargaDAO cargasDao;

    public int insereCarga(String filename, int tamanho){
        Carga carga = Carga.builder()
            .nome(filename)
            .data(LocalDateTime.now())
            .numero_tuplas(tamanho)
            .build();

        log.info("[CargasService] Criando carga!");
        return cargasDao.create(carga);
    }

    public List<Carga> getCargas(){
        log.info("[CargasService] Retornando todas cargas registradas!");
        return cargasDao.getAll();
    }
}
