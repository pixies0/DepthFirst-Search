public class BuscaProfundidade {
    int tempoDescoberta;
    int lowPoint;
    int pai;
    boolean visitado;

    public BuscaProfundidade(){
        this.tempoDescoberta = -1; // Indefinido
        this.lowPoint = -1; // Indefinido
        this.pai = -1; // Indefinido
        this.visitado = false;
    }
}
