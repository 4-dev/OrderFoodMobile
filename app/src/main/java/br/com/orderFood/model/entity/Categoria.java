package br.com.orderFood.model.entity;

import java.io.Serializable;

import br.com.orderFood.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */
public class Categoria implements Serializable, EntidadePersistivel {

    private static final long serialVersionUID = -221105128135301277L;

    private int codigo;
    private String categoria;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
