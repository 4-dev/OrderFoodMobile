package br.com.orderFood.interfaces;

import br.com.orderFood.dto.ObjectSync;
import br.com.orderFood.dto.PedidoSync;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;

/**
 * Created by Ruan Alves
 */
public interface APIServiceConection {

    @GET("verificarmesa/{id}")
    Call<ObjectSync> verificarmesa(@Path("id") int codMesa);

    @POST("gerarpedido")
    Call<String> enviarPedidos(@Body PedidoSync pedidoSync);

}
