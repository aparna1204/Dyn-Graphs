import java.util.Arrays;

/**
 *
 * @author Aparna Shankar
 */
public class VertexNode {
    /**
     * an array with the set of vertices in the node
     */
    int[] vertices;
    /**
     * an array of size 2 with the children of this node
     */
    VertexNode[] children;
    
    /**
     * constructor
     * 
     * @param v the array of vertices
     */
    public VertexNode(int[] v){
        vertices = v;
        int n = vertices.length;
        if(n<=1){
            children = new VertexNode[0];
        }
        else{
            int[] child1 = new int[n/2];
            int[] child2 = new int[n-n/2];
            System.arraycopy(vertices, 0, child1, 0, n/2);
            System.arraycopy(vertices, n/2, child2, 0, n-n/2);
            children = new VertexNode[2];
            children[0] = new VertexNode(child1);
            children[1] = new VertexNode(child2);
        } 
   }
    
    
    /**
     * checks if two vertex sets are equal
     * 
     * @param other the other node to compare it with
     * @return true if the two vertex sets are identical, false otherwise
     */
    public boolean equals(VertexNode other){
        return (this.vertices==other.vertices);
    }
    
    
    /**
     * string form of the vertex node, overrides the inherited method from Object
     * 
     * @return the string representation of the array of vertices
     */
    @Override
    public String toString(){
        return Arrays.toString(vertices);
    }
    
}
