package com.study.grid.Service;

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
}
