package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Arrays;



public class UI {

    public static void launchUI() {
        JFrame frame = new JFrame("i18n JSON ⇄ Excel 工具");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        JButton jsonToExcelBtn = new JButton("上传 JSON 生成 Excel");
        JButton excelToJsonBtn = new JButton("上传 Excel 生成 JSON");

        jsonToExcelBtn.setSize(150, 50);
        excelToJsonBtn.setSize(150, 50);

        jsonToExcelBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] jsonFiles = fileChooser.getSelectedFiles();
                JFileChooser saveChooser = new JFileChooser();
                saveChooser.setDialogTitle("保存 Excel 文件");
                result = saveChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File saveFile = saveChooser.getSelectedFile();
                    try {
                        JsonToExcelConverter.convert(Arrays.asList(jsonFiles), saveFile);
                        JOptionPane.showMessageDialog(frame, "✅ 导出成功: " + saveFile.getAbsolutePath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "❌ 导出失败: " + ex.getMessage());
                    }
                }
            }
        });

        excelToJsonBtn.addActionListener(e -> {
            JFileChooser openChooser = new JFileChooser();
            int result = openChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File excelFile = openChooser.getSelectedFile();
                JFileChooser saveChooser = new JFileChooser();
                saveChooser.setDialogTitle("保存 JSON 文件");
                result = saveChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File jsonFile = saveChooser.getSelectedFile();
                    try {
                        ExcelToJsonConverter.convert(excelFile, jsonFile);
                        JOptionPane.showMessageDialog(frame, "✅ 导出成功: " + jsonFile.getAbsolutePath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "❌ 导出失败: " + ex.getMessage());
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.add(jsonToExcelBtn);
        panel.add(excelToJsonBtn);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
