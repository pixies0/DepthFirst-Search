import java.util.Objects;

public class Aresta {
    int u, v;

    public Aresta(int u, int v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto)
            return true;
        if (!(objeto instanceof Aresta))
            return false;

        Aresta other = (Aresta) objeto;

        return (u == other.u && v == other.v) || (u == other.v && v == other.u);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(u, v), Math.max(v, u));
    }

    @Override
    public String toString() {
        return "(" + (u+1) + "," + (v+1) + ")";
    }
}
