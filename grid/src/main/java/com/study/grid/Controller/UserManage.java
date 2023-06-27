package com.study.grid.Controller;

import com.study.grid.DAO.AuthMapper;
import com.study.grid.DAO.UserManageMapper;
import com.study.grid.Service.AuthUtil;
import com.study.grid.Service.PWencryption;
import com.study.grid.Service.SetData;
import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/list") // 사용자 목록
    public void list(@RequestBody UserData data){

    }

    @GetMapping("/roleList") // 사용자 목록
    public ArrayList<EttRoleGrp> roleList(){
        ArrayList<EttRoleGrp> roleList = mapper.getRoleGrpList();
        return roleList;
    }

    @PostMapping("/register") // 사용자 등록
    public ResponseEntity<?> register(@RequestBody UserData data) throws NoSuchAlgorithmException {

        EttUserMst mst = authMapper.IdCheck(data.getEttUserMst().getUser_id());
        if(mst != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복된 아이디입니다.");
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);
        int user_seq = mapper.getUserSeq();

        SetData.setCreData(data, AuthUtil.getId(), strDate);
        SetData.setUserSeq(data, user_seq);
        data.getEttUserPwd().setUser_pwd(PWencryption.encrypt(data.getEttUserPwd().getUser_pwd()));

        mapper.istUserMst(data.getEttUserMst());
        mapper.istUserPwd(data.getEttUserPwd());
        mapper.istUserRoleGrpMap(data.getEttUserRoleGrpMap());

        return ResponseEntity.ok("사용자 등록에 성공했습니다.");
    }

}
