package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Articles;
import edu.zjsr.yygh.service.ArticlesService;
import edu.zjsr.yygh.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lss
 * @since 2024-01-30
 */
@RestController
@CrossOrigin
@RequestMapping("yygh")
public class ArticlesController {


    @Autowired
    ArticlesService articlesService;


    @GetMapping("/articleList")
    @CrossOrigin
    public Message<ArticleVO> getArticlesWithPagination(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) Integer category) {
        System.out.println("ArticlesController---------search"+search);
        System.out.println("ArticlesController---------category"+category);
        ArticleVO articleVO = articlesService.findArticlesWithPagination(currentPage, pageSize, search, category);
        Message<ArticleVO> response = new Message<>();

        if (articleVO != null && articleVO.getArticleList() != null && !articleVO.getArticleList().isEmpty()) {
            response.setCode("200");
            response.setMsg("Success");
            response.setData(articleVO);
        } else {
            response.setCode("204");
            response.setMsg("No Content");
            response.setData(null);
        }

        return response;
    }

    @GetMapping("/selectArticleById")
    @CrossOrigin
    public Message<Articles> getArticleWithId(@RequestParam int ArticleId){
        Message<Articles> message = new Message<>();
        Articles article = articlesService.selectById(ArticleId);
        message.setData(article);
        message.setCode("200");
        message.setMsg("文章内容");
        return message;
    }

    @PostMapping("/addArticle")
    @CrossOrigin
    public Message<Articles> addArticle(@RequestBody Articles newArticle) {
        Message<Articles> message = new Message<>();
        boolean isSaved = articlesService.save(newArticle);
        if (isSaved) {
            message.setCode("200");
            message.setMsg("Article added successfully");
            message.setData(newArticle); // 或者返回保存后的文章对象（如果它包含了新生成的ID等信息）
        } else {
            message.setCode("500");
            message.setMsg("An error occurred while adding the article");
            message.setData(null);
        }
        return message;
    }
    @PostMapping("/updateArticle")
    @CrossOrigin
    public Message<Articles> updateArticle(@RequestBody Articles newArticle) {
        Message<Articles> message = new Message<>();
        boolean isUpdate = articlesService.updateById(newArticle);
        if (isUpdate) {
            message.setCode("200");
            message.setMsg("Article update successfully");
            message.setData(newArticle); // 或者返回保存后的文章对象（如果它包含了新生成的ID等信息）
        } else {
            message.setCode("500");
            message.setMsg("An error occurred while updating the article");
            message.setData(null);
        }
        return message;
    }

    @PostMapping("/delArticle")
    @CrossOrigin
    public Message<Articles> delArticle(@RequestBody Articles newArticle) {
        Message<Articles> message = new Message<>();
        articlesService.removeById(newArticle.getId());
        return message;
    }


}
