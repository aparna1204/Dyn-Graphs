/**
 *
 * @author Aparna Shankar
 */
import java.util.*;

public class Graph {
    int n; //number of vertices
    int[][] adjMatrix;
    SparseTreeNode sparseTree; //the sparsification tree. we won't compute it in constructor because the edge 
                               //nodes are also graphs and we don't want to compute sparsification trees for those
    
    public Graph(int num, int[][] am){
        n = num;
        adjMatrix = am;
    }
    
    public SparseTreeNode sparsify(){
        int[] vertices = new int[n];
        for(int i=0;i<n;i++){
            vertices[i]=i;
        }
        VertexNode vn = new VertexNode(vertices); //this creates the entire tree
        SparseTreeNode stn = new SparseTreeNode(vn, vn, this);
        return stn;
    }
    
    public void addEdge(int i, int j){ // the endpoints
        adjMatrix[i][j]=1;
        sparseTree.addEdge(i, j);
    }
    
    public void removeEdge(int i, int j){
        adjMatrix[i][j]=0;
        sparseTree.removeEdge(i,j);
    }
    
    public Graph bipartiteSparseCert(){ // a spanning forest with or without an edge that completes and odd cycle
        Queue<Integer> queue = new LinkedList<>();
        int[] colours = new int[n]; //fill it with all the colours 
        int[][] am = new int[n][n];
        int count = 1;
        queue.add(0);
        colours[0] = 1;
        while(true){ // actually while there are connected components not covered, will check later
            while(!queue.isEmpty()){
                System.out.println("Queue rn: "+queue.toString());
                int v = queue.remove();
                for(int i=0;i<n;i++){
                    if (adjMatrix[v][i]==1){ // edge bw v and i
                        if(colours[i]==0){ //not yet visited
                            am[i][v]=1;
                            am[v][i]=1; // adding it to the tree
                            colours[i] = 3-colours[v]; // colouring as 1 or 2 depending on v
                            System.out.println("Colour of "+i+" is "+colours[i]);
                            count++;
                            System.out.println("Count is "+count);
                            queue.add(i);
                        }
                        else if(colours[i]==colours[v]){//odd cycle
                            am[i][v]=1;
                            am[v][i]=1;//adding the incriminating edge bc this is a sparse cert not a spanning forest
                        }
                    }
                }
            }
            if(count<n){ // some vertices not in the connected component
                for(int i=1;i<n;i++){
                    if(colours[i]==0){
                        colours[i]=1;
                        queue.add(i);
                        count++;
                        break;
                    }
                }
            }
            else break;
        }
        return new Graph(n, am);
    }
    
    public boolean isBipartite(int src){ //src is the bfs source
        Queue<Integer> queue = new LinkedList<>();
        int[] colours = new int[n];
        int count = 1;
        colours[src] = 1;
        queue.add(src);
        while(true){
            while(!queue.isEmpty()){
                int v = queue.remove();
                for(int u = 0;u<n;u++){
                    if(adjMatrix[u][v]==1 && colours[u]==0){
                        colours[u] = 3-colours[v];
                        queue.add(u);
                    }
                    else if(adjMatrix[u][v]==1 && colours[u]==colours[v]){//odd cycle
                        return false; 
                    }
                }
            }
            if(count<n){
                for(int i=1;i<n;i++){
                    if(colours[i]==0){
                        colours[i]=1;
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
    
    public boolean isBipartiteDyn(){
        return sparseTree.graph.isBipartite(0);
    }
    
    public void printSparseTree(){
        SparseTreeNode.printTree(sparseTree);
    }
    
    public static void main(String[] args){
        int[][] am = {{0,1,0,0,0,1,0},{1,0,0,0,0,0,0},{0,0,0,1,1,0,0},{0,0,1,0,1,1,0},{0,0,1,1,0,0,1},{1,0,0,1,0,0,0},{0,0,0,0,1,0,0}};
        Graph graph = new Graph(7,am);
        graph.sparseTree = graph.sparsify();
        graph.printSparseTree();
    }
    
}
