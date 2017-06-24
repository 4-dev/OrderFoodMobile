package br.com.orderFood.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.sql.SQLException;

import br.com.orderFood.R;
import br.com.orderFood.helper.ResumoHelper;
import br.com.orderFood.model.bo.PedidoBO;
import br.com.orderFood.model.entity.Parametro;


/**
 * @author Ruan Alves
 */
public class ResumoFragment extends BaseFragment {

    protected static final String TAG = "LOG";
    private int color;
    private View mView;
    private ResumoHelper mResumoHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resumo, container, false);
        mView = view;
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fragment_resumoperfil_id);
        frameLayout.setBackgroundColor(color);

        return view;

    }

    private void init() {

        try {

            if (mResumoHelper == null) mResumoHelper = new ResumoHelper(getActivity(), mView);

            PedidoBO pedidoBO = new PedidoBO(getActivity());
            Parametro parametro = new Parametro();
            parametro.setStatus("Em Atendimento");
            parametro.setCodMesa(2);
            parametro.setEmpresa("Deyvid Solares e Lanches");

            mResumoHelper.setInformacoes(parametro, pedidoBO.getPedidos());

        } catch (SQLException e) {
            showAlert("Ops! Ocorreu um erro ao buscar resumo do carregamento");
            e.printStackTrace();
        }

    }

    public ResumoFragment() {

    }

    @SuppressLint("ValidFragment")
    public ResumoFragment(int color) {
        this.color = color;
    }

}
