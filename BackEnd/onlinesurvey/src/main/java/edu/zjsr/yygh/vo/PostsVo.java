package edu.zjsr.yygh.vo;

import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Posts;
import lombok.Data;

import java.util.List;

/**
 * @Author lishishan
 * @date 2024/3/8/11:04
 */
@Data
public class PostsVo {
    private Posts posts;
    private List<CommentVo> commentsList;
}
