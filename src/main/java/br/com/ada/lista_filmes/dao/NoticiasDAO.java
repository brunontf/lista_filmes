package br.com.ada.lista_filmes.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.ada.lista_filmes.model.Noticia;

@Component
public class NoticiasDAO {
    private static List<Noticia> listaNoticias = new ArrayList<>();
    private static int id = 1;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public void adicionar(Noticia noticia) {
        noticia.setId(id++);
        listaNoticias.add(noticia);
    }

    public List<Noticia> getListaNoticias() {
        return listaNoticias;
    }

    public void salvarNoticiasJson() throws StreamWriteException, DatabindException, IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("src/main/resources/json/lista_noticias.json"), listaNoticias);
    }

    public void carregarNoticiasJson() throws StreamReadException, DatabindException, IOException {
        List<Noticia> noticias = objectMapper.readValue(new File("src/main/resources/json/lista_noticias.json"), new TypeReference<List<Noticia>>(){});
        listaNoticias = noticias;
        if(noticias.size()>0){
            id = noticias.get(noticias.size()-1).getId()+1;
        }
    }

    public void atualizarNoticia(Noticia noticia) {
        for (int i = 0; i < listaNoticias.size(); i++) {
            Noticia f = listaNoticias.get(i);
            if (f.getId() == noticia.getId()) {
                listaNoticias.set(i, noticia);
                break;
            }
        }
    }

    public Noticia buscarPorId(int id) {
        return listaNoticias.stream().filter(f->f.getId() == id).findFirst().orElse(null);
    }

    public void removerNoticia(int id) {
        listaNoticias.removeIf(f->f.getId() == id);
    }
}
