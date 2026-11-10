package Model;

import java.time.LocalDateTime;

public class BorrowDetails {
    private String detailId;
    private String slipId;
    private String copyId;
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;
    private String returnCondition;
    private String returnConfirmedBy;
    private String note;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getSlipId() {
        return slipId;
    }

    public void setSlipId(String slipId) {
        this.slipId = slipId;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public String getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(String returnCondition) {
        this.returnCondition = returnCondition;
    }

    public String getReturnConfirmedBy() {
        return returnConfirmedBy;
    }

    public void setReturnConfirmedBy(String returnConfirmedBy) {
        this.returnConfirmedBy = returnConfirmedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
