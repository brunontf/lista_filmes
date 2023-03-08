package br.com.ada.lista_filmes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import br.com.ada.lista_filmes.dao.NoticiasDAO;
import br.com.ada.lista_filmes.model.Noticia;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {
    @Autowired
    private NoticiasDAO noticiasDAO;

    @GetMapping
    public String listarNoticias(Model model) {
        List<Noticia> noticias = noticiasDAO.getListaNoticias();
        model.addAttribute("noticias", noticias);
        return "noticias";
    }

    @GetMapping("add_noticia")
    public String novaNoticia(Model model) {
        model.addAttribute("noticia", new Noticia());
        return "add_noticia";
    }

    @PostMapping("add_noticia")
    public String addNoticia(Noticia noticia) {
        noticiasDAO.adicionar(noticia);
        return "redirect:/noticias/";
    }

    @GetMapping("/remover_noticia/{i}")
    public String removerNoticia(@PathVariable int i) {
        noticiasDAO.removerNoticia(i);
        return "redirect:/noticias/";
    }

    @GetMapping("/editar_noticia/{id}")
    public String editarNoticia(@PathVariable int id, Model model) {
        Noticia noticia =noticiasDAO.buscarPorId(id);
        model.addAttribute("noticia", noticia);
        return "editar_noticia";
    }

    @PostMapping("/editar_noticia")
    public String atualizarNoticia(Noticia noticia) {
        noticiasDAO.atualizarNoticia(noticia);
        return "redirect:/noticias/";
    }

    @GetMapping("/salvar_lista")
    public String salvarListaNoticias() throws StreamWriteException, DatabindException, IOException {
        noticiasDAO.salvarNoticiasJson();
        return "redirect:/noticias/";
    }
    
    @GetMapping("/carregar_lista")
    public String carregarListaNoticias() throws StreamWriteException, DatabindException, IOException {
        noticiasDAO.carregarNoticiasJson();
        return "redirect:/noticias/";
    }
}
