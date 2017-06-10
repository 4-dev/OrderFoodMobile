package br.com.orderFood.dao;

import android.content.ContentValues;
import android.content.Context;

import br.com.orderFood.model.entity.ItensPedido;

/**
 * Created by Ruan Alves
 */
public class ItensPedidoDAO extends GenericDAO<ItensPedido> {

    private static final String TAG = "PRODUTO_DAO";
    private static final String NOME_TABELA = "PRODUTO";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE  IF NOT EXISTS " + NOME_TABELA + " ([CODIGO] INTEGER PRIMARY KEY AUTOINCREMENT, [CODPRODUTO] INTEGER," +
                                                        "[CODPEDIDO] INTEGER, [QUANTIDADE] REAL)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    public static final String SCRIPT_LIMPAR_TABELA = "DELETE FROM " + NOME_TABELA;

    private static final String COLUNA_CODIGO = "CODIGO";
    private static final String COLUNA_CODPRODUTO = "CODPRODUTO";
    private static final String COLUNA_CODPEDIDO = "CODPEDIDO";
    private static final String COLUNA_QUANTIDADE = "QUANTIDADE";

    public ItensPedidoDAO(Context context) {
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
    public ContentValues entidadeParacontentValues(ItensPedido item) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_CODPRODUTO, item.getCodProduto());
        contentValues.put(COLUNA_CODPEDIDO, item.getCodPedido());
        contentValues.put(COLUNA_QUANTIDADE, item.getQuantidade());

        return contentValues;

    }

    @Override
    public ItensPedido contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }


}
