
/**
 * a class to store one or two vertex sets
 * 
 * @author Aparna Shankar
 */
public class Interval {
    int startpt1;
    int endpt1;
    int startpt2;
    int endpt2;

    /**
     * constructor for only one vertex set
     * 
     * @param startpt the start point of the interval
     * @param endpt the end point of the interval
     */
    public Interval(int startpt, int endpt) {
        this.startpt1 = startpt;
        this.endpt2 = endpt;
    }

    /**
     * constructor for two vertex sets
     *
     * @param startpt1 start point of the first interval
     * @param endpt1 end point of the first interval
     * @param startpt2 start point of the second interval
     * @param endpt2 end point of the second interval
     */
    public Interval(int startpt1, int endpt1, int startpt2, int endpt2) {
        this.startpt1 = startpt1;
        this.endpt1 = endpt1;
        this.startpt2 = startpt2;
        this.endpt2 = endpt2;
    }
    
    /**
     * constructor from a SparseTreeNode
     * 
     * @param stn the SparseTreeNode, will match start/endpts with vertex sets stn is made of
     */
    public Interval(SparseTreeNode stn){
        startpt1 = stn.vn1[0];
        endpt1 = stn.vn1[stn.vn1.length-1];
        startpt2 = stn.vn2[0];
        endpt2 = stn.vn2[stn.vn2.length-1];
    }
    
    /**
     * checks if a given edge has endpoints in the given vertex sets
     * 
     * @param i endpoint of the edge
     * @param j endpoint of the edge
     * @return true if each endpoint is in one vertex set, false otherwise
     */
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
