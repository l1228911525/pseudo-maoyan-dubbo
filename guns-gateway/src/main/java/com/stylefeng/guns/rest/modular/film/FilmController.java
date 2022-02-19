package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.api.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmController {

    // 获取首页信息接口
    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public ResponseVO getIndex() {

        // 1.获取Banner的信息

        // 2.获取正在热映的电影

        // 3.即将上映的电影

        // 4.票房排行榜

        // 5.获取受欢迎的榜单

        // 6.获取前一百

        return null;
    }

}
