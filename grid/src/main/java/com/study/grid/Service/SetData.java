package com.study.grid.Service;

import com.study.grid.Security.AuthUtil;
import com.study.grid.VO.Paging;
import com.study.grid.VO.UserData;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SetData {

    static Date date = new Date();
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static String strDate = simpleDateFormat.format(date);

    public static void setPaging(Paging paging) {

        int total_page = (paging.getTotal_count() - 1) / paging.getPage_size() + 1;
        int current_page = paging.getCurrent_page() > total_page ? total_page : paging.getCurrent_page();
        int start_no = (current_page - 1) * paging.getPage_size();
        int start_page = (current_page - 1) / paging.getPage_size() * paging.getPage_size() + 1;

        paging.setTotal_page(total_page);
        paging.setStart_no(start_no);
        paging.setStart_page(start_page);
    }

    public static void setUserSeq(UserData data, int seq) {
        data.getEttUserMst().setUser_seq(seq);
        data.getEttUserPwd().setUser_seq(seq);
        data.getEttUserRoleGrpMap().setUser_seq(seq);
    }

    public static void setUserCreData(UserData data, String id) {
        data.getEttUserMst().setCre_id(id);
        data.getEttUserMst().setCre_dt(strDate);
    }

    public static void setUserUdtData(UserData data, String id) {
        data.getEttUserMst().setUdt_id(id);
        data.getEttUserMst().setUdt_dt(strDate);
    }

    public static void setRoleUdtData(UserData data, String id) {
        data.getEttRoleGrp().setUdt_id(id);
        data.getEttRoleGrp().setUdt_dt(strDate);
    }

    public static void setRoleCreData(UserData data, int roleGrpSeq) {
        data.getEttRoleGrp().setCre_id(AuthUtil.getId());
        data.getEttRoleGrp().setCre_dt(strDate);
        data.getEttRoleGrp().setRole_grp_seq(roleGrpSeq);
    }
}
