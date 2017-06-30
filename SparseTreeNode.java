import java.util.*;

/**
 *
 * @author Aparna Shankar
 */
public class SparseTreeNode {
    /**
     * the edge set of the node
     */
    Graph graph; 
    /**
     * the list of children of this node
     */
    ArrayList children;
    /**
     * the number of edges
     */
    int noOfEdges;
    /**
     * the number of the node. we number from root, level by level.
     */
    int nodeNumber;
    /**
     * the first vertex set to make the edge set
     */
    int[] vn1;
    /**
     * the second vertex set to make the edge set
     */
    int[] vn2;
    /**
     * an array with all the nodes, with each node stored at the index given by its nodeNumber.
     */
    static SparseTreeNode[] nodes;
    /**
     * the number of nodes. will increment.
     */
    static int count = 0;
    /**
     * a matrix that stores the parent information. 
     * if j is a child of i, parentMatrix[i][j] will contain the two vertex sets
     * (an Interval object) that j is made of
     * 
     */
    static Interval[][] parentMatrix;
    
    /**
     * constructor with a given edge set
     * 
     * @param graph the edge set we want to make a node with
     * @param children the list of children of the given node
     */
    public SparseTreeNode(Graph graph, ArrayList children){
        this.graph = graph;        
        this.children = children;
    }
    
    /**
     * constructor with 2 vertex sets
     * 
     * @param vn1 the first vertex set
     * @param vn2 the second vertex set
     * @param g the main graph we are making subgraphs of (compare with parameter graph in earlier constructor)
     */
    public SparseTreeNode(VertexNode vn1, VertexNode vn2, Graph g){ 
        noOfEdges = 0;
        this.vn1 = vn1.vertices;
        this.vn2 = vn2.vertices;
        //System.out.println("Making a node out of "+vn1.toString()+" and "+vn2.toString());
        graph = new Graph(g.n, new int[g.n][g.n]); //empty, will fill with edges in vn1 X vn2
        if(vn1.equals(vn2)){ // then only 3 children. also must not double count edges
            for(int i=0;i<vn1.vertices.length;i++){
                for(int j=0;j<i;j++){ // to not double count edges
                    if(g.adjMatrix[vn1.vertices[i]][vn1.vertices[j]]==1){
                        graph.adjMatrix[vn1.vertices[i]][vn1.vertices[j]]=1;
                        graph.adjMatrix[vn1.vertices[j]][vn1.vertices[i]]=1;
                        noOfEdges++;
                    }
                }
            }
            //System.out.println("Child of "+vn1.toString()+" is "+Arrays.toString(vn1.children));
            if(vn1.children.length>0){
                children = new ArrayList();
                children.add(new SparseTreeNode(vn1.children[0], vn1.children[0], g));
                children.add(new SparseTreeNode(vn1.children[0], vn1.children[1], g));
                children.add(new SparseTreeNode(vn1.children[1], vn1.children[1], g));
                //System.out.println("Success in making node of "+vn1.toString());
            }
            //else System.out.println("No children of "+vn1.toString());
        }
        else{
            for(int i=0;i<vn1.vertices.length;i++){
                for(int j=0;j<vn2.vertices.length;j++){ 
                    if(g.adjMatrix[vn1.vertices[i]][vn2.vertices[j]]==1){
                        graph.adjMatrix[vn1.vertices[i]][vn2.vertices[j]]=1;
                        graph.adjMatrix[vn2.vertices[j]][vn1.vertices[i]]=1;
                        noOfEdges++;
                    }
                }
            }
            if(vn1.children.length>0 && vn2.children.length>0){
                children = new ArrayList();
                children.add(new SparseTreeNode(vn1.children[0], vn2.children[0], g));
                children.add(new SparseTreeNode(vn1.children[0], vn2.children[1], g));
                children.add(new SparseTreeNode(vn1.children[1], vn2.children[0], g));
                children.add(new SparseTreeNode(vn1.children[1], vn2.children[1], g)); // i could have used 2 loops but this is clearer
            }
        }
        if(children!=null){
            for(int i=0;i<children.size();i++){
                if(((SparseTreeNode)children.get(i)).noOfEdges==1){
                    Object empty = children.remove(i);
                }
            }
        }
    }
    
