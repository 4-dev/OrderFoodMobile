package br.com.orderFood.helper;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.model.entity.Parametro;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.utils.Utils;

/**
 * Created by Ruan Alves
 */
public class ResumoHelper {

    private TextView mEmpresa;
    private TextView mMesa;
    private TextView mStatusAtendimento;
    private TextView mValorTotal;
    private View mView;
    private Context mContext;

    public ResumoHelper(FragmentActivity fragmentActivity, View view) {

        this.mView = view;
        this.mContext = fragmentActivity;
        activityEditText();

    }

    private void activityEditText() {

        mEmpresa = (TextView) mView.findViewById(R.id.texto_empresa);
        mMesa = (TextView) mView.findViewById(R.id.texto_mesa);
        mStatusAtendimento = (TextView) mView.findViewById(R.id.texto_status);
        mValorTotal = (TextView) mView.findViewById(R.id.texto_valortotal);

    }

    public void setInformacoes(Parametro parametro, List<Pedido> listPedidos) {

        double valorTotal = 0.0;
        for(Pedido p : listPedidos)valorTotal += p.getValorTotal();

        mEmpresa.setText(parametro.getEmpresa());
        mMesa.setText("Mesa: " + parametro.getCodMesa());
        mStatusAtendimento.setText(parametro.getStatus());
        mValorTotal.setText("R$ " + Utils.getMaskMoney(valorTotal));

    }

}
