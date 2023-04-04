package com.programmersjail.chemtunesorganicchemistry.payment;

public class PaymentHistoryModel {

    private String userName,phoneNumber,collageName,subscriptionFee,mobBankingNo,transID,
            amount,date,time,packageName,monthLimit;

    public PaymentHistoryModel(String userName, String phoneNumber, String collageName, String subscriptionFee,
                               String mobBankingNo, String transID, String amount, String date, String time, String packageName, String monthLimit) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.collageName = collageName;
        this.subscriptionFee = subscriptionFee;
        this.mobBankingNo = mobBankingNo;
        this.transID = transID;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.packageName = packageName;
        this.monthLimit = monthLimit;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCollageName() {
        return collageName;
    }

    public String getSubscriptionFee() {
        return subscriptionFee;
    }

    public String getMobBankingNo() {
        return mobBankingNo;
    }

    public String getTransID() {
        return transID;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMonthLimit() {
        return monthLimit;
    }
}
