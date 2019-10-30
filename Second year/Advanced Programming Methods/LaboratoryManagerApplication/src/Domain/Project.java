package Domain;

import java.util.Objects;

public class Project implements HasID<String>{
    private String id;
    private String description;
    private Integer deadLine;
    private Integer currentWeek;
    private Integer startWeek;

    public Project(String id, String description, Integer deadLine, Integer currentWeek, Integer startWeek) {
        this.id = id;
        this.description = description;
        this.deadLine = deadLine;
        this.currentWeek = currentWeek;
        this.startWeek = startWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Integer deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(Integer currentWeek) {
        this.currentWeek = currentWeek;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", deadLine=" + deadLine +
                ", currentWeek=" + currentWeek +
                ", startWeek=" + startWeek +
                '}';
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String s) {
        this.id=s;
    }

    @Override
    public String toFile(){
        return  id +
                "/" + description +
                "/" + deadLine +
                "/" + currentWeek +
                "/" + startWeek;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project projects = (Project) o;
        return Objects.equals(id, projects.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
