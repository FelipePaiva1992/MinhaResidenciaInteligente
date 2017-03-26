package br.com.code4u.minharesidenciainteligente.wservice;

import java.util.List;

import br.com.code4u.minharesidenciainteligente.model.Dispositivo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by felipepaiva on 25/03/17.
 */

public interface ServicoRaspberryPi {

    @GET("/ligar/{id}")
    Call<Void> ligar(@Path("id") long idDispositivo);

    @GET("/desligar/{id}")
    Call<Void> desligar(@Path("id") long idDispositivo);

    @GET("/listar")
    Call<List<Dispositivo>> listar();

}
