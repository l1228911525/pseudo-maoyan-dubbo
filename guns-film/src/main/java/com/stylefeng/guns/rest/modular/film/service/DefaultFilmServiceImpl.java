package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service(interfaceClass = FilmServiceAPI.class)
@Component
public class DefaultFilmServiceImpl implements FilmServiceAPI {

    @Autowired
    private MoocBannerTMapper bannerTMapper;

    @Autowired
    private MoocFilmTMapper filmTMapper;

    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;

    @Override
    public List<BannerVO> getBanners() {
        List<MoocBannerT> bannerTList = bannerTMapper.selectList(null);

        List<BannerVO> bannerVOList = new LinkedList<>();

        for (MoocBannerT moocBannerT : bannerTList) {

            BannerVO bannerVO = new BannerVO();

            bannerVO.setBannerId(moocBannerT.getUuid() + "");
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());

            bannerVOList.add(bannerVO);

        }

        return bannerVOList;
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilmTList) {
        List<FilmInfo> filmInfoList = new ArrayList<>();

        for (MoocFilmT moocFilmT : moocFilmTList) {
            FilmInfo filmInfo = new FilmInfo();

            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid() + "");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            filmInfoList.add(filmInfo);
        }

        return filmInfoList;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        // 判断是否是首页需要的内容
        if(isLimit) {
            // 如果是，则限制条数，限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1, nums);

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setFilmInfo(filmInfos);
        } else {
            // 如果不是，则为列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(nowPage, nums);

            //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }

            if(sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }

            if(yearId != 99) {

                entityWrapper.eq("film_date", yearId);
            }

            if(catId != 99) {
                String conditionStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", conditionStr);
            }

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(filmInfos.size());

            Integer totalCount = filmTMapper.selectCount(entityWrapper);

            int totalPages = (totalCount / nums) + 1;

            filmVO.setFilmInfo(filmInfos);

            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);
        }
        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        // 判断是否是首页需要的内容
        if(isLimit) {
            // 如果是，则限制条数，限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1, nums);

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setFilmInfo(filmInfos);
        } else {
            // 如果不是，则为列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(nowPage, nums);

            //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }


            if(sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }

            if(yearId != 99) {

                entityWrapper.eq("film_date", yearId);
            }

            if(catId != 99) {
                String conditionStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", conditionStr);
            }

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(filmInfos.size());

            Integer totalCount = filmTMapper.selectCount(entityWrapper);

            int totalPages = (totalCount / nums) + 1;

            filmVO.setFilmInfo(filmInfos);

            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);
        }
        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "3");

        // 如果不是，则为列表页，同样需要限制内容为热映影片
        Page<MoocFilmT> page = new Page<>(nowPage, nums);

        //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
        switch (sortId) {
            case 1:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }


        if(sourceId != 99) {
            entityWrapper.eq("film_source", sourceId);
        }

        if(yearId != 99) {

            entityWrapper.eq("film_date", yearId);
        }

        if(catId != 99) {
            String conditionStr = "%#" + catId + "#%";
            entityWrapper.like("film_cats", conditionStr);
        }

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

        filmInfos = getFilmInfos(moocFilms);
        filmVO.setFilmNum(filmInfos.size());

        Integer totalCount = filmTMapper.selectCount(entityWrapper);

        int totalPages = (totalCount / nums) + 1;

        filmVO.setFilmInfo(filmInfos);

        filmVO.setTotalPage(totalPages);
        filmVO.setNowPage(nowPage);

        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // 条件 -> 正在上映的，票房前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // 条件 -> 已经上映的，预售前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        // 条件 -> 经典影片，打分前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {

        List<CatVO> catVOList = new ArrayList<>();

        List<MoocCatDictT> moocCatDictTS = moocCatDictTMapper.selectList(null);

        for (MoocCatDictT moocCatDictT : moocCatDictTS) {

            CatVO catVO = new CatVO();

            catVO.setCatId(moocCatDictT.getUuid() + "");
            catVO.setCatName(moocCatDictT.getShowName());

            catVOList.add(catVO);

        }

        return catVOList;

    }

    @Override
    public List<SourceVO> getSources() {

        List<SourceVO> sourceVOList = new ArrayList<>();

        List<MoocSourceDictT> moocSourceDictTS = moocSourceDictTMapper.selectList(null);

        for (MoocSourceDictT moocSourceDictT : moocSourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictT.getUuid() + "");
            sourceVO.setSourceName(moocSourceDictT.getShowName());

            sourceVOList.add(sourceVO);
        }

        return sourceVOList;
    }

    @Override
    public List<YearVO> getYears() {

        List<YearVO> yearVOList = new ArrayList<>();

        List<MoocYearDictT> moocYearDictTS = moocYearDictTMapper.selectList(null);

        for (MoocYearDictT moocYearDictT : moocYearDictTS) {
            YearVO yearVO = new YearVO();

            yearVO.setYearId(moocYearDictT.getUuid() + "");
            yearVO.setYearName(moocYearDictT.getShowName());

            yearVOList.add(yearVO);
        }

        return yearVOList;
    }
}
