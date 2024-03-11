package edu.zjsr.yygh.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kotlin.jvm.Transient;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 郑为中
 */
@Data
@TableName("a_doctor")
@ApiModel(value = "医生")
public class Doctor   {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医生年龄")
    private int age;

    @ApiModelProperty(value = "学历学位")
    private String education;

    @ApiModelProperty(value = "开始工作时间")
    private String workDate;

    @ApiModelProperty(value = "毕业学校")
    private String university;

    @ApiModelProperty(value = "职称")
    private String postLevel;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "所属科室ID")
    private String subjectId;

    @ApiModelProperty(value = "所属科室")
    private String subjectName;

    @ApiModelProperty(value = "医生介绍")
    private String about;

    @ApiModelProperty(value = "医生照片")
    private String photo;

    @ApiModelProperty(value = "挂号费用")
    private BigDecimal orderMoney;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否当日")
    private String noeDate;
}