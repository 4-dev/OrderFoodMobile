package br.com.orderFood.interfaces;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;

/**
 * Created by Ruan Alves
 */
public interface APIServiceConection {

    @GET("verificarmesa/{id}")
    Call<String> verificarColaborador(@Path("id") int codColaborador);

}
