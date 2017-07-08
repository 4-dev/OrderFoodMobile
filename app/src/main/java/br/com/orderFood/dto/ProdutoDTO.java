package br.com.orderFood.dto;

public class ProdutoDTO {

    private int codigo;
    private String categoria;
    private String descricao;
    private Double valor;
    private long qtEstoque;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public long getQtEstoque() {
        return qtEstoque;
    }

    public void setQtEstoque(long qtEstoque) {
        this.qtEstoque = qtEstoque;
    }
}
