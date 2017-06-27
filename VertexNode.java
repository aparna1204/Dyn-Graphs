
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aparna Shankar
 */
public class VertexNode {
    int[] vertices;
    VertexNode[] children;
    
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
    
    public boolean equals(VertexNode other){
        return (this.vertices==other.vertices);
    }
    
    @Override
    public String toString(){
        return Arrays.toString(vertices);
    }
    
}
