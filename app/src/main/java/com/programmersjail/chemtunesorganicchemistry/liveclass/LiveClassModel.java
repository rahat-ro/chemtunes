package com.programmersjail.chemtunesorganicchemistry.liveclass;

public class LiveClassModel {

    private String name,url,icon,text;

    public LiveClassModel(String name, String url, String icon, String text) {
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
}