    /**
     * numbers all the nodes of the tree level by level starting from 1
     * 
     * @param s the SparseTreeNode to number the nodes of
     */
    public static void numberNodes(SparseTreeNode s){
        Queue <SparseTreeNode> queue = new LinkedList<>();
        queue.add(s);
        while(!queue.isEmpty()){
            SparseTreeNode stn = queue.remove();
            stn.nodeNumber = ++count;
            if(stn.children!=null){
                for(int i=0;i<stn.children.size();i++){
                    queue.add((SparseTreeNode) stn.children.get(i));
                }
            }
        }
    }
    
    /**
     * string representation of the node
     * 
     * @return string representation of the node
     */
    @Override
    public String toString(){
        if(noOfEdges==0) return "(" + Integer.toString(nodeNumber)+")Edges: {}";
        String s = "(" + Integer.toString(nodeNumber)+")Edges: {";
        for(int i =0;i<graph.n;i++){
            for(int j=0;j<i;j++){ //for no repeats
                if (graph.adjMatrix[i][j]==1){
                    s = s+"("+Integer.toString(i)+","+Integer.toString(j)+"),";
                }
            }
        }
        s = s.substring(0,s.length()-1);//to remove the comma at the end
        s = s+"}, Child of "+Integer.toString(parent());
        return s;
    }
    
    /**
     * computes height of the tree
     * 
     * @param root the topmost node of the tree
     * @return height of the subtree with root on top
     */
    public static int height(SparseTreeNode root){
        if(root==null) return 0;
        else if (root.children==null) return 1;
        else{
            int[] heights = new int[root.children.size()];
            int max = 0;
            for(int i=0;i<heights.length;i++){
                heights[i] = height((SparseTreeNode) root.children.get(i));
                if(heights[i]>max) max = heights[i];
            }
            return 1+max;
        }
    }
    
    /**
     * prints one level of the tree
     * 
     * @param root the top of the tree
     * @param level the number of the level to be printed
     */
    public static void printLevel(SparseTreeNode root, int level){
        if(root==null) return;
        if(level==1) System.out.print(root.toString()+"   ");
        else if(root.children==null) System.err.print("Error in printing level "+level+" ");
        else if (level >1){
            for(int i=0;i<root.children.size();i++){
                printLevel((SparseTreeNode)root.children.get(i), level-1);
            }
        }
    }
    
    /**
     * prints the entire tree starting from root
     * 
     * @param root the top of the tree
     */
    public static void printTree(SparseTreeNode root){
        int h = height(root);
        for(int i=0;i<h;i++){
            printLevel(root,i);
            System.out.println();
        }
    }
    
    /**
     * populates the parent information matrix with the various intervals.
     */
    public void fillParentMatrix(){
        parentMatrix = new Interval[count+1][count+1];
        Queue<SparseTreeNode> queue = new LinkedList<>();
        queue.add(this);
        while(!queue.isEmpty()){
            SparseTreeNode stn = queue.remove();
            if(stn.children!=null){
                Iterator iter = stn.children.iterator();
                while(iter.hasNext()){
                    SparseTreeNode child = (SparseTreeNode) iter.next();
                    parentMatrix[stn.nodeNumber][child.nodeNumber] = new Interval(child.vn1[0],child.vn1[child.vn1.length-1], child.vn2[0], child.vn2[child.vn2.length-1]);
                }
            }
        }
    }
    
    /**
     * populates the array of nodes so we can access any node by its nodeNumber
     */
    public void fillNodeArray(){
        nodes = new SparseTreeNode[count+1];
        Queue <SparseTreeNode> queue = new LinkedList<>();
        queue.add(this);
        while(!queue.isEmpty()){
            SparseTreeNode stn = queue.remove();
            nodes[stn.nodeNumber] = stn;
            if(stn.children!=null){
                Iterator iter = stn.children.iterator();
                while (iter.hasNext()){
                    queue.add((SparseTreeNode)iter.next());
                }
            }
        }
    }
    
