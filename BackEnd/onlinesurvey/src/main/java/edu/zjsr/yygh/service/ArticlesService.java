package edu.zjsr.yygh.service;

import edu.zjsr.yygh.entity.Articles;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.vo.ArticleVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lss
 * @since 2024-01-30
 */
public interface ArticlesService extends IService<Articles> {
    ArticleVO findArticlesWithPagination(int currentPage, int pageSize);

    Articles selectById(int id);

    ArticleVO findArticlesWithPagination(int currentPage, int pageSize, String search, Integer category);
}
