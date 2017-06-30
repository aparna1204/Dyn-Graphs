
/**
 *
 * @author Aparna Shankar
 */
import java.util.*;
import java.io.*;

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
                        count++;
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
            } 
            else break;
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
     * @throws java.io.IOException file related exceptions i didn't want to handle
     */
    public static void main(String[] args) throws IOException{
        int[][] am = new int[20][20];
        BufferedReader reader = new BufferedReader(new FileReader("edgeset2.txt"));
        String s = reader.readLine();
        while(s!=null){
            int[] endpts = new int[2];
            int count = 0;
            StringTokenizer st = new StringTokenizer(s);
            while(st.hasMoreTokens()){
                String str = st.nextToken();
                int i = Integer.valueOf(str);
                endpts[count++] = i;
            }
            am[endpts[0]][endpts[1]] = 1;
            am[endpts[1]][endpts[0]] = 1;
            s = reader.readLine();
        }
        Graph g = new Graph(20, am);
        System.out.println(g.isBipartite(0));
        g.sparseTree = new Bipartite().sparsify(g);
        System.out.println(g.isBipartiteDyn());
        g.addEdge(1,3);
        System.out.println(g.isBipartiteDyn());
        g.removeEdge(8,19);
        System.out.println(g.isBipartiteDyn());
        g.removeEdge(1,17);
    }

}
