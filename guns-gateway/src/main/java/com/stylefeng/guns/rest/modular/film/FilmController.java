package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.CatVO;
import com.stylefeng.guns.api.film.vo.SourceVO;
import com.stylefeng.guns.api.film.vo.YearVO;
import com.stylefeng.guns.api.vo.ResponseVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    @Reference(interfaceClass = FilmServiceAPI.class)
    FilmServiceAPI filmServiceAPI;

    // 获取首页信息接口
    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public ResponseVO getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        // 1.获取Banner的信息
        filmIndexVO.setBanners(filmServiceAPI.getBanners());
        // 2.获取正在热映的电影
        filmIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true, 8));

        // 3.即将上映的电影
        filmIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true, 8));

        // 4.票房排行榜
        filmIndexVO.setBoxRanking(filmServiceAPI.getBoxRanking());

        // 5.获取受欢迎的榜单
        filmIndexVO.setExpectRanking(filmServiceAPI.getExpectRanking());

        // 6.获取前一百
        filmIndexVO.setTop100(filmServiceAPI.getTop());

        return ResponseVO.success(IMG_PRE, filmIndexVO);
    }

    @RequestMapping(value = "/getConditionList", method = RequestMethod.GET)
    public ResponseVO getConditionList(
            @RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
            @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
            @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {

        // 类型集
        boolean flag = false;
        List<CatVO> cats = filmServiceAPI.getCats();
        List<CatVO> catResult = new ArrayList<>();
        CatVO cat = null;
        for (CatVO catVO : cats) {
            if("99".equals(catVO.getCatId())) {
                cat = catVO;
                continue;
            }

            if(catVO.getCatId().equals(catId)) {
                flag = true;
                catVO.setActive(true);
            }

            catResult.add(catVO);

        }
        if(!flag) {
            cat.setActive(true);
        } else {
            cat.setActive(false);
        }

        catResult.add(cat);


        // 片源集合
        flag = false;
        List<SourceVO> sources = filmServiceAPI.getSources();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO source = null;
        for (SourceVO sourceVO : sources) {
            if("99".equals(sourceVO.getSourceId())) {
                source = sourceVO;
                continue;
            }

            if(sourceVO.getSourceId().equals(sourceId)) {
                flag = true;
                sourceVO.setActive(true);
            }

            sourceResult.add(sourceVO);

        }
        if(!flag) {
            source.setActive(true);
        } else {
            source.setActive(false);
        }
        sourceResult.add(source);


        // 年代集合
        flag = false;
        List<YearVO> years = filmServiceAPI.getYears();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO year = null;
        for (YearVO yearVO : years) {
            if("99".equals(yearVO.getYearId())) {
                year = yearVO;
                continue;
            }

            if(yearVO.getYearId().equals(sourceId)) {
                flag = true;
                yearVO.setActive(true);
            }

            yearResult.add(yearVO);

        }
        if(!flag) {
            year.setActive(true);
        } else {
            year.setActive(false);
        }
        yearResult.add(year);

        FilmConditionVO filmConditionVO = new FilmConditionVO();

        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourceResult);
        filmConditionVO.setYearInfo(yearResult);

        return ResponseVO.success(filmConditionVO);
    }

}
