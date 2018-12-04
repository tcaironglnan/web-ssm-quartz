import com.ssm.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author FaceFeel
 * @Created 2018-05-06 10:42
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-*.xml")
public class UserTest {

    @Test
    public void insertUser(){

        UserModel userModel = new UserModel();
        userModel.setSex(1)
                .setAddTime(new Date())
                .setStatus(1)
                .setAuth(0)
                .setPassWord("123456")
                .setMail("123456789@qq.com")
                .setPhone("12345678910")
                .setUserName("曹操")
                .setHeadImage("");
    }
}
