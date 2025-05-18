package com.futbol.app.controller;

import com.futbol.app.model.Entrenador;
import com.futbol.app.model.Club;
import com.futbol.app.repository.EntrenadorRepository;
import com.futbol.app.repository.ClubRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorController {

    private final EntrenadorRepository entrenadorRepository;
    private final ClubRepository clubRepository;

    public EntrenadorController(EntrenadorRepository entrenadorRepository, ClubRepository clubRepository) {
        this.entrenadorRepository = entrenadorRepository;
        this.clubRepository = clubRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        return "entrenadores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoEntrenador(Model model) {
        List<Club> clubes = clubRepository.findAll();
        if (clubes.isEmpty()) {
            return "redirect:/?error=sinClubes";
        }

        model.addAttribute("entrenador", new Entrenador());
        model.addAttribute("clubes", clubes);
        return "entrenadores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Entrenador entrenador) {
        entrenadorRepository.save(entrenador);
        return "redirect:/entrenadores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Entrenador entrenador = entrenadorRepository.findById(id).orElseThrow();
        model.addAttribute("entrenador", entrenador);
        model.addAttribute("clubes", clubRepository.findAll());
        return "entrenadores/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        entrenadorRepository.deleteById(id);
        return "redirect:/entrenadores";
    }
}
