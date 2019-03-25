package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static String file="";
    private static boolean solseq = false;
    private static boolean pcost = false;
    private static boolean nvisited = false;
    private static int H=1;
    private static int cost;
    private static int visited=0;
    private static int N;
    private static int M;
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            switch (s) {
                case "-input":
                    if(i+1==args.length){
                        System.out.println("Hiba nincs file");
                        return;
                    }
                    file =args[i+1];
                    break;
                case "–solseq":
                    solseq=true;
                    break;
                case "–pcost":
                    pcost=true;
                    break;
                case "–nvisited":
                    nvisited=true;
                    break;
                case "–h":
                    if(i+1==args.length){
                        System.out.println("hiba nincs szam");
                        return;
                    }
                    H=Integer.parseInt(args[i+1]);
                    break;
                case "–rand":
                    if(i+1==args.length){
                        System.out.println("hiba nincs szam");
                        return;
                    }
                    if(i+2==args.length){
                        System.out.println("hiba nincs szam");
                        return;
                    }
                    N=Integer.parseInt(args[i+1]);
                    M=Integer.parseInt(args[i+2]);
                    break;
                default:
                    break;
            }
        }
        int [][] T=initialize();
        int [][] goal=initializeEND(T.length);
        Node start = new Node(null,T,0);
        if(solution(start,goal)){
            System.out.println("Sikerult");
            if(pcost){
                System.out.print("Koltseg: ");
                System.out.println(cost);
            }
            if(nvisited){
                System.out.print("Meglatogatott: ");
                System.out.println(visited);
            }
        }else {
            System.out.println("Nem sikerult");
        }
    }

    private static boolean solution(Node start,int [][] goal) {
        List<Node> Open = new ArrayList<>();
        List<Node> Closed = new ArrayList<>();
        Open.add(start);
        int n;
        while(!Open.isEmpty()){
            visited++;
            n=lowestf(Open);
            Closed.add(Open.get(n));
            Node nNode = Open.get(n);
            Open.remove(n);
            if(calculateCost(nNode)==0){
                cost=nNode.cost;
                if(solseq){
                    printP(nNode);
                }
                return true;
            }
            List<Node> s = new ArrayList<>();
            gSuc(s,nNode);
            for (Node i:s) {
                i.parent = nNode;
                i.cost = i.parent.cost+1;
                int x=inList(Open,i);
                if(x!=-1 && Open.get(x).cost<=i.cost){
                    continue;
                }
                x=inList(Closed,i);
                if(x!=-1 && Closed.get(x).cost<=i.cost){
                    continue;
                }
                remove(i,Open,Closed);
                Open.add(i);
            }
        }
        return false;
    }

    private static void remove(Node i, List<Node> open, List<Node> closed) {
        int x =inList(open,i);
        while (x!=-1){
            open.remove(x);
            x =inList(open,i);
        }
        x=inList(closed,i);
        while (x!=-1){
            closed.remove(x);
            x=inList(closed,i);
        }
    }

    private static int inList(List<Node> l, Node n) {
        for (int i=0; i<l.size(); i++) {
            if(n.Same(l.get(i))){
                return i;
            }
        }
        return -1;
    }


    private static void gSuc(List<Node> s, Node node) {
        int ei=0;
        int ej=0;
        int n = node.table.length;
        int [][] t = node.table;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(t[i][j]==0){
                    ei=i;
                    ej=j;
                    break;
                }
            }
        }
        if(ei==0 && ej==0){
            Node n2 = new Node(null,node.table,0);
            n2.table[ei][ej]=n2.table[ei+1][ej];
            n2.table[ei+1][ej]=0;
            Node n3 = new Node(null,node.table,0);
            n3.table[ei][ej]=n3.table[ei][ej+1];
            n3.table[ei][ej+1]=0;
            s.add(n2);
            s.add(n3);
        }else {
            if(ei==0 && ej==n-1){
                Node n2 = new Node(null,node.table,0);
                n2.table[ei][ej]=n2.table[ei+1][ej];
                n2.table[ei+1][ej]=0;
                Node n3 = new Node(null,node.table,0);
                n3.table[ei][ej]=n3.table[ei][ej-1];
                n3.table[ei][ej-1]=0;
                s.add(n2);
                s.add(n3);
            }else{
                if(ei==n-1 && ej==0){
                    Node n2 = new Node(null,node.table,0);
                    n2.table[ei][ej]=n2.table[ei-1][ej];
                    n2.table[ei-1][ej]=0;
                    Node n3 = new Node(null,node.table,0);
                    n3.table[ei][ej]=n3.table[ei][ej+1];
                    n3.table[ei][ej+1]=0;
                    s.add(n2);
                    s.add(n3);
                }else{
                    if(ei==ej && ej==n-1){
                        Node n2 = new Node(null,node.table,0);
                        n2.table[ei][ej]=n2.table[ei-1][ej];
                        n2.table[ei-1][ej]=0;
                        Node n3 = new Node(null,node.table,0);
                        n3.table[ei][ej]=n3.table[ei][ej-1];
                        n3.table[ei][ej-1]=0;
                        s.add(n2);
                        s.add(n3);
                    }else{
                        if(ei==0){
                            Node n2 = new Node(null,node.table,0);
                            n2.table[ei][ej]=n2.table[ei+1][ej];
                            n2.table[ei+1][ej]=0;
                            Node n3 = new Node(null,node.table,0);
                            n3.table[ei][ej]=n3.table[ei][ej+1];
                            n3.table[ei][ej+1]=0;
                            Node n4 = new Node(null,node.table,0);
                            n4.table[ei][ej]=n4.table[ei][ej-1];
                            n4.table[ei][ej-1]=0;
                            s.add(n2);
                            s.add(n3);
                            s.add(n4);
                        }else{
                            if(ej==0){
                                Node n2 = new Node(null,node.table,0);
                                n2.table[ei][ej]=n2.table[ei+1][ej];
                                n2.table[ei+1][ej]=0;
                                Node n3 = new Node(null,node.table,0);
                                n3.table[ei][ej]=n3.table[ei][ej+1];
                                n3.table[ei][ej+1]=0;
                                Node n4 = new Node(null,node.table,0);
                                n4.table[ei][ej]=n4.table[ei-1][ej];
                                n4.table[ei-1][ej]=0;
                                s.add(n2);
                                s.add(n3);
                                s.add(n4);
                            }else{
                                if(ei==n-1){
                                    Node n2 = new Node(null,node.table,0);
                                    n2.table[ei][ej]=n2.table[ei-1][ej];
                                    n2.table[ei-1][ej]=0;
                                    Node n3 = new Node(null,node.table,0);
                                    n3.table[ei][ej]=n3.table[ei][ej+1];
                                    n3.table[ei][ej+1]=0;
                                    Node n4 = new Node(null,node.table,0);
                                    n4.table[ei][ej]=n4.table[ei-1][ej];
                                    n4.table[ei-1][ej]=0;
                                    s.add(n2);
                                    s.add(n3);
                                    s.add(n4);
                                }else{
                                    if(ej==n-1){
                                        Node n2 = new Node(null,node.table,0);
                                        n2.table[ei][ej]=n2.table[ei+1][ej];
                                        n2.table[ei+1][ej]=0;
                                        Node n3 = new Node(null,node.table,0);
                                        n3.table[ei][ej]=n3.table[ei][ej-1];
                                        n3.table[ei][ej-1]=0;
                                        Node n4 = new Node(null,node.table,0);
                                        n4.table[ei][ej]=n4.table[ei-1][ej];
                                        n4.table[ei-1][ej]=0;
                                        s.add(n2);
                                        s.add(n3);
                                        s.add(n4);
                                    }else{
                                        Node n2 = new Node(null,node.table,0);
                                        n2.table[ei][ej]=n2.table[ei+1][ej];
                                        n2.table[ei+1][ej]=0;
                                        Node n3 = new Node(null,node.table,0);
                                        n3.table[ei][ej]=n3.table[ei][ej+1];
                                        n3.table[ei][ej+1]=0;
                                        Node n4 = new Node(null,node.table,0);
                                        n4.table[ei][ej]=n4.table[ei-1][ej];
                                        n4.table[ei-1][ej]=0;
                                        Node n5 = new Node(null,node.table,0);
                                        n5.table[ei][ej]=n5.table[ei][ej-1];
                                        n5.table[ei][ej-1]=0;
                                        s.add(n2);
                                        s.add(n3);
                                        s.add(n4);
                                        s.add(n5);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static int lowestf(List<Node> o){
        int n = 0;
        int c = calculateCost(o.get(0));
        for (int i = 1; i<o.size(); i++) {
            int x = calculateCost(o.get(i));
            if(x<c){
                n=i;
                c=x;
            }
        }
        return n;
    }

    private static int calculateCost(Node n) {
        int [][]t = n.table;
        switch (H){
            case 1:
                return WrongPositions(t);
            case 2:
                return Manhattan(t);
        }
        return 0;
    }

    private static int WrongPositions(int[][] t) {
        int count = 0;
        int n = t.length;
        int c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] != 0 && t[i][j] != n * i + j)
                {
                    count++;
                }
                c++;
            }
        }

        return count;
    }

    private static int Manhattan(int[][] t) {
        int c = 0;

        int n = t.length;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                int value = t[i][j];
                if (value == 0) continue;
                int x = -1;
                int y = -1;
                x = value / n;
                y = value % n;
                int dist = ManhattanDistance(i, j, x, y);
                c += dist;
            }
        }

        return c;
    }

    private static int ManhattanDistance(int x1, int y1, int x2, int y2)
    {
        int x = Math.abs(x1 - x2);
        int y = Math.abs(y1 - y2);
        return x + y;
    }

    private static int [][] initialize(){
        Scanner in = new Scanner(System.in);
        System.out.println("Tabla merete: ");
        int n = in.nextInt();
        int [][] T=new int [n][n];
        if(file.equals("")){
            System.out.println("Kezdoalapot megadasa, 0 az ures");
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    T[i][j]=in.nextInt();
                }
            }
        }else{
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
                while(sc.hasNextLine()) {
                    for (int i=0; i<n; i++) {
                        String[] line = sc.nextLine().trim().split(" ");
                        for (int j=0; j<n; j++) {
                            T[i][j] = Integer.parseInt(line[j]);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return T;
    }
    private static int [][]initializeEND(int n){
        int [][] Tend=new int [n][n];
        int l=0;
        for(int i=0; i<n;i++){
            for(int j=0; j<n; j++){
                Tend[i][j]=l;
                l++;
            }
        }
        return Tend;
    }
    private static void printM(int [][]t, int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(t[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    private static  void  printP(Node m){
        if(m==null){
            return;
        }
        printP(m.parent);
        printM(m.table,m.table.length);
        System.out.println();
    }
    private static boolean comp(int [][]t1, int [][]t2){
        for(int i = 0; i<t1.length;i++){
            for(int j=0; j<t1[i].length;i++){
                if(t1[i][j]!=t2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
