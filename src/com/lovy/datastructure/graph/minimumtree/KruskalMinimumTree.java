package com.lovy.datastructure.graph.minimumtree;

import com.lovy.datastructure.LovyUnionFindSets;
import com.lovy.datastructure.graph.LovyEdgeNode;
import com.lovy.datastructure.graph.LovyGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2017/4/13.
 */
public class KruskalMinimumTree<T extends Comparable<? super T> ,V> {


    public KruskalMinimumTree(){}

    public List<MSTEdgeNode<T>> collectEdges(LovyGraph<T,V> lovyGraph){
        List<MSTEdgeNode<T>> edges=new LinkedList<>();
        for(int i=0;i<lovyGraph.getSize();i++){
            int from=i;
            LovyEdgeNode<T> edgeNode=lovyGraph.getVertexes()[i].getFirst();
            while(edgeNode!=null){
                int to=edgeNode.getDest();
                T cost=edgeNode.getCost();
                MSTEdgeNode<T> mstEdgeNode=new MSTEdgeNode<T>(from,to,cost);
                edges.add(mstEdgeNode);
                edgeNode=edgeNode.getLink();
            }
        }
        return edges;
    }

    public List<MSTEdgeNode<T>> createKruskalTree(LovyGraph<T,V> lovyGraph){
        List<MSTEdgeNode<T>> edegs=collectEdges(lovyGraph);
        Collections.sort(edegs);
        LovyUnionFindSets unionFindSets=new LovyUnionFindSets(lovyGraph.getSize());
        int k=0;
        int vertexNum=lovyGraph.getSize();
        List<MSTEdgeNode<T>> kruskalEdges=new ArrayList<>();
        while(k<vertexNum-1&&edegs.size()>0){
            MSTEdgeNode<T> selectedEdge=edegs.remove(0);
            int from=selectedEdge.getOrigin();
            int to=selectedEdge.getEnd();
            if((unionFindSets.parent(from)==-1&&unionFindSets.parent(to)==-1)||
                    unionFindSets.parent(from)!=unionFindSets.parent(to)){
                kruskalEdges.add(selectedEdge);
                unionFindSets.union(from,to);
                k++;
            }
        }
        return kruskalEdges;
    }

    public static void test() throws Exception {
        BufferedReader reader=new BufferedReader(new FileReader(new File("d:/labrep/dfsgraph.lvy")));
        List<Character[]> pairs=new ArrayList<>();
        String line="";
        List<Integer> costs=new ArrayList<>();
        while((line=reader.readLine())!=null){
            String[] lineContent=line.split(",");
            pairs.add(new Character[]{lineContent[0].charAt(0),lineContent[0].charAt(1)});
            costs.add(Integer.valueOf(lineContent[1]));
        }
        LovyGraph<Integer,Character> lovyGraph=LovyGraph.createLovyGraph(pairs,costs);
        System.out.println(lovyGraph);


        KruskalMinimumTree kruskalMinimumTree=new KruskalMinimumTree();

        List<MSTEdgeNode<Integer>> edges=kruskalMinimumTree.collectEdges(lovyGraph);
        System.out.println(edges);
        System.out.println("There are "+edges.size()+" edges...");
        System.out.println("After sort...");
        Collections.sort(edges);
        System.out.println(edges);


        System.out.println("I find the kruskal edges...");
        List<MSTEdgeNode<Integer>> kruskalEdges=kruskalMinimumTree.createKruskalTree(lovyGraph);
        System.out.println(kruskalEdges);
    }
}
