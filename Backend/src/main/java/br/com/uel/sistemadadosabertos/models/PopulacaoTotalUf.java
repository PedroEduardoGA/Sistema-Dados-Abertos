package br.com.uel.sistemadadosabertos.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopulacaoTotalUf {
    
    int ano;
    String nome_est;
    int pop_total;
    
    @Override
    public String toString(){
        return this.nome_est +" registrou uma populacao total de " +this.pop_total +" habitantes no ano de " +this.ano;
    }
}
