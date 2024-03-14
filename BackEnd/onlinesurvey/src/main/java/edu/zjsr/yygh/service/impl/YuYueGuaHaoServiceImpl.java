package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.entity.YuYueGuaHao;
import edu.zjsr.yygh.mapper.YuYueGuaHaoMapper;
import edu.zjsr.yygh.service.YuYueGuaHaoService;

import edu.zjsr.yygh.vo.DoctorListVo;
import edu.zjsr.yygh.vo.YuYueGuaHaoListVo;
import org.springframework.stereotype.Service;

/**
 * @Author lishishan
 * @date 2024/3/13/16:44
 */
@Service
public class YuYueGuaHaoServiceImpl extends ServiceImpl<YuYueGuaHaoMapper, YuYueGuaHao> implements YuYueGuaHaoService {
    @Override
    public YuYueGuaHaoListVo findListrWithPagination(int currentPage, int pageSize, String search) {
        Page<YuYueGuaHao> page = new Page<>(currentPage, pageSize);
        QueryWrapper<YuYueGuaHao> queryWrapper = new QueryWrapper<>();

        // 如果有搜索词，增加名字字段的like条件
        if (search != null && !search.trim().isEmpty()) {
            queryWrapper.like("title", search); // 假设文章表中的名称字段为"name"
        }
//        // 如果有选择类别，增加类别字段的条件
//        if (keshi != null ) {
//            queryWrapper.eq("category", keshi); // 假设文章表中的类别字段为"category"
//        }
        // 执行分页查询
        IPage<YuYueGuaHao> guaHaoPage = this.page(page, queryWrapper);

        // 构建VO并返回
        YuYueGuaHaoListVo guaHaoListVo = new YuYueGuaHaoListVo();
        guaHaoListVo.setYueGuaHaoList(guaHaoPage.getRecords());
        guaHaoListVo.setTotal(guaHaoPage.getTotal());
        guaHaoListVo.setCurrent((int) guaHaoPage.getCurrent());
        guaHaoListVo.setPages((int) guaHaoPage.getPages());

        return guaHaoListVo;
    }
}
