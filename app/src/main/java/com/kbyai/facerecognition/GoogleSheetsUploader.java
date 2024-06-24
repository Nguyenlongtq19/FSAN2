package com.kbyai.facerecognition;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleSheetsUploader {

    public static void writeToGoogleSheets(int row, int col, String data) {
        try {
            // Thay thế bằng URL triển khai của ứng dụng web từ Google Apps Script
            String scriptUrl = "https://script.google.com/macros/s/AKfycbyOEpc7Owac3xflkj5ck_tM3Td4wvA5si_ty6ak783YyV0YcEIIzafmUF09hTEuf8yC/exec";
            URL url = new URL(scriptUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            // Tạo dữ liệu để gửi lên Google Sheets
            String postData = "row=" + row + "&col=" + col + "&data=" + data;

            // Gửi dữ liệu thông qua body của request
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();

            // Lấy mã phản hồi từ máy chủ
            int responseCode = urlConnection.getResponseCode();

            // Xử lý phản hồi nếu cần

            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}