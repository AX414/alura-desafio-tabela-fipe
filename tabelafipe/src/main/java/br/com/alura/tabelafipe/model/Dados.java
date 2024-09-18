package br.com.alura.tabelafipe.model;

public record Dados(String codigo, String nome) {


    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Override
    public String toString() {
        return ANSI_GREEN + "| Código: " + ANSI_RESET + codigo
                + ANSI_GREEN + " | Nome: " + ANSI_RESET + nome
                + ANSI_GREEN + " |" + ANSI_RESET;
    }
}
