package br.com.orderFood.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.model.entity.Produto;
import br.com.orderFood.model.model.Item;

public class AdaptadorProdutos
        extends RecyclerView.Adapter<AdaptadorProdutos.ViewHolder> {


    private final List<Produto> items;
    private List<Item> listItens;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public ImageView imagen;
        public LinearLayout linearlayout_color;

        public ViewHolder(View v) {

            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio = (TextView) v.findViewById(R.id.precio_comida);
            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);
            linearlayout_color = (LinearLayout) v.findViewById(R.id.linearlayout_color);

        }

    }

    public AdaptadorProdutos(List<Produto> items, List<Item> listItens) {
        this.items = items;
        this.listItens = listItens;
    }

    public Object getItemAtPosition(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Produto item = items.get(i);
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getFoto())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getDescricao());
        viewHolder.precio.setText("R$ " + item.getValor());
        viewHolder.linearlayout_color.setBackgroundResource(R.color.accentColor);

        if(listItens != null && listItens .size() > 0) {
            for (Item itemAd : listItens) {
                if (itemAd.getCodProduto() == items.get(i).getCodigo()) {
                    viewHolder.linearlayout_color.setBackgroundResource(R.color.pedidoAdicionado);
                    break;
                }
            }
        } else {
            viewHolder.linearlayout_color.setBackgroundResource(R.color.accentColor);
        }

    }

}