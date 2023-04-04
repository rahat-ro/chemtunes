package com.programmersjail.chemtunesorganicchemistry.studyui;

public class StudyLayoutModel {

    private String StudyTitle;
    private int StudyIcon;

    public StudyLayoutModel(String studyTitle, int studyIcon) {
        StudyTitle = studyTitle;
        StudyIcon = studyIcon;
    }

    public String getStudyTitle() {
        return StudyTitle;
    }

    public int getStudyIcon() {
        return StudyIcon;
    }

    public void setStudyTitle(String studyTitle) {
        StudyTitle = studyTitle;
    }

    public void setStudyIcon(int studyIcon) {
        StudyIcon = studyIcon;
    }
}
