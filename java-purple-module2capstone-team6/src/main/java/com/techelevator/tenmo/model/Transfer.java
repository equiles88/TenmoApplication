package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String senderUsername;
    private String recipientName;
    private BigDecimal amount;
    private String message;
    private String status = "approved";

    public Transfer() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transfer(int transferId, String senderUsername, String recipientName, BigDecimal amount, String message, String status) {
        this.transferId = transferId;
        this.senderUsername = senderUsername;
        this.recipientName = recipientName;
        this.amount = amount;
        this.message = message;
        this.status = status;
    }

    public Transfer(String senderUsername, String recipientName, BigDecimal amount, String message, String status) {
        this.senderUsername = senderUsername;
        this.recipientName = recipientName;
        this.amount = amount;
        this.message = message;
        this.status = status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", senderUsername='" + senderUsername + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
