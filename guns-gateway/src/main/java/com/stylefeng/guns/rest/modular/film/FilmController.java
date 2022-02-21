package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.vo.ResponseVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
