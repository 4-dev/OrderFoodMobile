package br.com.orderFood.interfaces;

import android.view.View;

/**
 * Created by Ruan Alves
 */
public interface RecyclerViewOnClickListenerHack {
    public void onClickListener(View view, int position);

    public void onLongPressClickListener(View view, int position);
}
