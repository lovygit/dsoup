package com.lovy.datastructure.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2017/4/23.
 */
public class Trie {
    public static class TrieNode{
        private char content;
        private boolean isEnd;
        private int count;//the sharing time of this character
        private List<TrieNode> children=new LinkedList<>();

        public TrieNode(char content){
            this.content=content;
            this.count=1;
        }

        public TrieNode subNode(char ch){
            if(this.children==null)
                return null;
            for(TrieNode node:children)
                if(node.content==ch)
                    return node;
            return null;
        }

        public char getContent() {
            return content;
        }

        public void setContent(char content) {
            this.content = content;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<TrieNode> getChildren() {
            return children;
        }

        public void setChildren(List<TrieNode> children) {
            this.children = children;
        }


    }
    private TrieNode root;

    public Trie(){
        this.root=new TrieNode(' ');//it's corresponding to the definition of trie
    }

    public boolean search(String word){
        TrieNode next=this.root;
        int i=0;
        while(i<word.length()){
            boolean findNext=false;
            for(TrieNode node:next.children)
                if(node.content==word.charAt(i)){
                    i++;
                    next=node;
                    findNext=true;
                    break;
                }

            if(!findNext)
                return false;
        }
        return next.isEnd;
    }

    public void insert(String word){
        if(search(word))
            return ;

        TrieNode next=this.root;
        for(char ch:word.toCharArray()){
            boolean findNext=false;
            for(TrieNode node:next.children){
                if(node.content==ch){
                    node.count++;
                    findNext=true;
                    next=node;
                    break;
                }
            }

            if(!findNext){
                TrieNode newNode=new TrieNode(ch);
                next.children.add(newNode);
                next=newNode;
            }
        }
        next.isEnd=true;
    }

    public void delete(String word){
        if(!search(word))
            return;
        TrieNode next=this.root;
        for(char ch:word.toCharArray()){
            for(TrieNode node:next.children)
                if(node.content==ch){
                    if(node.count==1){
                        next.children.remove(node);
                        return;
                    }
                    node.count--;
                    next=node;
                    break;
                }
        }
    }


    public List<String> hint(String word,int wordNum){
        TrieNode next=this.root;
        String preStr="";
        for(char ch:word.toCharArray()){
            boolean findNext=false;
            for(TrieNode node:next.children)
                if(node.content==ch){
                    next=node;
                    findNext=true;
                    break;
                }
            
            if(!findNext)
                break;
                
            preStr+=ch;
        }
        List<String> words=new ArrayList<>();
        readAll(next,preStr,words,wordNum);

        return words;
    }

    public void readAll(TrieNode next,String preStr,List<String> words,int wordNum){
        if(words.size()==wordNum)
            return;
        for(TrieNode node:next.children){
            if(node.isEnd){
                words.add(preStr+node.content);
                if(words.size()>wordNum)
                    return;
            }
            readAll(node,preStr+node.content,words,wordNum);
            if(words.size()>wordNum)
                return;
        }
    }

    public static void test(){
        Trie trie=new Trie();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(new File("d:/labrep/words.csv")));
            String line="";
            int count=0;
            while(count<1000&(line=reader.readLine())!=null){
                trie.insert(line.split(",")[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(trie.hint("flag",10));
    }
}
