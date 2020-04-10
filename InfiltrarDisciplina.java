package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class InfiltrarDisciplina implements Notas {
    Scanner scanner = new Scanner(System.in);
    public static File f = new File("Disciplina");
    public String name;
    public String gabaritoOficial;
    public int divisao = 0;
    private static String TRUE = "V";
    private static String FALSE = "F";
    public ArrayList<String> novalinha = new ArrayList<>();
    public ArrayList<Integer> nota = new ArrayList<>();

    public InfiltrarDisciplina() {
        try {
            String[] arqs = f.list();
            assert arqs != null;
            for(String fl: arqs) {
                System.out.println(fl);
            }
            System.out.print("Digite a Disciplina escolhida: ");
            String name = scanner.next();
            this.name = name;
            File arq = new File(f,name+".txt");
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while((linha = br.readLine()) != null){
                System.out.println(linha);
            }
            fr.close();
            br.close();
            FileWriter fw = new FileWriter(arq);
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.print("Alterar Gabarito Oficial?\n0-Sim\n1-Nao\n>>> ");
            int alterarGabaritoOficial = scanner.nextInt();
            if(alterarGabaritoOficial == 0){
                bw.write(gabarito());
                bw.newLine();
            }
            int soma = 0;
            int adicionarAlunos = 0;
            for(int mais1 = 0; mais1 != 1;) {
                if(soma == 0){
                    System.out.print("Deseja adicionar alunos?\n0-Sim\n1-Nao\n>>> ");
                    adicionarAlunos = scanner.nextInt();
                }
                if (adicionarAlunos == 0) {
                    String alunos = adicionarAlunos();
                    bw.write(alunos);
                    bw.newLine();
                }
                System.out.println("Inserir mais alunos?\n0-Sim\n1-Não\n>>> ");
                mais1 = scanner.nextInt();
                soma +=1;
            }
            bw.close();
            br.close();
            fr.close();
            fw.close();
            NotaDoAluno();
            arquivoEmOrdemCrescente();
            arquivoEmOrdemDescrescente();
        } catch (FileNotFoundException e) {
            System.out.println("Disciplina nao encontrada");
        } catch (IOException e) {
            System.out.println("Erro na leitura dos arquivos");
        }
    }

    @Override
    public String gabarito() {
        //boolean confirma = false;
        int i = 0;
        while (i == 0){
            System.out.print("Gabarito: ");
            String gab = scanner.next();
            try {
                for (String ari : gab.split("")) {
                    if (ari.equals(TRUE) || ari.equals(FALSE)) {
                        //             System.out.println(ari);
                        //              confirma = true;
                        i = 1;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Gabarito não coicinde com a questao");
                i = 0;
            }
            if (gab.length() < 10) {
                System.out.println("Gabarito não coicinde com a quantidade de questao");
                i = 0;
            }
            if (i == 1) {
                gabaritoOficial = gab;
            }
        }
        return gabaritoOficial;
    }

    @Override
    public String adicionarAlunos() {
        String gaba,rito;
        System.out.print("Digite o nome do aluno:");
        rito = scanner.next();
        gaba = gabarito();
        return gaba+"\t"+rito;
    }

    @Override
    public void NotaDoAluno() {
        String linhaNova;
        try {
            File arq = new File(f,name+".txt");
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            int soma = 0;
            while((linha = br.readLine()) != null){
                if(soma == 0){
                    this.gabaritoOficial = linha;
                }else{
                    String gabaritoDoAluno = linha.substring(0,10);
                    String nomeDoAluno = linha.substring(11);
                    int nota = aluno(gabaritoOficial,gabaritoDoAluno);
                    linhaNova = nota+"\t"+gabaritoDoAluno+"\t"+nomeDoAluno;
                    novalinha.add(linhaNova);
                }
                soma =+1;
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Disciplina nao encontrada");
        } catch (IOException e) {
            System.out.println("Erro na  leitura do arquivo " + this.name + ".txt");
        }
    }

    @Override
    public int aluno(String gabaritoOficial, String gabaritoDoAluno) {
        int nota;
        int deucerto = 0;
        String[] gabO = new String[10];
        String[] gabA = new String[10];
        for(int i = 0; i <gabO.length;i++){
            gabO[i] = gabaritoOficial.substring(i);
        }
        for(int i = 0; i <gabA.length;i++){
            gabA[i] = gabaritoDoAluno.substring(i);
        }
        if(valido(gabA)){
            return 0;
        }else {
            for (int i = 0; i < gabA.length; i++) {
                if (gabA[i].equals(gabO[i])) {
                    deucerto += 1;
                }
            }
            nota = deucerto;
        }
        return nota;
    }

    public boolean valido(String[] gabA) {
        int soma = 0;
        for (String s : gabA) {
            if (s.equals(TRUE)) {
                soma += 1;
            }
        }
        for (String s : gabA) {
            if (s.equals(FALSE)) {
                soma += 1;
            }
        }
        if (soma == 10)return true;
        return false;
    }

    public void arquivoEmOrdemCrescente() throws IOException {
        File dir = new File("Disciplina");
        File arq = new File(dir,name + "_OrdemCrescente.txt");
        FileWriter fw = new FileWriter(arq,true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        String[] nomes = compare();
        for (int i=0; i<nomes.length; i++){
            if(i == 0){pw.println(gabaritoOficial);}
            else {pw.println(nomes[i]);}
        }
        int soma = 0;
        while (nota != null){
            soma += nota.get(divisao);
            this.divisao+=1;
        }
        pw.println("Media = " +soma/divisao);
        pw.close();
        bw.close();
        fw.close();
    }

    public String[] compare(){
        String[] nomes = novalinha.toArray(new String[novalinha.size()]);
        String aux;
        for(int i = 0; i < nomes.length;i++){
            for(int j = 0; j < nomes[i].length();i++){
                 if(nomes[j].charAt(Integer.parseInt("[^0-10]*")) > nomes[i].charAt(Integer.parseInt("[^0-10]*"))) {
                     aux = nomes[i];
                     nomes[i] = nomes[j];
                     nomes[j] = aux;
                 }
            }
        }
        return nomes;
    }
    public void arquivoEmOrdemDescrescente() throws IOException {
        File dir = new File("Disciplina");
        File arq = new File(dir,name + "_OrdemDescrescente.txt");
        FileWriter fw = new FileWriter(arq,true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        String[] nomes = compare2();
        for (int i=0; i<nomes.length; i++){
            if(i == 0){pw.println(gabaritoOficial);}
            else {pw.println(nomes[i]);}
        }
        int soma = 0;
        while (nota != null){
            soma += nota.get(divisao);
            this.divisao+=1;
        }
        pw.println("Media = " +soma/divisao);
        pw.close();
        bw.close();
        fw.close();
    }

    public String[] compare2(){
        String[] nomes = novalinha.toArray(new String[novalinha.size()]);
        String aux;
        for(int i = 0; i < nomes.length;i++){
            for(int j = 0; j < nomes[i].length();i++){
                if(nomes[j].charAt(Integer.parseInt("[^0-10]*")) > nomes[i].charAt(Integer.parseInt("[^0-10]*"))) {
                    aux = nomes[i];
                    nomes[i] = nomes[j];
                    nomes[j] = aux;
                }
            }
        }
        return nomes;
    }

    public void OrdemCrescente() throws IOException {
        try {
            File arq = new File(f,name+"_OrdemCrescente.txt");
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while((linha = br.readLine()) != null){
                System.out.println(linha);
            }
            fr.close();
            br.close();
        }catch (IOException e){
            System.out.println("Erro na leitura dos arquivos");
        }
    }

    public void OrdemDescrescente() throws IOException {
        try {
            File arq = new File(f,name+"_OrdemDescrescente.txt");
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while((linha = br.readLine()) != null){
                System.out.println(linha);
            }
            fr.close();
            br.close();
        }catch (IOException e){
            System.out.println("Erro na leitura dos arquivos");
        }
    }
}
