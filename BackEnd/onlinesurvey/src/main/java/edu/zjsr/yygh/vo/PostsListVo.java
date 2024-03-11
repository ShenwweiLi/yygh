package edu.zjsr.yygh.vo;

import edu.zjsr.yygh.entity.Posts;
import lombok.Data;

import java.util.List;

/**
 * @Author lishishan
 * @date 2024/3/7/14:45
 */
@Data
public class PostsListVo {
    private List<Posts> PostsList;
    private Long total;
    private Integer current;
    private Integer pages;
}
