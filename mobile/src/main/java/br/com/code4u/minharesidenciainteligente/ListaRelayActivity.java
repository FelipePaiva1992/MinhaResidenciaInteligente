package br.com.code4u.minharesidenciainteligente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

import br.com.code4u.minharesidenciainteligente.wservice.RetrofitUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaRelayActivity extends AppCompatActivity {

    @BindView(R.id.lista_dispositivos)
    private ListView listaDispositivos;

    @BindView(R.id.activity_lista_relay)
    private RelativeLayout layout;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_relay);

        ButterKnife.bind(this);

        listarDispositivos(20000);

    }

    private void listarDispositivos(final int millis){
        try {
            final RetrofitUtil retrofitUtil = new RetrofitUtil(this,layout);
            retrofitUtil.listarDispositivos(listaDispositivos);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            sleep(millis);
                            retrofitUtil.listarDispositivos(listaDispositivos);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();


        }catch (Exception e){
            Snackbar.make(layout, "IP e Porta de configuração invalidos", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
        stopHandler();
    }

    @Override
    public void onResume() {
        super.onResume();
        startHandler();
    }
    private void stopHandler(){
        if(handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    private void startHandler(){
        final RetrofitUtil retrofitUtil = new RetrofitUtil(this,layout);
        handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                retrofitUtil.listarDispositivos(listaDispositivos);
                handler.postDelayed( this, TimeUnit.SECONDS.toMillis(10));
            }
        }, TimeUnit.SECONDS.toMillis(15));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuacao, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.config) {
            Intent intent = new Intent(this, ConfiguracaoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
