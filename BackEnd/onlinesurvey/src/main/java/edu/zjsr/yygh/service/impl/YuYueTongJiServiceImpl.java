package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.entity.Articles;
import edu.zjsr.yygh.entity.Doctor;
import edu.zjsr.yygh.entity.YuYueTongJi;
import edu.zjsr.yygh.mapper.DoctorMapper;
import edu.zjsr.yygh.mapper.YuYueTongJiMapper;
import edu.zjsr.yygh.service.DoctorService;
import edu.zjsr.yygh.service.YuYueTongJiService;
import edu.zjsr.yygh.vo.ArticleVO;
import edu.zjsr.yygh.vo.DoctorListVo;
import org.springframework.stereotype.Service;

/**
 * @Author lishishan
 * @date 2024/3/13/16:47
 */
@Service

public class YuYueTongJiServiceImpl extends ServiceImpl<YuYueTongJiMapper, YuYueTongJi> implements YuYueTongJiService {

}