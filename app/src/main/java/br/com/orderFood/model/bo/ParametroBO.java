package br.com.orderFood.model.bo;

import android.content.Context;

import java.sql.SQLException;

import br.com.orderFood.dao.ParametroDAO;

/**
 * Created by Ruan Alves
 */

public class ParametroBO {

    private ParametroDAO mDao;
    private Context mContext;

    public ParametroBO(Context context) {
        mContext = context;
    }

    public void limparTabelas() throws SQLException {
        mDao = new ParametroDAO(mContext);
        mDao.limparTabelas();
        mDao = null;
    }

}
