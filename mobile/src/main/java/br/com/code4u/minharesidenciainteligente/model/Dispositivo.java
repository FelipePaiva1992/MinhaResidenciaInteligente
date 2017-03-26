package br.com.code4u.minharesidenciainteligente.model;

/**
 * Created by felipepaiva on 25/03/17.
 */

public class Dispositivo {

    private int id;
    private String nome;
    private boolean status;

    public Dispositivo() {
    }

    public Dispositivo(int id, String nome, boolean status) {
        this.id = id;
        this.nome = nome;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
