package br.com.orderFood.dao;

import android.content.ContentValues;
import android.content.Context;

import br.com.orderFood.model.entity.Categoria;
import br.com.orderFood.model.entity.Pedido;

/**
 * Created by Ruan Alves
 */
public class PedidoDAO extends GenericDAO<Pedido> {

    private static final String TAG = "PRODUTO_DAO";
    private static final String NOME_TABELA = "PRODUTO";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE  IF NOT EXISTS " + NOME_TABELA + " ([CODIGO] INTEGER PRIMARY KEY AUTOINCREMENT, [DTEMISSAO] TEXT," +
                                                        "[VALOTTOTAL] REAL, [OBSERVACAO] TEXT, [STATUS] TEXT)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    public static final String SCRIPT_LIMPAR_TABELA = "DELETE FROM " + NOME_TABELA;

    private static final String COLUNA_CODIGO = "CODIGO";
    private static final String COLUNA_DTEMISSAO = "DTEMISSAO";
    private static final String COLUNA_VALORTOTAL = "VALOTTOTAL";
    private static final String COLUNA_OBSERVACAO = "OBSERVACAO";
    private static final String COLUNA_STATUS = "STATUS";

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

        return contentValues;

    }

    @Override
    public Pedido contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }


}
