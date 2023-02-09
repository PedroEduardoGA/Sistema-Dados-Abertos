package br.com.uel.sistemadadosabertos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uel.sistemadadosabertos.models.CrimesUf;
import br.com.uel.sistemadadosabertos.models.CrimesUfMorte;
import br.com.uel.sistemadadosabertos.models.OcorrenciasCrimes;
import br.com.uel.sistemadadosabertos.service.CrimesService;

@CrossOrigin
@RestController
@RequestMapping("/sda-api/dados-crimes")
public class DadosCrimesController {
    
    @Autowired
    CrimesService crimesService;

    @PostMapping("/insert-crimes/file")
    public int insereCrime(@RequestParam String name, @RequestBody List<CrimesUf> crimes){
        return crimesService.insereCrimes(name, crimes);
    }

    @PostMapping("/insert-crimes-vitimas/file")
    public int insereCrimeVitimas(@RequestParam String name, @RequestBody List<CrimesUfMorte> crimesMorte){
        return crimesService.insereCrimesVitimas(name, crimesMorte);
    }

    @GetMapping("/read-crimes")
    public List<CrimesUf> getCrimes(){
        return crimesService.getCrimesUf();
    }

    @GetMapping("/read-tipos")
    public List<OcorrenciasCrimes> getTipos(){
        return crimesService.getTiposCrimes();
    }

}
