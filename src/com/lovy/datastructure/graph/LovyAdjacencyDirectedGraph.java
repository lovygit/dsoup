package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyAdjacencyDirectedGraph<T extends Comparable<? super T>,V> extends LovyAdjacencyUndirectedGraph<T,V> {

    public LovyAdjacencyDirectedGraph(){}
    public LovyAdjacencyDirectedGraph(int maxSize){
        super(maxSize);
    }

    public static <T extends Comparable<? super T> ,V> LovyAdjacencyDirectedGraph<T,V> createLovyAdjacencyDirectedGraph(LovyGraph<T,V> lovyGraph){
        LovyAdjacencyDirectedGraph<T,V> directedGraph=new LovyAdjacencyDirectedGraph(lovyGraph.getSize());

        for(int i=0;i<lovyGraph.getSize();i++){
            directedGraph.vertexes[directedGraph.size++]=new LovyAdjacencyDirectedVertexNode(lovyGraph.getVertexes()[i].getData());
        }

        for(int i=0;i<lovyGraph.getSize();i++){
            int fromIndex=i;
            LovyEdgeNode<T> lovyEdgeNode=lovyGraph.getVertexes()[i].getFirst();
            while(lovyEdgeNode!=null){
                int toIndex=lovyEdgeNode.getDest();

                LovyAdjacencyEdgeNode<T,V> outEdgeNode=new LovyAdjacencyEdgeNode<T, V>(fromIndex,toIndex,directedGraph.vertexes[fromIndex].getFirstOut(),directedGraph.vertexes[toIndex].getFirstOut());

                directedGraph.vertexes[fromIndex].setFirstOut(outEdgeNode);

                LovyAdjacencyEdgeNode<T,V> inEdgeNode=new LovyAdjacencyEdgeNode<T, V>(fromIndex,toIndex,((LovyAdjacencyDirectedVertexNode<T,V>)directedGraph.vertexes[fromIndex]).getFirstIn(),((LovyAdjacencyDirectedVertexNode<T,V>)directedGraph.vertexes[toIndex]).getFirstIn());

                ((LovyAdjacencyDirectedVertexNode<T,V>) directedGraph.vertexes[toIndex]).setFirstIn(inEdgeNode);

                lovyEdgeNode=lovyEdgeNode.getLink();
            }
        }
        return directedGraph;
    }


    public String toReverseString(){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<this.size;i++){
            builder.append(i+"[ "+this.vertexes[i].getVal()+" ] >");
            LovyAdjacencyEdgeNode<T,V> edgeNode=((LovyAdjacencyDirectedVertexNode)this.vertexes[i]).getFirstIn();

            while(edgeNode!=null){
                builder.append(edgeNode.getVertexA()+",");
                edgeNode=edgeNode.getPathB();
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void test() throws Exception {
        LovyGraph<Integer,Character> lovyGraph=new LovyGraph().createLovyGraphInstance("d:/labrep/dirgraph.lvy");
        System.out.println(lovyGraph);

        System.out.println(lovyGraph.reverseLovyGraph());
        LovyAdjacencyDirectedGraph<Integer,Character> directedGraph=createLovyAdjacencyDirectedGraph(lovyGraph);

        System.out.println(directedGraph);

        System.out.println(directedGraph.toReverseString());
    }
}
