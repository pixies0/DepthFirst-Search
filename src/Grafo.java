import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Grafo {

    private int[][] matrizAdjacencia;
    private int numVertices;
    private BuscaProfundidade[] buscaProfundidade;
    private int tempo;
    private List<Integer> pontosArticulacao;
    private Map<Integer, List<Aresta>> componentesConexas;
    private Stack<Aresta> pilhaArestas;

    public Grafo(int[][] matrizAdjacencia) {

        this.matrizAdjacencia = matrizAdjacencia;
        this.numVertices = matrizAdjacencia.length;
        this.buscaProfundidade = new BuscaProfundidade[numVertices];
        for (int i = 0; i < numVertices; i++) {

            buscaProfundidade[i] = new BuscaProfundidade();

        }

        this.tempo = 0;
        this.pontosArticulacao = new ArrayList<>();
        this.componentesConexas = new HashMap<>();
        this.pilhaArestas = new Stack<>();

    }

    public int getNumVertices() {
        return numVertices;
    }

    public void buscaProfundidadeFunc(int u) {

        buscaProfundidade[u].visitado = true;
        buscaProfundidade[u].tempoDescoberta = tempo++;
        buscaProfundidade[u].lowPoint = buscaProfundidade[u].tempoDescoberta;

        for (int v = 0; v < numVertices; v++) {
            if (matrizAdjacencia[u][v] == 1) {
                if (!buscaProfundidade[v].visitado) {

                    buscaProfundidade[v].pai = u;
                    pilhaArestas.push(new Aresta(u, v));
                    buscaProfundidadeFunc(v);
                    buscaProfundidade[u].lowPoint = Math.min(buscaProfundidade[u].lowPoint,
                            buscaProfundidade[v].lowPoint);

                    if (buscaProfundidade[v].lowPoint >= buscaProfundidade[u].tempoDescoberta
                            && buscaProfundidade[u].pai != -1) {
                        pontosArticulacao.add(u);

                        List<Aresta> currentBCC = new ArrayList<>();

                        while (!pilhaArestas.isEmpty() && !pilhaArestas.peek().equals(new Aresta(u, v))) {
                            currentBCC.add(pilhaArestas.pop());
                        }

                        if (!pilhaArestas.isEmpty()) {
                            currentBCC.add(pilhaArestas.pop());
                        }

                        componentesConexas.computeIfAbsent(u, k -> new ArrayList<>()).addAll(currentBCC);
                    }

                } else if (v != buscaProfundidade[u].pai) {
                    buscaProfundidade[u].lowPoint = Math.min(buscaProfundidade[u].lowPoint,
                            buscaProfundidade[v].tempoDescoberta);
                    if (buscaProfundidade[v].tempoDescoberta < buscaProfundidade[u].tempoDescoberta) {
                        pilhaArestas.push(new Aresta(u, v));

                    }
                }
            }

        }

    }

    public void findArticulacaoComponente(int noSaida) {

        for (int i = 0; i < numVertices; i++) {

            buscaProfundidade[i] = new BuscaProfundidade();

        }

        tempo = 0;
        pontosArticulacao.clear();
        componentesConexas.clear();
        pilhaArestas.clear();
        buscaProfundidadeFunc(noSaida);

        int root = 0;

        for (int i = 0; i < numVertices; i++) {
            if (buscaProfundidade[i].pai == noSaida) {
                root++;
            }

        }

        if (root > 1) {
            pontosArticulacao.add(noSaida);
        }

        if (!pilhaArestas.isEmpty()) {
            List<Aresta> remanescenteComponent = new ArrayList<>(pilhaArestas);
            componentesConexas.computeIfAbsent(-1, k -> new ArrayList<>()).addAll(remanescenteComponent);
        }

    }

    public void imprimiFloresta() {

        System.out.println("Árvore de Busca em Profundidade:");
        List<Aresta> arestasArvore = new ArrayList<>();
        List<Aresta> arestasVolta = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] == 1) {
                    if (buscaProfundidade[j].pai == i
                            && buscaProfundidade[j].tempoDescoberta > buscaProfundidade[i].tempoDescoberta) {
                        arestasArvore.add(new Aresta(i, j));
                    } else if (buscaProfundidade[j].visitado
                            && buscaProfundidade[j].tempoDescoberta < buscaProfundidade[i].tempoDescoberta
                            && buscaProfundidade[i].pai != j) {
                        arestasVolta.add(new Aresta(i, j));
                    }
                }
            }
        }

        System.out.println("Arestas de Árvore: " + arestasArvore);
        System.out.println("Arestas de Retorno: " + arestasVolta);

    }

    public void imprimitLowPt() {

        System.out.println("\nTabela Lowpt(v) e g(v):");
        System.out.printf("%-10s %-10s %-10s%n", "Vértice", "g(v)", "lowpt(v)");

        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-10d %-10d %-10d%n", (i + 1), buscaProfundidade[i].tempoDescoberta,
                    buscaProfundidade[i].lowPoint);

        }

    }

    public void imprimiArticulacoes() {

        System.out.println("\nArticulações e seus Demarcadores:");

        if (pontosArticulacao.isEmpty()) {
            System.out.println("Não há pontos de articulação neste grafo.");
            return;
        }

        for (int pa : new HashSet<>(pontosArticulacao)) { // Usa HashSet para evitar duplicatas
            System.out.print("Vértice " + (pa + 1) + " (Articulação): ");
            List<String> demarcadores = new ArrayList<>();

            for (int v = 0; v < numVertices; v++) {
                if (matrizAdjacencia[pa][v] == 1 && buscaProfundidade[v].pai == pa) { // Para cada filho de ap na árvore
                    if (buscaProfundidade[v].lowPoint >= buscaProfundidade[pa].tempoDescoberta) {
                        demarcadores.add("V" + (v + 1));
                    }
                }
            }

            if (demarcadores.isEmpty()) {
                System.out.println(
                        "Nenhum demarcador claro (pode ser a raiz com múltiplos filhos ou ponto de articulação em grafo simples).");
            } else {
                System.out.println("Demarcadores: " + demarcadores);
            }
        }
    }

    public void imprimiComponente() {

        System.out.println("\nComponentes Biconexas:");
        if (componentesConexas.isEmpty()) {
            System.out.println("Não há componentes biconexas distintas neste grafo (pode ser um grafo biconexo).");
            return;
        }

        int compNum = 1;

        for (Map.Entry<Integer, List<Aresta>> entry : componentesConexas.entrySet()) {
            System.out.print("Componente Biconexa " + compNum++ + " (Enraizada por Tw: "
                    + (entry.getKey() == -1 ? "Raiz DFS ou Componente Final" : "V" + (entry.getKey() + 1)) + "): ");
            System.out.println(entry.getValue());
        }
    }

    public void imprimiGrafo() {

        System.out.println("\nRepresentação Gráfica do Grafo (Matriz de Adjacência):");
        System.out.print(" ");

        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-3d", (i + 1));
        }

        System.out.println();
        System.out.println("---" + "----".repeat(numVertices));

        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-2d|", (i + 1));
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%-3d", matrizAdjacencia[i][j]);
            }

            System.out.println();
        }

        System.out.println("\nConexões:");

        for (int i = 0; i < numVertices; i++) {

            System.out.print("V" + (i + 1) + ": ");
            List<Integer> neighbors = new ArrayList<>();

            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] == 1) {
                    neighbors.add(j + 1);
                }

            }

            System.out.println(neighbors);
        }
    }
}