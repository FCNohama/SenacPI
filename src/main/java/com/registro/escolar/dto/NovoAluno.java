package com.registro.escolar.dto;

import com.registro.escolar.model.Aluno;
import com.registro.escolar.model.PeriodoAluno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class NovoAluno {
    
    @NotBlank
    @NotNull
    private String nome;
    private PeriodoAluno periodoAluno;
    @NotNull
    private int ano;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PeriodoAluno getPeriodoAluno() {
        return periodoAluno;
    }

    public void setPeriodoAluno(PeriodoAluno periodoAluno) {
        this.periodoAluno = periodoAluno;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    
    public Aluno toAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome(this.nome);
        aluno.setAno(this.ano);
        aluno.setPeriodoAluno(this.periodoAluno);

        return aluno;
    }

    public Aluno toAluno(Aluno aluno) {
        aluno.setNome(this.nome);
        aluno.setNome(this.nome);
        aluno.setAno(this.ano);
        aluno.setPeriodoAluno(this.periodoAluno);

        return aluno;
    }

    public void fromAluno(Aluno aluno) {
        this.nome = aluno.getNome();
        this.ano = aluno.getAno();
        this.periodoAluno = aluno.getPeriodoAluno();
    }
    
}
