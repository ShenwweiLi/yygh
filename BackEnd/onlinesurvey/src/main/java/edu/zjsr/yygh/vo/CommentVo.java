package edu.zjsr.yygh.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author lishishan
 * @date 2024/3/8/11:14
 */

@Data
public class CommentVo {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer postId;

    private String userId;

    private String content;

    private LocalDateTime createdDate;

    private Boolean status;

    private Boolean isDelete;

    private String username; // 新增字段，用于存放用户名
}
