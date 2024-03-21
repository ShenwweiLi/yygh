package edu.zjsr.yygh.mapper;

import edu.zjsr.yygh.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zjsr.yygh.vo.ListPostsVo;
import edu.zjsr.yygh.vo.PostsListVo;
import edu.zjsr.yygh.vo.PostsVo;
import edu.zjsr.yygh.vo.UserManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lss
 * @since 2024-02-04
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

    @Select("<script>" +
            "select * " +
            "from t_posts where is_delete=0 " +
            "order by updated_date desc " +
            "<if test=\"start != null and limit != null\">\n" +
            "  limit #{start}, #{limit}\n" +
            "</if>" +
            "</script>")
    List<ListPostsVo> findAllPostsByStatus(Integer status, Integer start, Integer limit);

    @Select("select count(*) from t_posts where is_delete=0 ")
    Integer getCountPost();

}
