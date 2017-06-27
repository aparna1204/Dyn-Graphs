/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aparna Shankar
 */



public class Union {

    public static Graph union(Graph g1, Graph g2){
        //here union means same vertex set and disjoint edge set
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
    
    public static Graph union(Graph[] arr){
        int[][] g = new int[arr[0].adjMatrix.length][arr[0].adjMatrix.length];
        Graph graph = new Graph(g.length, g);
        for(int i=0;i<arr.length;i++){
            graph = union(graph,arr[i]);
        }
        return graph;
    }
    
}
