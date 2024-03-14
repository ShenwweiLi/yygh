package edu.zjsr.yygh.vo;

import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.entity.YuYueGuaHao;
import lombok.Data;

import java.util.List;

/**
 * @Author lishishan
 * @date 2024/3/14/9:34
 */

@Data
public class YuYueGuaHaoListVo {

    private List<YuYueGuaHao> yueGuaHaoList;
    private Long total;
    private Integer current;
    private Integer pages;
}
