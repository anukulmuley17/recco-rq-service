package com.recco.qr.service.services;

import org.springframework.stereotype.Service;

import com.recco.qr.service.entity.TableEntity;
import com.recco.qr.service.repository.TableRepository;
import com.recco.qr.service.util.QRCodeGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class QRService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    public String generateQRForTable(String tableId) throws Exception {
        Optional<TableEntity> existingTable = tableRepository.findByTableId(tableId);
        if (existingTable.isPresent()) {
            return existingTable.get().getQrCodeUrl();
        }

        // Generate QR Code
        String qrCodePath = qrCodeGenerator.generateQRCode(tableId);
        TableEntity newTable = new TableEntity(tableId, qrCodePath);
        tableRepository.save(newTable);

        return qrCodePath;
    }

    public String getQRCodeByTableId(String tableId) {
        return tableRepository.findByTableId(tableId)
                .map(TableEntity::getQrCodeUrl)
                .orElseThrow(() -> new RuntimeException("Table Not Found"));
    }
}

