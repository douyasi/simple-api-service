package com.douyasi.example.spring_demo.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PageBean {

    private Long uid;

    private int offset = 0;

    private int limit = 10;

    private int page = 1;

    private int perPage = 10;

    private String startTime = null;

    private String endTime = null;

    private String strPageIds = null;

    private List<Long> pageIds = null;

    public List<Long> getPageIds() {
        pageIds = Collections.emptyList();
        if (strPageIds == null) {
            return pageIds;
        }
        String[] explodedPageIds = strPageIds.split(",");
        if (explodedPageIds.length > 0) {
            ArrayList<String> pageIdsList = new ArrayList<String>(explodedPageIds.length);
            Collections.addAll(pageIdsList, explodedPageIds);
            pageIds = (List<Long>) pageIdsList.stream().map(Long::parseLong).collect(Collectors.toList());
        }
        return pageIds;
    }
    
    public int getPerPage() {
        if (perPage < 0 || perPage > 100) {
            perPage = 10;
        }
        return perPage;
    }
    
    public int getPage() {
        if (page <= 0) {
            page = 1;
        }
        return page;
    }
    
    public Long getUid() {
        return uid;
    }
    
    public void setUid(Long uid) {
        this.uid = uid;
    }
    
    public int getOffset() {
        offset = (getPage() - 1) * getPerPage();
        return offset;
    }
    
    public int getLimit() {
        limit = getPerPage();
        return limit;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "PageBean [uid=" + uid + ", offset=" + getOffset() + ", page=" + page + ", perPage=" + getPerPage()
                + ", startTime=" + startTime + ", endTime=" + endTime + ", strPageIds=" + strPageIds + ", pageIds="
                + getPageIds() + "]";
    }
}
