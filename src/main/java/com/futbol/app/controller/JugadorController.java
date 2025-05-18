package com.futbol.app.controller;

import com.futbol.app.model.Jugador;
import com.futbol.app.repository.JugadorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorRepository jugadorRepository;

    public JugadorController(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    // Mostrar lista de jugadores
    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("jugadores", jugadorRepository.findAll());
        if (error != null) {
            model.addAttribute("error", "Error al guardar el jugador. Verifica los datos.");
        }
        return "jugadores/lista";
    }

    // Formulario nuevo jugador
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("jugador", new Jugador());
        return "jugadores/formulario";
    }

    // Guardar jugador
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Jugador jugador) {
        if (jugador.getNombre() == null || jugador.getNombre().isBlank()) {
            return "redirect:/jugadores?error=nombre";
        }
        jugadorRepository.save(jugador);
        return "redirect:/jugadores";
    }

    // Formulario editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Jugador jugador = jugadorRepository.findById(id).orElseThrow();
        model.addAttribute("jugador", jugador);
        return "jugadores/formulario";
    }

    // Eliminar jugador
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        jugadorRepository.deleteById(id);
        return "redirect:/jugadores";
    }
}
