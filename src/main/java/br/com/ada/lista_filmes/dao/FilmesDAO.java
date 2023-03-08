package br.com.ada.lista_filmes.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.ada.lista_filmes.model.Filme;

@Component
public class FilmesDAO {
    private static List<Filme> listaFilmes = new ArrayList<>();
    private static List<Filme> listaFilmesFavoritos = new ArrayList<>();
    private static int id = 1;
    private static ObjectMapper objectMapper = new ObjectMapper();

    FilmesDAO() throws StreamReadException, DatabindException, IOException{
        carregarJson();
        carregarJsonFavoritos();
    }

    public void adicionar(Filme filme) {
        filme.setId(id++);
        filme.setLike(0);
        listaFilmes.add(filme);
    }

    public List<Filme> getListaFilmes(){
        sortListByLikes();
        return listaFilmes;
    }

    private void sortListByLikes() {
        listaFilmes.sort(Comparator.comparing(Filme::getLike).reversed());
    }

    public List<Filme> getListaFilmesFavoritos() {
        List<Filme> listaAtualizada = new ArrayList<>();
        for (Filme filme : listaFilmes) {
            for (Filme filmeFavorito : listaFilmesFavoritos) {
                if(filmeFavorito.getId()==filme.getId()){
                    listaAtualizada.add(filme);
                }
            }
        }
        listaFilmesFavoritos=listaAtualizada;
        return listaFilmesFavoritos;
    }

    public void salvarJson() throws StreamWriteException, DatabindException, IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("src/main/resources/json/lista_filmes.json"), listaFilmes);
    }

    public void carregarJson() throws StreamReadException, DatabindException, IOException {
        List<Filme> filmes = objectMapper.readValue(new File("src/main/resources/json/lista_filmes.json"), new TypeReference<List<Filme>>(){});
        listaFilmes = filmes;
        if(filmes.size()>0){
            id = filmes.get(filmes.size()-1).getId()+1;
        }
    }

    public void salvarJsonFavoritos() throws StreamWriteException, DatabindException, IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("src/main/resources/json/lista_filmes_favoritos.json"), listaFilmesFavoritos);
    }

    public void carregarJsonFavoritos() throws StreamReadException, DatabindException, IOException {
        List<Filme> filmes = objectMapper.readValue(new File("src/main/resources/json/lista_filmes_favoritos.json"), new TypeReference<List<Filme>>(){});
        listaFilmesFavoritos = filmes;
        if(filmes.size()>0){
            id = filmes.get(filmes.size()-1).getId()+1;
        }
    }

    public void atualizarFilme(Filme filme) {
        for (int i = 0; i < listaFilmes.size(); i++) {
            Filme f = listaFilmes.get(i);
            if (f.getId() == filme.getId()) {
                listaFilmes.set(i, filme);
                break;
            }
        }
    }

    public Filme buscarPorId(int id) {
        return listaFilmes.stream().filter(f->f.getId() == id).findFirst().orElse(null);
    }

    public void removerFilme(int id) {
        listaFilmes.removeIf(f->f.getId() == id);
    }

    public void likeUp(int id) {
        Filme filme = buscarPorId(id);
        filme.setLike(filme.getLike()+1);
    }
    
    public void likeDown(int id) {
        Filme filme = buscarPorId(id);
        filme.setLike(filme.getLike()-1);
    }

    public void adicionarFilmeFavorito(int id) {
        Filme filme = buscarPorId(id);
        if(!listaFilmesFavoritos.stream().anyMatch(f-> f.getId() == filme.getId())){
            listaFilmesFavoritos.add(filme);
        }
    }
    
    public void removerFilmeFavorito(int id) {
        Filme filme = buscarPorId(id);
        if(listaFilmesFavoritos.stream().anyMatch(f-> f.getId() == filme.getId())){
            listaFilmesFavoritos.remove(filme);
        }
    }
    
}
