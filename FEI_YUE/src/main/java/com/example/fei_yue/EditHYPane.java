package com.example.fei_yue;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class EditHYPane {
  private TextField inputPhone = null;
  private TextField inputName = null;
  private TextField inputAmount = null;
  private TextField inputChargeDate =  null;
  private TextField inputTimeLeft = null;
  private Button btnConfirm = new Button();
  private Button btnClear = new Button();

  private Button btnSearch = new Button();



  public GridPane buildEditHYPane(GridPane grid) {
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(0, 10, 0, 10));
    Label phone = new Label("手机号：");
    grid.add(phone, 0, 1, 1, 1);
    inputPhone = new TextField();
    grid.add(inputPhone, 1, 1, 3, 1);
    btnSearch.setText(AppConstants.BTN_SEARCH);
    btnSearch.setOnAction((actionEvent -> {
      System.out.println("click search btn");
    }));
    grid.add(btnSearch, 5, 1, 1, 1);

    Label name = new Label("昵称：");
    grid.add(name, 0, 2, 1, 1);
    inputName = new TextField();
    inputName.setEditable(false);
    inputName.setDisable(true);
    grid.add(inputName, 1, 2, 3, 1);

    Label amount = new Label("充值：");
    grid.add(amount, 0, 3, 1, 1);
    inputAmount = new TextField();
    grid.add(inputAmount, 1, 3, 3, 1);

    Label chargeDate = new Label("日期：");
    grid.add(chargeDate, 0, 4, 1, 1);
    inputChargeDate = new TextField();
    inputChargeDate.setPromptText("yyyy-mm-dd");
    grid.add(inputChargeDate, 1, 4, 3, 1);

    Label timeLeft = new Label("剩余次数：");
    grid.add(timeLeft, 0, 5, 1, 1);
    inputTimeLeft = new TextField();
    grid.add(inputTimeLeft, 1, 5, 3, 1);

    btnConfirm.setText(AppConstants.BTN_CONFIRM);
    btnConfirm.setOnAction((actionEvent -> {
      if(this.isFieldEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(AppConstants.ALERT_HEAD);
        alert.setHeaderText(AppConstants.ALERT_ADD_HY);
        alert.showAndWait().ifPresent(rs-> {
          if (rs == ButtonType.OK) {}
        });
      } else {
        this.populateAddHY();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(AppConstants.ALERT_HEAD);
        alert.setHeaderText(AppConstants.ALERT_ADD_HY);
        alert.showAndWait().ifPresent(rs-> {
          if (rs == ButtonType.OK) {}
        });
      }

    }));

    grid.add(btnConfirm, 1, 6, 1,1);
    btnClear.setText(AppConstants.BTN_CLEAR);
    grid.add(btnClear, 2, 6, 1,1);
    return grid;
  }

  private boolean isFieldEmpty() {
    return inputPhone.getText().isEmpty() ||
        inputName.getText().isEmpty() ||
        inputAmount.getText().isEmpty() ||
        inputChargeDate.getText().isEmpty()||
        inputTimeLeft.getText().isEmpty();
  }

  private void populateAddHY() {
    if (!this.isFieldEmpty()) {
      AppConstants.addHY.setPhone(inputPhone.getText());
      AppConstants.addHY.setName(inputName.getText());
      AppConstants.addHY.setAmount(Double.valueOf(inputAmount.getText()));
      AppConstants.addHY.setChargeDate(inputChargeDate.getText());
      AppConstants.addHY.setLeftTime(inputTimeLeft.getText());
    }
  }
}
