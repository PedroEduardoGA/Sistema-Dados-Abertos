package br.com.uel.sistemadadosabertos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import br.com.uel.sistemadadosabertos.models.Estado;
import br.com.uel.sistemadadosabertos.models.PopulacaoTotalUf;
import br.com.uel.sistemadadosabertos.models.Regiao;
import br.com.uel.sistemadadosabertos.service.EstadoService;

@RestController
@RequestMapping("/sda-api/dados-estados")
public class DadosEstadosController {
    
    @Autowired
    EstadoService estadoService;

    @PostMapping("/insert-pop/file")
    public int inserePopulacaoTotal(@RequestParam String name, @RequestBody List<PopulacaoTotalUf> popTotal){
        return estadoService.inserePopulacaoTotal(name, popTotal);
    }

    @PostMapping("/insert-estado")
    public int insereEstado(@RequestBody Estado estado){
        return estadoService.insereEstado(estado);
    }

    @GetMapping("/read-estado")
    public Estado getEstado(@RequestParam String nome){
        return estadoService.getEstado(nome);
    }

    @GetMapping("/read-estados")
    public List<Estado> getEstados(){
        return estadoService.getEstados();
    }

    @GetMapping("/read-regioes")
    public List<Regiao> getRegioes(){
        return estadoService.getRegioes();
    }

    @PutMapping("/update-estado")
    public int atualizaEstado(@RequestBody Estado estado){
        return estadoService.atualizaEstado(estado);
    }

    @DeleteMapping("/delete-estado")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public int removeEstado(@RequestParam String nome){
        return estadoService.removeEstado(nome);
    }
}
