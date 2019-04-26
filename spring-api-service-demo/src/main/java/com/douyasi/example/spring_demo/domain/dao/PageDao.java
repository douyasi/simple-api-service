package com.douyasi.example.spring_demo.domain.dao;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.douyasi.example.spring_demo.model.dto.PageBean;
import com.douyasi.example.spring_demo.domain.Page;

@Mapper
@Component
public interface PageDao {
    Page getPage(@NotNull @Param("id") Long id, @NotNull @Param("uid") Long uid);
    
    List<Page> getPagesByCondition(PageBean pageBean);
    
    int getPagesCountByCondition(PageBean pageBean);
    
    int insert(Page record);
    
    int update(@NotNull @Param("content") String content, @NotNull @Param("id") Long id);
    
    int delete(Long id);

}

