package edu.zjsr.yygh.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lss
 * @since 2024-01-30
 */
@Getter
@Setter
@TableName("t_articles")
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private Integer popularity;

    private String authorId;

    private String summary;

    private Integer category;

    private Integer commentCount;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private User user;

}
