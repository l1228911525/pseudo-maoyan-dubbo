package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class FilmIndexVO {

    private List<BannerVO> banners = new LinkedList<>();
    private FilmVO hotFilms = new FilmVO();
    private FilmVO soonFilms = new FilmVO();
    private List<FilmInfo> boxRanking = new LinkedList<>();
    private List<FilmInfo> expectRanking = new LinkedList<>();
    private List<FilmInfo> top100 = new LinkedList<>();

}
