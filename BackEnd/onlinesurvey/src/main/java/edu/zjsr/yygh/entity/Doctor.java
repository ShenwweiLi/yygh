package edu.zjsr.yygh.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kotlin.jvm.Transient;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author lishishan
 * @date 2024/3/13/16:15
 */
@Data
@TableName("t_doctor")
public class Doctor   {

    /**
     * 主键id
     */
    @TableId
    private String id;
    /**
     * 医生工号
     */

    private String yishenggonghao;

    /**
     * 密码
     */

    private String mima;

    /**
     * 医生姓名
     */

    private String yishengxingming;

    /**
     * 性别
     */

    private String xingbie;

    /**
     * 职称
     */

    private String zhicheng;

    /**
     * 科室
     */

    private String keshi;

    /**
     * 手机
     */

    private String shouji;

    /**
     * 身份证
     */

    private String shenfenzheng;
}