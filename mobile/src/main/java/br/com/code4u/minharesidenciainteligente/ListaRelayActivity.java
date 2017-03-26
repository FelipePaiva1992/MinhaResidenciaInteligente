package br.com.code4u.minharesidenciainteligente;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.code4u.minharesidenciainteligente.adapter.DispositivoAdapter;
import br.com.code4u.minharesidenciainteligente.model.Dispositivo;
import br.com.code4u.minharesidenciainteligente.wservice.RetrofitUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaRelayActivity extends AppCompatActivity {

    @BindView(R.id.lista_dispositivos)
    ListView listaDispositivos;

    @BindView(R.id.activity_lista_relay)
    RelativeLayout layout;

    Thread thread;

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
    protected void onResume() {
        listarDispositivos(20000);
        super.onResume();
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
