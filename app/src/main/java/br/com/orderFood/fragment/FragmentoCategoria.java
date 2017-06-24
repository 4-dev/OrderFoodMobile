package br.com.orderFood.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.adapter.AdaptadorCategorias;
import br.com.orderFood.enumerador.TipoCategoria;
import br.com.orderFood.helper.DialogContagemHelper;
import br.com.orderFood.interfaces.RecyclerViewOnClickListenerHack;
import br.com.orderFood.model.bo.PedidoBO;
import br.com.orderFood.model.bo.ProdutoBO;
import br.com.orderFood.model.entity.ItensPedido;
import br.com.orderFood.model.entity.Parametro;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.model.entity.Produto;
import br.com.orderFood.model.model.Item;
import br.com.orderFood.utils.Utils;


public class FragmentoCategoria extends BaseFragment implements RecyclerViewOnClickListenerHack {

    private static final String INDICE_SECCION = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;
    private Produto produtoSelecionado;
    private DialogContagemHelper dialogContagemHelper;
    private List<Item> listItens;
    private List<Produto> listProdutos;
    private Parametro parametro;

    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    private List<Produto> listProdutos() {

        try{

            ProdutoBO produtoBO = new ProdutoBO(getActivity());
            return produtoBO.getProdutos();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    private List<Produto> listProdutosPRATOS(List<Produto> listProdutos){

        List<Produto> listResult = new ArrayList<>();

        for(Produto p : listProdutos){
            if(p.getCategoria().equals(TipoCategoria.PRATOS)){

                if(p.getCodigo() == 1) p.setFoto(R.drawable.camarones);
                if(p.getCodigo() == 2) p.setFoto(R.drawable.rosca);
                if(p.getCodigo() == 3) p.setFoto(R.drawable.sushi);
                if(p.getCodigo() == 4) p.setFoto(R.drawable.sandwich);
                if(p.getCodigo() == 5) p.setFoto(R.drawable.lomo_cerdo);

                listResult.add(p);


            }
        }

        return listResult;

    }

    private List<Produto> listProdutosBEBIDAS(List<Produto> listProdutos){

        List<Produto> listResult = new ArrayList<>();

        for(Produto p : listProdutos){
            if(p.getCategoria().equals(TipoCategoria.BEBIDAS)) {

                if(p.getCodigo() == 6) p.setFoto(R.drawable.cafe);
                if(p.getCodigo() == 7) p.setFoto(R.drawable.coctel);
                if(p.getCodigo() == 8) p.setFoto(R.drawable.jugo_natural);
                if(p.getCodigo() == 9) p.setFoto(R.drawable.coctel_jordano);
                if(p.getCodigo() == 10) p.setFoto(R.drawable.vino_tinto);

                listResult.add(p);
            }
        }

        return listResult;

    }

    private List<Produto> listProdutosSOBREMESAS(List<Produto> listProdutos){

        List<Produto> listResult = new ArrayList<>();

        for(Produto p : listProdutos){
            if(p.getCategoria().equals(TipoCategoria.SOBREMESAS)) {

                if(p.getCodigo() == 11) p.setFoto(R.drawable.postre_vainilla);
                if(p.getCodigo() == 12) p.setFoto(R.drawable.flan_celestial);
                if(p.getCodigo() == 13) p.setFoto(R.drawable.cupcakes_festival);
                if(p.getCodigo() == 14) p.setFoto(R.drawable.pastel_fresa);
                if(p.getCodigo() == 15) p.setFoto(R.drawable.muffin_amoroso);

                listResult.add(p);
            }
        }

        return listResult;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        if(listProdutos == null) listProdutos = listProdutos();

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), reciclador, this));
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategorias(listProdutosPRATOS(listProdutos));
                break;
            case 1:
                adaptador = new AdaptadorCategorias(listProdutosBEBIDAS(listProdutos));
                break;
            case 2:
                adaptador = new AdaptadorCategorias(listProdutosSOBREMESAS(listProdutos));
                break;
        }

        reciclador.setAdapter(adaptador);

        return view;
    }

    public void showDialogContagemView() {

        final Activity activity = getActivity();
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_dialog)
                .limitIconToDefaultSize()
                .title(R.string.inserir_item)
                .customView(R.layout.dialog_inseriritem, true)
                .positiveText(R.string.inserir)
                .negativeText(R.string.voltar)
                .neutralText(R.string.remover)
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.accentColor))
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.accentColor))
                .neutralColor(ContextCompat.getColor(getActivity(), R.color.accentColor))
                .canceledOnTouchOutside(false)

                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        DialogContagemHelper helper = dialogContagemHelper;
                        Item item = helper.getItem();

                        for (Item i : listItens) {
                            if (i.getCodProduto() == item.getCodProduto()) {
                                listItens.remove(i);
                                break;
                            }
                        }

                        calcularValorToolbar();

                    }
                })

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        DialogContagemHelper helper = dialogContagemHelper;

                        if(listItens == null) listItens = new ArrayList<>();
                        Item item = helper.getItem();
                        boolean isVerific = false;

                        if(listItens.size() > 0) {
                            for (Item i : listItens) {
                                if (i.getCodProduto() == item.getCodProduto()) {
                                    if (item.getQuantidade() <= 0) {
                                        isVerific = true;
                                        listItens.remove(i);
                                        break;
                                    } else {
                                        isVerific = true;
                                        i.setQuantidade(item.getQuantidade());
                                        i.setValor(item.getValor());
                                        i.setValorUnit(item.getValorUnit());
                                        break;
                                    }
                                }
                            }

                            if(!isVerific)listItens.add(item);

                        } else {
                            listItens.add(item);
                        }

                        calcularValorToolbar();

                        dialogContagemHelper = null;
                        dialog.dismiss();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })

                .build();

        if (produtoSelecionado != null) {

            dialogContagemHelper = new DialogContagemHelper(getActivity(), dialog, produtoSelecionado);

            if(listItens != null && listItens.size() > 0) {
                for(Item item : listItens) {
                    if(produtoSelecionado.getCodigo() == item.getCodProduto()){
                        dialogContagemHelper.setmQuantidadeDigitada(item.getQuantidade());
                    }
                }
            }


        }  else dialog.dismiss();

        dialog.show();

    }

    private void calcularValorToolbar(){

        double valorPedido = 0.0;
        String descricaoValor = "R$ 0,00";
        if(listItens.size() > 0) {
            for (Item i : listItens) {
                valorPedido += i.getValor();
            }
        }

        if(valorPedido > 0){
            descricaoValor = "R$ " + Utils.getMaskMoney(valorPedido);
        }

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Pedido   ("+descricaoValor+")");

    }

    private boolean validarPedido() {

        if(listItens == null || listItens.size() == 0){
            showAlert("Não há itens inseridos para realizar o pedido!");
        }

        double valorPedido = 0.0;
        if(listItens.size() > 0) {
            for (Item i : listItens) {
                valorPedido += i.getValor();
            }
        }
        if(valorPedido <= 0) {
            showAlert("Valor do Pedido se encontra zerado, impossibilitando o fechamento do mesmo!");
        }

        return true;

    }

    private List<ItensPedido> getItensPedido(List<Item> listItens) {

        List<ItensPedido> listResult = new ArrayList<>();

        for(Item i : listItens) {
            ItensPedido item = new ItensPedido();
            item.setCodProduto(i.getCodProduto());
            item.setQuantidade(i.getQuantidade());
            item.setValorTotal(i.getValor());
            item.setValorUnitario(i.getValorUnit());

            listResult.add(item);
            item = null;
        }

        return listResult;

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
    public void onEvent(Parametro parametro) {

        this.parametro = parametro;
        EventBus.getDefault().removeStickyEvent(parametro);

    }

    @Override
    public void onClickListener(View view, int position) {

        AdaptadorCategorias adapter = (AdaptadorCategorias) reciclador.getAdapter();
        produtoSelecionado = (Produto) adapter.getItemAtPosition(position);

        showDialogContagemView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_categorias, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().finish();
        } else if (id == R.id.action_salvar) {

            if(validarPedido()){

                Pedido pedido = new Pedido();
                pedido.setStatus("A");
                pedido.setDtEmissao(Utils.retornaDateFormatadaDDMMYY_HHmmss(new Date()));
                pedido.setItens(getItensPedido(listItens));
                pedido.setObservacao("");

                PedidoBO pedidoBO = new PedidoBO(getActivity());
                pedidoBO.salvarPedido(pedido);
                pedidoBO = null;

                showAlert("Mensagem de sucesso");
                getActivity().finish();

            }

        }

        return true;

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh) {
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if (cv != null && mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildAdapterPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if (cv != null && mRecyclerViewOnClickListenerHack != null) {
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildAdapterPosition(cv));
                    }

                    return (true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

}
