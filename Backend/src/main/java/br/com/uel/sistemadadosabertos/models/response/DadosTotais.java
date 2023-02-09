package br.com.uel.sistemadadosabertos.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosTotais {
    
    String nome; //nome de estado ou regiao
    int qtdTotal;
    int ano;
    double percentual;

}
