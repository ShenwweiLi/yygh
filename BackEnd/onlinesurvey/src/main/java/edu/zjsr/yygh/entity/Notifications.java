package edu.zjsr.yygh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2024-02-04
 */
@Getter
@Setter
@TableName("t_notifications")
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime readDate;

    private Boolean status;

    private Boolean isDelete;


}
