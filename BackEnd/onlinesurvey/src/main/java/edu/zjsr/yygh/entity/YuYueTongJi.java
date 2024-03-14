package edu.zjsr.yygh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lishishan
 * @date 2024/3/13/16:12
 */

@Data
@TableName("t_yuyue")
public class YuYueTongJi {

    /**
     * 主键id
     */
    @TableId
    private Long id;
    /**
     * 科室名称
     */

    private String keshimingcheng;

    /**
     * 科室位置
     */

    private String keshiweizhi;

    /**
     * 医生姓名
     */

    private String yishengxingming;

    /**
     * 预约人数
     */

    private Integer yuyuerenshu;

    /**
     * 接诊人数
     */

    private Integer jiezhenrenshu;

    /**
     * 日期
     */

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat
    private Date riqi;

    /**
     * 备注
     */

    private String beizhu;


    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date addtime;
}
