package com.example.squiddemo.entity;

public class Hutang {
    private int id;
    private String pemberiUtang;
    private double jumlah;
    private Player player;

    public Hutang() {
    }

    public Hutang(int id, String pemberiUtang, double jumlah, Player player) {
        this.id = id;
        this.pemberiUtang = pemberiUtang;
        this.jumlah = jumlah;
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPemberiUtang() {
        return pemberiUtang;
    }

    public void setPemberiUtang(String pemberiUtang) {
        this.pemberiUtang = pemberiUtang;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
