package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.entity.User;
import edu.zjsr.yygh.entity.YuYueGuaHao;
import edu.zjsr.yygh.mapper.CommentsMapper;
import edu.zjsr.yygh.mapper.UserMapper;
import edu.zjsr.yygh.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.vo.CommentListVo;
import edu.zjsr.yygh.vo.CommentVo;
import edu.zjsr.yygh.vo.YuYueGuaHaoListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lss
 * @since 2024-02-04
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {


    @Resource
    CommentsMapper  commentsMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public List<CommentVo> selectCommentByPostIdList(Integer postId) {
        LambdaQueryWrapper<Comments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comments::getPostId,postId);
        List<Comments> comments =    commentsMapper.selectList(queryWrapper);

        List<CommentVo> commentVos = new ArrayList<>();
        for (Comments comment : comments) {
            User user = userMapper.findUserById(comment.getUserId()); // 假设你有个方法来根据 ID 获取用户
            CommentVo commentDTO = new CommentVo();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUsername(user.getUsername()); // 设置用户名
            commentVos.add(commentDTO);
        }

      return commentVos ;
    }

    @Override
    public CommentListVo getCommentsWithPagination(int currentPage, int pageSize, int postId) {
//        Page<Comments> page = new Page<>(currentPage, pageSize);
//        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
//
//
//            queryWrapper.like("post_id", postId); // 假设文章表中的名称字段为"name"
//
//        // 执行分页查询
//        IPage<Comments> guaHaoPage = this.page(page, queryWrapper);
//
//        // 构建VO并返回
//        CommentListVo guaHaoListVo = new CommentListVo();
//        guaHaoListVo.setCommentVoList(guaHaoPage.getRecords());
//        guaHaoListVo.setTotal(guaHaoPage.getTotal());
//        guaHaoListVo.setCurrent((int) guaHaoPage.getCurrent());
//        guaHaoListVo.setPages((int) guaHaoPage.getPages());
//
//        return guaHaoListVo;
        return  null;
    }
}
