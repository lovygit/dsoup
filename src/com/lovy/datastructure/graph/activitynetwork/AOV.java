package com.lovy.datastructure.graph.activitynetwork;

import com.lovy.datastructure.graph.LovyEdgeNode;
import com.lovy.datastructure.graph.LovyGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/4/13.
 */
public class AOV<V> {

    public List<V> createTopologySeq(LovyGraph<Integer,V> lovyGraph){
        LovyGraph<Integer,V> reverseGraph=lovyGraph.reverseLovyGraph();
        System.out.println("reverse graph: \n"+reverseGraph);

        List<V> vertexes=new ArrayList<V>();

        while(true){
            boolean find=false;
            for(int i=0;i<reverseGraph.getSize();i++){
                if(reverseGraph.getVertexes()[i]!=null&&reverseGraph.getVertexes()[i].getFirst()==null){
                    for(int j=0;j<reverseGraph.getSize();j++){
                        if(reverseGraph.getVertexes()[j]!=null&&reverseGraph.getVertexes()[j].getFirst()!=null){
                            LovyEdgeNode<Integer> edgeNode=reverseGraph.getVertexes()[j].getFirst();
                            if(edgeNode.getDest()==i){
                                reverseGraph.getVertexes()[j].setFirst(reverseGraph.getVertexes()[j].getFirst().getLink());
                            }
                            else{
                                while(edgeNode.getLink()!=null){
                                    if(edgeNode.getLink().getDest()==i){
                                        edgeNode.setLink(edgeNode.getLink().getLink());
                                        break;
                                    }
                                    edgeNode=edgeNode.getLink();
                                }
                            }
                        }
                    }

                    vertexes.add(reverseGraph.getVertexes()[i].getData());
                    reverseGraph.getVertexes()[i]=null;
                    find=true;
                }
            }
            if(!find)
                break;
        }
        return vertexes;
    }



    public static void test()throws Exception{
        BufferedReader reader=new BufferedReader(new FileReader(new File("d:/labrep/dfsgraph.lvy")));
        List<Character[]> pairs=new ArrayList<>();
        String line=null;
//        List<Integer> costs=new ArrayList<>();
        while((line=reader.readLine())!=null){
            String[] lineContent=line.split(",");
            pairs.add(new Character[]{lineContent[0].charAt(0),lineContent[0].charAt(1)});
//            costs.add(Integer.valueOf(lineContent[1]));
        }
        LovyGraph<Integer,Character> lovyGraph=LovyGraph.createLovyGraph(pairs,null);
        System.out.println(lovyGraph);

        List<Character> topology=new AOV<Character>().createTopologySeq(lovyGraph);
        System.out.println(topology);
    }
}
