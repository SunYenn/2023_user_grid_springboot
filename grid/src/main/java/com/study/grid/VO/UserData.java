package com.study.grid.VO;

import lombok.Data;

@Data
public class UserData {

    private UserMst userMst;
    private UserPwd userPwd;
    private CommRoleGrp commRoleGrp;
    private UserRoleGrpMap userRoleGrpMap;
    private CommData commData;
}
