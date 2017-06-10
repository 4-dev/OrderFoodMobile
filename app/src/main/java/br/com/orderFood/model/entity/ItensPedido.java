package br.com.orderFood.model.entity;

import java.io.Serializable;

import br.com.orderFood.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */
public class ItensPedido implements Serializable, EntidadePersistivel {

    private static final long serialVersionUID = -221105128135301277L;

    private int codigo;
    private int codProduto;
    private int codPedido;
    private int quantidade;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(int codPedido) {
        this.codPedido = codPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
