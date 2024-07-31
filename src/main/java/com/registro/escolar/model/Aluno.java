package com.registro.escolar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    private int Ano;
    @Enumerated(EnumType.STRING)
    private PeriodoAluno PeriodoAluno;    

    public Aluno() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int Ano) {
        this.Ano = Ano;
    }

    public PeriodoAluno getPeriodoAluno() {
        return PeriodoAluno;
    }

    public void setPeriodoAluno(PeriodoAluno PeriodoAluno) {
        this.PeriodoAluno = PeriodoAluno;
    }

    
}
