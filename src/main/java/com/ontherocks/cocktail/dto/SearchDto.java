package com.ontherocks.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SearchDto {
    private String colName; // 검색 카테고리(아이디,상호명)
    private String keyword; //검색어
    private Integer pageNum; //현재 페이지 번호
    private Integer listCnt; //페이지 당 글 개수 ex)10
    private Integer startIdx; // listCnt가 10일 때 1p: limit 0,10 2P: limit 10,10 3p: limit 20,10

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getListCnt() {
        return listCnt;
    }

    public void setListCnt(Integer listCnt) {
        this.listCnt = listCnt;
    }

    public Integer getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }
}
