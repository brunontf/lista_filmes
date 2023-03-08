package br.com.ada.lista_filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ada.lista_filmes.dao.FilmesDAO;
import br.com.ada.lista_filmes.dao.NoticiasDAO;

@Controller
@RequestMapping
public class IndexController {
    @Autowired
    private FilmesDAO filmesDAO;
    @Autowired
    private NoticiasDAO noticiasDAO;

    @GetMapping
    public String listarNoticias(Model model){
        model.addAttribute("lista_filmes", filmesDAO.getListaFilmes().subList(0, 2));
        model.addAttribute("noticias", noticiasDAO.getListaNoticias().subList(0, 2));
        return "index";
    }
}
