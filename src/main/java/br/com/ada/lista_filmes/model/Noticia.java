package br.com.ada.lista_filmes.model;

import lombok.Data;

@Data
public class Noticia {
    private String titulo;
    private String descricao;
    private String imagem;
    private String autor;
    private int id;
}
