import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;


import java.util.Collections;

/**
 * @Author: lss
 * @Date: 2024/1/17 15:39
 */

public class JavaTest {

    @Test
    public   void CodeGenerator(){
        // 数据库连接信息
        String url = "jdbc:mysql://192.168.234.128:3306/surveydb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        // 子模块的路径
        String projectPath = System.getProperty("user.dir");
        String javaOutputDir = projectPath + "/src/main/java";
        String resourceOutputDir = projectPath + "/src/main/resources";
        String mapperDir = resourceOutputDir + "/mapper"; // MyBatis Mapper XML 存放路径

        // 需要生成的数据表名称
        String tableName = "t_notifications"; // 数据库中的表名

        // 执行代码生成
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("lss") // 设置作者
                            .outputDir(javaOutputDir); // 指定Java类输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("edu.zjsr.yygh") // 设置父包名为项目的根包名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper") // 指定Mapper XML目录
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperDir)); // 设置Mapper XML生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); // 开启Lombok
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix("t_"); // 根据实际情况设置表前缀，如果没有则不设置
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker模板引擎
                .execute();
    }

}
