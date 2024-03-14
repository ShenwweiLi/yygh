package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Notifications;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.service.ICommentsService;
import edu.zjsr.yygh.service.INotificationsService;
import edu.zjsr.yygh.service.IPostsService;
import edu.zjsr.yygh.vo.ArticleVO;
import edu.zjsr.yygh.vo.CommentListVo;
import edu.zjsr.yygh.vo.CommentVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lss
 * @since 2024-02-04
 */
@RestController
@RequestMapping("yygh/comments")
public class CommentsController {
    @Autowired
    ICommentsService commentsService;
    @Autowired
    INotificationsService notificationsService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    IPostsService postsService;


    @PostMapping("addComment")
    public Message<Comments> addComment(@RequestBody Comments comment){
        Message<Comments> response = new Message<>();
        boolean isSave = commentsService.save(comment);
        if (isSave){
            response.setCode("200");
            response.setMsg("添加评论成功");
            response.setData(comment);

            // 构建通知消息
            Map<String, Object> notificationInfo = new HashMap<>();
            // 帖子ID
            notificationInfo.put("postId", comment.getPostId());
            // 评论者ID
            notificationInfo.put("commenterId", comment.getUserId());

            //获取post的详细详细
            Posts posts = postsService.getById(comment.getPostId());
            // 帖子拥有者ID
            notificationInfo.put("targetUserId", posts.getUserId());

          }else {
            response.setCode("500");
            response.setMsg("添加评论失败");
            response.setData(comment);
        }
        return response;
    }


    @PostMapping("delComment")
    public Message<Comments> delComment(@RequestBody Comments comment) {
        Message<Comments> response = new Message<>();
        postsService.removeById(comment.getId());
        return response;
    }

    @GetMapping("/commentsList")
    @CrossOrigin
    public Message<CommentListVo> getCommentsWithPagination(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "PostId",required = false) int PostId) {
        CommentListVo commentListVo = commentsService.getCommentsWithPagination(currentPage, pageSize,PostId);
        Message<CommentListVo> response = new Message<>();

        if (commentListVo != null && commentListVo.getCommentVoList() != null && !commentListVo.getCommentVoList().isEmpty()) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(commentListVo);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }

        return response;
    }
}
