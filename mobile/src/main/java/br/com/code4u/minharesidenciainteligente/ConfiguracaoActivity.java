package br.com.code4u.minharesidenciainteligente;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.com.code4u.minharesidenciainteligente.model.Configuracao;
import br.com.code4u.minharesidenciainteligente.wservice.RetrofitUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.Manifest.permission.READ_CONTACTS;

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



    }


    @OnClick(R.id.email_sign_in_button)
    public void salvarConfiguracao(){

        if(ip.getText().toString().isEmpty() || porta.getText().toString().isEmpty()){
            Snackbar.make(layout, "Erro ao gravar configurações", Snackbar.LENGTH_SHORT).show();
        }else{
            Realm realm = Realm.getInstance(getApplicationContext());
            realm.beginTransaction();
            realm.clear(Configuracao.class);
            Configuracao configuracao = realm.createObject(Configuracao.class);
            configuracao.setIp(ip.getText().toString());
            configuracao.setPorta(porta.getText().toString());
            realm.commitTransaction();

            Intent intent = new Intent(this, ListaRelayActivity.class);
            finish();
            startActivity(intent);
        }

    }

}

