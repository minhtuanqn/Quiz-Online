/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MINH TUAN
 */
public class QuestionHistoryPagingModel {
    public final static int NUMRECORDOFEACHPAGE = 3;
    private int totalPage = 1;
    private List pagingList;
    
    public int getNUMRECORDOFEACHPAGE() {
        return NUMRECORDOFEACHPAGE;
    }
    
    public int getTotalPage(List mapQuesAndAnsList) {
        if(mapQuesAndAnsList == null || mapQuesAndAnsList.size() <= NUMRECORDOFEACHPAGE) {
            return 1;
        }
        if(mapQuesAndAnsList.size() % NUMRECORDOFEACHPAGE == 0) {
            totalPage = mapQuesAndAnsList.size() / NUMRECORDOFEACHPAGE;
        }
        else {
            totalPage = mapQuesAndAnsList.size() / NUMRECORDOFEACHPAGE + 1;
        }
        return totalPage;
    }
    
    public List<List> loadPaging(List quesAndAnsMapList, int curPage) {
        if(quesAndAnsMapList == null || quesAndAnsMapList.size() <= NUMRECORDOFEACHPAGE) {
            return quesAndAnsMapList;
        }
        if(pagingList == null) {
            pagingList = new ArrayList<>();
        }

        int sizeTotal = quesAndAnsMapList.size();
        int startRecord = (curPage - 1) * NUMRECORDOFEACHPAGE + 1;
        int endRecord = startRecord + NUMRECORDOFEACHPAGE - 1;
        if(sizeTotal < endRecord) {
            endRecord = sizeTotal;
        }
        for (int count = 0; count < quesAndAnsMapList.size(); count ++) {
            if(count + 1 >= startRecord && count + 1 <= endRecord) {
                pagingList.add(quesAndAnsMapList.get(count));
            }
        }

        return pagingList;
    }
}
