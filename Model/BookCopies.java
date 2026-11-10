package Model;

import java.time.LocalDateTime;

public class BookCopies {
    private String copyId;
    private String bookId;
    private String copyCode;
    private String copyStatus;
    private LocalDateTime createdAt;

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCopyCode() {
        return copyCode;
    }

    public void setCopyCode(String copyCode) {
        this.copyCode = copyCode;
    }

    public String getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(String copyStatus) {
        this.copyStatus = copyStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
