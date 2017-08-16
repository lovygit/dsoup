package com.lovy.datastructure.graph.minimumtree;

import com.lovy.datastructure.graph.LovyGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * T is the edge cost type,V is the vertex's value type
 * Created by asus on 2017/4/13.
 */
public class PrimMinimumTree<T extends Comparable<? super T>,V> {

    public List<MSTEdgeNode<T>> collectEdges(LovyGraph<T,V> lovyGraph){
        return new KruskalMinimumTree<T,V>().collectEdges(lovyGraph);
    }


    public List<MSTEdgeNode<T>> createPrimTree(LovyGraph<T,V> lovyGraph){
        List<MSTEdgeNode<T>> edges=collectEdges(lovyGraph);
        Collections.sort(edges);
        System.out.println(edges);

        List<MSTEdgeNode<T>> primTree=new ArrayList<>();

        primTree.add(edges.get(0));
        int k=1;
        int vertexNum=lovyGraph.getSize();
        Set<Integer> selectedVertexes=new HashSet<>();
        selectedVertexes.add(edges.get(0).getOrigin());
        selectedVertexes.add(edges.get(0).getEnd());

        while(k<vertexNum-1){
            int index=1;
            T minimunCost=null;
            boolean first=true;
            for(int i=0;i<edges.size();i++){
                MSTEdgeNode<T> newEdge=edges.get(i);
                int origin=edges.get(i).getEnd();
                int end=edges.get(i).getOrigin();
                if(selectedVertexes.contains(origin)&&selectedVertexes.contains(end)||
                        (!selectedVertexes.contains(origin)&&!selectedVertexes.contains(end)))
                    continue;

                if(first){
                    index=i;
                    minimunCost= newEdge.getCost();
                    first=false;
                }else if(minimunCost.compareTo(newEdge.getCost())>0){
                    minimunCost=newEdge.getCost();
                    index=i;
                }
            }

            if(minimunCost==null)
                return null;
            k++;
            MSTEdgeNode<T> foundEdge=edges.get(index);
            int anotherVertex=(selectedVertexes.contains(foundEdge.getOrigin()))?foundEdge.getEnd():foundEdge.getOrigin();
            selectedVertexes.add(anotherVertex);
            primTree.add(foundEdge);
        }
        Collections.sort(primTree);
        return primTree;
    }



    public static void test()throws Exception{
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

        PrimMinimumTree<Integer,Character> primMinimumTree=new PrimMinimumTree<>();

        List<MSTEdgeNode<Integer>> primTree=primMinimumTree.createPrimTree(lovyGraph);
        System.out.println(primTree);
    }
}
