package com.study.grid.VO;

import lombok.Data;

@Data
public class UserData {

    private EttUserMst ettUserMst;
    private EttUserPwd ettUserPwd;
    private EttRoleGrp ettRoleGrp;
    private EttUserRoleGrpMap ettUserRoleGrpMap;

    private Paging paging;

    public UserData() {}

    public UserData(EttUserMst mst, EttUserPwd pwd, EttRoleGrp roleGrp) {
        this.ettUserMst = mst;
        this.ettUserPwd = pwd;
        this.ettRoleGrp = roleGrp;
    }
}
