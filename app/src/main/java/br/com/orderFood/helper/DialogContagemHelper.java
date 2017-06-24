package br.com.orderFood.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;

import br.com.orderFood.R;
import br.com.orderFood.model.entity.Produto;
import br.com.orderFood.model.model.Item;
import br.com.orderFood.utils.Utils;

/**
 * @author Ruan Alves
 */
public class DialogContagemHelper {

    private TextView mValorTotal;
    private EditText mQuantidade;
    private MaterialDialog dialog;
    private Context mContext;
    private Produto produto;

    public DialogContagemHelper(Activity activity, MaterialDialog dialog, Produto produto) {

        mContext = activity;
        this.produto = produto;
        this.dialog = dialog;
        activityEditText();

    }

    private void activityEditText() {

        mValorTotal = (TextView) dialog.getCustomView().findViewById(R.id.inserirItem_valorTotal);
        mQuantidade = (EditText) dialog.getCustomView().findViewById(R.id.inserirItem_quantidade);

        final View neutralAction = dialog.getActionButton(DialogAction.NEUTRAL);
        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        mQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                positiveAction.setEnabled(s.toString().trim().length() > 0);

                String qtDigitadaStr = mQuantidade.getText().toString();
                if (qtDigitadaStr != null && !qtDigitadaStr.isEmpty()) {

                    Double quantidade = Double.parseDouble(mQuantidade.getText().toString());
                    Double valor = quantidade * produto.getValor();

                    mValorTotal.setText(String.valueOf(Utils.converterDoubleDoisDecimais(valor.doubleValue())));
                    valor = null;
                    quantidade = null;
                } else {
                    mValorTotal.setText(String.valueOf("0.0"));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        neutralAction.setEnabled(false);
        positiveAction.setEnabled(false);

        int widgetColor = ThemeSingleton.get().widgetColor;
        MDTintHelper.setTint(mQuantidade, widgetColor == 0 ? ContextCompat.getColor(mContext, R.color.accent) : widgetColor);

    }

    public Item getItem() {

        Double quantidade = Double.parseDouble(mQuantidade.getText().toString());

        Item item = new Item();
        item.setCodProduto(produto.getCodigo());
        item.setQuantidade(quantidade);
        item.setValor(quantidade * produto.getValor());
        item.setValorUnit(produto.getValor());

        return item;

    }

    public void setmQuantidadeDigitada(Double quantidade) {

        final View neutralAction = dialog.getActionButton(DialogAction.NEUTRAL);
        neutralAction.setEnabled(true);
        mQuantidade.setText(quantidade.toString());

    }

}
