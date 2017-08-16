package com.lovy.datastructure.graph;

import com.lovy.datastructure.binarytree.LovyNode;
import com.lovy.datastructure.binarytree.LovyTree;
import com.lovy.datastructure.tree.LovyGeneralNode;
import com.lovy.datastructure.tree.LovyGeneralTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by asus on 2017/4/12.
 */
public class LovyGraph<T extends Comparable<? super T>,V> {

    private int maxSize=16;
    private int size=0;

    private LovyVertexNode<T,V>[] vertexes=new LovyVertexNode[maxSize];

    public LovyGraph(){}
    public LovyGraph(int maxSize){
        this.maxSize=maxSize;
        this.vertexes=new LovyVertexNode[this.maxSize];
    }

    public int getSize(){
        return this.size;
    }

    public LovyVertexNode<T, V>[] getVertexes() {
        return vertexes;
    }

    public static <T extends Comparable<? super T>,V> LovyGraph<T,V> createLovyGraph(List<V[]> pairs, List<T> costs){
        if(pairs==null||pairs.size()==0)
            return null;

        LovyGraph<T,V> lovyGraph=new LovyGraph<T, V>(pairs.size()*2);

        for(int k=0;k<pairs.size();k++){
            V from=pairs.get(k)[0];
            V to=pairs.get(k)[1];

            boolean containsFrom=false;
            boolean containsToInEdge=false;
            boolean containsToInVertex=false;

            int fromIndex=0;
            int toIndex=0;

            for(int i=0;i<lovyGraph.size;i++){
                if(lovyGraph.vertexes[i].getData().equals(from)){
                    containsFrom=true;
                    fromIndex=i;
                    LovyEdgeNode<T> p=lovyGraph.vertexes[i].getFirst();
                    while(p!=null){
                        if(lovyGraph.vertexes[p.getDest()].getData().equals(to)){
                            containsToInEdge=true;
                            break;
                        }
                        p=p.getLink();
                    }
                }
                if(lovyGraph.vertexes[i].getData().equals(to)){
                    containsToInVertex=true;
                    toIndex=i;
                }

                if(containsFrom&&(containsToInEdge||containsToInVertex))
                    break;
            }


//            System.out.println("containsFrom "+containsFrom+", containsInEdge "+containsToInEdge+", containsInVertex "+containsToInVertex);


            if(containsFrom&&containsToInEdge)
                continue;
            else if(containsFrom&&containsToInVertex){
                if(from.equals(to))
                    continue;
                LovyEdgeNode<T> p=lovyGraph.vertexes[fromIndex].getFirst();
                if(p==null){
                    lovyGraph.vertexes[fromIndex].setFirst(new LovyEdgeNode<T>(toIndex));
                    if(costs!=null){
                        lovyGraph.vertexes[fromIndex].getFirst().setCost(costs.get(k));
                    }
                }else{
                    while(p.getLink()!=null)
                        p=p.getLink();
                    p.setLink(new LovyEdgeNode<T>(toIndex));
                    if(costs!=null){
                        p.getLink().setCost(costs.get(k));
                    }
                }
            }
            else if(containsFrom){
                lovyGraph.vertexes[lovyGraph.size++]=new LovyVertexNode<T, V>(to);
                LovyEdgeNode<T> p=lovyGraph.vertexes[fromIndex].getFirst();
                if(p==null){
                    lovyGraph.vertexes[fromIndex].setFirst(new LovyEdgeNode<T>(lovyGraph.size-1));
                    if(costs!=null){
                        lovyGraph.vertexes[fromIndex].getFirst().setCost(costs.get(k));
                    }
                }else{
                    while(p.getLink()!=null)
                        p=p.getLink();
                    p.setLink(new LovyEdgeNode<T>(lovyGraph.size-1));
                    if(costs!=null){
                        p.getLink().setCost(costs.get(k));
                    }
                }
            }else if(containsToInVertex){
                lovyGraph.vertexes[lovyGraph.size++]=new LovyVertexNode<T, V>(from);
                lovyGraph.vertexes[lovyGraph.size-1].setFirst(new LovyEdgeNode<T>(toIndex));
                if(costs!=null){
                    lovyGraph.vertexes[lovyGraph.size-1].getFirst().setCost(costs.get(k));
                }
            }else{
                lovyGraph.vertexes[lovyGraph.size++]=new LovyVertexNode<T, V>(from);
                if(from.equals(to));
                else {
                    lovyGraph.vertexes[lovyGraph.size++]=new LovyVertexNode<T, V>(to);
                    lovyGraph.vertexes[lovyGraph.size-2].setFirst(new LovyEdgeNode<T>(lovyGraph.size-1));
                    if(costs!=null){
                        lovyGraph.vertexes[lovyGraph.size-2].getFirst().setCost(costs.get(k));
                    }
                }
            }
//            System.out.println(lovyGraph);
        }
        return lovyGraph;
    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<this.size;i++){
            builder.append(i+"[ "+this.vertexes[i].getData()+" ] >");
            LovyEdgeNode<T> p=this.vertexes[i].getFirst();
            while(p!=null){
                builder.append(p.getDest()+"("+p.getCost()+"),");
                p=p.getLink();
            }
            builder.append("\n");
        }
        return builder.toString();
    }



    public LovyGraph reverseLovyGraph(){
        LovyGraph<T,V> lovyGraph=new LovyGraph<T, V>(this.size);

        for(int i=0;i<this.size;i++){
            lovyGraph.vertexes[lovyGraph.size++]=new LovyVertexNode<T, V>(this.vertexes[i].getData());
        }

        for(int i=0;i<this.size;i++){
            LovyEdgeNode<T> edgeNode=this.vertexes[i].getFirst();
            int toIndex=i;
            while(edgeNode!=null){
                int fromIndex=edgeNode.getDest();
                if(lovyGraph.vertexes[fromIndex].getFirst()==null){
                    lovyGraph.vertexes[fromIndex].setFirst(new LovyEdgeNode<T>(toIndex,edgeNode.getCost()));
                }else{
                    LovyEdgeNode<T> p=lovyGraph.vertexes[fromIndex].getFirst();
                    while(p.getLink()!=null)
                        p=p.getLink();
                    p.setLink(new LovyEdgeNode<T>(toIndex,edgeNode.getCost()));
                }
                edgeNode=edgeNode.getLink();
            }
        }
        return lovyGraph;
    }



    public LovyGraph<Integer,Character> createLovyGraphInstance(String fileName)throws  Exception{
        BufferedReader reader=new BufferedReader(new FileReader(new File(fileName)));
        List<Character[]> pairs=new ArrayList<>();
        List<Integer> costs=new ArrayList<>();
        String line="";
        while((line=reader.readLine())!=null){
            Character[] pair=new Character[2];
            String[] lineContent=line.split(",");
            pair[0]=lineContent[0].charAt(0);
            pair[1]=lineContent[0].charAt(1);
            pairs.add(pair);
            costs.add(Integer.valueOf(lineContent[1]));
        }
        LovyGraph lovyGraph=createLovyGraph(pairs,costs);
        return lovyGraph;
    }


    private void DFS(int k,boolean[] visited){
        if(!visited[k]){
            System.out.print(this.vertexes[k].getData()+"->");
            visited[k]=true;
        }
        LovyEdgeNode<T> edgeNode=this.vertexes[k].getFirst();

        while(edgeNode!=null){
            if(!visited[edgeNode.getDest()])
                DFS(edgeNode.getDest(),visited);
            edgeNode=edgeNode.getLink();
        }
    }


    public void DFS(){
        boolean[] visited=new boolean[this.size];
        for(int i=0;i<this.size;i++){
            if(visited[i])
                continue;
            DFS(i,visited);
            System.out.println();
        }
        System.out.println("STOP");
    }


   public LovyNode<V> createDFSLovyNode(LovyEdgeNode<T> node,boolean[] visited){
        if(node==null||visited[node.getDest()])
            return null;

        visited[node.getDest()]=true;

        LovyNode<V> root=new LovyNode<V>(this.vertexes[node.getDest()].getData());


        if(root!=null){
            root.setLeft(createDFSLovyNode(this.vertexes[node.getDest()].getFirst(),visited));
            root.setRight(createDFSLovyNode(node.getLink(),visited));
        }
        return root;
   }

   public LovyTree<V> createDFSLovyTree(){
       boolean[] visited=new boolean[this.size];
       List<LovyTree<V>> forest=new ArrayList<>();
       for(int i=0;i<this.size;i++){
           if(visited[i])
               continue;
           forest.add(new LovyTree<>(new LovyNode<V>(this.vertexes[i].getData())));
           forest.get(forest.size()-1).getRoot().setLeft(createDFSLovyNode(this.vertexes[i].getFirst(),visited));
       }

       LovyGeneralTree<V>[] trees=new LovyGeneralTree[forest.size()];
       for(int i=0;i<forest.size();i++) {
           trees[i]=new LovyGeneralTree(LovyGeneralTree.createLovyGeneralNodeFromLovyNode(forest.get(i).getRoot()));
       }

       LovyTree lovyTree=LovyGeneralTree.createLovyTreeFromLovyForest(trees);
       return lovyTree;
   }


   private void BFS(int k,boolean[] visited){
       if(!visited[k]){
           System.out.print(this.vertexes[k].getData()+"->");
           visited[k]=true;
       }
       List<Integer> queue=new ArrayList<>();
       queue.add(k);
       while(queue.size()>0){
           int currentIndex=queue.remove(0);
           LovyEdgeNode<T> edgeNode=this.vertexes[currentIndex].getFirst();
           while(edgeNode!=null){
               if(!visited[edgeNode.getDest()]){
                   System.out.print(this.vertexes[edgeNode.getDest()].getData()+"->");
                   visited[edgeNode.getDest()]=true;
                   queue.add(edgeNode.getDest());
               }
               edgeNode=edgeNode.getLink();
           }
       }
   }

   public void BFS(){
       boolean[] visited=new boolean[this.size];

       for(int i=0;i<this.size;i++){
           if(visited[i])
               continue;
           BFS(i,visited);
       }
       System.out.println("STOP");
   }

   private void createLowArray(int u,int v,int[] dfn,int[] low,int[] num){
       dfn[u]=low[u]=num[0]++;
       for(LovyEdgeNode<T> edgeNode=this.vertexes[u].getFirst();edgeNode!=null;edgeNode=edgeNode.getLink()){
           int w=edgeNode.getDest();
           if(w==v)//a circle edge
               continue;

           if(dfn[w]<0){
               createLowArray(w,u,dfn,low,num);//means meet u is earlier than w,so u is the parent node in DFS
               low[u]=Math.min(low[u],low[w]);// is corresponding to the definition part -> low(u)=min{dfn(u),min{low(v)|v is the child node of u}}
           }else{//means we meet w node earlier than u)
                low[u]=Math.min(low[u],dfn[w]);//is corresponding to the definition part -> low(u)=min{dfn(u)<low(u) is initialed by dfn(u)> , dfn(w)|(u,w) is a backing edge
           }
       }
   }

    /**
     * the DEFINITION of low array:low(u)指的是从顶点u出发，经过一条由后代顶点形成的路径和一条回退边，所能到达的具有最小深度优先编号的顶点的编号
     * you should be careful to understand the definition of low array
     * low(u)=min{dfn(u),min{low(w)|w is the child of u},min{dfn(w)|(u,w) is a backing edge}}
     * @return
     */
   public int[] createLowArray(){
       int[] dfn=new int[this.size];
       int[] low=new int[this.size];
       for(int i=0;i<dfn.length;i++){
           dfn[i]=-1;//the judge situation--> dfn[w]<0 to explain that w haven't visited
       }
       createLowArray(0,-1,dfn,low,new int[1]);
       return low;
   }


    /**
     * v is the parent node of u v!=w is avoid a special situation w->u,u->w it's a circle path
     * this method is just suitable for undirected connected graph
     * @param u
     * @param v
     * @param stack
     */
   private void printBiconnectedComponents(int u,int v,List<Object[]> stack,int[] dfn,int[] low,int[] num){
        low[u]=dfn[u]=num[0]++;
        for(LovyEdgeNode<T> edgeNode=this.vertexes[u].getFirst();edgeNode!=null;edgeNode=edgeNode.getLink()){
            int w=edgeNode.getDest();
            if(w==v)//the circle path
                continue;
            if(dfn[w]<dfn[u]){
                stack.add(new Object[]{this.vertexes[w].getData(),this.vertexes[u].getData()});
                if(dfn[w]<0){//w is a child node
                    printBiconnectedComponents(w,u,stack,dfn,low,num);
                    low[u]=Math.min(low[u],low[w]);
                    if(low[w]>=dfn[u]){//vertex w can't reach any parent vertex of u
                        V wVal,uVal;
                        do{
                            Object[] edge=stack.remove(stack.size()-1);
                            wVal= (V) edge[0];
                            uVal= (V) edge[1];
                            System.out.print("<"+wVal+","+uVal+">");
                        }while(!((wVal.equals(this.vertexes[w].getData()))&(uVal.equals(this.vertexes[u].getData()))));
                        System.out.println();
                    }
                }else// w is parent(except v) node
                    low[u]=Math.min(low[u],dfn[w]);//this means
            }
        }
   }


   public void printBiconnectedComponents(){
       List<Object[]> stack=new LinkedList<>();
       int[] low=new int[this.size];
       int[] dfn=new int[this.size];
       for(int i=0;i<dfn.length;i++)
           dfn[i]=-1;
       printBiconnectedComponents(0,-1,stack,dfn,low,new int[]{0});
   }

   private void recordSeq(int u,int v,boolean[] visited,List<V> recorder){
       if(!visited[u]){
           recorder.add(this.vertexes[u].getData());
           visited[u]=true;
       }
       for(LovyEdgeNode<T> edgeNode=this.vertexes[u].getFirst();edgeNode!=null;edgeNode=edgeNode.getLink()){
           int w=edgeNode.getDest();
           if(!visited[w])
               recordSeq(w,u,visited,recorder);
       }
   }


   private List<V> recordSeq(){
       List<V> resultRecorder=new ArrayList<V>();
       boolean[] visited=new boolean[this.size];
       for(int i=0;i<this.size;i++){
           if(visited[i])
               continue;
           List<V> recorder=new ArrayList<V>();
           recordSeq(i,-1,visited,recorder);
           Collections.reverse(recorder);
           resultRecorder.addAll(recorder);
       }
       return resultRecorder;
   }

   public void printStronglyConnectedGraph(){
       List<V> recorder=recordSeq();
       LovyGraph<T,V> reverseGraph=this.reverseLovyGraph();
       boolean[] visited=new boolean[this.size];
       for(int i=recorder.size()-1;i>=0;i--){
           int  j;
           for(j=0;j<reverseGraph.size;j++){
               if(reverseGraph.vertexes[j].getData().equals(recorder.get(i)))
                   break;
           }
           if(visited[j])
               continue;
           reverseGraph.DFS(j,visited);
           System.out.println();
       }
   }


    public static void test() throws Exception {
       BufferedReader reader=new BufferedReader(new FileReader(new File("d:/labrep/dfsgraph.lvy")));
       List<Character[]> pairs=new ArrayList<>();
       String line="";
       while((line=reader.readLine())!=null){
           pairs.add(new Character[]{line.charAt(0),line.charAt(1)});
       }
       LovyGraph<Integer,Character> lovyGraph=createLovyGraph(pairs,null);
       lovyGraph.printStronglyConnectedGraph();
    }
}
