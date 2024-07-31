package com.registro.escolar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private StatusProfessor StatusProfessor;

    public Professor() {
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

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public StatusProfessor getStatusProfessor() {
        return StatusProfessor;
    }

    public void setStatusProfessor(StatusProfessor StatusProfessor) {
        this.StatusProfessor = StatusProfessor;
    }
    
    
    
}
