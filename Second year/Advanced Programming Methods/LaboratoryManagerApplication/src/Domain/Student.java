package Domain;

import java.util.Objects;

public class Student implements HasID<String>{
    private String id;
    private String name;
    private String email;
    private String teacher;
    private String group;

    public Student(String id, String name, String email, String teacher,String group) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.teacher = teacher;
        this.group=group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", group='" + group + '\'' +
                ", teacher=" + teacher +
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
    public String toFile() {
        return  id +
                "/" + name +
                "/" + email +
                "/" + teacher +
                "/" + group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
