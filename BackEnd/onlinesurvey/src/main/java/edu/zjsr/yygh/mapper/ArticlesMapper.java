package edu.zjsr.yygh.mapper;

import edu.zjsr.yygh.entity.Articles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lss
 * @since 2024-01-30
 */
@Mapper
public interface ArticlesMapper extends BaseMapper<Articles> {

    Articles selectByIdWithAuthor(int id);
}
