package edu.zjsr.yygh.vo;

import edu.zjsr.yygh.entity.Doctor;
import lombok.Data;

import java.util.List;

/**
 * @Author lishishan
 * @date 2024/3/14/17:36
 */

@Data
public class CommentListVo {

    private List<CommentVo> commentVoList;
    private Long total;
    private Integer current;
    private Integer pages;
}
