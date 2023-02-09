package br.com.uel.sistemadadosabertos.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utilities {
    
    //Retorna string com sigla&nome_reg de acordo com o nome do estado fornecido
    public static String getSiglaAndRegiao(String nome_est) {
		for (EstadosEnum estado : EstadosEnum.values()) {
			if (estado.getNome_Est().equals(nome_est))
                return estado.getSiglaAndRegiao();
		}

        log.error("[Utilities] Estado informado '{}', nao existe no enum!",nome_est);
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado inexsistente no enum");
	}

}
