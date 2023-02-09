package br.com.uel.sistemadadosabertos.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vitima {
    
    String sexo;
    String descricao;
}
