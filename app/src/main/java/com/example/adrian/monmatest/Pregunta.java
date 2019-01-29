package com.example.adrian.monmatest;

public class Pregunta {

    private int id;
    private String enunciado;
    private String rsp1;
    private String rsp2;
    private String rsp3;
    private String rsp4;
    private String categoria;
    private String foto;

    public Pregunta() {

    }

    //este contructor sirve para hacer un select a la BD
    public Pregunta(int id, String enunciado, String rsp1, String rsp2, String rsp3, String rsp4, String categoria, String foto) {
        this.id = id;
        this.enunciado = enunciado;
        this.rsp1 = rsp1;
        this.rsp2 = rsp2;
        this.rsp3 = rsp3;
        this.rsp4 = rsp4;
        this.categoria = categoria;
        this.foto = foto;
    }

    //este constructor insertara una pregunta pero sin foto en la BD
    public Pregunta(String enunciado, String rsp1, String rsp2, String rsp3, String rsp4, String categoria) {
        this.enunciado = enunciado;
        this.rsp1 = rsp1;
        this.rsp2 = rsp2;
        this.rsp3 = rsp3;
        this.rsp4 = rsp4;
        this.categoria = categoria;
    }

    //este constructor insertara una pregunta junto con una foto en la BD
    public Pregunta(String enunciado, String rsp1, String rsp2, String rsp3, String rsp4, String categoria, String foto) {
        this.enunciado = enunciado;
        this.rsp1 = rsp1;
        this.rsp2 = rsp2;
        this.rsp3 = rsp3;
        this.rsp4 = rsp4;
        this.categoria = categoria;
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRsp1() {
        return rsp1;
    }

    public void setRsp1(String rsp1) {
        this.rsp1 = rsp1;
    }

    public String getRsp2() {
        return rsp2;
    }

    public void setRsp2(String rsp2) {
        this.rsp2 = rsp2;
    }

    public String getRsp3() {
        return rsp3;
    }

    public void setRsp3(String rsp3) {
        this.rsp3 = rsp3;
    }

    public String getRsp4() {
        return rsp4;
    }

    public void setRsp4(String rsp4) {
        this.rsp4 = rsp4;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
