package Model;

import java.time.LocalDateTime;

public class BorrowSlips {
    private String slipId;
    private String readerId;
    private String createdBy;
    private LocalDateTime borrowedAt;
    private String slipStatus;
    private String note;

    public String getSlipId() {
        return slipId;
    }

    public void setSlipId(String slipId) {
        this.slipId = slipId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public String getSlipStatus() {
        return slipStatus;
    }

    public void setSlipStatus(String slipStatus) {
        this.slipStatus = slipStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
