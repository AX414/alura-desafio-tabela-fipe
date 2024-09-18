package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)


public record Veiculo(
        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") Integer ano,
        @JsonAlias("Combustivel") String tipoCombustivel
) {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Override
    public String toString() {
        return ANSI_GREEN + "| Marca: " + ANSI_RESET + marca
                + ANSI_GREEN + " | Modelo: " + ANSI_RESET + modelo
                + ANSI_GREEN + " | Ano: " + ANSI_RESET + ano
                + ANSI_GREEN + " | Tipo de Combustível: " + ANSI_RESET + tipoCombustivel
                + ANSI_GREEN + " | Valor: " + ANSI_RESET + valor
                + ANSI_GREEN + " |" + ANSI_RESET;
    }
}