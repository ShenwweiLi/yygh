package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.entity.User;
import edu.zjsr.yygh.mapper.CommentsMapper;
import edu.zjsr.yygh.mapper.UserMapper;
import edu.zjsr.yygh.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.vo.CommentVo;
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
}
