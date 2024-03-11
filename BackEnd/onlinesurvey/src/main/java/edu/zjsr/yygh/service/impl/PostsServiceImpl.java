package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.mapper.PostsMapper;
import edu.zjsr.yygh.service.IPostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.vo.PostsListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lss
 * @since 2024-02-04
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    @Resource
    PostsMapper postsMapper;
    @Override
    public PostsListVo selectPostList(int currentPage, int pageSize, String postName) {
        LambdaQueryWrapper<Posts> queryWrapper = new LambdaQueryWrapper<>();

        // 检查postName是否非空，然后加入筛选条件
        if (postName != null && !postName.trim().isEmpty()) {
            queryWrapper.like(Posts::getTitle, postName);
        }

        Page<Posts> page = new Page<>(currentPage, pageSize);


        // 执行分页查询
        IPage<Posts> PostsPage = this.page(page, queryWrapper);

        // 构建ArticleVO并返回
        PostsListVo postsListVO = new PostsListVo();
        postsListVO.setPostsList(PostsPage.getRecords());
        postsListVO.setTotal(PostsPage.getTotal());
        postsListVO.setCurrent((int) PostsPage.getCurrent());
        postsListVO.setPages((int) PostsPage.getPages());

        return postsListVO;
    }


}
