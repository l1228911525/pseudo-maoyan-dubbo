package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.api.film.vo.CatVO;
import com.stylefeng.guns.api.film.vo.SourceVO;
import com.stylefeng.guns.api.film.vo.YearVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilmConditionVO {

    private List<CatVO> catInfo = new ArrayList<>();
    private List<SourceVO> sourceInfo = new ArrayList<>();
    private List<YearVO> yearInfo = new ArrayList<>();

}
