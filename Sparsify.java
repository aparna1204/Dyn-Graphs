/**
 *
 * @author Aparna Shankar
 */
public interface Sparsify {
 
    /**
     * creates a sparsification tree for the graph
     * 
     * @param g the graph to sparsify
     * @return the sparsification tree of the graph
     */
    public SparseTreeNode sparsify(Graph g);
    
    /**
     * finds the sparse certificate for a given graph
     *
     * @param g the graph
     * @return another graph which is a sparse certificate for g. 
     */
    public Graph sparseCert(Graph g);
    
    public SparseTreeNode sparseCertTree(SparseTreeNode stn);
    
}
