package com.recco.qr.service.util;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

@Component
public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "./qrcodes/";

    public String generateQRCode(String tableId) throws Exception {
        ensureDirectoryExists(QR_CODE_IMAGE_PATH);

        String qrText = "http://localhost:8082/api/menu/all?tableId=" + tableId;
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

        // Save the QR code image
        String filePath = QR_CODE_IMAGE_PATH + tableId + ".png";
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return filePath; // Return file path of generated QR code
    }

    private void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + directoryPath);
            }
        }
    }
}
