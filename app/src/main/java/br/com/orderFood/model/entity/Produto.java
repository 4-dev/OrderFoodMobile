package br.com.orderFood.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.enumerador.TipoCategoria;
import br.com.orderFood.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */
public class Produto implements Serializable, EntidadePersistivel {

    private static final long serialVersionUID = -221105128135301277L;

    private int codigo;
    private TipoCategoria categoria;
    private String descricao;
    private int foto;
    private double valor;
    private double qtEstoque;

    public Produto(double valor, String descricao, int idDrawable) {
        this.valor = valor;
        this.descricao = descricao;
        this.foto = idDrawable;
    }

    public static final List<Produto> COMIDAS_POPULARES = new ArrayList<Produto>();
    public static final List<Produto> BEBIDAS = new ArrayList<>();
    public static final List<Produto> POSTRES = new ArrayList<>();
    public static final List<Produto> PLATILLOS = new ArrayList<>();

    static {

        COMIDAS_POPULARES.add(new Produto(5, "Camarão Tismados", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Produto(3.2, "Rosca Herbárea", R.drawable.rosca));
        COMIDAS_POPULARES.add(new Produto(12, "Sushi Extremo", R.drawable.sushi));
        COMIDAS_POPULARES.add(new Produto(9, "Sandwich Deli", R.drawable.sandwich));
        COMIDAS_POPULARES.add(new Produto(34, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));

        PLATILLOS.add(new Produto(5, "Camarão Tismados", R.drawable.camarones));
        PLATILLOS.add(new Produto(3.2, "Rosca Herbárea", R.drawable.rosca));
        PLATILLOS.add(new Produto(12, "Sushi Extremo", R.drawable.sushi));
        PLATILLOS.add(new Produto(9, "Sandwich Deli", R.drawable.sandwich));
        PLATILLOS.add(new Produto(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));

        BEBIDAS.add(new Produto(3, "Taza de Café", R.drawable.cafe));
        BEBIDAS.add(new Produto(12, "Coctel Tronchatoro", R.drawable.coctel));
        BEBIDAS.add(new Produto(5, "Jugo Natural", R.drawable.jugo_natural));
        BEBIDAS.add(new Produto(24, "Coctel Jordano", R.drawable.coctel_jordano));
        BEBIDAS.add(new Produto(30, "Botella Vino Tinto Darius", R.drawable.vino_tinto));

        POSTRES.add(new Produto(2, "Postre De Vainilla", R.drawable.postre_vainilla));
        POSTRES.add(new Produto(3, "Flan Celestial", R.drawable.flan_celestial));
        POSTRES.add(new Produto(2.5, "Cupcake Festival", R.drawable.cupcakes_festival));
        POSTRES.add(new Produto(4, "Pastel De Fresa", R.drawable.pastel_fresa));
        POSTRES.add(new Produto(5, "Muffin Amoroso", R.drawable.muffin_amoroso));
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public TipoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoCategoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getQtEstoque() {
        return qtEstoque;
    }

    public void setQtEstoque(Double qtEstoque) {
        this.qtEstoque = qtEstoque;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
