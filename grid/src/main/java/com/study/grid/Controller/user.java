package com.study.grid.Controller;


import com.study.grid.VO.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class user {

    @PostMapping("/list") // 글 등록
    public void list(@RequestBody UserData data){
        log.info("hi");
        log.info("mst : {}", data.getUserMst());
        log.info("map : {}", data.getUserRoleGrpMap());
    }
}
