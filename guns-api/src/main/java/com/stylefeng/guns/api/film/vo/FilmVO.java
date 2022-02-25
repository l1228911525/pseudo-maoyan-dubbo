package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class FilmVO implements Serializable {

    private Integer filmNum;
    private Integer totalPage;
    private Integer nowPage;
    private List<FilmInfo> filmInfo = new LinkedList<>();

}
