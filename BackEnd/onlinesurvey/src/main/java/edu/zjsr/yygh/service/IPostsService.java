package edu.zjsr.yygh.service;

import edu.zjsr.yygh.config.UserRole;
import edu.zjsr.yygh.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.vo.ListPostsVo;
import edu.zjsr.yygh.vo.PostsListVo;
import edu.zjsr.yygh.vo.PostsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lss
 * @since 2024-02-04
 */
public interface IPostsService extends IService<Posts> {

    PostsListVo selectPostList(int currentPage, int pageSize, String PostName);

    List<ListPostsVo> findAllPost(UserRole roleSysAdmin, Integer page, Integer size);

    Integer getCountPost();
}
