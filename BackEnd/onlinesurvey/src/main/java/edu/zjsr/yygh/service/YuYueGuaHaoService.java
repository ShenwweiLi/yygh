package edu.zjsr.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.entity.YuYueGuaHao;
import edu.zjsr.yygh.vo.DoctorListVo;
import edu.zjsr.yygh.vo.YuYueGuaHaoListVo;

/**
 * @Author lishishan
 * @date 2024/3/13/16:35
 */
public interface YuYueGuaHaoService  extends IService<YuYueGuaHao> {
    YuYueGuaHaoListVo findListrWithPagination(int currentPage, int pageSize, String search);
}
