package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Comments;
import edu.zjsr.yygh.entity.Posts;
import edu.zjsr.yygh.service.ICommentsService;
import edu.zjsr.yygh.service.IPostsService;
import edu.zjsr.yygh.vo.ArticleVO;
import edu.zjsr.yygh.vo.CommentVo;
import edu.zjsr.yygh.vo.PostsListVo;
import edu.zjsr.yygh.vo.PostsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("insertOrUpdatePost")
    public Message<Posts> insertPost(@RequestBody Posts post){

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
