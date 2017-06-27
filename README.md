# Dyn-Graphs

Dynamically checks a graph for bipartiteness.

The graph is sparsified using a technique described by Eppstein et al (1997). The graph is recursively divided into several subgraphs which are stored in a tree. Then the "sparse certificate" of each node is computed by taking the union of the sparse certificates of the children nodes, then further sparsifying (in the case of bipartiteness, finding a spanning tree with or without the edge that completes an odd cycle). Adding and removing edges is by modifying the relevant branch of the sparsification tree. This reduces the time of checking for the given property as described in that paper.
The graph is stored as an adjacency matrix.

The addEdge(), removeEdge(), isBipartiteDyn() and main() functions are in Graph.java. The sparsification methods etc are in the other classes.
