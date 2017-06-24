package br.com.orderFood.model.bo;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.dao.ProdutoDAO;
import br.com.orderFood.model.entity.Produto;

/**
 * Created by Ruan Alves
 */
public class ProdutoBO {

    private ProdutoDAO mDao;
    private Context mContext;

    public ProdutoBO(Context context){
        mContext = context;
    }

    public Boolean isProdutos(){
        mDao = new ProdutoDAO(mContext);
        return mDao.isProdutos();
    }

    public void salvar(Produto produto) throws Exception {
        mDao = new ProdutoDAO(mContext);
        mDao.salvar(produto);
        mDao.fecharConexao();
        mDao = null;
    }

    public List<Produto> getProdutos() throws SQLException {

        mDao = new ProdutoDAO(mContext);
        List<Produto> listProdutos = new ArrayList<>();
        listProdutos = mDao.getProdutos();
        mDao = null;

        return listProdutos;

    }

}
