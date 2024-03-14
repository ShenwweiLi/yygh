package edu.zjsr.yygh.vo;

import edu.zjsr.yygh.entity.Articles;
import edu.zjsr.yygh.entity.Doctor;
import lombok.Data;

import java.util.List;

/**
 * @Author lishishan
 * @date 2024/3/13/17:10
 */
@Data
public class DoctorListVo {

    private List<Doctor> doctorList;
    private Long total;
    private Integer current;
    private Integer pages;
}
