package edu.zjsr.yygh.controller;

import edu.zjsr.common.utils.Message;
import edu.zjsr.yygh.entity.Favorite;
import edu.zjsr.yygh.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lss
 * @Date: 2024/1/18 16:34
 */

@RestController
@RequestMapping("yygh")
@CrossOrigin
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @PostMapping("addFavorite")
    public Message<Favorite> addFavorite(@RequestBody Favorite favorite){
        Message<Favorite> message = new Message<>();
        favoriteService.addFavorite(favorite);
        message.setCode("200");
        message.setMsg("OK");
        return  message;
    }

    @PostMapping("selectFavorite")
    public Message<Boolean> selectFavorite(@RequestBody Favorite favorite){
        Message<Boolean> message = new Message<>();
     Boolean flag =   favoriteService.selectFavorite(favorite);
        message.setData(flag);
        message.setCode("200");
        message.setMsg("成功");
        return message;
    }

    @PostMapping("removeFavorite")
    public Message<Boolean> removeFavorite(@RequestBody Favorite favorite){
        Message<Boolean> message = new Message<>();
        Boolean flag =   favoriteService.removeFavorite(favorite);
        if (flag){
            message.setData(flag);
            message.setCode("200");
            message.setMsg("删除成功");
            return message;
        }else {
            message.setCode("400");
            message.setMsg("删除失败");
            return message;
        }
    }

}
