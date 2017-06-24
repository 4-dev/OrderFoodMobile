package br.com.orderFood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.utils.Utils;

/**
 * Created by Ruan Alves
 */
public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pedido> mLista;
    private LayoutInflater mLayoutInflater;

    public PedidosAdapter(Context c, List<Pedido> l) {

        mContext = c;
        mLista = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.pedidos_row, viewGroup, false);
        MyViewHolder mhv = new MyViewHolder(v);

        return mhv;
    }

    @Override
    public void onBindViewHolder(PedidosAdapter.MyViewHolder holder, int position) {

        Pedido pedido = mLista.get(position);

        holder.textStatus.setText(pedido.getStatus());
        holder.textDtEmissao.setText(pedido.getDtEmissao());
        holder.textValores.setText("Vl. Total: R$ " + Utils.getMaskMoney(pedido.getValorTotal()) + "    Qtde. Itens: " + pedido.getQtItens());

        if(pedido.getStatus().equalsIgnoreCase("PENDENTE")){
            holder.statusIndicator.setBackgroundResource(R.color.naoAtendidoStatus);
        } else if(pedido.getStatus().equalsIgnoreCase("ENVIADO")){
            holder.statusIndicator.setBackgroundResource(R.color.ativoStatus);
        } else if(pedido.getStatus().equalsIgnoreCase("EM ANDAMENTO")){
            holder.statusIndicator.setBackgroundResource(R.color.andamentoStatus);
        } else if(pedido.getStatus().equalsIgnoreCase("FINALIZADO")){
            holder.statusIndicator.setBackgroundResource(R.color.completoStatus);
        }

    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public Object getItemAtPosition(int position) {
        return mLista.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textStatus, textDtEmissao, textValores;
        View statusIndicator;

        MyViewHolder(View itemView) {
            super(itemView);
            textStatus = (TextView) itemView.findViewById(R.id.text_pedido_status);
            textDtEmissao = (TextView) itemView.findViewById(R.id.text_pedido_dtemissao);
            textValores = (TextView) itemView.findViewById(R.id.text_pedido_valores);
            statusIndicator = (View) itemView.findViewById(R.id.entrega_indicator_status);
        }

    }

}
