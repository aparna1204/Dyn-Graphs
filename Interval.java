/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aparna Shankar
 */
// this is kind of a weird class to store 1 or 2 vertex sets
public class Interval {
    int startpt1;
    int endpt1;
    int startpt2;
    int endpt2;

    public Interval(int startpt, int endpt) {
        this.startpt1 = startpt;
        this.endpt2 = endpt;
    }

    public Interval(int startpt1, int endpt1, int startpt2, int endpt2) {
        this.startpt1 = startpt1;
        this.endpt1 = endpt1;
        this.startpt2 = startpt2;
        this.endpt2 = endpt2;
    }
    
    public Interval(SparseTreeNode stn){
        startpt1 = stn.vn1[0];
        endpt1 = stn.vn1[stn.vn1.length-1];
        startpt2 = stn.vn2[0];
        endpt2 = stn.vn2[stn.vn2.length-1];
    }
    
    public boolean contains(int i){
        return (i>=startpt1 && i<=endpt1);
    }
    
    public boolean contains(int i, int j){// i and j are endpoints of an edge
        if ((i>=startpt1 && i<=endpt1)&&(j>=startpt2 && j<=endpt2)){
            return true;
        }
        else if ((j>=startpt1 && j<=endpt1)&&(i>=startpt2 && i<=endpt2)){
            return true;
        }
        return false;
    }
    
}
