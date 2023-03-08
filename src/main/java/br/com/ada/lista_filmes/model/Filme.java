package br.com.ada.lista_filmes.model;

import java.util.UUID;

// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;

import lombok.Data;

// @Entity
@Data
public class Filme {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String titulo;
    private String genero;
    private int duracao;
    private String sinopse;
    private Float imdb;
    private String imagem;
    private int like;
    // private List<Ator> atores;


}
