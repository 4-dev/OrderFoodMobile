package br.com.orderFood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.model.entity.Pedido;

/**
 * Created by Ruan Alves
 */
public class PedidoDAO extends GenericDAO<Pedido> {

    private static final String TAG = "PRODUTO_DAO";
    private static final String NOME_TABELA = "PRODUTO";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE  IF NOT EXISTS " + NOME_TABELA + " ([CODIGO] INTEGER PRIMARY KEY AUTOINCREMENT, [DTEMISSAO] TEXT," +
            "[VALOTTOTAL] REAL, [OBSERVACAO] TEXT, [STATUS] TEXT, [STATUSENVIO] INTEGER)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    public static final String SCRIPT_LIMPAR_TABELA = "DELETE FROM " + NOME_TABELA;

    private static final String COLUNA_CODIGO = "CODIGO";
    private static final String COLUNA_DTEMISSAO = "DTEMISSAO";
    private static final String COLUNA_VALORTOTAL = "VALOTTOTAL";
    private static final String COLUNA_OBSERVACAO = "OBSERVACAO";
    private static final String COLUNA_STATUS = "STATUS";
    private static final String COLUNA_STATUSENVIO = "STATUSENVIO";

    public PedidoDAO(Context context) {
        super(context);
    }

    @Override
    public String getNomeColunaPrimaryKey() {
        return COLUNA_CODIGO;
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public ContentValues entidadeParacontentValues(Pedido pedido) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_DTEMISSAO, pedido.getDtEmissao());
        contentValues.put(COLUNA_VALORTOTAL, pedido.getValorTotal());
        contentValues.put(COLUNA_OBSERVACAO, pedido.getObservacao());
        contentValues.put(COLUNA_STATUS, pedido.getStatus());
        contentValues.put(COLUNA_STATUSENVIO, 0);

        return contentValues;

    }

    @Override
    public Pedido contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }


    public List<Pedido> getPedidos() throws SQLException {

        StringBuffer sql = new StringBuffer();
        Cursor cursor = null;
        List<Pedido> resultados = new ArrayList<>();

        sql.append("SELECT CODIGO, DTEMISSAO, VALOTTOTAL, OBSERVACAO, STATUS, STATUSENVIO FROM PEDIDO");

        cursor = dataBase.rawQuery(sql.toString(), null);

        while (cursor.moveToNext()) {

            Pedido model = new Pedido();
            model.setCodigo(cursor.getInt(0));
            model.setDtEmissao(cursor.getString(1));
            model.setValorTotal(cursor.getDouble(2));
            model.setObservacao(cursor.getString(3));
            model.setStatus(cursor.getString(4));
            model.setEnviado(cursor.getInt(5) == 1 ? true : false);

            resultados.add(model);
            model = null;
        }

        return resultados;
    }

    public List<Pedido> getPedidosPendentes() throws SQLException {

        StringBuffer sql = new StringBuffer();
        Cursor cursor = null;
        List<Pedido> resultados = new ArrayList<>();

        sql.append("SELECT CODIGO, DTEMISSAO, VALOTTOTAL, OBSERVACAO, STATUS, STATUSENVIO FROM PEDIDO WHERE STATUSENVIO = 0");

        cursor = dataBase.rawQuery(sql.toString(), null);

        while (cursor.moveToNext()) {

            Pedido model = new Pedido();
            model.setCodigo(cursor.getInt(0));
            model.setDtEmissao(cursor.getString(1));
            model.setValorTotal(cursor.getDouble(2));
            model.setObservacao(cursor.getString(3));
            model.setStatus(cursor.getString(4));
            model.setEnviado(cursor.getInt(5) == 1 ? true : false);

            resultados.add(model);
            model = null;
        }

        return resultados;
    }

}
