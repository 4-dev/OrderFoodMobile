package br.com.orderFood.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.model.entity.Pedido;

/**
 * @author Ruan Alves
 */
public class PedidoSync {

    private List<Pedido> listPedidos;
    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public PedidoSync() {
        listPedidos = new ArrayList<>();
    }

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }
}
