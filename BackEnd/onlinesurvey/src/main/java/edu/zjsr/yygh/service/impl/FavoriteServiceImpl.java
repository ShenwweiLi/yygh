package edu.zjsr.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zjsr.yygh.entity.Favorite;
import edu.zjsr.yygh.mapper.FavoriteMapper;
import edu.zjsr.yygh.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lss
 * @Date: 2024/1/18 16:21
 */

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public void addFavorite(Favorite favorite) {
      favoriteMapper.insert(favorite);
    }

    @Override
    public boolean  selectFavorite(Favorite favorite) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",favorite.getUserId());
        queryWrapper.eq("article_id",favorite.getArticleId());
        long  count = favoriteMapper.selectCount(queryWrapper);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(count);
        return count>0;
    }

    @Override
    public boolean removeFavorite(Favorite favorite) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",favorite.getUserId());
        queryWrapper.eq("article_id",favorite.getArticleId());
        int  count =    favoriteMapper.delete(queryWrapper);
        return count>0;
    }
}
