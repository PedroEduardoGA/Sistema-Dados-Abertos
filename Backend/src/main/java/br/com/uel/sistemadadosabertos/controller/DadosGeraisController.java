package br.com.uel.sistemadadosabertos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uel.sistemadadosabertos.models.Ano;
import br.com.uel.sistemadadosabertos.models.Consulta;
import br.com.uel.sistemadadosabertos.service.DadosService;

@RestController
@RequestMapping("/sda-api/dados-gerais")
public class DadosGeraisController {
    
    @Autowired
    DadosService dadosService;
    
    @GetMapping("/read-anos")
    public List<Ano> getAnos(){
        return dadosService.getAnos();
    }

    @PostMapping("/consult-dados")
    public List<Object> consultaDados(@RequestBody Consulta consulta, @RequestParam int opcao){
        return dadosService.consultaDadosCrimes(consulta, opcao);
    }
}
