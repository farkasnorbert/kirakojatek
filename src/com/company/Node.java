package com.company;

public class Node {
    public Node parent;
    public int[][] table;

    public Node(Node parent, int[][] table) {
        this.parent = parent;
        this.table = new int[table.length][];
        for(int i=0; i<table.length; i++){
            this.table[i] = table[i].clone();
        }
    }
}
