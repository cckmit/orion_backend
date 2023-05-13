package br.com.live.model;

public class ObterInfoRolos {
    public String nivel;
    public String grupo;
    public String sub;
    public String item;
    public int periodo;
    public float qtdeKilos;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public float getQtdeKilos() {
        return qtdeKilos;
    }

    public void setQtdeKilos(float qtdeKilos) {
        this.qtdeKilos = qtdeKilos;
    }

    public ObterInfoRolos() {

    }

    public ObterInfoRolos(String nivel, String grupo, String sub, String item, int periodo, float qtdeKilos) {
        this.nivel = nivel;
        this.grupo = grupo;
        this.sub = sub;
        this.item = item;
        this.periodo = periodo;
        this.qtdeKilos = qtdeKilos;
    }
}
