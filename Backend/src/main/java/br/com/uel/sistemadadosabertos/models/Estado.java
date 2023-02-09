package br.com.uel.sistemadadosabertos.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estado {
    
    String nome_reg;
    String nome;
    String sigla;

    @Override
    public String toString(){
        return this.nome +"-" +this.sigla +" pertence a " +this.nome_reg;
    }
}
