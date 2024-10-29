package com.spring.mvc.entity;

public class Paging {
    private int totalRows;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private int offset; //bat dau lay danh sach tu phan tu thu bao nhieu

    public Paging(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOffset() {
        //VD: trang hien tai la 1, thi offset se la tu  0 (lay tu 0 den 9)
        //trang hien tai la 2, thi offset se la tu 10 (lay tu 10 den 19)
        if(currentPage>0){
            offset=(currentPage-1)*pageSize;
        }
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        if (totalRows > 0) {
            totalPages = (int) Math.ceil(totalRows / (double) pageSize);
        }
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
    @Override
    public String toString() {
        return "Paging{" +
                "currentPage=" + getCurrentPage() +
                ", totalRows=" + getTotalRows() +
                ", totalPages=" + getTotalPages() +
                ", pageSize=" + getPageSize() +
                ", offset=" + getOffset() +
                '}';
    }
}
