package com.futbol.app.controller;

import com.futbol.app.model.Club;
import com.futbol.app.model.Entrenador;
import com.futbol.app.model.Jugador;
import com.futbol.app.repository.ClubRepository;
import com.futbol.app.repository.EntrenadorRepository;
import com.futbol.app.repository.AsociacionRepository;
import com.futbol.app.repository.CompeticionRepository;
import com.futbol.app.repository.JugadorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clubes")
public class ClubController {

    private final ClubRepository clubRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final AsociacionRepository asociacionRepository;
    private final CompeticionRepository competicionRepository;
    private final JugadorRepository jugadorRepository;

    public ClubController(ClubRepository clubRepository,
                          EntrenadorRepository entrenadorRepository,
                          AsociacionRepository asociacionRepository,
                          CompeticionRepository competicionRepository,
                          JugadorRepository jugadorRepository) {
        this.clubRepository = clubRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.asociacionRepository = asociacionRepository;
        this.competicionRepository = competicionRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @GetMapping
    public String listarClubes(Model model) {
        model.addAttribute("clubes", clubRepository.findAll());
        return "clubes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        Club club = new Club();
        club.setJugadores(List.of());
        model.addAttribute("club", club);

        // Filtrar entrenadores disponibles
        List<Entrenador> entrenadoresDisponibles = entrenadorRepository.findAll()
                .stream()
                .filter(e -> e.getClub() == null)
                .collect(Collectors.toList());

        // Filtrar jugadores disponibles (no asignados a ningún club)
        List<Jugador> jugadoresDisponibles = jugadorRepository.findAll()
                .stream()
                .filter(j -> j.getClub() == null)
                .collect(Collectors.toList());

        model.addAttribute("entrenadores", entrenadoresDisponibles);
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
        model.addAttribute("jugadores", jugadoresDisponibles);
        return "clubes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarClub(@ModelAttribute Club club,
                              @RequestParam(required = false) List<Long> jugadorIds) {

        clubRepository.save(club); // Guarda el club y obtiene su ID

        // Desasociar jugadores actualmente asignados a este club
        List<Jugador> todos = jugadorRepository.findAll();
        for (Jugador j : todos) {
            if (j.getClub() != null && j.getClub().getId().equals(club.getId())) {
                j.setClub(null);
                jugadorRepository.save(j);
            }
        }

        // Asociar los nuevos seleccionados
        if (jugadorIds != null) {
            for (Long id : jugadorIds) {
                Jugador jugador = jugadorRepository.findById(id).orElseThrow();
                jugador.setClub(club);
                jugadorRepository.save(jugador);
            }
        }

        return "redirect:/clubes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Club club = clubRepository.findById(id).orElseThrow();
        model.addAttribute("club", club);

        // Mostrar entrenadores no asignados o el actual del club
        List<Entrenador> entrenadoresDisponibles = entrenadorRepository.findAll()
                .stream()
                .filter(e -> e.getClub() == null || (club.getEntrenador() != null && e.getId().equals(club.getEntrenador().getId())))
                .collect(Collectors.toList());

        // Mostrar jugadores no asignados o los que ya están en este club
        List<Jugador> jugadoresDisponibles = jugadorRepository.findAll()
                .stream()
                .filter(j -> j.getClub() == null || (club.getJugadores() != null && club.getJugadores().contains(j)))
                .collect(Collectors.toList());

        model.addAttribute("entrenadores", entrenadoresDisponibles);
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
        model.addAttribute("jugadores", jugadoresDisponibles);
        return "clubes/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        clubRepository.deleteById(id);
        return "redirect:/clubes";
    }
}