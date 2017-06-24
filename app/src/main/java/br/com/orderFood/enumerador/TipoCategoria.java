package br.com.orderFood.enumerador;

/**
 * Created by Ruan Alves on 20/05/2017.
 */

public enum TipoCategoria {

    PRATOS("PRATOS"),
    BEBIDAS("BEBIDAS"),
    SOBREMESAS("SOBREMESAS");

    private String descricao;

    TipoCategoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

}
