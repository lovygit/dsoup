package com.lovy.datastructure.binarytree;

/**
 * Created by asus on 2017/4/11.
 */
public class LovyHeap<T extends Comparable<? super T>> extends LovyTree<T>{
    private int maxSize=16;
    private LovyNode<T>[] tree=new LovyNode[maxSize];
    private int size=0;

    public LovyHeap(){}

    public LovyHeap(int maxSize){
        this.maxSize=maxSize;
        this.tree=new LovyNode[maxSize];
    }

    public void expand(){
        int newMaxSize=maxSize*2;
        LovyNode<T>[] newTree=new LovyNode[newMaxSize];
        System.arraycopy(this.tree,0,newTree,0,this.size);
        this.tree=newTree;
        this.maxSize=newMaxSize;
    }

    /**
     * I want to create the minimum heap
     * @param val
     */
    public void insert(T val){
        if(size==0){
            tree[size++]=new LovyNode<T>(val);
            this.root=tree[0];
            return;
        }

        if(size==maxSize)
            expand();

        this.tree[size++]=new LovyNode<T>(val);

        int index=size-1;
        T flag=this.tree[size-1].val;
        while(index>0){
            T parentVal=this.tree[(index-1)/2].val;
            if(flag.compareTo(parentVal)<0){
                this.tree[index].val=this.tree[(index-1)/2].val;
            }else{
                break;
            }
            index=(index-1)/2;
        }

        this.tree[index].val=flag;
    }



    public void remove(T val){
        if(contains(val)){
            int i;
            for(i=0;i<this.size;i++)
                if(this.tree[i].val.equals(val))
                    break;
            this.size--;

            int parent=(size-1)/2;

            if(this.tree[parent].left==this.tree[size])
                this.tree[parent].left=null;
            else
                this.tree[parent].right=null;


            T flag=this.tree[size].val;

            int index=i;
            while((2*index+1)<this.size){
                boolean moved=false;

                if(this.tree[2*index+1].val.compareTo(flag)<0){
                    if(2*index+2>this.size||this.tree[2*index+2].val.compareTo(this.tree[2*index+1].val)>0){
                        this.tree[index].val=this.tree[2*index+1].val;
                        index=index*2+1;
                        moved=true;
                    }
                }

                if(!moved&&2*index+2<this.size&&this.tree[2*index+2].val.compareTo(flag)<0){
                    this.tree[index].val=this.tree[2*index+2].val;
                    index=index*2+2;
                    moved=true;
                }
                if(!moved)
                    break;
            }
            this.tree[index].val=flag;
        }
    }


    public T getMin(){
        return this.root.val;
    }

    public void removeMin(){
        remove(getMin());
    }

    public static <AnyType extends Comparable<? super AnyType>> LovyHeap<AnyType> createMinLovyHeap(AnyType... vals){
        if(vals==null||vals.length==0)
            return null;
        LovyHeap<AnyType> lovyHeap=new LovyHeap<AnyType>(vals.length);

        for(int i=0;i<vals.length;i++)
            lovyHeap.insert(vals[i]);

        for(int i=0;i*2+1<lovyHeap.size;i++){
            lovyHeap.tree[i].setLeft(lovyHeap.tree[i*2+1]);
            if(2*i+2<lovyHeap.size)
                lovyHeap.tree[i].setRight(lovyHeap.tree[i*2+2]);
        }

        return lovyHeap;
    }



    @Override
    public int size(){
        return this.size;
    }



    public static void test(){
        LovyHeap<Integer> lovyHeap=createMinLovyHeap(4,5,3,2,7,5,12,13,14,15);
        lovyHeap.print();
        while(lovyHeap.size>0){
            System.out.println(lovyHeap.getMin());
            lovyHeap.removeMin();
        }
    }


}
