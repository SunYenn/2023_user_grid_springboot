package com.study.grid.Controller;

import com.study.grid.DAO.AuthMapper;
import com.study.grid.DAO.UserManageMapper;
import com.study.grid.Security.AuthUtil;
import com.study.grid.Service.CreateExcel;
import com.study.grid.Service.PWencryption;
import com.study.grid.Service.SetData;
import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.Paging;
import com.study.grid.VO.AllData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public ResponseEntity<?> register(@RequestBody AllData data) throws NoSuchAlgorithmException {

        EttUserMst mst = authMapper.IdCheck(data.getEttUserMst().getUser_id());
        if (mst != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복된 아이디입니다.");
        }

        int user_seq = UMmapper.getUserSeq();

        SetData.setUserCreData(data, AuthUtil.getId());
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
    public ResponseEntity<?> alter(@RequestBody AllData data) throws NoSuchAlgorithmException {

        SetData.setUserUdtData(data, AuthUtil.getId());
        data.getEttUserRoleGrpMap().setUser_seq(data.getEttUserMst().getUser_seq());

        try {
            if(data.getEttUserRoleGrpMap().getRole_grp_seq() == 0) {
                data.getEttUserRoleGrpMap().setRole_grp_seq(UMmapper.getRoleGrpSeq(data.getEttUserMst().getRole_grp_name()));
            }
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
    public ResponseEntity<?> getUserData(@PathVariable String id) throws NoSuchAlgorithmException {

        EttUserMst userMst = UMmapper.getUserData(id);
        return ResponseEntity.ok(userMst);
    }

    @PostMapping("/delete")
    public void deleteUsef(@RequestBody ArrayList<Integer> userSeqs) {
        for(int seq : userSeqs) {
            UMmapper.deleteUserBySeq(seq);
        }
    }

    @GetMapping("/excelDown")
    public ResponseEntity<?> excelDown() throws IOException {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String strDate = simpleDateFormat.format(date);

        ArrayList<EttUserMst> allUser = UMmapper.selectAllUser();

        Workbook wb = CreateExcel.setUserFile(allUser);

        File tempFile = File.createTempFile("temp_" + strDate, ".xlsx");

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            wb.write(outputStream);
        }

        // Set the response headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_" + strDate + ".xlsx");

        // Create a FileSystemResource from the temporary file
        FileSystemResource fileResource = new FileSystemResource(tempFile);

        // Return the file as a ResponseEntity
        return ResponseEntity.ok().headers(headers).body(fileResource);
    }


}
