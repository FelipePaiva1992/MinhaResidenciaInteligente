package br.com.code4u.minharesidenciainteligente.model;

import io.realm.RealmObject;

/**
 * Created by felipepaiva on 26/03/17.
 */

public class Configuracao extends RealmObject {

    private String ip;
    private String porta;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

}
