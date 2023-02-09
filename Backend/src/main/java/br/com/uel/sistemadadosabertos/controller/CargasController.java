package br.com.uel.sistemadadosabertos.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uel.sistemadadosabertos.models.Carga;
import br.com.uel.sistemadadosabertos.service.CargasService;

@CrossOrigin
@RestController
@RequestMapping("/sda-api/cargas")
public class CargasController {
    
    @Autowired
    CargasService cargasService;
    
    @GetMapping("/read-cargas")
    public List<Carga> getCargas(){
        List<Carga> cargas = cargasService.getCargas();

        for (Carga carga : cargas) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
            carga.setDataStr(carga.getData().format(formatter));
        }

        return cargas;
    }
}
