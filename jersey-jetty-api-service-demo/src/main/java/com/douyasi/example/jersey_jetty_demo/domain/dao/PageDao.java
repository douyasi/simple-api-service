package com.douyasi.example.jersey_jetty_demo.domain.dao;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;

import com.douyasi.example.jersey_jetty_demo.api.bean.PageBean;
import com.douyasi.example.jersey_jetty_demo.domain.Page;

public interface PageDao {
    Page getPage(@NotNull @Param("id") Long id, @NotNull @Param("uid") Long uid);
    
    List<Page> getPagesByCondition(PageBean pageBean);
    
    int getPagesCountByCondition(PageBean pageBean);
    
    int insert(Page record);
    
    int update(@NotNull @Param("content") String content, @NotNull @Param("id") Long id);
    
    int delete(Long id);

}
