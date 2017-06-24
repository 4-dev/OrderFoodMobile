package br.com.orderFood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.enumerador.TipoCategoria;
import br.com.orderFood.model.entity.Categoria;
import br.com.orderFood.model.entity.Produto;

/**
 * Created by Ruan Alves
 */
public class ProdutoDAO extends GenericDAO<Produto> {

    private static final String TAG = "PRODUTO_DAO";
    private static final String NOME_TABELA = "PRODUTO";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE IF NOT EXISTS " + NOME_TABELA + " ([CODIGO] INTEGER PRIMARY KEY, [CATEGORIA] TEXT," +
                                                        "[DESCRICAO] TEXT, [FOTO] TEXT, [VALOR] REAL, [QTESTOQUE] REAL)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    public static final String SCRIPT_LIMPAR_TABELA = "DELETE FROM " + NOME_TABELA;

    private static final String COLUNA_CODIGO = "CODIGO";
    private static final String COLUNA_CATEGORIA = "CATEGORIA";
    private static final String COLUNA_DESCRIACAO = "DESCRICAO";
    private static final String COLUNA_FOTO = "FOTO";
    private static final String COLUNA_VALOR = "VALOR";
    private static final String COLUNA_QTDEESTOQUE = "QTESTOQUE";

    public ProdutoDAO(Context context) {
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
    public ContentValues entidadeParacontentValues(Produto produto) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_CODIGO, produto.getCodigo());
        contentValues.put(COLUNA_CATEGORIA, produto.getCategoria().name());
        contentValues.put(COLUNA_DESCRIACAO, produto.getDescricao());
        //contentValues.put(COLUNA_FOTO, produto.getCategoria());
        contentValues.put(COLUNA_VALOR, produto.getValor());
        contentValues.put(COLUNA_QTDEESTOQUE, produto.getQtEstoque());

        return contentValues;

    }

    @Override
    public Produto contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }

    public boolean isProdutos() {

        String sql = "SELECT * FROM " + NOME_TABELA + " LIMIT 1";
        Cursor cursor = null;

        try {

            cursor = dataBase.rawQuery(sql, null);
            if (cursor.getCount() > 0) return true;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        } finally {
            cursor.close();
        }

        return false;

    }

    public List<Produto> getProdutos() throws SQLException {

        StringBuffer sql = new StringBuffer();
        Cursor cursor = null;
        List<Produto> resultados = new ArrayList<>();

        sql.append("SELECT CODIGO, DESCRICAO, VALOR, QTESTOQUE, CATEGORIA FROM PRODUTO");

        cursor = dataBase.rawQuery(sql.toString(), null);

        while (cursor.moveToNext()) {

            Produto model = new Produto();
            model.setCodigo(cursor.getInt(0));
            model.setDescricao(cursor.getString(1));
            model.setValor(cursor.getDouble(2));
            model.setQtEstoque(cursor.getDouble(3));

            if(cursor.getString(4).equalsIgnoreCase("PRATOS")) {
                model.setCategoria(TipoCategoria.PRATOS);
            } else if(cursor.getString(4).equalsIgnoreCase("BEBIDAS")) {
                model.setCategoria(TipoCategoria.BEBIDAS);
            } else {
                model.setCategoria(TipoCategoria.SOBREMESAS);
            }

            resultados.add(model);
            model = null;
        }

        return resultados;
    }

}
