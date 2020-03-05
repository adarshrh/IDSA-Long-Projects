/**
 * IDSA Short Project 5
 * Team members:
 * Adarsh Raghupati   axh190002
 * Keerti Keerti      kxk190012
 */


package axh190002;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DFS extends Graph.GraphAlgorithm<DFS.DFSVertex> {
    public static boolean isCyclic;

    //Enum to save the state of each vertex
    public enum VertexState {
    NEW,
    ACTIVE,
    COMPLETE;
}
    public static class DFSVertex implements Graph.Factory {
        int cno;
        public Graph.Vertex parent;
        VertexState vertexState;

        /**
         * Initializes Vertex with parent, component number and vertex state
         * @param u
         */
        public DFSVertex(Graph.Vertex u) {
            this.parent = null;
            this.cno = 0;
            vertexState = VertexState.NEW;
        }

        public DFSVertex make(Graph.Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        isCyclic = false;
    }

    public static DFS depthFirstSearch(Graph g) {
      return null;
    }

    /**
     * Method runs DFS algorithm to find the topological order of the DAG
     * @return List of vertices in topological order
     */
    public LinkedList<Graph.Vertex> depthFirstSearch() {
        for (Graph.Vertex u : g) {
            get(u).parent = null;
            get(u).vertexState = VertexState.NEW;
        }
        LinkedList<Graph.Vertex>  topologicalOrderList = new LinkedList<Graph.Vertex>();
        for (Graph.Vertex u : g) {
            if (get(u).vertexState == VertexState.NEW) {
                doDFSVisit(u,topologicalOrderList);
            }
        }
        return topologicalOrderList;
    }

    /**
     * @param u
     * Starts DFS visit of vertices starting from the vertex u.
     * Updates the topologicalOrderList with the visited vertices
     */
    private void doDFSVisit(Graph.Vertex u, LinkedList<Graph.Vertex> topologicalOrderList ) {
        get(u).vertexState = VertexState.ACTIVE;
        for (Graph.Edge e : g.incident(u)) {
            Graph.Vertex v = e.otherEnd(u);
            if (get(v).vertexState == VertexState.NEW) {
                get(v).parent = u;
                get(v).cno = get(u).cno;
                doDFSVisit(v,topologicalOrderList);
            } else {
                if (get(v).vertexState == VertexState.ACTIVE) {
                    isCyclic = true;
                }
            }
        }
        get(u).vertexState = VertexState.COMPLETE;
        topologicalOrderList.addFirst(u);
    }


    // Member function to find topological order
    public List<Graph.Vertex> topologicalOrder1() {
        LinkedList<Graph.Vertex> topologicalOrderList = depthFirstSearch();
        if(isCyclic) {
            return null;
        }
        return topologicalOrderList;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Graph.Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Graph.Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Graph.Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        List<Graph.Vertex> toplogicalList = topologicalOrder1(g);
        if(toplogicalList==null) {
            System.out.println("Graph is cyclic. Topological order does not exists");
        }else {
            boolean isFirst = true;
            System.out.println("Topological order of the given graph is:");
            for(Graph.Vertex u: toplogicalList) {
                if(isFirst){

                    System.out.print(u);
                    isFirst = false;
                } else{
                    System.out.print("->"+u);
                }
            }
            System.out.println("");
        }
    }
}

