package com.company;

public class Node {
    public Node parent;
    public int cost;
    public int[][] table;

    public Node(Node parent, int[][] table,int cost) {
        this.parent = parent;
        this.cost= cost;
        this.table = new int[table.length][];
        for(int i=0; i<table.length; i++){
            this.table[i] = table[i].clone();
        }
    }
    public boolean Same(Node node){
        int [][]x = node.table;
        int [][]y = this.table;
        int n = node.table.length;
        int m = this.table.length;

        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < m; ++j)
            {
                if (x[i][j] != y[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }
}
