package br.com.orderFood.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import br.com.orderFood.R;
import br.com.orderFood.helper.ResumoHelper;


/**
 * @author Ruan Alves
 */
public class ResumoFragment extends BaseFragment {

    protected static final String TAG = "LOG";
    private int color;
    private View mView;
    private ResumoHelper mResumoEntregaHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        //init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resumo, container, false);
        mView = view;
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fragment_resumoperfil_id);
        frameLayout.setBackgroundColor(color);

        return view;

    }

    /*private void init(){

        try {

            if(mResumoEntregaHelper == null) mResumoEntregaHelper = new ResumoHelper(getActivity(), mView);

            CarregamentoBO carregamentoBO = new CarregamentoBO(getActivity());
            CarregamentoModel cargaModel = carregamentoBO.getResumoCarregamento();
            mResumoEntregaHelper.setInformacoes(cargaModel);

        } catch (SQLException e) {
            showAlert("Ops! Ocorreu um erro ao buscar resumo do carregamento");
            e.printStackTrace();
        }

    }*/

    public ResumoFragment() {

    }

    @SuppressLint("ValidFragment")
    public ResumoFragment(int color) {
        this.color = color;
    }

}
