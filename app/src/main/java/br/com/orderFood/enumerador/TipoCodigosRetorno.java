/*
 * TipoCodigosRetorno.java
 * Copyright (c) SN SISTEMAS.
 *
 * Este software é confidencial e propriedade da SN SISTEMAS.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da SN SISTEMAS.
 * Este arquivo contém informações proprietárias.
 */
package br.com.orderFood.enumerador;


/**
 * Created by Ruan Alves
 */
public enum TipoCodigosRetorno {

    T0001("Veículo apto a usar o Gestor Entrega"),
    T0002("Veículo informado não tem cadastro"),
    T0003("Veículo não possui carregamento"),
    T0004("Data do servidor não é compativél com a data do GE"),
    T0005("Versão do WebService incompatível"),
    T0006("Foi indentificado mais de um carregamento para o veículo, favor verificar com o Administrador"),
    T0007("Existem mais de 1 veículo com a mesma placa, favor verificar antes de dar carga"),
    T0008("Ops, Houve algum erro no servidor, favor entrar em contato com o Administrador!"),
    T0009("Operação realizada com sucesso"),
    T0010("Erro ao realizar operação"),
    T0011("Erro ao atualizar status de carga no servidor");
    
    private String descricao;

    TipoCodigosRetorno(String descricao) {
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
