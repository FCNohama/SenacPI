package com.registro.escolar.controller;

import com.registro.escolar.dto.NovoProfessor;
import com.registro.escolar.model.Professor;
import com.registro.escolar.model.StatusProfessor;
import com.registro.escolar.repository.ProfessorRepository;
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
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("")
    public ModelAndView index() {
        List<Professor> professores = this.professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("professores/cadastro");
        mv.addObject("novoProfessor", new NovoProfessor()); // Adicione o objeto novoProfessor ao modelo
        mv.addObject("listaStatusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid @ModelAttribute NovoProfessor np, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/cadastro");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Professor professor = np.toProfessor();
            this.professorRepository.save(professor);
            return new ModelAndView("redirect:/professores");
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()) {
            Professor professor = optional.get();
            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("professores/erro");
            mv.addObject("message", "Professor não encontrado.");
            return mv;
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, NovoProfessor requisicao) {
        Optional<Professor> optional = professorRepository.findById(id);

        if (optional.isPresent()) {
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);

            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", professor.getId());
            mv.addObject("listaStatusProfessor", StatusProfessor.values());

            return mv;
        } else {
            ModelAndView mv = new ModelAndView("professores/erro");
            mv.addObject("message", "Professor não encontrado.");
            return mv;
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid NovoProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Optional<Professor> optional = this.professorRepository.findById(id);

            if (optional.isPresent()) {
                Professor professor = requisicao.toProfessor(optional.get());
                this.professorRepository.save(professor);

                return new ModelAndView("redirect:/professores/" + professor.getId());
            } // não achou um registro na tabela Professor com o id informado
            else {
                System.out.println("############ NÃO ACHOU O PROFESSOR DE ID " + id + " ############");
                return this.retornaErroProfessor("UPDATE ERROR: Professor #" + id + " não encontrado no banco!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/professores");

        try {
            this.professorRepository.deleteById(id);
            mv.addObject("mensagem", "Professor #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroProfessor("DELETE ERROR: Professor #" + id + " não encontrado no banco!");
        }

        return mv;
    }

    private ModelAndView retornaErroProfessor(String msg) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }
}
