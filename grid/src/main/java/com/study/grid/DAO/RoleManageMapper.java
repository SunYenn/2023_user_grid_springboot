package com.study.grid.DAO;

import com.study.grid.VO.*;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface RoleManageMapper {

    // 목록
    int countRoleGrpList(Paging paging);
    ArrayList<EttRoleGrp> selectRoleGrpList(Paging paging);

    // 수정
    void udtRoleGrp(EttRoleGrp ettRoleGrp);

    // 등록
    EttRoleGrp roleCheck(String roleGrpName);
    int getRoleGrpSeq();
    void istRoleGrp(EttRoleGrp ettRoleGrp);

    // excel
    ArrayList<EttRoleGrp> selectAllRoleGrp();

    // 삭제
    void deleteRoleBySeq(int seq);
}
