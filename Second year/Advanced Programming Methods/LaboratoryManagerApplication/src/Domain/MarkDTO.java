package Domain;

import java.time.LocalDate;

public class MarkDTO {
    private String studentName;
    private String projectNumber;
    private Float value;
    private Integer presentationWeek;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private LocalDate date;

    public Integer getPresentationWeek() {
        return presentationWeek;
    }

    public void setPresentationWeek(Integer presentationWeek) {
        this.presentationWeek = presentationWeek;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public MarkDTO(String studentName, String projectNumber, Float value,LocalDate date){
        this.studentName=studentName;
        this.projectNumber=projectNumber;
        this.value=value;
        this.date=LocalDate.now();
    }
}
