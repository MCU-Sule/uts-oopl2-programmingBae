package com.example.squiddemo.dao;
/**
 * Abednego Steven 1972009
 */
import com.example.squiddemo.entity.Player;
import com.example.squiddemo.util.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerDao implements daointerface<Player>{

    @Override
    public int addData(Player data) {
        int result = 0;
        try {
            String query = "INSERT INTO player (id,Nama,Umur,Keahlian) VALUES (?,?,?,?)";
            PreparedStatement ps;
            ps = JDBCConnection.getConnection().prepareStatement(query);
            ps.setInt(1,data.getId());
            ps.setString(2,data.getNama());
            ps.setInt(3,data.getUmur());
            ps.setString(4,data.getKeahlian());
            result = ps.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return result;
    }

    @Override
    public int delData(Player data) {
        int result = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            String query = "DELETE from player WHERE id=? ";
            String query2 = "DELETE from hutang WHERE Player_id=?";
            PreparedStatement ps2 = JDBCConnection.getConnection().prepareStatement(query2);
            ps2.setInt(1,data.getId());
            result = ps2.executeUpdate();
            if (result!=0){
                connection.commit();
            } else {
                connection.rollback();
            }
            PreparedStatement ps = JDBCConnection.getConnection().prepareStatement(query);
            ps.setInt(1,data.getId());
            result = ps.executeUpdate();
            if (result!=0){
                connection.commit();
            } else {
                connection.rollback();
            }

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int updateData(Player data) {
        int result = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            String query = "UPDATE player SET Nama=?, Umur=?, Keahlian=? WHERE id=? ";
            PreparedStatement ps = JDBCConnection.getConnection().prepareStatement(query);
            ps.setString(1, data.getNama());
            ps.setInt(2,data.getUmur());
            ps.setString(3,data.getKeahlian());
            ps.setInt(4,data.getId());
            result = ps.executeUpdate();
            if (result!=0){
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<Player> showData() {
        ObservableList<Player> players = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM player";
            PreparedStatement ps;
            ps = JDBCConnection.getConnection().prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                Player player = new Player();
                player.setId(res.getInt("id"));
                player.setNama(res.getString("Nama"));
                player.setUmur(res.getInt("Umur"));
                player.setKeahlian(res.getString("Keahlian"));
                players.add(player);
            }
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return players;
    }
}
