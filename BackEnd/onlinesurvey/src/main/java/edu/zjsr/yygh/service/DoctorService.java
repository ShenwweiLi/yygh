package edu.zjsr.yygh.service;


import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.vo.DoctorListVo;

/**
 * @Author lishishan
 * @date 2024/3/13/16:35
 */

public interface DoctorService extends IService<Doctor> {

    DoctorListVo findDoctorWithPagination(int currentPage, int pageSize, String doctorName, String keshi );
   	
}

