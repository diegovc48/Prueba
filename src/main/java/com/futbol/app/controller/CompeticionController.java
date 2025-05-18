package com.futbol.app.controller;

import com.futbol.app.model.Competicion;
import com.futbol.app.repository.CompeticionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/competiciones")
public class CompeticionController {

    private final CompeticionRepository competicionRepository;

    public CompeticionController(CompeticionRepository competicionRepository) {
        this.competicionRepository = competicionRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "competiciones/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("competicion", new Competicion());
        return "competiciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Competicion competicion) {
        competicionRepository.save(competicion);
        return "redirect:/competiciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("competicion", competicionRepository.findById(id).orElseThrow());
        return "competiciones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        competicionRepository.deleteById(id);
        return "redirect:/competiciones";
    }
}
