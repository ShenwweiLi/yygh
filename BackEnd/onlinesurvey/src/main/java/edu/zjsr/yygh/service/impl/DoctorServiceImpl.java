package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.mapper.DoctorMapper;
import edu.zjsr.yygh.service.DoctorService;
import edu.zjsr.yygh.vo.DoctorListVo;
import org.springframework.stereotype.Service;

/**
 * @Author lishishan
 * @date 2024/3/13/16:37
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
    @Override
    public DoctorListVo findDoctorWithPagination(int currentPage, int pageSize, String search, String keshi) {
        Page<Doctor> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();

        // 如果有搜索词，增加名字字段的like条件
        if (search != null && !search.trim().isEmpty()) {
            queryWrapper.like("title", search); // 假设文章表中的名称字段为"name"
        }
        // 如果有选择类别，增加类别字段的条件
        if (keshi != null ) {
            queryWrapper.eq("category", keshi); // 假设文章表中的类别字段为"category"
        }

        // 执行分页查询
        IPage<Doctor> articlePage = this.page(page, queryWrapper);

        // 构建VO并返回
        DoctorListVo doctorListVo = new DoctorListVo();
        doctorListVo.setDoctorList(articlePage.getRecords());
        doctorListVo.setTotal(articlePage.getTotal());
        doctorListVo.setCurrent((int) articlePage.getCurrent());
        doctorListVo.setPages((int) articlePage.getPages());

        return doctorListVo;
    }
}
