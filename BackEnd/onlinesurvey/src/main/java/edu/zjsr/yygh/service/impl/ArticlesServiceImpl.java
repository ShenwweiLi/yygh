package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zjsr.yygh.entity.Articles;
import edu.zjsr.yygh.mapper.ArticlesMapper;
import edu.zjsr.yygh.service.ArticlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lss
 * @since 2024-01-30
 */
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles> implements ArticlesService {

    @Autowired
    ArticlesMapper articlesMapper;

    @Override
    public ArticleVO findArticlesWithPagination(int currentPage, int pageSize) {
        IPage<Articles> page = new Page<>(currentPage, pageSize);
        IPage<Articles> articlePage = this.page(page);
        ArticleVO articleVO = new ArticleVO();
        articleVO.setArticleList(articlePage.getRecords());
        articleVO.setTotal(articlePage.getTotal());
        articleVO.setCurrent((int)articlePage.getCurrent());
        articleVO.setPages((int)articlePage.getPages());
        return articleVO;
    }

    @Override
    public Articles selectById(int id) {
        return articlesMapper.selectByIdWithAuthor(id);
    }

    @Override
    public ArticleVO findArticlesWithPagination(int currentPage, int pageSize, String search, Integer category) {
        Page<Articles> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();

        // 如果有搜索词，增加名字字段的like条件
        if (search != null && !search.trim().isEmpty()) {
            queryWrapper.like("title", search); // 假设文章表中的名称字段为"name"
        }
        // 如果有选择类别，增加类别字段的条件
        if (category != null ) {
            queryWrapper.eq("category", category); // 假设文章表中的类别字段为"category"
        }

        // 执行分页查询
        IPage<Articles> articlePage = this.page(page, queryWrapper);

        // 构建ArticleVO并返回
        ArticleVO articleVO = new ArticleVO();
        articleVO.setArticleList(articlePage.getRecords());
        articleVO.setTotal(articlePage.getTotal());
        articleVO.setCurrent((int) articlePage.getCurrent());
        articleVO.setPages((int) articlePage.getPages());

        return articleVO;
    }
}
