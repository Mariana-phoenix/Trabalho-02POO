package com.company;
import java.util.Scanner;
import java.io.*;
public class Main {

    public static void main(String[] args) throws IOException {
        int verNotas;
        int ordem;
        Scanner scanner = new Scanner(System.in);
        int outro;
        do {
            System.out.print("\t\tPerfil do Professor\n");
            System.out.print("Opção a desejar:\n1-Criar Disciplina nova\n2-Infiltrar em uma disciplina\n");
            System.out.print("3-Sair\n>>> ");
            int k = scanner.nextInt();
            switch (k) {
                case 1:
                    NovaDisciplina novaDisciplina = new NovaDisciplina();
                    System.out.print("Deseja em ordem?\n0-Sim\n1-Nao\n>>> ");
                    verNotas = scanner.nextInt();
                    if(verNotas == 0){
                        System.out.print("Ver em ordem crescente ou descrescente?\n0-Crescente\n1-Descrescente\n>>>");
                        ordem = scanner.nextInt();
                        if(ordem == 0) {
                            novaDisciplina.OrdemCrescente();
                        }else{
                           novaDisciplina.OrdemDescrescente();
                        }
                    }
                    break;
                case 2:
                    InfiltrarDisciplina infiltrarDisciplina = new InfiltrarDisciplina();
                    System.out.print("Deseja ver a resolucao?\n0-Sim\n1-Nao\n>>> ");
                    verNotas = scanner.nextInt();
                    if(verNotas == 0){
                        System.out.print("Ver em ordem crescente ou descrescente?\n0-Crescente\n1-Descrescente\n>>>");
                        ordem = scanner.nextInt();
                        if(ordem == 0) {
                            infiltrarDisciplina.OrdemCrescente();
                        }else{
                            infiltrarDisciplina.OrdemDescrescente();
                        }
                    }
                    break;
                default:
            }
            System.out.print("Deseja fazer outra operação?\n0-Sim\n1-Não\n>>> ");
            outro = scanner.nextInt();
        }while (outro == 0);
        System.out.println("Acabou o programa");
    }
}
