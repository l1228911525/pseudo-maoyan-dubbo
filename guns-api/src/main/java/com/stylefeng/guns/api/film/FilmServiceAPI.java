package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;

import java.util.List;

public interface FilmServiceAPI {

    // 1.获取Banner的信息
    public List<BannerVO> getBanners();

    // 2.获取正在热映的电影
    FilmVO getHotFilms(boolean isLimit, int nums);

    // 3.即将上映的电影
    FilmVO getSoonFilms(boolean isLimit, int nums);

    // 4.票房排行榜
    List<FilmInfo> getBoxRanking();

    // 5.获取受欢迎的榜单
    List<FilmInfo> getExpectRanking();

    // 6.获取前一百
    List<FilmInfo> getTop();

}
