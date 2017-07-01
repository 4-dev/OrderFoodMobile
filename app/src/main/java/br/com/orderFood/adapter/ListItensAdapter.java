package br.com.orderFood.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.model.model.ItensModel;
import br.com.orderFood.utils.Utils;


/**
 * Created by Ruan Alves
 */
public class ListItensAdapter extends RecyclerView.Adapter<ListItensAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItensModel> mLista;
    private LayoutInflater mLayoutInflater;

    public ListItensAdapter(Context c, List<ItensModel> l) {

        mContext = c;
        mLista = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.fullscreen_itens_row, viewGroup, false);
        MyViewHolder mhv = new MyViewHolder(v);

        return mhv;
    }

    @Override
    public void onBindViewHolder(ListItensAdapter.MyViewHolder holder, int position) {

        holder.textProduto.setText(mLista.get(position).getCodProduto() + " - " + mLista.get(position).getProduto());
        holder.textQuantidade.setText("Qtde: " + mLista.get(position).getQuantidade());
        holder.textValor.setText("Valor: R$ " + Utils.getMaskMoney(mLista.get(position).getValorUnitario()));
        holder.textValorTotal.setText("Total: R$ " + Utils.getMaskMoney(mLista.get(position).getValorTotal()));

    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public Object getItemAtPosition(int position) {
        return mLista.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textProduto, textQuantidade, textValor, textValorTotal;

        MyViewHolder(View itemView) {
            super(itemView);
            textProduto    = (TextView) itemView.findViewById(R.id.produto_nome);
            textQuantidade    = (TextView) itemView.findViewById(R.id.item_quantidade);
            textValor = (TextView) itemView.findViewById(R.id.item_valor);
            textValorTotal  = (TextView) itemView.findViewById(R.id.item_valortotal);
        }

    }
}
