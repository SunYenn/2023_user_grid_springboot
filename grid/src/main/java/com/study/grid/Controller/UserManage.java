package com.study.grid.Controller;

import com.study.grid.DAO.UserManageMapper;
import com.study.grid.DAO.authMapper;
import com.study.grid.Service.AuthUtil;
import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@CrossOrigin(origins = "http://192.168.1.139:3000")
@RestController
@RequestMapping("/api/user")
public class UserManage {

    @Autowired
    private UserManageMapper mapper;

    @PostMapping("/list") // 사용자 목록
    public void list(@RequestBody UserData data){

    }

    @GetMapping("/roleList") // 사용자 목록
    public ArrayList<EttRoleGrp> roleList(){
        ArrayList<EttRoleGrp> roleList = mapper.getRoleGrpList();
        return roleList;
    }

    @PostMapping("/register") // 사용자 등록
    public void register(@RequestBody UserData data){

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);

        data.getEttUserMst().setCre_id(AuthUtil.getId());
        data.getEttUserMst().setCre_dt(strDate);

        int user_seq = mapper.getUserSeq();
        data.getEttUserMst().setUser_seq(user_seq);
        // mapper.istUserMst(data.getEttUserMst());
        log.info("mst : {}", data.getEttUserMst());
        log.info("pwd : {}", data.getEttUserPwd());
        log.info("roleGrp : {}", data.getEttRoleGrp());
    }

}
