package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

public class ExcelToJsonConverter {

    public static void convert(File excelFile, File jsonFile) throws IOException {
        Map<String, String> map = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String key = row.getCell(0).getStringCellValue();
                    String value = row.getCell(1).getStringCellValue();
                    map.put(key, value);
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(jsonFile, map);
    }
}
