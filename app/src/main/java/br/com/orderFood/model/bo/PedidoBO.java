package br.com.orderFood.model.bo;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.dao.ItensPedidoDAO;
import br.com.orderFood.dao.PedidoDAO;
import br.com.orderFood.model.entity.ItensPedido;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.model.model.ItensModel;

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

            inserirItensPedido(pedido.getItens(), idPedido);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean alterarPedido(Pedido pedido) {

        try {

            mDao = new PedidoDAO(mContext);
            ItensPedidoDAO itemDao = new ItensPedidoDAO(mContext);

            pedido = buscaValorTotal(pedido);
            itemDao.deletarItensPedido(pedido.getCodigo());

            mDao.alterarPedido(pedido);
            inserirItensPedido(pedido.getItens(), pedido.getCodigo());

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

    private void inserirItensPedido(List<ItensPedido> itens, long idPedido) throws Exception {

        ItensPedidoDAO itemDao = new ItensPedidoDAO(mContext);

        for (ItensPedido item : itens) {

            item.setCodPedido((int) idPedido);
            itemDao.salvar(item);
        }

        itemDao = null;

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

        if(listPedidos != null && listPedidos.size() > 0) {
            for (Pedido p : listPedidos) {
                p.setItens(mDao.getItensPedido(p));
            }
        }

        mDao = null;

        return listPedidos;

    }

    public List<ItensPedido> getItensPedido(Pedido pedido) throws SQLException {
        mDao = new PedidoDAO(mContext);
        return mDao.getItensPedido(pedido);
    }

    public List<ItensModel> getItensModel(int numPedido) {

        try {

            mDao = new PedidoDAO(mContext);
            List<ItensModel> listItens = new ArrayList<>();
            listItens = mDao.getItensModel(numPedido);
            mDao = null;

            return listItens;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public void deletarPedido(Pedido pedido){

        mDao = new PedidoDAO(mContext);
        mDao.deletarPedido(pedido.getCodigo());
        mDao = null;

        ItensPedidoDAO pedidoItemDAO = new ItensPedidoDAO(mContext);
        pedidoItemDAO.deletarItensPedido(pedido.getCodigo());
        pedidoItemDAO = null;

    }

}
