package br.com.ada.lista_filmes.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import br.com.ada.lista_filmes.dao.FilmesDAO;
import br.com.ada.lista_filmes.model.Filme;

@Controller
@RequestMapping("/")
public class FilmesController {
    @Autowired
    private FilmesDAO filmesDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista_filmes", filmesDAO.getListaFilmes() );
        return "index";
    }

    @GetMapping("/add_filme")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "add_filme";
    }

    @PostMapping("/add_filme")
    public String addFilme(Filme filme) {
        filmesDAO.adicionar(filme);
        return "redirect:/";
    }

    @GetMapping("/editar_filme/{id}")
    public String editarFilme(@PathVariable int id, Model model) {
        Filme filme =filmesDAO.buscarPorId(id);
        model.addAttribute("filme", filme);
        return "editar_filme";
    }

    @PostMapping("editar_filme")
    public String atualizarFilme(Filme filme) {
        filmesDAO.atualizarFilme(filme);
        return "redirect:/";
    }

    @GetMapping("remover_filme/{id}")
    public String removerFilme(@PathVariable int id) {
        filmesDAO.removerFilme(id);
        return "redirect:/";
    }

    @GetMapping("like_up/{id}")
    public String likeUp(@PathVariable int id) {
        filmesDAO.likeUp(id);
        return "redirect:/";
    }

    @GetMapping("like_down/{id}")
    public String likeDown(@PathVariable int id) {
        filmesDAO.likeDown(id);
        return "redirect:/";
    }

    @GetMapping("/filmes_favoritos")
    public String listarFilmesFavoritos(Model model) {
        model.addAttribute("lista_filmes_favoritos", filmesDAO.getListaFilmesFavoritos());
        return "filmes_favoritos";
    }

    @GetMapping("/add_favorito/{id}")
    public String addFilmeFavorito(@PathVariable int id) {
        filmesDAO.adicionarFilmeFavorito(id);
        return "redirect:/";
    }

    @GetMapping("remover_favorito/{id}")
    public String removerFilmeFavorito(@PathVariable int id) {
        filmesDAO.removerFilmeFavorito(id);
        return "redirect:/filmes_favoritos";
    }

    @GetMapping("salvar_lista")
    public String salvarListaFilmes() throws StreamWriteException, DatabindException, IOException {
        filmesDAO.salvarJson();
        return "redirect:/";
    }
    
    @GetMapping("carregar_lista")
    public String carregarListaFilmes() throws StreamWriteException, DatabindException, IOException {
        filmesDAO.carregarJson();
        return "redirect:/";
    }

}
