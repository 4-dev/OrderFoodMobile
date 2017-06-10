package br.com.orderFood.model.entity;

import java.io.Serializable;
import java.util.List;

import br.com.orderFood.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */
public class Pedido implements Serializable, EntidadePersistivel {

    private static final long serialVersionUID = -221105128135301277L;

    private int codigo;
    private String dtEmissao;
    private Double valorTotal;
    private String observacao;
    private String status;
    private List<ItensPedido> itens;

    public List<ItensPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItensPedido> itens) {
        this.itens = itens;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(String dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
