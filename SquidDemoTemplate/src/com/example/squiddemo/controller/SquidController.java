package com.example.squiddemo.controller;

import com.example.squiddemo.dao.HutangDao;
import com.example.squiddemo.dao.PlayerDao;
import com.example.squiddemo.entity.Hutang;
import com.example.squiddemo.entity.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SquidController implements Initializable {
    @FXML
    public javafx.scene.layout.HBox HBox;
    @FXML
    public TableColumn<Hutang, String> kolomPemain;
    @FXML
    public TableColumn<Hutang, String> kolomHutang;
    @FXML
    public Button hapusHutang;
    @FXML
    public TableColumn<Hutang,String> kolomJumlah;
    @FXML
    private TableView<Player> tablePemain;
    @FXML
    private ComboBox<Player> cmbPeserta;
    @FXML
    private TextField txtPemberiUtang;
    @FXML
    private TextField txtJumlah;
    @FXML
    private TableView tableHutang;
    @FXML
    private TableColumn<Player,String> kolomId;
    @FXML
    private TableColumn<Player,String> kolomNama;
    @FXML
    private TableColumn<Player,String> kolomUmur;
    @FXML
    private TableColumn<Player,String> kolomKemampuan;

    private ObservableList<Player> players;
    private ObservableList<Hutang> hutangs;

    public TableView<Player> getTablePemain() {
        return tablePemain;
    }

    private Boolean updateOrDelete;

    public Boolean getUpdateOrDelete() {
        return updateOrDelete;
    }

    public void setUpdateOrDelete(Boolean updateOrDelete) {
        this.updateOrDelete = updateOrDelete;
    }

    public ObservableList<Player> getPlayers() {
        return players;
    }

    public void addPemain(ActionEvent actionEvent) throws IOException {
        setUpdateOrDelete(false);
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModalPage.fxml"));
        Parent root = loader.load();
        StageModalController controller2 =loader.getController();
        controller2.setSquidController(this);
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.initOwner(HBox.getScene().getWindow());
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.setTitle("");
        newStage.show();
    }

    public void updatePemain(ActionEvent actionEvent) throws IOException {
        setUpdateOrDelete(true);
        Player player;
        player =tablePemain.getSelectionModel().getSelectedItem();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ModalPage.fxml"));
        Parent root = loader.load();
        StageModalController controller2 =loader.getController();
        controller2.setSquidController(this);
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.initOwner(HBox.getScene().getWindow());
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.setTitle("");
        newStage.show();
    }

    public void deletePemain(ActionEvent actionEvent) {
        Player player;
        player =tablePemain.getSelectionModel().getSelectedItem();
        HutangDao hutangDao = new HutangDao();
        PlayerDao playerDao = new PlayerDao();
        int result = playerDao.delData(player);
        if (result==0){
            System.out.println("Delete Gagal");
        } else {
            System.out.println("Delete Berhasil");
        }
        hutangs.clear();
        hutangs.addAll(hutangDao.showData());
        players.clear();
        players.addAll(playerDao.showData());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayerDao playerDao = new PlayerDao();
        HutangDao hutangDao = new HutangDao();
        players = (ObservableList<Player>) playerDao.showData();
        hutangs = (ObservableList<Hutang>) hutangDao.showData();
        cmbPeserta.setItems(players);
        tablePemain.setItems(players);
        kolomId.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        kolomNama.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNama()));
        kolomUmur.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(data.getValue().getUmur())));
        kolomKemampuan.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getKeahlian()));
        tableHutang.setItems(hutangs);
        kolomPemain.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(data.getValue().getPlayer().getId())));
        kolomHutang.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(data.getValue().getPemberiUtang())));
        kolomJumlah.setCellValueFactory(data->new SimpleStringProperty(String.valueOf(data.getValue().getJumlah())));

    }

    public void tambahDataHutang(ActionEvent actionEvent) {
        HutangDao hutangDao = new HutangDao();
        if (txtJumlah.getText().trim().isEmpty() || txtPemberiUtang.getText().trim().isEmpty()
                || cmbPeserta.getSelectionModel().isEmpty()) {
            Alert alertInformation = new Alert(Alert.AlertType.ERROR);
            alertInformation.setContentText("Please fill in all the field");
            alertInformation.show();
        } else {
            try {
                Hutang hutang = new Hutang();
                hutang.setPemberiUtang(txtPemberiUtang.getText());
                hutang.setJumlah(Double.valueOf(txtJumlah.getText()));
                hutang.setPlayer(cmbPeserta.getValue());
                hutangDao.addData(hutang);
                hutangs.clear();
                hutangs.addAll(hutangDao.showData());
                txtPemberiUtang.setText("");
                txtJumlah.setText("");
            } catch (NumberFormatException ex) {
                Alert alertInformation = new Alert(Alert.AlertType.ERROR);
                alertInformation.setContentText("Please only input number for Jumlah Hutang");
                alertInformation.show();
            }
        }


    }

    public void hapusHutang(ActionEvent actionEvent) {
        Hutang hutang = (Hutang) tableHutang.getSelectionModel().getSelectedItem();
        HutangDao hutangDao = new HutangDao();
        int result = hutangDao.delData(hutang);
        if (result==0){
            System.out.println("Delete Gagal");
        } else {
            System.out.println("Delete Berhasil");
        }
        hutangs.clear();
        hutangs.addAll(hutangDao.showData());
    }
}
