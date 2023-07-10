package com.study.grid.Service;

import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class CreateExcel {

    public static Workbook setUserFile(ArrayList<EttUserMst> allUserData) {
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        Workbook wb = new XSSFWorkbook(); // 확장자 지정 XSS : xlsx, HSS : xls
        Sheet sheet = wb.createSheet("사용자정보");

        // Preface
        CellStyle prefacestyle = wb.createCellStyle();
        prefacestyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬

        // Header
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex()); // 배경색 설정
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        String[] title = {"식별번호", "아이디", "이름", "사원번호", "이메일", "전화번호", "카드번호", "카드 만료일", "계정 만료일", "마지막 로그인 날짜", "마지막 로그인 IP", "삭제 여부"};

        row = sheet.createRow(rowNum++);
        for(int i =0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(title[i]);
        }

        for(int i =0; i < allUserData.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(allUserData.get(i).getUser_seq());
            for(int j =1; j < title.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(allUserData.get(i).getter(j));
            }
        }

        //컬럼 너비 자동 설정
        for (int i=0; i<title.length; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)1024);
        }

        return wb;
    }

    public static Workbook setRoleGrpFile(ArrayList<EttRoleGrp> allRoleGrp) {

        Workbook wb = new XSSFWorkbook(); // 확장자 지정 XSS : xlsx, HSS : xls
        Sheet sheet = wb.createSheet("그룹 정보");

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Preface
        CellStyle prefacestyle = wb.createCellStyle();
        prefacestyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬

        // Header
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER); // 가운데 정렬
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex()); // 배경색 설정
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        String[] title = {"식별번호", "그룹명", "그룹설명", "상태코드", "추가권한", "삭제여부", "등록자", "등록일시", "수정자", "수정일시"};

        row = sheet.createRow(rowNum++);
        for(int i =0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(title[i]);
        }

        for(int i =0; i < allRoleGrp.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);

            cell.setCellValue(allRoleGrp.get(i).getRole_grp_seq());
            for(int j =1; j < title.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(allRoleGrp.get(i).getter(j));
            }
        }

        //컬럼 너비 자동 설정
        for (int i=0; i<title.length; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)1024);
        }

        return wb;

    }
}
