package com.icia.cocktail.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Paging {
    private int totalNum; //전체 글 개수
    private int pageNum; //현재 페이지 번호
    private int listCnt; //페이지 당 글 개수 ex)1페이지에 10개
    private int pageCnt; //페이지 그룹 당 페이지 개수 ex)1 2 ~ 9 10 [다음] 한 그룹 당 10개
    private  String listUrl; //리스트 종류 ex) localhost/admin/sellerList?pageNum=3

    public String makePaging() {
        //전체 페이지 개수             totalNum  listCnt
        //ex)리스트가 200개, 1p에 10개 씩일 때 200/10 20개
        //ex)리스트가 201개면 전체 페이지 개수=201/10+1 21개
        int totalPage = (totalNum % listCnt == 0 ? totalNum/listCnt : totalNum/listCnt+1);

        //전체 페이지 그룹 개수          totalPage  pageCnt
        //ex)전체 페이지 개수가 20개면 그룹 개수=20/10 2개
        //ex)전체 페이지 개수가 21개면 그룹 개수=20/10+1 3개
        int totalGroup = (totalPage % pageCnt == 0 ? totalPage/pageCnt : totalPage/pageCnt+1);

        //현재 페이지가 속해 있는 그룹 번호
        //현재 페이지를 그룹 당 페이지 개수로 나눈 나머지로 판단
        //ex) 현재 13p 13/10(그룹 당 페이지 개수)+1 2그룹
        int curGroup = (pageNum % pageCnt == 0 ? pageNum/pageCnt : pageNum/pageCnt+1);
        return makeHtml(totalPage, curGroup,listUrl);
    }

    private String makeHtml(int totalPage, int curGroup, String listUrl) {
        StringBuffer sb = new StringBuffer();
        //현재 그룹의 시작 페이지 번호
        int start = (curGroup * pageCnt) - (pageCnt - 1);
        //현재 그룹의 끝 페이지 번호
        //ex) 전체 페이지는 12개 현재 1그룹이면 끝 번호=1*10=10
        //ex) 현재 2그룹이면 2*10>=12 이므로 끝 번호는 12
        int end = (curGroup * pageCnt >= totalPage ? totalPage : curGroup * pageCnt);

        //시작 페이지가 1이 아니면 이전 페이지 그룹으로 가는 링크 걸기
        if(start != 1){ //ex) <a href='listurl?pageNum=10'>[이전]</a>
            sb.append("<a class='pno' href='"+listUrl+"pageNum="+(start-1)+"'>");
            sb.append("[이전]");
            sb.append("</a>");
        }

        for(int i = start; i <= end; i++){
            if(pageNum != i){ //현재 페이지가 아닌 각 페이지 번호에 링크걸기
                //ex) <a href='listUrl?pageNum=1>[1]</a>
                sb.append("<a class='pno' href='"+listUrl+"pageNum="+i+"'> ");
                sb.append(i+" </a>");
            }else{ //현재 페이지 번호에는 링크 x
                sb.append("<font class='pno' style='color:blue'> ");
                sb.append(i+" </font>");
            }
        }
        //끝 페이지가 전체 페이지 전체 페이지 수가 아니면 다음 그룹으로 가는 링크걸기
        if(end != totalPage){
            sb.append("<a class='pno' href='"+listUrl+"pageNum="+(end+1)+"'>");
            sb.append("[다음]</a>");
        }
        return sb.toString();
    }
}
