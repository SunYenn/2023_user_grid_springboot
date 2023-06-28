package com.study.grid.Controller;

import com.study.grid.DAO.AuthMapper;
import com.study.grid.DAO.UserManageMapper;
import com.study.grid.Service.AuthUtil;
import com.study.grid.Service.PWencryption;
import com.study.grid.Service.SetData;
import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.Paging;
import com.study.grid.VO.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
    private UserManageMapper UMmapper;

    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/list") // 사용자 목록
    public ResponseEntity<?> list(@RequestBody Paging paging) {
        paging.setTotal_count(UMmapper.countUserMstList(paging));
        SetData.setPaging(paging);
        paging.setUserMsts(UMmapper.selectUserMstList(paging));
        return ResponseEntity.ok(paging);
    }

    @GetMapping("/roleList") // 역할 목록
    public ArrayList<EttRoleGrp> roleList() {
        ArrayList<EttRoleGrp> roleList = UMmapper.getRoleGrpList();
        return roleList;
    }

    @PostMapping("/register") // 사용자 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<?> register(@RequestBody UserData data) throws NoSuchAlgorithmException {

        EttUserMst mst = authMapper.IdCheck(data.getEttUserMst().getUser_id());
        if (mst != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복된 아이디입니다.");
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);
        int user_seq = UMmapper.getUserSeq();

        SetData.setCreData(data, AuthUtil.getId(), strDate);
        SetData.setUserSeq(data, user_seq);
        data.getEttUserPwd().setUser_pwd(PWencryption.encrypt(data.getEttUserPwd().getUser_pwd()));

        try {
            UMmapper.istUserMst(data.getEttUserMst());
            UMmapper.istUserPwd(data.getEttUserPwd());
            UMmapper.istUserRoleGrpMap(data.getEttUserRoleGrpMap());

            return ResponseEntity.ok("사용자 등록에 성공했습니다.");
        } catch (RuntimeException e) {
            log.info("register 메소드 트랜잭션 발생");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 트랜잭션 발생시 롤백
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 등록에 실패했습니다.");
        }
    }

    @PostMapping("/alter") // 사용자 정보 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<?> alter(@RequestBody UserData data) throws NoSuchAlgorithmException {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);

        SetData.setUdtData(data, AuthUtil.getId(), strDate);
        data.getEttUserRoleGrpMap().setUser_seq(data.getEttUserMst().getUser_seq());

        try {
            log.info("getEttUserMst : {}", data.getEttUserMst());
            log.info("getEttUserRoleGrpMap : {}",data.getEttUserRoleGrpMap());
            UMmapper.udtUserMst(data.getEttUserMst());
            UMmapper.udtUserRoleGrpMap(data.getEttUserRoleGrpMap());

            return ResponseEntity.ok("사용자 정보 수정에 성공했습니다.");
        } catch (RuntimeException e) {
            log.info("alter 메소드 트랜잭션 발생");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 트랜잭션 발생시 롤백
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 정보 수정에 실패했습니다.");
        }
    }

    @GetMapping("/getUserData/{id}")
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<?> getUserData(@PathVariable String id) throws NoSuchAlgorithmException {

        log.info("id : {}", id);
        EttUserMst userMst = UMmapper.getUserData(id);
        return ResponseEntity.ok(userMst);
    }

}
