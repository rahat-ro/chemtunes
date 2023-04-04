package com.programmersjail.chemtunesorganicchemistry.flashcard;

public class FlashCardModel {

    private String backTitle,backImg,fontTitle,fontImg,chapterName,type;

    public FlashCardModel(String backTitle, String backImg, String fontTitle, String fontImg, String chapterName, String type) {
        this.backTitle = backTitle;
        this.backImg = backImg;
        this.fontTitle = fontTitle;
        this.fontImg = fontImg;
        this.chapterName = chapterName;
        this.type = type;
    }

    public String getBackTitle() {
        return backTitle;
    }

    public String getBackImg() {
        return backImg;
    }

    public String getFontTitle() {
        return fontTitle;
    }

    public String getFontImg() {
        return fontImg;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getType() {
        return type;
    }
}
