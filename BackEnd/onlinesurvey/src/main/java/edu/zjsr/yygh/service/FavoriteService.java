package edu.zjsr.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zjsr.yygh.entity.Favorite;

/**
 * @Author: lss
 * @Date: 2024/1/18 16:21
 */
public interface FavoriteService extends IService<Favorite> {

     void addFavorite(Favorite favorite) ;

     boolean selectFavorite(Favorite favorite);

     boolean removeFavorite(Favorite favorite);
}
