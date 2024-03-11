package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Notifications;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.service.ICommentsService;
import edu.zjsr.yygh.service.INotificationsService;
import edu.zjsr.yygh.service.IPostsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

            // 发送消息
            rabbitTemplate.convertAndSend("notificationExchange", "notificationRoutingKey", notificationInfo);
        }else {
            response.setCode("500");
            response.setMsg("添加评论失败");
            response.setData(comment);
        }
        return response;
    }
}
