package edu.zjsr.yygh.vo;


import edu.zjsr.yygh.entity.Articles;
import edu.zjsr.yygh.entity.User;
import lombok.Data;


import java.util.List;


/**
 * @author 24340
 */
@Data
public class ArticleVO {
    private List<Articles> articleList;
    private Long total;
    private Integer current;
    private Integer pages;
}
