package br.com.orderFood.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import br.com.orderFood.R;
import br.com.orderFood.adapter.AdaptadorCategorias;
import br.com.orderFood.interfaces.RecyclerViewOnClickListenerHack;
import br.com.orderFood.model.entity.Produto;


public class FragmentoCategoria extends Fragment implements RecyclerViewOnClickListenerHack {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;

    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), reciclador, this));
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategorias(Produto.PLATILLOS);
                break;
            case 1:
                adaptador = new AdaptadorCategorias(Produto.BEBIDAS);
                break;
            case 2:
                adaptador = new AdaptadorCategorias(Produto.POSTRES);
                break;
        }

        reciclador.setAdapter(adaptador);

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {

        AdaptadorCategorias adapter = (AdaptadorCategorias) reciclador.getAdapter();
        Produto produto = (Produto) adapter.getItemAtPosition(position);

        Log.i("SILJOJE", produto.getDescricao());

        showDialogContagemView();

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

    public void showDialogContagemView() {

        final Activity activity = getActivity();
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_dialog)
                .limitIconToDefaultSize()
                .title(R.string.inserir_item)
                .customView(R.layout.dialog_inseriritem, true)
                .positiveText(R.string.inserir)
                .negativeText(android.R.string.cancel)
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.accentColor))
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.accentColor))
                .canceledOnTouchOutside(false)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })

                .build();

        dialog.show();

    }

}
