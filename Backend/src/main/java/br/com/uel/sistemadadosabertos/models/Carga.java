package br.com.uel.sistemadadosabertos.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carga {

    String nome;
    LocalDateTime data;
    String dataStr;
    int numero_tuplas;

}
