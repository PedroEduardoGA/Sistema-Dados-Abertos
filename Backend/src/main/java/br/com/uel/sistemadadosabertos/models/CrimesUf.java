package br.com.uel.sistemadadosabertos.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrimesUf {
    
    String nome_est;
    String tipo;
    int mes;
    int ano;
    int quantidade_ocorrencias;

    @Override
    public String toString(){
        return this.quantidade_ocorrencias +" ocorrencias de " +this.tipo +" ocorridos no " +this.nome_est +" em " +this.mes +" de " +this.ano;
    }
}
