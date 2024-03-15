package edu.zjsr.yygh.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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
@CrossOrigin
public class CommentsController {
    @Autowired
    ICommentsService commentsService;
    @Autowired
    INotificationsService notificationsService;

    @Autowired
    IPostsService postsService;

    String secret = "survey.yonatan.cn.secret";
    @PostMapping("addComment")
    @CrossOrigin
    public Message<Comments> addComment(@RequestBody Comments comment,@RequestHeader(value="Authorization") String token){
        // 使用相同的密钥和算法构建JWT验证器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // 验证token并解析token
        DecodedJWT jwt = verifier.verify(token);

        // 获取token中包含的所有claims
        Map<String, Claim> claims = jwt.getClaims();

        // 遍历claims，获取键值对
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().asString());
        }

        // 直接通过claim名称获取特定的claim
        String userId = jwt.getClaim("id").asString();
        Message<Comments> response = new Message<>();
        comment.setUserId(userId);
        System.out.println(comment.toString());
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
    @CrossOrigin
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
