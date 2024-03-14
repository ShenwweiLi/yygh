package edu.zjsr.yygh.service;

import edu.zjsr.yygh.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.vo.CommentListVo;
import edu.zjsr.yygh.vo.CommentVo;
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
public interface ICommentsService extends IService<Comments> {

    List<CommentVo> selectCommentByPostIdList(Integer postId);

    CommentListVo getCommentsWithPagination(int currentPage, int pageSize, int postId);
}
