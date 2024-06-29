import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.example.fei_yue.HuiYuan;
import com.example.fei_yue.JsonUtils;
import org.junit.jupiter.api.Assertions;
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
        huiYuan.setLeftTime("3");
    }


    private String readJsonFile(String jsonPath) {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[256];
        try (FileInputStream fis = new FileInputStream(jsonPath)) {
            while (fis.read(buffer) != -1) {
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


}
