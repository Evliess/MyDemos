import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.example.fei_yue.HuiYuan;
import com.example.fei_yue.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class JsonUtilsTestCase {

  private String jsonPath = "src/test/resources/huiyuan.json";
  private HuiYuan huiYuan = new HuiYuan();

  @BeforeEach
  public void setup() {
    huiYuan.setId(001);
    huiYuan.setName("name");
    huiYuan.setPhone("1234");
    huiYuan.setAmount(108.0d);
    huiYuan.setChargeDate("2024/06/18");
    huiYuan.setTimeLeft(3);
  }


  @Test
  public void fun1() {
    JSONObject jsonObject = JsonUtils.huiYuanToJson(huiYuan);
    Assertions.assertEquals(001, JsonUtils.huiYuanToJava(jsonObject).getId());
    Assertions.assertEquals("name", JsonUtils.huiYuanToJava(jsonObject).getName());
    Assertions.assertEquals("123", JsonUtils.huiYuanToJava(jsonObject).getPhone());
    Assertions.assertEquals(108.0, JsonUtils.huiYuanToJava(jsonObject).getAmount());
    Assertions.assertEquals("2024/06/18", JsonUtils.huiYuanToJava(jsonObject).getChargeDate());
    Assertions.assertEquals(3, JsonUtils.huiYuanToJava(jsonObject).getTimeLeft());
  }

  private String readJsonFile(String jsonPath) {
    StringBuilder sb = new StringBuilder();
    byte[] buffer = new byte[256];
    try(FileInputStream fis = new FileInputStream(jsonPath)) {
      while (fis.read(buffer)!=-1) {
        sb.append(new String(buffer));
        buffer = new byte[256];
      }
    } catch (Exception e) {
      System.err.println("Read json file err:" + e.getMessage());
      e.printStackTrace();
    }
    return sb.toString();
  }

  private void writeJsonFile(String jsonPath, String content) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonPath))) {
      writer.write(content);
    } catch (Exception e) {
      System.err.println("Failed to write json file: " + jsonPath);
    }
  }

  @Test
  public void fun2() throws Exception {
    String jsonStr = readJsonFile(jsonPath);
    JSONObject root = JSON.parseObject(jsonStr);
    Boolean isNotExist  = root.getJSONObject(huiYuan.getPhone())==null?true:false;
    System.out.println(isNotExist);
    if(isNotExist) {
      JSONObject addObj = JsonUtils.huiYuanToJson(huiYuan);
      root.put(huiYuan.getPhone(), addObj);
      writeJsonFile(jsonPath, root.toJSONString());
    } else {
      System.out.println("Exist");
      HuiYuan currHuiYuan = new HuiYuan();
      currHuiYuan.setName(huiYuan.getName());
      currHuiYuan.setPhone(huiYuan.getPhone());
      currHuiYuan.setAmount(huiYuan.getAmount() + 100d);
      currHuiYuan.setTimeLeft(huiYuan.getTimeLeft() + 3);
      currHuiYuan.setChargeDate("2024/06/24");
      HuiYuan updated = JsonUtils.updateExist(huiYuan, currHuiYuan);
      JSONObject updatedObj = JsonUtils.huiYuanToJson(updated);
      root.put(currHuiYuan.getPhone(), updatedObj);
      writeJsonFile(jsonPath, root.toJSONString());
    }

  }
}
