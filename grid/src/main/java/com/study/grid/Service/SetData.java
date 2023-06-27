package com.study.grid.Service;

import com.study.grid.VO.Paging;
import com.study.grid.VO.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetData {
    public static void setUserSeq(UserData data, int seq) {
        data.getEttUserMst().setUser_seq(seq);
        data.getEttUserPwd().setUser_seq(seq);
        data.getEttUserRoleGrpMap().setUser_seq(seq);
    }

    public static void setCreData(UserData data, String id, String strDate) {
        data.getEttUserMst().setCre_id(id);
        data.getEttUserMst().setCre_dt(strDate);
    }

    public static void setPaging(Paging paging) {

        int total_page = (paging.getTotal_count() - 1) / paging.getPage_size() + 1;
        int current_page = paging.getCurrent_page() > total_page ? total_page : paging.getCurrent_page();
        int start_no = (current_page - 1) * paging.getPage_size();
        int start_page = (current_page - 1) / paging.getPage_size() * paging.getPage_size() + 1;

        paging.setTotal_page(total_page);
        paging.setStart_no(start_no);
        paging.setStart_page(start_page);
    }
}
