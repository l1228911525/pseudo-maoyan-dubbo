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

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Autowired
    private MoocActorTMapper moocActorTMapper;

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
        // ????????????????????????????????????
        if(isLimit) {
            // ?????????????????????????????????????????????????????????
            Page<MoocFilmT> page = new Page<>(1, nums);

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setFilmInfo(filmInfos);
        } else {
            // ????????????????????????????????????????????????????????????????????????
            Page<MoocFilmT> page = new Page<>(nowPage, nums);

            //???????????????1-??????????????????2-??????????????????3-???????????????
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
        // ????????????????????????????????????
        if(isLimit) {
            // ?????????????????????????????????????????????????????????
            Page<MoocFilmT> page = new Page<>(1, nums);

            List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

            filmInfos = getFilmInfos(moocFilms);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setFilmInfo(filmInfos);
        } else {
            // ????????????????????????????????????????????????????????????????????????
            Page<MoocFilmT> page = new Page<>(nowPage, nums);

            //???????????????1-??????????????????2-??????????????????3-???????????????
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

        // ????????????????????????????????????????????????????????????????????????
        Page<MoocFilmT> page = new Page<>(nowPage, nums);

        //???????????????1-??????????????????2-??????????????????3-???????????????
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
        // ?????? -> ??????????????????????????????
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // ?????? -> ??????????????????????????????
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");

        List<MoocFilmT> moocFilms = filmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilms);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        // ?????? -> ???????????????????????????
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

    @Override
    public FilmDetailVO getFilmDetailVO(int searchTyp, String searchParam) {
        FilmDetailVO filmDetailVO = null;
        // searchType 1-???????????? 2-???id???
        if(searchTyp == 1) {
            filmDetailVO = filmTMapper.getFilmDetailByName(searchParam);
        } else {
            filmDetailVO = filmTMapper.getFilmDetailById(searchParam);
        }

        return filmDetailVO;
    }

    private MoocFilmInfoT getFilmInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();

        moocFilmInfoT.setFilmId(filmId);

        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);

        return moocFilmInfoT;

    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {

        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();

        filmDescVO.setBiography(moocFilmInfoT.getBiography());

        filmDescVO.setFilmId(filmId);

        return filmDescVO;

    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        String filmImgStr = moocFilmInfoT.getFilmImgs();

        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDescInfo(String filmId) {

        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        // ??????????????????
        Integer directId = moocFilmInfoT.getDirectorId();

        MoocActorT moocActorT = moocActorTMapper.selectById(directId);

        ActorVO actorVO = new ActorVO();

        actorVO.setImgAddress(moocActorT.getActorImg());
        actorVO.setDirectorName(moocActorT.getActorName());

        return actorVO;

    }

    @Override
    public List<ActorVO> getActors(String filmId) {

        return moocActorTMapper.getActors(filmId);

    }
}
