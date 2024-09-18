package br.com.alura.tabelafipe.main;

import br.com.alura.tabelafipe.model.Dados;
import br.com.alura.tabelafipe.model.Modelos;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ConsumoAPI;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner ler = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void consultarAPI() {
        int op = 0;
        do {
            System.out.println("\n<Menu>\n1 - Carro\n2 - Moto\n3 - Caminhão\n0 - Encerrar programa");
            System.out.println("\n\nSelecione o veículo para efetuar a consulta: ");
            op = ler.nextInt();
            ler.nextLine();

            switch (op) {
                case 1:
                    consultarVeiculo(op);
                    break;
                case 2:
                    consultarVeiculo(op);
                    break;
                case 3:
                    consultarVeiculo(op);
                    break;
                case 0:
                    System.out.println("Programa encerrado.");
                    break;
                default:
                    System.out.println("Opção Inválida.");
                    break;
            }
        } while (op != 0);
    }

    public void consultarVeiculo(int tipoVeiculo) {
        String URL_COMPLETA = switch (tipoVeiculo) {
            case 1 -> URL_BASE + "carros/marcas";
            case 2 -> URL_BASE + "motos/marcas";
            case 3 -> URL_BASE + "caminhoes/marcas";
            default -> null;
        };

        var json = consumoAPI.obterDados(URL_COMPLETA);

        System.out.println("\nResultados encontrados deste tipo de veículo:");
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nInforme o código da marca para consultar: ");
        var codigo = ler.nextLine();

        //Construindo a URL para apresentar os modelos
        URL_COMPLETA = URL_COMPLETA + "/" + codigo + "/modelos";
        json = consumoAPI.obterDados(URL_COMPLETA);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos encontrados:");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        //Filtro de modelos
        System.out.println("\nInsira o nome do modelo para efetuar a consulta: ");
        var trechoNome = ler.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos()
                .stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoNome.toLowerCase()))
                .toList();

        System.out.println("\nModelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo que deseja consultar os valores para avaliação: ");
        codigo = ler.nextLine();

        URL_COMPLETA += "/" + codigo + "/anos";
        json = consumoAPI.obterDados(URL_COMPLETA);

        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = URL_COMPLETA + "/" + anos.get(i).codigo();
            json = consumoAPI.obterDados(enderecoAnos);
            Veiculo v = conversor.obterDados(json, Veiculo.class);
            veiculos.add(v);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano:");
        veiculos.forEach(System.out::println);
    }
}
