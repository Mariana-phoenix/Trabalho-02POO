package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Notas {
    public abstract String adicionarAlunos();
    public abstract void NotaDoAluno() throws FileNotFoundException, IOException;
    public abstract String gabarito();
    public abstract int aluno(String gabaritoOficial, String gabaritoDoAluno);
}
