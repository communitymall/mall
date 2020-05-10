package org.linlinjava.litemall.wx;

import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.wx.web.WxAuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WxRegistTest {
    @Autowired
    private WxAuthController wxAuthController;

    private MockHttpServletRequest request;
    //h5注册测试
    public void test1(){
        String mobile="12345678955";
        String password="123";
        LitemallUser user = new LitemallUser();
        String code="888";
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        //wxAuthController.register_h5(request,mobile,code,password);
    }
}
