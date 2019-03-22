package com.company;

import java.util.Scanner;

public class Main {

    private static String file="";
    private static boolean solseq = false;
    private static boolean pcost = false;
    private static boolean nvisited = false;
    private static int H=1;
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
    }
}
