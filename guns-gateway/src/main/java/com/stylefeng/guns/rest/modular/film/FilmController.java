package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.api.vo.ResponseVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRuqeustVO;
import org.springframework.web.bind.annotation.*;

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
        // 2.获取正在热映的电影 isLimit：用来判断是首页还是列表页，首页为true，列表页为false
        filmIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true, 8, 1, 1, 99, 99, 99));

        // 3.即将上映的电影
        filmIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true, 8, 1, 1, 99, 99, 99));

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

    @RequestMapping(value = "getFilms", method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRuqeustVO filmRuqeustVO) {

        FilmVO filmVO = new FilmVO();
        // 根据showType判断影片查询类型
        switch (filmRuqeustVO.getShowType()) {
            case 1:
                filmVO = filmServiceAPI.getHotFilms(false, filmRuqeustVO.getPageSize(), filmRuqeustVO.getNowPage(),
                        filmRuqeustVO.getSortId(), filmRuqeustVO.getSourceId(), filmRuqeustVO.getYearId(), filmRuqeustVO.getCatId());
                break;
            case 2:
                filmVO = filmServiceAPI.getSoonFilms(false, filmRuqeustVO.getPageSize(), filmRuqeustVO.getNowPage(),
                        filmRuqeustVO.getSortId(), filmRuqeustVO.getSourceId(), filmRuqeustVO.getYearId(), filmRuqeustVO.getCatId());
                break;
            case 3:
                filmVO = filmServiceAPI.getClassicFilms(filmRuqeustVO.getPageSize(), filmRuqeustVO.getNowPage(),
                        filmRuqeustVO.getSortId(), filmRuqeustVO.getSourceId(), filmRuqeustVO.getYearId(), filmRuqeustVO.getCatId());
            default:
                filmVO = filmServiceAPI.getHotFilms(false, filmRuqeustVO.getPageSize(), filmRuqeustVO.getNowPage(),
                        filmRuqeustVO.getSortId(), filmRuqeustVO.getSourceId(), filmRuqeustVO.getYearId(), filmRuqeustVO.getCatId());
                break;
        }

        return ResponseVO.success(filmVO.getNowPage(),filmVO.getTotalPage(), IMG_PRE, filmVO.getFilmInfo());

    }

    @RequestMapping(value = "/films/{searchParam}", method = RequestMethod.GET)
    public ResponseVO films(@PathVariable(value = "searchParam") String searchParam, Integer searchType) {

        // 根据searchType来判断查询类型
        FilmDetailVO filmDetailVO = filmServiceAPI.getFilmDetailVO(searchType, searchParam);

        // 不同的查询类型，传入的条件会略有不同

        // 查询影片的详细信息 -> dubbo的异步获取

        // 获取影片描述信息

        // 获取图片信息

        // 获取演员信息

    }

}
