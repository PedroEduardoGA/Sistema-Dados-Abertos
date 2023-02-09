package br.com.uel.sistemadadosabertos.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrimesUfMorte {
    
    String nome_est;
    String tipo;
    int mes;
    int ano;
    String sexo;
    int quantidade_vitimas;
    
    @Override
    public String toString(){
        return this.quantidade_vitimas +" vitimas do sexo: " +this.sexo +" foram vitimas de " +this.tipo +" ocorrido no " +this.nome_est +" em " +this.mes +" de " +this.ano;
    }
}
