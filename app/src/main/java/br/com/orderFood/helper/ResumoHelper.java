package br.com.orderFood.helper;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Ruan Alves
 */
public class ResumoHelper {

    private TextView mEmpresa;
    private TextView mCarregamento;
    private TextView mVeiculo;
    private TextView mFitChartDetalhe1;
    private TextView mFitChartDetalhe2;
    private TextView mFitChartPercentual;
    private LinearLayout mLinearFitChartDetalhe;
    private LinearLayout mLinearFitChartPercentual;
    private RelativeLayout mEmptyResumo;
    private LinearLayout mNotEmptyResumo;
    private View mView;
    private Context mContext;

    public ResumoHelper(FragmentActivity fragmentActivity, View view) {

        this.mView = view;
        this.mContext = fragmentActivity;
        activityEditText();

    }

    private void activityEditText() {

        /*

        mEmpresa                  = (TextView) mView.findViewById(R.id.tv_resumo_empresa);
        mCarregamento             = (TextView) mView.findViewById(R.id.tv_resumo_carga);
        mVeiculo                  = (TextView) mView.findViewById(R.id.tv_resumo_veiculo);
        mFitChartDetalhe1         = (TextView) mView.findViewById(R.id.tv_fitChart_detalhe1);
        mFitChartDetalhe2         = (TextView) mView.findViewById(R.id.tv_fitChart_detalhe2);
        mFitChartPercentual       = (TextView) mView.findViewById(R.id.tv_fitChart_percentual);
        mEmptyResumo              = (RelativeLayout) mView.findViewById(R.id.empty_state_container);
        mNotEmptyResumo           = (LinearLayout) mView.findViewById(R.id.not_empty_state_container);
        mLinearFitChartDetalhe    = (LinearLayout) mView.findViewById(R.id.linearLayout_fitChart_detalhe);
        mLinearFitChartPercentual = (LinearLayout) mView.findViewById(R.id.linearLayout_fitChart_percentual);

        */
    }

    /*public void setInformacoes(CarregamentoModel carga) throws SQLException {

        if(carga != null){

            mEmpresa.setText(carga.getCodEmpresa() + " - " + carga.getEmpresa());
            mCarregamento.setText("Num. Carga: " + carga.getNumCarga());
            mVeiculo.setText(carga.getPlaca() + " - " + carga.getVeiculo());
            setGraficAcompanhamento(carga);
            showResumo();

        } else showNoResumo();

    }*/

    private void showResumo(){
        mNotEmptyResumo.setVisibility(View.VISIBLE);
        mEmptyResumo.setVisibility(View.GONE);
    }

    private void showNoResumo(){
        mNotEmptyResumo.setVisibility(View.GONE);
        mEmptyResumo.setVisibility(View.VISIBLE);
    }

}
