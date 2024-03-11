package edu.zjsr.yygh;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.controller.TagController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sun.text.normalizer.UBiDiProps;

/**
 *  * @Author: yxl
 *  * @Date: 2024/2/6 01:08
 */
@SpringBootTest
@Slf4j
public class TagTest {

    @Autowired
    TagController tagController;

    @Test
    public void tagTest(){
        Object data = tagController.getAllTags("外卖").getData();
        log.info("========================");
        log.info("data={}",data);
    }
}
