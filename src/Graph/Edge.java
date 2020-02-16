
package Graph;

/**
 * @author André Reis 170221035 e Bruno Alves 170221041
 * @param <E> tipo da variavel guardada nas arestas
 * @param <V> tipo da variavel guardada nos vertices da aresta
 */
public interface Edge<E, V> {
    /**
     * @return elemento da aresta
     */
    public E element();
    
    /**
     * @return um array com os dois vertices da aresta
     */
    public Vertex<V>[] vertices();
}
