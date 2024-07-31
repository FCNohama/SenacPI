package com.registro.escolar.dto;

import com.registro.escolar.model.Professor;
import com.registro.escolar.model.StatusProfessor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class NovoProfessor {

    @NotBlank
    @NotNull
    private String nome;
    private StatusProfessor StatusProfessor;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal salario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusProfessor getStatusProfessor() {
        return StatusProfessor;
    }

    public void setStatusProfessor(StatusProfessor StatusProfessor) {
        this.StatusProfessor = StatusProfessor;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Professor toProfessor() {
        Professor professor = new Professor();
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.StatusProfessor);

        return professor;
    }

    public Professor toProfessor(Professor professor) {
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.StatusProfessor);
        return professor;
    }

    public void fromProfessor(Professor professor) {
        this.nome = professor.getNome();
        this.salario = professor.getSalario();
        this.StatusProfessor = professor.getStatusProfessor();
    }
}
