package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyAdjacencyDirectedVertexNode<T extends Comparable<? super T> ,V> extends LovyAdjacencyUndirectedVertexNode<T,V> {
    private LovyAdjacencyEdgeNode<T,V> firstIn;

    public LovyAdjacencyDirectedVertexNode(){}

    public LovyAdjacencyDirectedVertexNode(V val,LovyAdjacencyEdgeNode<T,V> firstIn,LovyAdjacencyEdgeNode<T,V> firstOut){
        super(firstOut,val);
        this.firstIn=firstIn;
    }

    public LovyAdjacencyDirectedVertexNode(V val){
        this.val=val;
    }
    public LovyAdjacencyEdgeNode<T, V> getFirstIn() {
        return firstIn;
    }

    public void setFirstIn(LovyAdjacencyEdgeNode<T, V> firstIn) {
        this.firstIn = firstIn;
    }
}
