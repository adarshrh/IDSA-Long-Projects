package axh190002;// Starter code for max flow

import axh190002.Graph.*;

import java.util.*;

public class Flow extends GraphAlgorithm<Flow.FlowVertex> {

    Graph flowGraph;
    Vertex s,t;
    HashMap<Edge,Integer> capacity;
    HashMap<Edge,Integer> flow;
    Queue<Vertex> queue;
    HashSet<Edge> saturated;

    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
        super(g,new FlowVertex((Vertex) null));
        flowGraph=g;
        this.s=s;
        this.t=t;
        this.capacity=capacity;
        this.flow= new HashMap<>(capacity.size());
        queue = new LinkedList<>();
        saturated = new HashSet<>();

    }


    public static class FlowVertex implements  Factory {
        int height;
        int excess;
        boolean seen;
        boolean inQueue;

        FlowVertex(Vertex u) {
            height = 0;
            excess=0;
            seen=false;
            inQueue=false;
        }

        public FlowVertex make(Vertex u) { return new FlowVertex(u); }

    }



    // Return max flow found. Use either relabel to front or FIFO.
    public int preflowPush() {
        initialize();
        while (queue.size()>0){
            Vertex u = queue.poll();
            get(u).inQueue=false;
            discharge(u);
            if(get(u).excess>0){
                relabel(u);
            }
           // printFlowGraph();
        }

	return get(t).excess;
    }



    private void discharge(Vertex u) {
       // System.out.println("discharge "+u.name);
        FlowVertex cur = get(u);
        boolean flag = false;
        for(Edge edge: flowGraph.outEdges(u)){
            Vertex v = edge.to;
            if(cur.height == get(v).height+1 && !saturated.contains(edge)){
                int delta = Math.min(cur.excess,capacity.get(edge)-flow.get(edge));
                if((flow.get(edge)+delta)==capacity.get(edge)){
                    saturated.add(edge);
                }
                if(delta > 0){
                    flag=true;
                    flow.put(edge,flow.get(edge)+delta);
                    cur.excess = cur.excess-delta;
                    get(v).excess = get(v).excess+delta;
                    if(!get(v).inQueue && v.name!=s.name && v.name!=t.name){
                        get(v).inQueue=true;
                        queue.add(v);
                    }
                    if(cur.excess==0){
                        return;
                    }
                }

            }
        }
        if(!flag){
            for(Edge edge: flowGraph.inEdges(u)){
                Vertex v = edge.from;
                if(cur.height == get(v).height+1 && flow.get(edge)>0){
                    int delta = Math.min(cur.excess,flow.get(edge));
                    if(delta > 0){
                        flow.put(edge,flow.get(edge)-delta);
                        if(flow.get(edge)< capacity.get(edge)){
                            saturated.remove(edge);
                        }
                        cur.excess = cur.excess-delta;
                        get(v).excess = get(v).excess+delta;
                        if(!get(v).inQueue && v.name!=s.name && v.name!=t.name){
                            get(v).inQueue=true;
                            queue.add(v);
                        }
                        if(cur.excess==0){
                            return;
                        }
                    }

                }
            }
        }


    }



    private void relabel(Vertex u) {
        int ht=Integer.MAX_VALUE;
       // boolean flag=false;
        for(Edge edge: flowGraph.outEdges(u)){
            if(!saturated.contains(edge)) {
                ht = Math.min(ht, get(edge.to).height);
               // flag=true;
            }
        }
      //  if(!flag){
            for(Edge edge: flowGraph.inEdges(u)){
                if(flow.get(edge)>0) {
                    ht = Math.min(ht, get(edge.from).height);
                }
            }
        //}
        get(u).height = 1+ht;
        get(u).inQueue=true;
        queue.add(u);
    }


    public void initialize(){
        //Set initial flow of all edges to 0
       for(Edge edge : flowGraph.getEdgeArray()){
           flow.put(edge,0);
       }
       initializeHeight();
       for(Edge edge: flowGraph.outEdges(s)){
           int cp = capacity.get(edge);
           flow.put(edge,cp);
           saturated.add(edge);
           get(s).excess = get(s).excess-cp;
           get(edge.to).excess = get(edge.to).excess + cp;
           get(edge.to).inQueue=true;
           this.queue.add(edge.to);
       }
       get(s).height = flowGraph.size();

    }

    public void initializeHeight(){
        get(t).height = 0;
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(t);
        while (queue.size()>0){
            Vertex current = queue.poll();
            int hop = get(current).height;
            for(Edge edge : flowGraph.inEdges(current)){
                FlowVertex v = get(edge.from);
                if(!v.seen){
                    v.height = hop+1;
                    v.seen = true;
                    queue.add(edge.from);
                }
            }
        }
    }

    private void printFlowGraph() {
        System.out.println();
        System.out.println("-------------------------------");
        for(Vertex vertex: flowGraph.getVertexArray()){
            FlowVertex v = get(vertex);
            System.out.print(vertex.name+":"+" ex:"+v.excess+" h:"+v.height+" ");
            for(Edge edge: flowGraph.outEdges(vertex)){
                System.out.print(" ("+edge.from.name+","+edge.to.name+"):"+flow.get(edge)+" ");
            }
        }
        System.out.println();
    }

    // flow going through edge e
    public int flow(Edge e) {
	return 0;
    }

    // capacity of edge e
    public int capacity(Edge e) {
	return 0;
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
	return null;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
	return null;
    }
}
