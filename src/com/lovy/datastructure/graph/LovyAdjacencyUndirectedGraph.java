package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyAdjacencyUndirectedGraph<T extends Comparable<? super T>,V> {
    protected int maxSize;
    protected int size;

    protected LovyAdjacencyUndirectedVertexNode<T,V>[] vertexes=new LovyAdjacencyUndirectedVertexNode[maxSize];

    public LovyAdjacencyUndirectedGraph(){}
    public LovyAdjacencyUndirectedGraph(int maxSize){
        this.maxSize=maxSize;
        this.vertexes=new LovyAdjacencyUndirectedVertexNode[this.maxSize];
    }

    protected void setSize(int size){
        this.size=size;
    }

    public int getSize() {
        return size;
    }

    public LovyAdjacencyUndirectedVertexNode<T, V>[] getVertexes() {
        return vertexes;
    }


    /**
     * this method create an adjacency graph for an undirected graph
     * we can use the method createLovyGraph(but we should input AB,1 & BA,1
     * because we just modified the vertexes[fromIndex] to point the next LovyAjacencyEdgeNode
     * It's more easy to find the next edge for a specific vertex
     * @param lovyGraph
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T extends Comparable<? super T> ,V> LovyAdjacencyUndirectedGraph<T,V> createLovyAdjacencyUndirectedGraphFromLovyGraph(LovyGraph<T,V> lovyGraph){
        LovyAdjacencyUndirectedGraph<T,V> LovyAdjacencyUndirectedGraph=new LovyAdjacencyUndirectedGraph<T, V>(lovyGraph.getSize());
        for(int i=0;i<lovyGraph.getSize();i++){
            LovyAdjacencyUndirectedGraph.vertexes[LovyAdjacencyUndirectedGraph.size++]=new LovyAdjacencyUndirectedVertexNode<T, V>(lovyGraph.getVertexes()[i].getData());
        }

        for(int i=0;i<lovyGraph.getSize();i++){
            int fromIndex=i;
            LovyEdgeNode<T> lovyEdgeNode=lovyGraph.getVertexes()[i].getFirst();
            while(lovyEdgeNode!=null){
                int toIndex=lovyEdgeNode.getDest();
                T cost=lovyEdgeNode.getCost();

                LovyAdjacencyEdgeNode<T,V> edgeNode=new LovyAdjacencyEdgeNode<T, V>(fromIndex,toIndex,LovyAdjacencyUndirectedGraph.vertexes[fromIndex].getFirstOut(),LovyAdjacencyUndirectedGraph.vertexes[toIndex].getFirstOut(),cost);

                LovyAdjacencyUndirectedGraph.vertexes[fromIndex].setFirstOut(edgeNode);
//                LovyAdjacencyUndirectedGraph.vertexes[toIndex].setFirstOut(edgeNode);

                lovyEdgeNode=lovyEdgeNode.getLink();
            }

        }
        return LovyAdjacencyUndirectedGraph;
    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<this.size;i++){
            builder.append(i+"[ "+vertexes[i].getVal()+" ] >");
            LovyAdjacencyEdgeNode<T,V> edgeNode=this.vertexes[i].getFirstOut();
//            int preIndex=i;
            while(edgeNode!=null){
//               int vertexA=edgeNode.getVertexA();
//               int vertexB=edgeNode.getVertexB();
//                builder.append(((vertexA==preIndex)?vertexB:vertexA)+",");
//                preIndex^=vertexA;
//                preIndex^=vertexB;
//                edgeNode=(preIndex==vertexA)?edgeNode.getPathA():edgeNode.getPathB();
                builder.append(edgeNode.getVertexB()+",");
                edgeNode=edgeNode.getPathA();
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void test()throws Exception{
        LovyGraph<Integer,Character> lovyGraph=new LovyGraph().createLovyGraphInstance("d:/labrep/undirectgraph.lvy");

        LovyAdjacencyUndirectedGraph LovyAdjacencyUndirectedGraph=createLovyAdjacencyUndirectedGraphFromLovyGraph(lovyGraph);

        System.out.println(LovyAdjacencyUndirectedGraph);

    }



}
