import java.util.*;
/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aparna Shankar
 */
public class SparseTreeNode {
    Graph graph; //the sparse graph
    ArrayList children;
    int noOfEdges;
    int nodeNumber;
    static SparseTreeNode[] nodes;
    static int count = 0;
    static Interval[][] parentMatrix;
    int[] vn1;
    int[] vn2;
    
    public SparseTreeNode(Graph graph, ArrayList children){// in this case we are trying to make a node with the graph g
        this.graph = graph;        
        this.children = children;
    }
    
    public SparseTreeNode(VertexNode vn1, VertexNode vn2, Graph g){ // in this case g is the main graph being sparsified
        noOfEdges = 0;
        this.vn1 = vn1.vertices;
        this.vn2 = vn2.vertices;
        System.out.println("Making a node out of "+vn1.toString()+" and "+vn2.toString());
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
        for(int i=0;i<children.size();i++){
            if(((SparseTreeNode)children.get(i)).noOfEdges==1){
                Object empty = children.remove(i);
            }
        }
    }
    
    public static void numberNodes(SparseTreeNode s){
        Queue <SparseTreeNode> queue = new LinkedList<>();
        queue.add(s);
        while(!queue.isEmpty()){
            SparseTreeNode stn = queue.remove();
            stn.nodeNumber = ++count;
            for(int i=0;i<stn.children.size();i++){
                queue.add((SparseTreeNode) stn.children.get(i));
            }
        }
    }
    
    @Override
    public String toString(){
        if(noOfEdges==0) return "(" + Integer.toString(nodeNumber)+")Edges: {}";
        String s = "Edges: {";
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
    
    public static void printTree(SparseTreeNode root){
        int h = height(root);
        for(int i=0;i<h;i++){
            printLevel(root,i);
            System.out.println();
        }
    }
    
    public void fillParentMatrix(){
        parentMatrix = new Interval[count][count];
        Queue<SparseTreeNode> queue = new LinkedList<>();
        queue.add(this);
        while(!queue.isEmpty()){
            SparseTreeNode stn = queue.remove();
            Iterator iter = stn.children.iterator();
            while(iter.hasNext()){
                SparseTreeNode child = (SparseTreeNode) iter.next();
                parentMatrix[stn.nodeNumber][child.nodeNumber] = new Interval(child.vn1[0],child.vn1[child.vn1.length-1], child.vn2[0], child.vn2[child.vn2.length-1]);
            }
        }
    }
    
    public void fillNodeArray(){
        nodes = new SparseTreeNode[count+1];
        Queue <SparseTreeNode> queue = new LinkedList<>();
        queue.add(this);
        while(!queue.isEmpty()){
            SparseTreeNode stn = queue.remove();
            nodes[stn.nodeNumber] = stn;
            Iterator iter = stn.children.iterator();
            while (iter.hasNext()){
                queue.add((SparseTreeNode)iter.next());
            }
        }
    }
    
    public SparseTreeNode sparseCertTree(){
        if(this.children==null || this.children.isEmpty()){
            Graph g = this.graph.bipartiteSparseCert();
            return new SparseTreeNode(g, null);
        }
        else{
            ArrayList listOfKids = new ArrayList();
            Graph[] arr = new Graph[this.children.size()];
            for(int i=0;i<arr.length;i++){
                SparseTreeNode kid = (SparseTreeNode)this.children.get(i);
                SparseTreeNode kid2 = kid.sparseCertTree();
                listOfKids.add(kid2);
                arr[i] = kid2.graph;
            }
            Graph g = Union.union(arr);
            g = g.bipartiteSparseCert();
            return new SparseTreeNode(g, listOfKids);
        }
    }
    
    public void addEdge(int i, int j){
        if(graph.adjMatrix[i][j]==1){
            System.out.println("Edge already present");
            return;
        }
        else{
            if(children==null || children.isEmpty()){
                int[][] am = new int[graph.n][graph.n];
                am[i][j]=1;
                am[j][i]=1;
                SparseTreeNode stn = new SparseTreeNode(new Graph(am.length, am), null);
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
    
    public void removeEdge(int i, int j){
        if(graph.adjMatrix[i][j]==0){
            System.out.println("Edge not present to remove");
            return;
        }    
        else{
                Iterator iter = children.iterator();
                while (iter.hasNext()){
                    SparseTreeNode child = (SparseTreeNode) iter.next();
                    Interval in = new Interval(child);
                    if (in.contains(i, j)){
                        if(child.noOfEdges==1){
                            children.remove(child);
                        }
                        else child.removeEdge(i, j);
                    }
                }
            }
        }
    
    public int parent(){
        for(int i=0;i<count;i++){
            if (parentMatrix[i][nodeNumber]!=null) return i;
        }
        return -1;
    }
    
    public void repairBranch(){ // fixes the parent of this particular node then calls repairbranch() for parent
        int parent = parent();
        if(parent==-1)return;
        else{
            SparseTreeNode parentnode = nodes[parent];
            ArrayList listOfKids = new ArrayList();
            Graph[] arr = new Graph[parentnode.children.size()];
            for(int i=0;i<arr.length;i++){
                SparseTreeNode kid = (SparseTreeNode)this.children.get(i);
                listOfKids.add(kid);
                arr[i] = kid.graph;
            }
            Graph g = Union.union(arr);
            g = g.bipartiteSparseCert();
            parentnode = new SparseTreeNode(g, listOfKids);
            parentnode.repairBranch();
        }
            
    }
    
    
}