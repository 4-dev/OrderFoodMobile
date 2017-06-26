package br.com.orderFood.dao;

import android.content.ContentValues;
import android.content.Context;

import br.com.orderFood.model.entity.Parametro;

/**
 * Created by Ruan Alves
 */
public class ParametroDAO extends GenericDAO<Parametro> {

    private static final String TAG = "PARAMETRO_DAO";
    private static final String NOME_TABELA = "PARAMETRO";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE  IF NOT EXISTS " + NOME_TABELA + " ([CODIGO] INTEGER PRIMARY KEY AUTOINCREMENT, [CODEMPRESA] INTEGER, [EMPRESA] TEXT," +
                                                        "[CODMESA] INTEGER, [STATUS] TEXT, [LINKMESA] TEXT)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    public static final String SCRIPT_LIMPAR_TABELA = "DELETE FROM " + NOME_TABELA;

    private static final String COLUNA_CODIGO     = "CODIGO";
    private static final String COLUNA_CODEMPRESA = "CODEMPRESA";
    private static final String COLUNA_EMPRESA    = "EMPRESA";
    private static final String COLUNA_CODMESA    = "CODMESA";
    private static final String COLUNA_STATUS     = "STATUS";
    private static final String COLUNA_LINKMESA   = "LINKMESA";

    public ParametroDAO(Context context) {
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
    public ContentValues entidadeParacontentValues(Parametro parametro) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNA_CODEMPRESA, parametro.getCodEmpresa());
        contentValues.put(COLUNA_EMPRESA, parametro.getEmpresa());
        contentValues.put(COLUNA_CODMESA, parametro.getCodMesa());
        contentValues.put(COLUNA_STATUS, parametro.getStatus());
        contentValues.put(COLUNA_LINKMESA, parametro.getLinkAcesso());


        return contentValues;

    }

    @Override
    public Parametro contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }

    public void limparTabelas() {
        dataBase.execSQL(ParametroDAO.SCRIPT_LIMPAR_TABELA);
        dataBase.execSQL(PedidoDAO.SCRIPT_LIMPAR_TABELA);
        dataBase.execSQL(ProdutoDAO.SCRIPT_LIMPAR_TABELA);
        dataBase.execSQL(ItensPedidoDAO.SCRIPT_LIMPAR_TABELA);
    }

}
