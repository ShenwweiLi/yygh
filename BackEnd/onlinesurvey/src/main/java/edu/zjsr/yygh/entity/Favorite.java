package edu.zjsr.yygh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: lss
 * @Date: 2024/1/18 15:16
 */

@Data
@TableName("t_favorites")
public class Favorite {

    @TableId
    private Integer id;

    private String userId;

    private Integer articleId;

    private Timestamp createdAt;


}
