import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<int[][]> leituraGrafoArquivo(String nomeArquivo) {

        List<int[][]> grafos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            List<String> linhaMatrizCorrente = new ArrayList<>();
            while ((linha = br.readLine()) != null) {

                linha = linha.trim();
                if (linha.isEmpty()) {
                    if (!linhaMatrizCorrente.isEmpty()) {
                        grafos.add(converteMatriz(linhaMatrizCorrente));
                        linhaMatrizCorrente.clear();
                    }
                } else {
                    linhaMatrizCorrente.add(linha);
                }
            }

            if (!linhaMatrizCorrente.isEmpty()) {
                grafos.add(converteMatriz(linhaMatrizCorrente));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return grafos;

    }

    private static int[][] converteMatriz(List<String> linhasMatriz) {

        int tamanho = linhasMatriz.size();

        int[][] matriz = new int[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {

            String linha = linhasMatriz.get(i).trim();
            String linhaLimpa = linha.replaceAll("[^01]", "");

            if (linhaLimpa.length() != tamanho) {
                System.err.println("Aviso: Linha malformada detectada. Esperava " + tamanho + " dígitos, obteve "
                        + linhaLimpa.length() + " na linha: '" + linha
                        + "'. Certifique-se de que cada linha da matriz contenha apenas '0's e '1's.");
            }

            for (int j = 0; j < tamanho; j++) {
                if (j < linhaLimpa.length()) {
                    matriz[i][j] = Character.getNumericValue(linhaLimpa.charAt(j));
                } else {
                    matriz[i][j] = 0;
                }
            }
        }
        return matriz;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<int[][]> grafosCarregados = leituraGrafoArquivo("grafoIndividual.txt");

        if (grafosCarregados.isEmpty()) {
            System.out.println("Nenhum grafo carregado do arquivo 'grafoIndividual.txt'. Verifique o arquivo.");
            scanner.close();
            return;
        }

        System.out.println(
                "Foram carregadas " + grafosCarregados.size() + " matriz(es) do arquivo 'grafoIndividual.txt'.");
        System.out.print("Qual matriz você deseja manipular (1-" + grafosCarregados.size() + "): ");

        int indexGrafo = scanner.nextInt();

        if (indexGrafo < 1 || indexGrafo > grafosCarregados.size()) {
            System.out.println("Índice de grafo inválido.");
            scanner.close();
            return;
        }

        Grafo grafo = new Grafo(grafosCarregados.get(indexGrafo - 1)); // -1 para ajuste de índice 0-base
        grafo.imprimiGrafo();

        while (true) {

            System.out.println("\nDigite a Opção Desejada:");
            System.out.println("1 - Apresentar Grafo (representação gráfica)");
            System.out.println("2 - Apresentar Árvore de Busca em Profundidade");
            System.out.println("3 - Apresentar Tabela Lowpt(v) e g(v)");
            System.out.println("4 - Listar Articulações com seus Respectivos Demarcadores");
            System.out.println("5 - Apresentar Componentes Biconexas e onde estão enraizadas (Tw)");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            int option = scanner.nextInt();

            switch (option) {

                case 1:
                    grafo.imprimiGrafo();
                    break;
                case 2:
                    System.out.print("Qual será o vértice raiz da busca (1-" + grafo.getNumVertices() + ")? ");
                    int verticeRaiz = scanner.nextInt();

                    if (verticeRaiz < 1 || verticeRaiz > grafo.getNumVertices()) {
                        System.out.println("Vértice raiz inválido.");
                        break;
                    }

                    grafo.findArticulacaoComponente(verticeRaiz - 1);
                    grafo.imprimiFloresta();
                    break;
                case 3:
                    System.out.print("Por favor, execute a opção 2 primeiro para gerar os valores de g(v) e lowpt(v).");
                    grafo.imprimitLowPt();
                    break;
                case 4:
                    System.out.print("Por favor, execute a opção 2 primeiro para identificar as articulações.");
                    grafo.imprimiArticulacoes();
                    break;
                case 5:
                    System.out
                            .print("Por favor, execute a opção 2 primeiro para identificar as componentes biconexas.");
                    grafo.imprimiComponente();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }

    }

}