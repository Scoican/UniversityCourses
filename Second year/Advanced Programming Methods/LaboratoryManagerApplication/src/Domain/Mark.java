package Domain;

import java.time.LocalDate;

public class Mark implements HasID<String> {
    private String studentId;
    private String projectId;
    private Float value;
    private String feedback;
    private LocalDate date;

    public Integer getPresentationWeek() {
        return presentationWeek;
    }

    public void setPresentationWeek(Integer presentationWeek) {
        this.presentationWeek = presentationWeek;
    }

    private Integer presentationWeek;

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Mark(String studentId, String projectId, Float value, String feedback) {
        this.studentId = studentId;
        this.projectId = projectId;
        this.value = value;
        this.feedback = feedback;
        this.date=LocalDate.now();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getProjectId() {
        return projectId;
    }

    @Override
    public String getID() {
        return studentId+"-"+projectId;
    }

    @Override
    public void setID(String s) {
        String[] split=s.split("-");
        this.studentId=split[0];
        this.projectId=split[1];
    }

    @Override
    public String toFile() {
        return getID()+"/"+this.value+"/"+this.feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback=feedback;
    }
}
