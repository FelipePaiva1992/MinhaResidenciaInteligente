package br.com.code4u.minharesidenciainteligente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.com.code4u.minharesidenciainteligente.util.ApplicationSession;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfiguracaoActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "MyPrefsFile";



    @BindView(R.id.ip)
    EditText ip;

    @BindView(R.id.porta)
    EditText porta;

    @BindView(R.id.telaConfiguracao)
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        ButterKnife.bind(this);

        ip.setText(ApplicationSession.getString("IP", ""));
        porta.setText(ApplicationSession.getString("PORTA", ""));
    }


    @OnClick(R.id.email_sign_in_button)
    public void salvarConfiguracao(){

        if(ip.getText().toString().isEmpty() || porta.getText().toString().isEmpty()){
            Snackbar.make(layout, "Erro ao gravar configurações", Snackbar.LENGTH_SHORT).show();
        }else{
            ApplicationSession.store("IP", ip.getText().toString());
            ApplicationSession.store("PORTA", porta.getText().toString());

            Intent intent = new Intent(this, ListaRelayActivity.class);
            finish();
            startActivity(intent);
        }

    }

}