    /**
     * adds an edge with endpoints i,j
     * 
     * @param i endpoint of the edge to be added
     * @param j endpoint of the edge to be added
     */
    public void addEdge(int i, int j){
        if(graph.adjMatrix[i][j]==1){
            System.out.println("Edge already present");
            return;
        }
        else if(i==j){
            System.out.println("No self-loops");
            return;
        }
        else{
            if(children==null || children.isEmpty()){
                int[][] am = new int[graph.n][graph.n];
                am[i][j]=1;
                am[j][i]=1;
                SparseTreeNode stn = new SparseTreeNode(new Graph(am.length, am), null);
                stn.nodeNumber = ++count;
                Interval[][] newPM = new Interval[count+1][count+1];
                for(int k=0;k<count-1;k++){
                    for(int l=0;l<count-1;l++){
                        newPM[k][l]=parentMatrix[k][l];
                    }
                }
                newPM[this.nodeNumber][count] = new Interval(i,i,j,j);
                parentMatrix = newPM;
                SparseTreeNode[] newNodes = new SparseTreeNode[count+1];
                System.arraycopy(nodes,0,newNodes,0,count-1);
                newNodes[count] = stn;
                nodes = newNodes;
                children = new ArrayList();
                children.add(stn);
                stn.repairBranch();
            }
            else{
                Iterator iter = children.iterator();
                while (iter.hasNext()){
                    SparseTreeNode stn = (SparseTreeNode) iter.next();
                    Interval in = new Interval(stn);
                    if (in.contains(i, j)){
                        stn.addEdge(i, j);
                    }
                }
            }
        }
    }
    
    /**
     * removes an edge with endpoints i,j
     * 
     * @param i endpoint of the edge to be removed
     * @param j endpoint of the edge to be removed
     */
    public void removeEdge(int i, int j){
        if(graph.adjMatrix[i][j]==0){
            System.out.println("Edge not present to remove");
            return;
        }    
        else{
                if(children==null){
                    graph.adjMatrix[i][j]=0;
                    repairBranch();
                }
                else{
                    Iterator iter = children.iterator();
                    while (iter.hasNext()){
                        SparseTreeNode child = (SparseTreeNode) iter.next();
                        Interval in = new Interval(child);
                        if (in.contains(i, j)){
                            if(child.noOfEdges==1){
                                children.remove(child);
                                count--;
                                Interval[][] newPM = new Interval[count+1][count+1];
                                int x =0,y =0;
                                for(int k=0;k<count;k++){
                                    x=k;
                                    for(int l=0;l<count;l++){
                                        if(x==child.nodeNumber)x++;
                                        if(y==child.nodeNumber)y++;
                                        newPM[k][l]=parentMatrix[x][y++];
                                    }
                                    y=0;
                                }
                                parentMatrix = newPM;
                                int p=0;
                                SparseTreeNode[] newNodes = new SparseTreeNode[count+1];
                                for(int m=0;m<count;m++){
                                    if(p==child.nodeNumber) p++;
                                    newNodes[m] = nodes[p];
                                    p++;
                                    if(m<p && newNodes[m]!=null) newNodes[m].nodeNumber--;
                                }
                                nodes = newNodes;
                            }
                            else child.removeEdge(i, j);
                        }
                }
            }
        }
    }
    /**
     * finds the parent of the current node using the parentMatrix
     * 
     * @return the nodeNumber of the parent, or -1 if there is no such parent (ie node is the root of the tree)
     */
    public int parent(){
        for(int i=0;i<count;i++){
            if (parentMatrix[i][nodeNumber]!=null) return i;
        }
        return -1;
    }
    
    /**
     * repairs one particular branch of the tree, upon adding or removing an edge 
     */
    public void repairBranch(){ // fixes the parent of this particular node then calls repairbranch() for parent
        int parent = parent();
        if(parent==-1)return;
        else{
            SparseTreeNode parentnode = nodes[parent];
            ArrayList listOfKids = new ArrayList();
            Graph[] arr = new Graph[parentnode.children.size()];
            for(int i=0;i<arr.length;i++){
                SparseTreeNode kid = (SparseTreeNode)parentnode.children.get(i);
                listOfKids.add(kid);
                arr[i] = kid.graph;
            }
            Graph g = Union.union(arr);
            g = new Bipartite().sparseCert(g);
            parentnode = new SparseTreeNode(g, listOfKids);
            parentnode.repairBranch();
        }
            
    }
    
    
}
