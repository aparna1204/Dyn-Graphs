
/**
 *
 * @author Aparna Shankar
 */
import java.util.*;

/**
 *
 * @author Aparna Shankar
 */
public class Graph {

    /**
     * the number of vertices
     */
    int n;
    /**
     * the adjacency matrix
     */
    int[][] adjMatrix;
    /**
     * the sparsification tree. not computed in constructor
     */
    SparseTreeNode sparseTree;

    /**
     * constructor
     *
     * @param num the number of vertices
     * @param am the adjacency matrix
     */
    public Graph(int num, int[][] am) {
        n = num;
        adjMatrix = am;
    }

    /**
     * computes the sparsification tree.
     *
     * @return the sparsification tree
     */
    

    /**
     * adds an edge to the graph with endpoints i,j.
     *
     * @param i endpoint of the edge to be added
     * @param j endpoint of the edge to be added
     */
    public void addEdge(int i, int j) { // the endpoints
        adjMatrix[i][j] = 1;
        sparseTree.addEdge(i, j);
    }

    /**
     * removes an edge from the graph with endpoints i,j
     *
     * @param i endpoint of the edge to be removed
     * @param j endpoint of the edge to be removed
     */
    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = 0;
        sparseTree.removeEdge(i, j);
    }

    /**
     * checks whether the graph is bipartite
     *
     * @param src the bfs source
     * @return true if the graph is bipartite, false otherwise
     */
    public boolean isBipartite(int src) {
        Queue<Integer> queue = new LinkedList<>();
        int[] colours = new int[n];
        int count = 1;
        colours[src] = 1;
        queue.add(src);
        while (true) {
            while (!queue.isEmpty()) {
                int v = queue.remove();
                for (int u = 0; u < n; u++) {
                    if (adjMatrix[u][v] == 1 && colours[u] == 0) {
                        colours[u] = 3 - colours[v];
                        queue.add(u);
                    } else if (adjMatrix[u][v] == 1 && colours[u] == colours[v]) {//odd cycle
                        return false;
                    }
                }
            }
            if (count < n) {
                for (int i = 1; i < n; i++) {
                    if (colours[i] == 0) {
                        colours[i] = 1;
                        queue.add(i);
                        count++;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        return true;
    }

    /**
     * checks whether the given graph is bipartite, dynamically.
     *
     * @return true if the graph is bipartite, false otherwise
     */
    public boolean isBipartiteDyn() {
        return sparseTree.graph.isBipartite(0);
    }

    /**
     * prints the sparsification tree
     */
    public void printSparseTree() {
        SparseTreeNode.printTree(sparseTree);
    }

    /**
     * main method for test cases
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] am = {{0, 1, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 1, 0, 0}, {0, 0, 1, 0, 1, 1, 0}, {0, 0, 1, 1, 0, 0, 1}, {1, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0}};
        Graph graph = new Graph(7, am);
        graph.sparseTree = new Bipartite().sparsify(graph);
        graph.printSparseTree();
    }

}
