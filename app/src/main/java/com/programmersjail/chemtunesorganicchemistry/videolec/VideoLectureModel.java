package com.programmersjail.chemtunesorganicchemistry.videolec;

public class VideoLectureModel {

    private String videoID,lectureTitle,duration,type,chapter,category_name;

    public VideoLectureModel(String videoID, String lectureTitle, String duration, String type, String chapter, String category_name) {
        this.videoID = videoID;
        this.lectureTitle = lectureTitle;
        this.duration = duration;
        this.type = type;
        this.chapter = chapter;
        this.category_name = category_name;
    }

    public String getVideoID() {
        return videoID;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getChapter() {
        return chapter;
    }

    public String getCategory_name() {
        return category_name;
    }
}
