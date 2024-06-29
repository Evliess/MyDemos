import com.example.fei_yue.AuditLog;
import com.example.fei_yue.H2Service;
import com.example.fei_yue.HuiYuan;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class H2ServiceTestCase {

    @Test
    public void testGetConn() {
        H2Service.getInstance();
    }

    @Test
    public void test() throws Exception{
        HuiYuan addHuiYuan = new HuiYuan();
        addHuiYuan.setPhone("15612344321");
        addHuiYuan.setName("fei");
        addHuiYuan.setAmount(108d);
        addHuiYuan.setLeftTime("3");
        addHuiYuan.setChargeDate("2024/06/03");
        addHuiYuan.setScore("3");
//        System.out.println(H2Service.getInstance().insertHuiYuan(addHuiYuan));
        System.out.println(H2Service.getInstance().updateHuiYuan(addHuiYuan));
    }
}