package br.com.orderFood.model.bo;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.dao.ItensPedidoDAO;
import br.com.orderFood.dao.PedidoDAO;
import br.com.orderFood.model.entity.ItensPedido;
import br.com.orderFood.model.entity.Pedido;

/**
 * Created by Ruan Alves
 */
public class PedidoBO {

    private PedidoDAO mDao;
    private Context mContext;

    public PedidoBO(Context context) {
        mContext = context;
    }

    public boolean salvarPedido(Pedido pedido) {

        try {

            long idPedido = 0;
            mDao = new PedidoDAO(mContext);
            pedido = buscaValorTotal(pedido);
            idPedido = mDao.salvarRetornID(pedido);

            inserirItensPedido(pedido.getItens(), idPedido, pedido);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Pedido buscaValorTotal(Pedido pedido) {

        double valorTotal = 0.0;

        for (ItensPedido item : pedido.getItens()) {
            valorTotal += item.getValorTotal();
        }

        pedido.setValorTotal(valorTotal);

        return pedido;
    }

    private void inserirItensPedido(List<ItensPedido> itens, long idPedido, Pedido pedido) throws Exception {

        ItensPedidoDAO itemDao = new ItensPedidoDAO(mContext);

        for (ItensPedido item : itens) {

            item.setCodPedido((int) idPedido);
            itemDao.salvar(item);
        }

    }

    public List<Pedido> getPedidos() throws SQLException {

        mDao = new PedidoDAO(mContext);
        List<Pedido> listPedidos = new ArrayList<>();
        listPedidos = mDao.getPedidos();
        mDao = null;

        return listPedidos;

    }

    public List<Pedido> getPedidosPendentes() throws SQLException {

        mDao = new PedidoDAO(mContext);
        List<Pedido> listPedidos = new ArrayList<>();
        listPedidos = mDao.getPedidosPendentes();
        mDao = null;

        return listPedidos;

    }

}
