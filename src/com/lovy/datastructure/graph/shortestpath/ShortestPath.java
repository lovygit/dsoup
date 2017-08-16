package com.lovy.datastructure.graph.shortestpath;

import com.lovy.datastructure.graph.LovyEdgeNode;
import com.lovy.datastructure.graph.LovyGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by asus on 2017/4/13.
 */
public class ShortestPath<V> {


    public Integer[] getWeight(LovyGraph<Integer,V> lovyGraph,int v){
        if(lovyGraph==null)
            return null;
        Integer[] weight=new Integer[lovyGraph.getSize()];
        for(int i=0;i<weight.length;i++)
            weight[i]=Integer.MAX_VALUE;

        for(LovyEdgeNode<Integer> edgeNode=lovyGraph.getVertexes()[v].getFirst();edgeNode!=null;edgeNode=edgeNode.getLink()){
            int to=edgeNode.getDest();
            weight[to]=edgeNode.getCost();
        }
        return weight;
    }

    /**
     * path is used to record the shortest path from u to ? ,we can print the shortest path with array <em>path</em>
     * @param lovyGraph
     * @param v
     * @return
     */
    public Integer[] shortestPath(LovyGraph<Integer,V> lovyGraph,int v){
        Integer[] dist=getWeight(lovyGraph,v);//记录当前找到的从源点v到终点的v(i)的最短路径长度
        Integer[] path=new Integer[lovyGraph.getSize()];//记录当前找到的从源点v到终点v(i)的最短路径上最后一条边的起点
        boolean[] shortestMark=new boolean[lovyGraph.getSize()];//表示顶点i是否已经求得最短路径

        for(int i=0;i<lovyGraph.getSize();i++){
            if(i!=v&&dist[i]!=Integer.MAX_VALUE)
                path[i]=v;
            else
                path[i]=-1;
        }

        //顶点v加入集合s
        shortestMark[v]=true;
        dist[v]=0;

        for(int i=0;i<lovyGraph.getSize()-1;i++){
            int minValue=Integer.MAX_VALUE;
            int u=v;

            //find the next shortest vertex
            for(int j=0;j<lovyGraph.getSize();j++)
                if(!shortestMark[j]&&dist[j]<minValue){
                    minValue=dist[j];
                    u=j;
                }

            //add this vertex to set containing all selected vertexes
            shortestMark[u]=true;
            for(LovyEdgeNode<Integer> edgeNode=lovyGraph.getVertexes()[u].getFirst();edgeNode!=null;edgeNode=edgeNode.getLink()){
                int to=edgeNode.getDest();
                if(edgeNode.getCost()+dist[u]<dist[to]){//v-u,u- to some vertex
                    dist[to]=edgeNode.getCost()+dist[u];
                    path[to]=u;//means the shortest path(v-to)'s last edge's origin is u
                }
            }
        }

//        for(int i=0;i<dist.length;i++){
//            System.out.print("The shortest distance from "+lovyGraph.getVertexes()[v].getData()+" to "+lovyGraph.getVertexes()[i].getData()+": ");
//            if(dist[i]==Integer.MAX_VALUE)
//                System.out.println("NO_PATH");
//            else
//                System.out.println(dist[i]);
//        }
        return dist;
    }


    public void findAllPairShotestPath(LovyGraph<Integer,V> lovyGraph){
        List<Integer[]> distances=new ArrayList<>();
        for(int i=0;i<lovyGraph.getSize();i++)
            distances.add(shortestPath(lovyGraph,i));

    }

//    public static void test()throws Exception{
//        BufferedReader reader=new BufferedReader(new FileReader(new File("d:/labrep/dfsgraph.lvy")));
//        List<Character[]> pairs=new ArrayList<>();
//        String line="";
//        List<Integer> costs=new ArrayList<>();
//        while((line=reader.readLine())!=null){
//            String[] lineContent=line.split(",");
//            pairs.add(new Character[]{lineContent[0].charAt(0),lineContent[0].charAt(1)});
//            costs.add(Integer.valueOf(lineContent[1]));
//        }
//        LovyGraph<Integer,Character> lovyGraph=LovyGraph.createLovyGraph(pairs,costs);
//        System.out.println(lovyGraph);
//
//    }
}
