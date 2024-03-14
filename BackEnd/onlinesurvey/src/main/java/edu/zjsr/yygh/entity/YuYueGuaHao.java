package edu.zjsr.yygh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author lishishan
 * @date 2024/3/13/16:15
 */

@Data
@TableName("t_yuyueguahao")
public class YuYueGuaHao {

    /**
     * 主键id
     */
    @TableId
    private Long id;


    /**
     * 医生工号
     */

    private String yishenggonghao;

    /**
     * 医生姓名
     */

    private String yishengxingming;

    /**
     * 挂号费用
     */

    private Integer guahaofeiyong;

    /**
     * 挂号时间
     */

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date guahaoshijian;

    /**
     * 备注
     */

    private String beizhu;

    /**
     * 账号
     */

    private String zhanghao;

    /**
     * 姓名
     */

    private String xingming;

    /**
     * 是否审核
     */

    private String sfsh;

    /**
     * 审核回复
     */

    private String shhf;

    /**
     * 是否支付
     */

    private String ispay;


    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date addtime;
}
