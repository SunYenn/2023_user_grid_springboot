package com.study.grid.VO;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data

public class Paging {

    private int page_size = 10;
    private int total_count = 0;
    private int total_page = 0;
    private int current_page = 1;
    private int start_no = 0;
    private int start_page = 0;

    private String user_name_fg;
    private String cre_dt_fg;
    private String udt_dt_fg;

    private String search_id;
    private String search_name;
    private String search_telno;

    private ArrayList<EttUserMst> userMsts = new ArrayList<EttUserMst>();
    
}
