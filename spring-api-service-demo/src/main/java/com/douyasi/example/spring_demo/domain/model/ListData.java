package com.douyasi.example.spring_demo.domain.model;

import java.util.List;

public class ListData<T> {

    public ListData(int total, int perPage, int currentPage, List<T> items) {
        this.total = total;
        this.currentPage = currentPage;
        this.items = items;
        this.perPage = perPage;
    }

    private int total, perPage, currentPage;
    private List<T> items;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getPerPage() {
        return perPage;
    }
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public List<T> getItems() {
        return (List<T>) items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }
}
