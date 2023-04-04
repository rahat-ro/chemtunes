package com.programmersjail.chemtunesorganicchemistry.helper;

public class SubscriptionFee {

    private String accountType,subscriptionFee,packageName,packageLimit;

    public SubscriptionFee(String accountType, String subscriptionFee, String packageName, String packageLimit) {
        this.accountType = accountType;
        this.subscriptionFee = subscriptionFee;
        this.packageName = packageName;
        this.packageLimit = packageLimit;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getSubscriptionFee() {
        return subscriptionFee;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageLimit() {
        return packageLimit;
    }
}
