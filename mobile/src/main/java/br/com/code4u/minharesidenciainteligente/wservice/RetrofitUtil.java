package br.com.code4u.minharesidenciainteligente.wservice;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import br.com.code4u.minharesidenciainteligente.R;
import br.com.code4u.minharesidenciainteligente.adapter.DispositivoAdapter;
import br.com.code4u.minharesidenciainteligente.model.Dispositivo;
import br.com.code4u.minharesidenciainteligente.util.ApplicationSession;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felipepaiva on 25/03/17.
 */

public class RetrofitUtil {

    private Retrofit retrofit;
    private ServicoRaspberryPi servicoRaspberryPi;
    private Activity activity;
    private RelativeLayout layout;

    public RetrofitUtil(Activity activity, RelativeLayout layout) {
        this.activity = activity;
        this.layout = layout;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + ApplicationSession.getString("IP", "") + ":" + ApplicationSession.getString("PORTA", "") + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            servicoRaspberryPi = retrofit.create(ServicoRaspberryPi.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ligarDispositivo(final Dispositivo dispositivo, final ImageView imagem, final Activity activity){
        try {
            Call<Void> ligar = servicoRaspberryPi.ligar(dispositivo.getId());
            ligar.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Snackbar.make(layout, "Ligando o dispositivo " + dispositivo.getNome(), Snackbar.LENGTH_SHORT).show();
                        Picasso.with(RetrofitUtil.this.activity).load(R.drawable.ligada).into(imagem);
                        listarDispositivos((ListView) activity.findViewById(R.id.lista_dispositivos));
                    }else{
                        Snackbar.make(layout, "Erro na comunicação com a Central", Snackbar.LENGTH_LONG)
                                .setAction("Tentar Novamente", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ligarDispositivo(dispositivo,imagem,activity);
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar.make(layout, "Erro na comunicação com a Central", Snackbar.LENGTH_LONG)
                            .setAction("Tentar Novamente", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ligarDispositivo(dispositivo,imagem,activity);
                                }
                            })
                            .show();
                }
            });
        }catch (Exception e){
            Log.e("Felipe", e.getMessage());
        }
    }

    public void desligarDispositivo(final Dispositivo dispositivo, final ImageView imagem, final Activity activity){
        try {
            Call<Void> desligar = servicoRaspberryPi.desligar(dispositivo.getId());
            desligar.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Snackbar.make(layout, "Desligando o dispositivo " + dispositivo.getNome(), Snackbar.LENGTH_SHORT).show();
                        Picasso.with(RetrofitUtil.this.activity).load(R.drawable.desligada).into(imagem);
                        listarDispositivos((ListView) RetrofitUtil.this.activity.findViewById(R.id.lista_dispositivos));
                    }else{
                        Snackbar.make(layout, "Erro na comunicação com a Central", Snackbar.LENGTH_LONG)
                                .setAction("Tentar Novamente", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        desligarDispositivo(dispositivo,imagem,activity);
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar.make(layout, "Erro na comunicação com a Central", Snackbar.LENGTH_LONG)
                            .setAction("Tentar Novamente", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    desligarDispositivo(dispositivo,imagem,activity);
                                }
                            })
                            .show();
                }
            });
        }catch (Exception e){
            Log.e("Felipe", e.getMessage());
        }
    }

    public void listarDispositivos(final ListView listView){
        try {
            Call<List<Dispositivo>> desligar = servicoRaspberryPi.listar();
            desligar.enqueue(new Callback<List<Dispositivo>>() {
                @Override
                public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                    if(response.isSuccessful()){
                        DispositivoAdapter adapter = new DispositivoAdapter(response.body(), activity);
                        listView.setAdapter(adapter);
                    }else{
                        Snackbar.make(layout, "Erro na comunicação com a Central", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Dispositivo>> call, Throwable t) {
                    Snackbar.make(layout, "Erro na comunicação com a Central", Snackbar.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Log.e("Felipe", e.getMessage());
        }
    }

}
