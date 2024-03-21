package edu.zjsr.yygh.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author lishishan
 * @date 2024/3/21/14:46
 */

@Data
public class ListPostsVo {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Integer repliesCount;

    private LocalDateTime lastReplyDate;

    private Boolean status;

    private Boolean isDelete;
}
