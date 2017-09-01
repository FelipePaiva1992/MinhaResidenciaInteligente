package br.com.code4u.minharesidenciainteligente.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.code4u.minharesidenciainteligente.R;
import br.com.code4u.minharesidenciainteligente.model.Dispositivo;
import br.com.code4u.minharesidenciainteligente.wservice.RetrofitUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by felipepaiva on 25/03/17.
 */

public class DispositivoAdapter  extends BaseAdapter {

    private List<Dispositivo> dispositivos;
    private Activity activity;

    @BindView(R.id.nomeDispositivo)
    private TextView nomeDispositivo;

    @BindView(R.id.statusDispositivo)
    private ImageView statusDispositivo;


    private RelativeLayout activityListaRelay;

    private RetrofitUtil retrofitUtil;

    public DispositivoAdapter(List<Dispositivo> dispositivos, Activity activity) {
        this.dispositivos = dispositivos;
        this.activity = activity;
        this.activityListaRelay = (RelativeLayout) activity.findViewById(R.id.activity_lista_relay);
        this.retrofitUtil = new RetrofitUtil(activity,activityListaRelay);
    }

    @Override
    public int getCount() {
        return dispositivos.size();
    }

    @Override
    public Dispositivo getItem(int position) {
        return dispositivos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dispositivos.get(position).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final Dispositivo dispositivo = getItem(position);

        View linha = activity.getLayoutInflater().inflate(R.layout.layout_dispositivo, viewGroup, false);

        ButterKnife.bind(this,linha);

        if(dispositivo.isStatus()){
            Picasso.with(activity).load(R.drawable.ligada).into(statusDispositivo);
        }else{
            Picasso.with(activity).load(R.drawable.desligada).into(statusDispositivo);
        }
        linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarAcaoDispositivo(position);
            }
        });

        nomeDispositivo.setText(dispositivo.getNome());

        return linha;
    }

    private void mudarAcaoDispositivo(int i){
        if (!getItem(i).isStatus()) {
            retrofitUtil.ligarDispositivo(getItem(i), statusDispositivo, activity);
        } else {
            retrofitUtil.desligarDispositivo(getItem(i), statusDispositivo, activity);
        }
    }
}
