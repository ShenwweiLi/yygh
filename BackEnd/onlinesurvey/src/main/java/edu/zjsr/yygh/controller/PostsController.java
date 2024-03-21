package edu.zjsr.yygh.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.config.UserRole;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.service.ICommentsService;
import edu.zjsr.yygh.service.IPostsService;
import edu.zjsr.yygh.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("yygh/posts")
@CrossOrigin
public class PostsController {
    @Autowired
    IPostsService postsService;
    @Autowired
    ICommentsService commentsService;


    String secret = "survey.yonatan.cn.secret";
    @PostMapping("insertOrUpdatePost")
    public Message<Posts> insertPost(@RequestHeader(value = "Authorization") String token , @RequestBody Posts post){
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

        post.setUserId(userId);
        System.out.println(post);
        boolean isSave = postsService.saveOrUpdate(post);
        Message<Posts> response = new Message<>();
        if (isSave){
            response.setCode("200");
            response.setMsg("添加帖子成功");
            response.setData(post);
        }else {
            response.setCode("500");
            response.setMsg("添加帖子失败");
            response.setData(post);
        }
        return response;
    }

    @GetMapping("listPost")
    @CrossOrigin
    public Message<PostsListVo> list(@RequestParam(value = "search",required = false) String PostName, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                     @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        Message<PostsListVo> response = new Message<>();
        PostsListVo postsListVo = postsService.selectPostList(currentPage,pageSize,PostName);
        response.setData(postsListVo);
        if (postsListVo != null && postsListVo.getPostsList() != null && !postsListVo.getPostsList().isEmpty()) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(postsListVo);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }
        return response;
    }
    @PostMapping("listAllPost")
    @CrossOrigin
    public Message listAllPost(@RequestParam Integer page,
                               @RequestParam Integer size) {
        Message message = new Message();
        try {
            List<ListPostsVo> userList = postsService.findAllPost(UserRole.ROLE_SYS_ADMIN, page, size);
            Integer count = postsService.getCountPost();
            JSONObject data = new JSONObject();
            data.put("postList", userList);
            data.put("count", count);
            message.setCode("200");
            message.setMsg("ok");
            message.setData(data);
        } catch (Exception e) {
            throw e;
        }
        return message;
    }


    @GetMapping("getPostById")
    public Message<PostsVo> getPostById(@RequestParam("PostId") Integer postId){
        Message<PostsVo> response = new Message<>();
        // 获取帖子详情
        Posts post = postsService.getById(postId);

        // 获取该帖子的评论列表
        List<CommentVo> comments = commentsService.selectCommentByPostIdList(postId);

        PostsVo postWithCommentsDTO = new PostsVo();
        postWithCommentsDTO.setPosts(post);
        postWithCommentsDTO.setCommentsList(comments);

            response.setCode("200");
            response.setMsg("Success");
        response.setData(postWithCommentsDTO);

        return response;
    }


    @PostMapping("delPost")
    public Message<PostsListVo> delPost(@RequestBody Posts posts){
        Message<PostsListVo> response = new Message<>();
        postsService.removeById(posts);
        response.setMsg("删除成功");
        return response ;
    }
}
