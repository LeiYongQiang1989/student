package com.student.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExportUtils
 * @Description: 导出工具类
 * @author: LeiYongQiang
 * @date: 2024-04-08
 */
@Slf4j
public class ExportUtils {

    public static void exportExcel(String fileName, String data, Class<?> c, HttpServletResponse response) throws Exception {
        Map<String,String> map = new HashMap<>(8);
        map.put("createName","创建人");
        map.put("className","班级");
        map.put("studentName","学生");
        map.put("subjectName","课程");
        map.put("gradeNumber","成绩");
        map.put("departmentName","院系");
        map.put("createTime","时间");
        map.put("updateTime","更新时间");

        try {
            // 创建表头
            // 创建工作薄
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            // 创建表头行
            Row rowHeader = sheet.createRow(0);
            if (c == null) {
                throw new RuntimeException("Class对象不能为空!");
            }
            Field[] declaredFields = c.getDeclaredFields();
            List<String> headerList = new ArrayList<>();
            if (declaredFields.length == 0) {
                return;
            }


            for (int i = 0; i < declaredFields.length; i++) {
                Cell cell = rowHeader.createCell(i, CellType.STRING);
                String headerName = String.valueOf(declaredFields[i].getName());
//                cell.setCellValue(headerName);
                // 设置中文单元格的表头
                cell.setCellValue(map.get(headerName));
                headerList.add(i, headerName);
            }
            // 填充数据
            List<?> objects = JSONObject.parseArray(data, c);
            Object obj = c.newInstance();
            if (!CollectionUtils.isEmpty(objects)) {
                for (int o = 0; o < objects.size(); o++) {
                    Row rowData = sheet.createRow(o + 1);
                    for (int i = 0; i < headerList.size(); i++) {
                        Cell cell = rowData.createCell(i);
                        Field nameField = c.getDeclaredField(headerList.get(i));
                        nameField.setAccessible(true);
                        String value = String.valueOf(nameField.get(objects.get(o)));
                        if ("null".equals(value)) {
                            value = "";
                        }
                        cell.setCellValue(value);
                    }
                }
            }
            response.setContentType("application/vnd.ms-excel");
            String resultFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + resultFileName + ";" + "filename*=utf-8''" + resultFileName);
            workbook.write(response.getOutputStream());
            response.flushBuffer();
            workbook.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
