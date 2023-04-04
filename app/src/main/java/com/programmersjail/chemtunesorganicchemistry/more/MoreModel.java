package com.programmersjail.chemtunesorganicchemistry.more;

public class MoreModel {

    private String title,description,titleTwo,descriptionTwo;

    public MoreModel(String title, String description, String titleTwo, String descriptionTwo) {
        this.title = title;
        this.description = description;
        this.titleTwo = titleTwo;
        this.descriptionTwo = descriptionTwo;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitleTwo() {
        return titleTwo;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }
}
