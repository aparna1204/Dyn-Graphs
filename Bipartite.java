
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Aparna Shankar
 */
public class Bipartite implements Sparsify{
    
    /**
     * creates a sparsification tree for the given graph
     *
     * @param g the graph to sparsify
     * @return the sparsification tree of the graph
     */
    public SparseTreeNode sparsify(Graph g) {
        int[] vertices = new int[g.n];
        for (int i = 0; i < g.n; i++) {
            vertices[i] = i;
        }
        VertexNode vn = new VertexNode(vertices); //this creates the entire tree
        SparseTreeNode stn = new SparseTreeNode(vn, vn, g);
        stn.fillNodeArray();
        SparseTreeNode.parentMatrix = new Interval[SparseTreeNode.count][SparseTreeNode.count];
        SparseTreeNode.numberNodes(stn);
        stn.fillNodeArray();
        stn.fillParentMatrix();
        return stn;
    }
    
    /**
     * computes the sparse certificate of a graph for the property of
     * bipartiteness.
     *
     * @param g the graph to compute the certificate of
     * @return a spanning forest with or without an edge that completes and odd
     * cycle
     */
    
    public Graph sparseCert(Graph g) {
        Queue<Integer> queue = new LinkedList<>();
        int[] colours = new int[g.n]; //fill it with all the colours 
        int[][] am = new int[g.n][g.n];
        int count = 1;
        queue.add(0);
        colours[0] = 1;
        while (true) { // actually while there are connected components not covered, will check later
            while (!queue.isEmpty()) {
                int v = queue.remove();
                for (int i = 0; i < g.n; i++) {
                    if (g.adjMatrix[v][i] == 1) { // edge bw v and i
                        if (colours[i] == 0) { //not yet visited
                            am[i][v] = 1;
                            am[v][i] = 1; // adding it to the tree
                            colours[i] = 3 - colours[v]; // colouring as 1 or 2 depending on v
                            count++;
                            queue.add(i);
                        } else if (colours[i] == colours[v]) {//odd cycle
                            am[i][v] = 1;
                            am[v][i] = 1;//adding the incriminating edge bc this is a sparse cert not a spanning forest
                        }
                    }
                }
            }
            if (count < g.n) { // some vertices not in the connected component
                for (int i = 1; i < g.n; i++) {
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
        return new Graph(g.n, am);
    }
    
    /**
     * makes another tree of sparse certificates of each node. 
     * this is done by taking the union of the children, then further sparsifying
     * 
     * @param stn
     * @return the tree of sparse certificates
     */
    public SparseTreeNode sparseCertTree(SparseTreeNode stn){
        if(stn.children==null || stn.children.isEmpty()){
            Graph g = sparseCert(stn.graph);
            return new SparseTreeNode(g, null);
        }
        else{
            ArrayList listOfKids = new ArrayList();
            Graph[] arr = new Graph[stn.children.size()];
            for(int i=0;i<arr.length;i++){
                SparseTreeNode kid = (SparseTreeNode)stn.children.get(i);
                SparseTreeNode kid2 = sparseCertTree(kid);
                listOfKids.add(kid2);
                arr[i] = kid2.graph;
            }
            Graph g = Union.union(arr);
            g = sparseCert(g);
            return new SparseTreeNode(g, listOfKids);
        }
    }
    
}
