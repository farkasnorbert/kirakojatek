package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class Main {

    private static String file="";
    private static boolean solseq = false;
    private static boolean pcost = false;
    private static boolean nvisited = false;
    private static int H=0;
    private static int N;
    private static int M;
    private static int [][] T;
    private static int n;
    private static int [][]Tend;
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
    }
    private static void beolvas(){
        Scanner in = new Scanner(System.in);
        System.out.println("Tabla merete: ");
        n = in.nextInt();
        T=new int [n][n];
        Tend=new int [n][n];
        int l=0;
        for(int i=0; i<n;i++){
            for(int j=0; j<n; j++){
                Tend[i][j]=l;
                l++;
            }
        }
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
    }
    private static void kiir(int [][]t, int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(t[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    private boolean comp(int [][]t1, int [][]t2){
        for(int i = 0; i<t1.length;i++){
            for(int j=0; j<t1.length;i++){
                if(t1[i][j]!=t2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
