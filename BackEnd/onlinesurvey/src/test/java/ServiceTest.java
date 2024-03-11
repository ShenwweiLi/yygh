import edu.zjsr.yygh.OnlinesurveyApplication;
import edu.zjsr.yygh.mapper.ArticlesMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: lss
 * @Date: 2024/1/30 16:49
 */

@SpringBootTest(classes =  OnlinesurveyApplication.class)
public class ServiceTest {

    @Autowired
    ArticlesMapper articlesMapper;

    @Test
    public  void TestArticle(){
        articlesMapper.selectByIdWithAuthor(7);
    }

}
