package br.com.orderFood.dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.adapter.ListItensAdapter;
import br.com.orderFood.model.bo.PedidoBO;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.model.model.ItensModel;

public class FullScreen_ItensDialog extends DialogFragment {

    private RecyclerView mRecyclerView;
    private ListItensAdapter adapter;
    private List<ItensModel> mList;

    public FullScreen_ItensDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //OBTEM A INTÂNCIA DA ACTION BAR
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_close);
            actionBar.setTitle("Itens do Pedido");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    public void onEvent(Pedido pedido) {

        if(mList == null) mList = new ArrayList<>();
        mList = getListItensPedido(pedido);

        EventBus.getDefault().removeStickyEvent(pedido);

        adapter = new ListItensAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public List<ItensModel> getListItensPedido(Pedido pedido) {

        List<ItensModel> listItens = new ArrayList<>();
        PedidoBO pedidoBO = new PedidoBO(getActivity());
        listItens = pedidoBO.getItensModel(pedido.getCodigo());

        return listItens;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fullscreen_itens_dialog, container, false);
        init(view);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fullscreen_default_dialog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_produtos);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

    }

}
