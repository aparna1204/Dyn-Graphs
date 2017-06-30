
/**
 *
 * @author Aparna Shankar
 */

public class Union {

    /**
     * takes the union of two graphs with the same vertex set
     * 
     * @param g1 the first graph
     * @param g2 the second graph
     * @return a new graph with all the edges in g1 and g2
     */
    public static Graph union(Graph g1, Graph g2){
        int num = g1.n;
        int[][] adjM = new int[num][num];
        for (int i=0;i<num;i++){
            for(int j=0;j<num;j++){
                if(g1.adjMatrix[i][j]==1||g2.adjMatrix[i][j]==1){
                    adjM[i][j]=1;
                }
            }
        }
        return new Graph(num, adjM);
    }
    
    /**
     * takes the union of an array of graphs all with the same vertex set
     * 
     * @param arr the array of graphs
     * @return a new graph with all the edges in all the graphs
     */
    public static Graph union(Graph[] arr){
        int[][] g = new int[arr[0].adjMatrix.length][arr[0].adjMatrix.length];
        Graph graph = new Graph(g.length, g);
        for(int i=0;i<arr.length;i++){
            graph = union(graph,arr[i]);
        }
        return graph;
    }
    
}
