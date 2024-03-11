package edu.zjsr.yygh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lss
 * @since 2024-02-02
 */
@Getter
@Setter
@TableName("t_type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String typeName;


}
