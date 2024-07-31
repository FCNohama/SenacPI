package com.registro.escolar.controller;

import com.registro.escolar.dto.NovoAluno;
import com.registro.escolar.model.Aluno;
import com.registro.escolar.model.PeriodoAluno;
import com.registro.escolar.repository.AlunoRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/alunos")
public class AlunoController {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("")
    public ModelAndView index() {
        List<Aluno> alunos = this.alunoRepository.findAll();
        ModelAndView mv = new ModelAndView("alunos/index");
        mv.addObject("alunos", alunos);
        return mv;
    }
    
    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("alunos/cadastro");
        mv.addObject("novoAluno", new NovoAluno()); // Adicione o objeto novoAluno ao modelo
        mv.addObject("listaPeriodoAluno", PeriodoAluno.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid @ModelAttribute NovoAluno na, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("alunos/cadastro");
            mv.addObject("listaPeriodoAluno", PeriodoAluno.values());
            return mv;
        } else {
            Aluno aluno = na.toAluno();
            this.alunoRepository.save(aluno);
            return new ModelAndView("redirect:/alunos");
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        Optional<Aluno> optional = this.alunoRepository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();
            ModelAndView mv = new ModelAndView("alunos/show");
            mv.addObject("aluno", aluno);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("alunos/erro");
            mv.addObject("message", "Aluno não encontrado.");
            return mv;
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, NovoAluno requisicao) {
        Optional<Aluno> optional = alunoRepository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();
            requisicao.fromAluno(aluno);

            ModelAndView mv = new ModelAndView("alunos/edit");
            mv.addObject("alunoId", aluno.getId());
            mv.addObject("listaPeriodoAluno", PeriodoAluno.values());

            return mv;
        } else {
            ModelAndView mv = new ModelAndView("alunos/erro");
            mv.addObject("message", "Aluno não encontrado.");
            return mv;
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid NovoAluno requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("alunos/edit");
            mv.addObject("alunoId", id);
            mv.addObject("listaPeriodoAluno", PeriodoAluno.values());
            return mv;
        } else {
            Optional<Aluno> optional = this.alunoRepository.findById(id);

            if (optional.isPresent()) {
                Aluno aluno = requisicao.toAluno(optional.get());
                this.alunoRepository.save(aluno);

                return new ModelAndView("redirect:/alunos/" + aluno.getId());
            } // não achou um registro na tabela Aluno com o id informado
            else {
                System.out.println("############ NÃO ACHOU O ALUNO DE ID " + id + " ############");
                return this.retornaErroAluno("UPDATE ERROR: Aluno #" + id + " não encontrado no banco!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/alunos");

        try {
            this.alunoRepository.deleteById(id);
            mv.addObject("mensagem", "Aluno #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroAluno("DELETE ERROR: Aluno #" + id + " não encontrado no banco!");
        }

        return mv;
    }

    private ModelAndView retornaErroAluno(String msg) {
        ModelAndView mv = new ModelAndView("redirect:/alunos");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }
}
