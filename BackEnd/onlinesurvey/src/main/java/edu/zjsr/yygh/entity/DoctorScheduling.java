package edu.zjsr.yygh.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * @author 郑为中
 */
@Data

@TableName("a_doctor_scheduling")
@ApiModel(value = "医生排班")
public class DoctorScheduling   {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医生ID")
    private String doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "就诊日期")
    private String date;

    @ApiModelProperty(value = "就诊编号")
    private String number;

    /**
     * 上午 | 下午
     */
    @ApiModelProperty(value = "时段")
    private String step;

    @ApiModelProperty(value = "是否预约")
    private int orderFlag;
}