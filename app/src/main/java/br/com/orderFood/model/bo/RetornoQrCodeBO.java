package br.com.orderFood.model.bo;

import android.content.Context;

import br.com.orderFood.dao.ParametroDAO;
import br.com.orderFood.dao.ProdutoDAO;
import br.com.orderFood.dto.ObjectSync;
import br.com.orderFood.dto.ProdutoDTO;
import br.com.orderFood.enumerador.TipoCategoria;
import br.com.orderFood.model.entity.Parametro;
import br.com.orderFood.model.entity.Produto;

/**
 * Created by Ruan Alves
 */
public class RetornoQrCodeBO {

    private Context mContext;

    public RetornoQrCodeBO(Context context) {
        mContext = context;
    }

    public boolean salvar(ObjectSync objectSync, int codMesa){

        try {

            ProdutoDAO mProdutoDAO = new ProdutoDAO(mContext);
            ParametroDAO parametroDAO = new ParametroDAO(mContext);

            for (ProdutoDTO p : objectSync.getListProdutos()){

                Produto produto = new Produto();
                produto.setCodigo((int) p.getCodigo());
                produto.setDescricao(p.getDescricao());
                produto.setQtEstoque((int) p.getQtEstoque());
                produto.setValor(p.getValor());
                produto.setLinkFoto(p.getFoto());

                if(p.getCategoria().equalsIgnoreCase("PRATOS")) {
                    produto.setCategoria(TipoCategoria.PRATOS);
                } else if(p.getCategoria().equalsIgnoreCase("BEBIDAS")) {
                    produto.setCategoria(TipoCategoria.BEBIDAS);
                } else {
                    produto.setCategoria(TipoCategoria.SOBREMESAS);
                }

                mProdutoDAO.salvar(produto);
                produto = null;

            }

            Parametro parametro = new Parametro();
            parametro.setEmpresa("ORDER FOOD");
            parametro.setCodMesa(codMesa);
            parametro.setCodEmpresa(1);
            parametro.setStatus("Em Atendimento");

            parametroDAO.salvar(parametro);
            parametro = null;

            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
