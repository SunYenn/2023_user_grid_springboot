package com.study.grid.DAO;

import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.EttUserPwd;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface UserManageMapper {

    // 사용자 등록의 역할 selectbox
    ArrayList<EttRoleGrp> getRoleGrpList();

    int getUserSeq();
}
