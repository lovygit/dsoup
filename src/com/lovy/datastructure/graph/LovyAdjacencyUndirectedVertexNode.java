package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyAdjacencyUndirectedVertexNode<T extends Comparable<? super T>,V> {
    protected V val;
    protected LovyAdjacencyEdgeNode<T,V> firstOut;

    public LovyAdjacencyUndirectedVertexNode(){}

    public LovyAdjacencyUndirectedVertexNode(LovyAdjacencyEdgeNode<T,V> firstOut,V val){
        this.val=val;
        this.firstOut=firstOut;
    }

    public LovyAdjacencyUndirectedVertexNode(V val){
        this.val=val;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    public LovyAdjacencyEdgeNode<T, V> getFirstOut() {
        return firstOut;
    }

    public void setFirstOut(LovyAdjacencyEdgeNode<T, V> firstOut) {
        this.firstOut = firstOut;
    }
}
