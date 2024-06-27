package com.example.fei_yue;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonUtils {

  public static JSONObject huiYuanToJson(HuiYuan huiYuan) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", huiYuan.getId());
    jsonObject.put("name", huiYuan.getName());
    jsonObject.put("phone", huiYuan.getPhone());
    jsonObject.put("amount", huiYuan.getAmount());
    jsonObject.put("chargeDate", huiYuan.getChargeDate());
    jsonObject.put("timeLeft", huiYuan.getTimeLeft());
    jsonObject.put("auditLog", huiYuan.getAuditLog());
    return jsonObject;
  }

  public static HuiYuan huiYuanToJava(JSONObject jsonObject) {
    HuiYuan huiYuan = new HuiYuan();
    huiYuan.setId(jsonObject.getInteger("id"));
    huiYuan.setName(jsonObject.getString("name"));
    huiYuan.setPhone(jsonObject.getString("phone"));
    huiYuan.setAmount(jsonObject.getDouble("amount"));
    huiYuan.setChargeDate(jsonObject.getString("chargeDate"));
    huiYuan.setTimeLeft(jsonObject.getInteger("timeLeft"));
    huiYuan.setAuditLog(jsonObject.getString("auditLog"));
    return huiYuan;
  }

  public static String getAuditDateStr() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:SS");
    return fmt.format(now);
  }

  public static HuiYuan updateExist(HuiYuan exist, HuiYuan current) {
    StringBuilder auditLog = new StringBuilder();
    JSONObject auditLogObj = new JSONObject();
    auditLogObj.put("TimeLeft", exist.getTimeLeft() + " <--> " + current.getTimeLeft());
    auditLogObj.put("Amount", exist.getAmount() + "  <-->  " + current.getAmount());
    auditLogObj.put("chargeDate", exist.getChargeDate() + " <--> " + current.getChargeDate());
    auditLog.append(getAuditDateStr()).append(" ").append(auditLogObj);
    exist.setPhone(current.getPhone());
    exist.setName(current.getName());
    exist.setAmount(current.getAmount());
    exist.setTimeLeft(current.getTimeLeft());
    exist.setChargeDate(current.getChargeDate());
    exist.setAuditLog(auditLog.toString());
    return exist;
  }
}
