package com.study.grid.Controller;

import com.study.grid.DAO.RoleManageMapper;
import com.study.grid.Security.AuthUtil;
import com.study.grid.Service.CreateExcel;
import com.study.grid.Service.SetData;
import com.study.grid.VO.EttRoleGrp;
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
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@CrossOrigin(origins = "http://192.168.1.139:3000")
@RestController
@RequestMapping("/api/role")
public class RoleManage {

    @Autowired
    private RoleManageMapper RMmapper;

    @PostMapping("/list") // 그룹 목록
    public ResponseEntity<?> list(@RequestBody Paging paging) {
        paging.setTotal_count(RMmapper.countRoleGrpList(paging));
        SetData.setPaging(paging);
        paging.setRoleGrps(RMmapper.selectRoleGrpList(paging));
        return ResponseEntity.ok(paging);
    }

    @PostMapping("/alter") // 그룹 정보 수정
    public ResponseEntity<?> alter(@RequestBody AllData data){

        SetData.setRoleUdtData(data, AuthUtil.getId());

        try {
            RMmapper.udtRoleGrp(data.getEttRoleGrp());
            return ResponseEntity.ok("그룹 정보 수정에 성공했습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 정보 수정에 실패했습니다.");
        }
    }

    @PostMapping("/register") // 그룹 등록
    public ResponseEntity<?> register(@RequestBody AllData data) {

        EttRoleGrp role = RMmapper.roleCheck(data.getEttRoleGrp().getRole_grp_name());

        if (role != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복된 그룹명입니다.");
        }

        int role_grp_seq = RMmapper.getRoleGrpSeq();

        SetData.setRoleCreData(data, role_grp_seq);
        try {
            RMmapper.istRoleGrp(data.getEttRoleGrp());
            return ResponseEntity.ok("사용자 등록에 성공했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 등록에 실패했습니다.");
        }
    }

    @PostMapping("/delete")
    public void deleteUsef(@RequestBody ArrayList<Integer> roleGrpSeqs) {
        for(int seq : roleGrpSeqs) {
            RMmapper.deleteRoleBySeq(seq);
        }
    }

    @GetMapping("/excelDown")
    public ResponseEntity<?> excelDown() throws IOException {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String strDate = simpleDateFormat.format(date);

        ArrayList<EttRoleGrp> allRoleGrp = RMmapper.selectAllRoleGrp();

        Workbook wb = CreateExcel.setRoleGrpFile(allRoleGrp);

        File tempFile = File.createTempFile("temp_" + strDate, ".xlsx");

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            wb.write(outputStream);
        }

        // Set the response headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "roleGrp_" + strDate + ".xlsx");

        // Create a FileSystemResource from the temporary file
        FileSystemResource fileResource = new FileSystemResource(tempFile);

        // Return the file as a ResponseEntity
        return ResponseEntity.ok().headers(headers).body(fileResource);
    }



}
