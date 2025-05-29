package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class JsonToExcelConverter {

    public static void convert(List<File> jsonFiles, File excelFile) throws IOException {
        List<Map.Entry<String, String>> allKeyValues = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (File file : jsonFiles) {
            JsonNode root = mapper.readTree(file);
            extractKeyValue("", root, allKeyValues);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Key-Value");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("key");
        header.createCell(1).setCellValue("value");

        for (int i = 0; i < allKeyValues.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(allKeyValues.get(i).getKey());
            row.createCell(1).setCellValue(allKeyValues.get(i).getValue());
        }

        try (FileOutputStream fos = new FileOutputStream(excelFile)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    private static void extractKeyValue(String prefix, JsonNode node, List<Map.Entry<String, String>> collector) {
        if (node.isObject()) {
            node.fields().forEachRemaining(field -> {
                String newKey = prefix.isEmpty() ? field.getKey() : prefix + "." + field.getKey();
                extractKeyValue(newKey, field.getValue(), collector);
            });
        } else if (node.isTextual()) {
            collector.add(new AbstractMap.SimpleEntry<>(prefix, node.asText()));
        }
    }
}
