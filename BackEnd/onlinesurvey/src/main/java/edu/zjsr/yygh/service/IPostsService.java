package edu.zjsr.yygh.service;

import edu.zjsr.yygh.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.vo.PostsListVo;

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
}
