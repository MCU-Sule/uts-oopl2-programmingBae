package com.example.squiddemo.controller;
/**
 * Abednego Steven 1972009
 */
import com.example.squiddemo.dao.PlayerDao;
import com.example.squiddemo.entity.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StageModalController implements Initializable {
    @FXML
    public TextField txtId;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtUmur;
    @FXML
    private TextField txtKeahlian;
    private SquidController squidController;
    private Boolean playerDuplikat;



    public void setSquidController(SquidController squidController) {
        if (squidController.getUpdateOrDelete()){
            txtId.setDisable(true);
            txtId.setText(String.valueOf(squidController.getTablePemain().getSelectionModel().getSelectedItem().getId()));
            txtNama.setText(squidController.getTablePemain().getSelectionModel().getSelectedItem().getNama());
            txtUmur.setText(String.valueOf(squidController.getTablePemain().getSelectionModel().getSelectedItem().getUmur()));
            txtKeahlian.setText(squidController.getTablePemain().getSelectionModel().getSelectedItem().getKeahlian());
        } else {
           txtId.setDisable(false);
        }

        this.squidController = squidController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addButton(ActionEvent actionEvent) {
        if (txtId.isDisabled()){
            if (txtNama.getText().trim().isEmpty() || txtId.getText().trim().isEmpty()
                    || txtUmur.getText().trim().isEmpty()) {
                Alert alertInformation = new Alert(Alert.AlertType.ERROR);
                alertInformation.setContentText("Please fill in all the field");
                alertInformation.show();
            } else {
                try {

                    Player player = squidController.getTablePemain().getSelectionModel().getSelectedItem();
                    player.setId(Integer.valueOf(txtId.getText()));
                    player.setNama(txtNama.getText());
                    player.setUmur(Integer.valueOf(txtUmur.getText()));
                    if (txtKeahlian.getText().isEmpty()) {
                        player.setKeahlian("-");
                    } else {
                        player.setKeahlian(txtKeahlian.getText());
                    }
                        PlayerDao playerDao = new PlayerDao();
                        playerDao.updateData(player);
                        squidController.getPlayers().clear();
                        squidController.getPlayers().addAll(playerDao.showData());
                        squidController.getPlayers().clear();
                        squidController.getPlayers().addAll(playerDao.showData());

                } catch (NumberFormatException ex) {
                    Alert alertInformation = new Alert(Alert.AlertType.ERROR);
                    alertInformation.setContentText("Please only input number for ID and Umur");
                    alertInformation.show();
                }
            }
        }
        else {
            if (txtNama.getText().trim().isEmpty() || txtId.getText().trim().isEmpty()
                    || txtUmur.getText().trim().isEmpty()) {
                Alert alertInformation = new Alert(Alert.AlertType.ERROR);
                alertInformation.setContentText("Please fill in all the field");
                alertInformation.show();
            } else {
                try {
                    for (int i = 0; i < squidController.getPlayers().size(); i++) {
                        if (squidController.getPlayers().get(i).getId() == Integer.parseInt(txtId.getText())) {
                            playerDuplikat = true;
                            break;
                        } else {
                            playerDuplikat = false;
                        }
                    }
                    Player player = new Player();
                    player.setId(Integer.valueOf(txtId.getText()));
                    player.setNama(txtNama.getText());
                    player.setUmur(Integer.valueOf(txtUmur.getText()));
                    if (txtKeahlian.getText().isEmpty()) {
                        player.setKeahlian("-");
                    } else {
                        player.setKeahlian(txtKeahlian.getText());
                    }


                    if (playerDuplikat != true) {
                        PlayerDao playerDao = new PlayerDao();
                        playerDao.addData(player);
                        squidController.getPlayers().clear();
                        squidController.getPlayers().addAll(playerDao.showData());
                        squidController.getPlayers().clear();
                        squidController.getPlayers().addAll(playerDao.showData());
                    } else {
                        Alert alertInformation = new Alert(Alert.AlertType.ERROR);
                        alertInformation.setContentText("Player dengan ID yang sama sudah ada di dalam table");
                        alertInformation.show();
                    }
                } catch (NumberFormatException ex) {
                    Alert alertInformation = new Alert(Alert.AlertType.ERROR);
                    alertInformation.setContentText("Please only input number for ID and Umur");
                    alertInformation.show();
                }
            }
        }
        Stage stage = (Stage) txtUmur.getScene().getWindow();
        stage.close();
    }

    public void cancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) txtUmur.getScene().getWindow();
        stage.close();
    }
}
