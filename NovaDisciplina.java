package com.company;
import java.io.*;
import java.util.*;

public class NovaDisciplina implements Notas {
    public static String TRUE = "V";
    public static String FALSE = "F";
    public  static File f = new File("Disciplina");
    public int divisao = 0;
    public String name;
    public String gabaritoOficial;
    public ArrayList<String> novalinha = new ArrayList<>();
    public ArrayList<Integer> nota = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public NovaDisciplina() throws IOException {
        System.out.print("Digite o nome da Disciplina nova: ");
        String nova_disc = scanner.next();
        this.name = nova_disc;
        File dir = new File("Disciplina");
        //dir.mkdir();
        File arq = new File(dir, nova_disc + ".txt");
        FileWriter f = new FileWriter(arq);
        //   String[] arqs = dir.list();
       /* for(int i = 0; i < arqs.length;i++){
            File filho = new File(dir, arqs[i]);
            System.out.println(filho.getAbsolutePath());
        }*/
        BufferedWriter bw = new BufferedWriter(f);
        PrintWriter pw = new PrintWriter(bw);
        System.out.print("Deseja adicionar gabarito?\n0-Sim\n1-Nao\n>>> ");
        int adicionarGabarito = scanner.nextInt();
        if(adicionarGabarito == 0) {
            String gabarito = gabarito();
            pw.println(gabarito);
        }else{
            String gabarito;
            String[] val = new String[10];
            for(int i = 0; i < 10;i++){
                val[i] =random();
            }
            gabarito = val[0].concat(val[1]).concat(val[2]).concat(val[3]).concat(val[4]).concat(val[5]).concat(val[6]).concat(val[7]).concat(val[8]).concat(val[9]);
            pw.println(gabarito);
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
                pw.println(alunos);
            }
            else if(adicionarAlunos == 1) mais1 = 1;
            soma +=1;
            System.out.println("Inserir mais alunos?\n0-Sim\n1-Não");
            mais1 = scanner.nextInt();
        }
        pw.close();
        bw.close();
        f.close();
        NotaDoAluno();
        arquivoEmOrdemCrescente();
        arquivoEmOrdemDescrescente();
    }

    @Override
    public String gabarito() {
        int i = 0;
        while (i == 0){
            System.out.print("Gabarito: ");
            String gab = scanner.next();
            try {
                for (String ari : gab.split("")) {
                    if (ari.equals(TRUE) || ari.equals(FALSE)) {
                        i = 1;
                    }
                }
            } catch (Exception e) {
                System.out.println("Gabarito não coicinde com a questao");
                i = 0;
            }
            if (gab.length() != 10) {
                System.out.println("Gabarito não coicinde com a quantidade de questao");
                i = 0;
            }
            if (i == 1) {
                gabaritoOficial = gab;
            }
        }
        return gabaritoOficial;
    }

    public String random(){
        Random random = new Random();
        char[] vOUf = "VF".toCharArray();
        StringBuilder stringBuilder = new StringBuilder(10);
        for (int i = 0; i < 10;i++){
            stringBuilder.append(vOUf[random.nextInt(vOUf.length)]);
            return stringBuilder.toString();
        }
        return null;
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
    public void  NotaDoAluno() {
        String linhaNova;
        try {
            File arq = new File(f,name+".txt");
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            int soma = 0;
            while((linha = br.readLine()) != null){
                if(soma == 0){
                    gabaritoOficial = linha;
                }else{
                    String gabaritoDoAluno = linha.substring(0,10);
                    String nomeDoAluno = linha.substring(11);
                    int notas = aluno(gabaritoOficial,gabaritoDoAluno);
                    nota.add(notas);
                    linhaNova = nota+"\t"+gabaritoDoAluno+"\t"+nomeDoAluno;
                    System.out.println(linhaNova);
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
             for(int i = 0; i < gabO.length;i++){
                 String gA = gabA[i].split("")[1];
                 String gO = gabO[i].split("")[1];
                 for (int j = 0; j < gO.length();j++){
                     if (gA.charAt(j) == gO.charAt(j)){
                         deucerto++;
                     }
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
        return soma == 10;
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
        FileWriter fw = new FileWriter(arq);
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

