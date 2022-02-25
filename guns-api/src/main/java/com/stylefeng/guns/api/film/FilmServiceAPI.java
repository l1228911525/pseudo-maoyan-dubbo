package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceAPI {

    // 1.获取Banner的信息
    public List<BannerVO> getBanners();

    // 2.获取正在热映的电影
    FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 3.即将上映的电影
    FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 获取经典影片
    FilmVO getClassicFilms( int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    // 4.票房排行榜
    List<FilmInfo> getBoxRanking();

    // 5.获取受欢迎的榜单
    List<FilmInfo> getExpectRanking();

    // 6.获取前一百
    List<FilmInfo> getTop();

    // 获取类型
    List<CatVO> getCats();

    // 获取源
    List<SourceVO> getSources();

    // 获取年份信息
    List<YearVO> getYears();

}
