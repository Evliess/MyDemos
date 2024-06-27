package com.example.fei_yue;
public class HuiYuan {

  private int  id;
  private String name;
  private String phone;
  private Double amount;
  private String chargeDate;
  private Integer timeLeft;
  private String auditLog;

  public String getAuditLog() {
    return auditLog;
  }

  public void setAuditLog(String auditLog) {
    this.auditLog = auditLog;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getChargeDate() {
    return chargeDate;
  }

  public void setChargeDate(String chargeDate) {
    this.chargeDate = chargeDate;
  }

  public Integer getTimeLeft() {
    return timeLeft;
  }

  public void setTimeLeft(Integer timeLeft) {
    this.timeLeft = timeLeft;
  }

  @Override
  public String toString() {
    return "HuiYuan{" +
        "name='" + name + '\'' +
        ", phone='" + phone + '\'' +
        ", amount=" + amount +
        ", chargeDate='" + chargeDate + '\'' +
        ", timeLeft=" + timeLeft +
        '}';
  }
}
