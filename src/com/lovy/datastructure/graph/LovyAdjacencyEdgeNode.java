package com.lovy.datastructure.graph;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyAdjacencyEdgeNode<T extends Comparable<? super T>,V> {
    private boolean mark;
    private int vertexA,vertexB;
    private LovyAdjacencyEdgeNode<T,V> pathA,pathB;
    private T cost;

    public LovyAdjacencyEdgeNode(){}

    public LovyAdjacencyEdgeNode(int vertexA,int vertexB,LovyAdjacencyEdgeNode pathA,LovyAdjacencyEdgeNode pathB,T cost){
        this.vertexA=vertexA;
        this.vertexB=vertexB;
        this.pathA=pathA;
        this.pathB=pathB;
        this.cost=cost;
    }

    public LovyAdjacencyEdgeNode(int vertexA,int vertexB,LovyAdjacencyEdgeNode pathA,LovyAdjacencyEdgeNode pathB){
        this(vertexA,vertexB,pathA,pathB,null);
    }
    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getVertexA() {
        return vertexA;
    }

    public void setVertexA(int vertexA) {
        this.vertexA = vertexA;
    }

    public int getVertexB() {
        return vertexB;
    }

    public void setVertexB(int vertexB) {
        this.vertexB = vertexB;
    }

    public LovyAdjacencyEdgeNode<T, V> getPathA() {
        return pathA;
    }

    public void setPathA(LovyAdjacencyEdgeNode<T, V> pathA) {
        this.pathA = pathA;
    }

    public LovyAdjacencyEdgeNode<T, V> getPathB() {
        return pathB;
    }

    public void setPathB(LovyAdjacencyEdgeNode<T, V> pathB) {
        this.pathB = pathB;
    }

    public T getCost() {
        return cost;
    }

    public void setCost(T cost) {
        this.cost = cost;
    }
}
