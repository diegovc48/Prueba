package com.futbol.app.controller;

import com.futbol.app.repository.ClubRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ClubRepository clubRepository;

    public HomeController(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("hayClubes", clubRepository.count() > 0);
        return "index";
    }
}
