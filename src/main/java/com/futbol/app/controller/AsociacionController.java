package com.futbol.app.controller;

import com.futbol.app.model.Asociacion;
import com.futbol.app.repository.AsociacionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asociaciones")
public class AsociacionController {

    private final AsociacionRepository asociacionRepository;

    public AsociacionController(AsociacionRepository asociacionRepository) {
        this.asociacionRepository = asociacionRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        return "asociaciones/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("asociacion", new Asociacion());
        return "asociaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Asociacion asociacion) {
        asociacionRepository.save(asociacion);
        return "redirect:/asociaciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("asociacion", asociacionRepository.findById(id).orElseThrow());
        return "asociaciones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        asociacionRepository.deleteById(id);
        return "redirect:/asociaciones";
    }
}
