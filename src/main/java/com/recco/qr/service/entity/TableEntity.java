package com.recco.qr.service.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tables")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tableId; // e.g., "TBL_101"
    
    @Column(name = "qr_code_url", nullable = false)
    private String qrCodeUrl; // URL to access the QR Code


    // ✅ Default constructor (Required for JPA)
    public TableEntity() {}

    // ✅ Constructor with parameters
    public TableEntity(String tableId, String qrCodeUrl) {
        this.tableId = tableId;
        this.qrCodeUrl = qrCodeUrl;
    }

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
    
    
}

