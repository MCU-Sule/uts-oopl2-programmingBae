package com.example.squiddemo.dao;
/**
 * Abednego Steven 1972009
 */
import com.example.squiddemo.entity.Hutang;
import com.example.squiddemo.entity.Player;
import com.example.squiddemo.util.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HutangDao implements daointerface<Hutang>{
    @Override
    public int addData(Hutang data) {
        int result = 0;
        try {
            String query = "INSERT INTO hutang (PemberiUtang,Jumlah,Player_id) VALUES (?,?,?)";
            PreparedStatement ps;
            ps = JDBCConnection.getConnection().prepareStatement(query);
            ps.setString(1,data.getPemberiUtang());
            ps.setDouble(2,data.getJumlah());
            ps.setInt(3,data.getPlayer().getId());
            result = ps.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return result;
    }

    @Override
    public int delData(Hutang data) {
        int result = 0;
        try {
            Connection connection = JDBCConnection.getConnection();
            connection.setAutoCommit(false);
            String query = "DELETE from hutang WHERE id=? ";
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
    public int updateData(Hutang data) {
        return 0;
    }

    @Override
    public List<Hutang> showData() {
        ObservableList<Hutang> hutangs= FXCollections.observableArrayList();

        try {
            String query="SELECT hutang.id, hutang.PemberiUtang, hutang.jumlah, hutang.player_id, player.id, player.nama AS namaPlayer  FROM hutang INNER JOIN player ON hutang.Player_id = player.id";
            PreparedStatement ps;
            ps= JDBCConnection.getConnection().prepareStatement(query);
            ResultSet res=ps.executeQuery();
            while (res.next()){
                int playerId = res.getInt("player_id");
                String namaPlayer = res.getString("namaPlayer");
                Player player = new Player();
                player.setId(playerId);
                player.setNama(namaPlayer);
                Hutang hutang = new Hutang();
                hutang.setId(res.getInt("id"));
                hutang.setPemberiUtang(res.getString("PemberiUtang"));
                hutang.setJumlah(res.getDouble("jumlah"));
                hutang.setPlayer(player);
                hutangs.add(hutang);
            }

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return hutangs;
    }
}
