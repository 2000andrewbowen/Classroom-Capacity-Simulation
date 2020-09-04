import java.util.ArrayList;

public class Class {
    private String teacherName;
    private String courseNum;
    private ArrayList<Student> students;
    private int period;
    private int numStudentsIP;

    public Class(String teacherName, String courseNum, int period){
        this.teacherName = teacherName;
        this.courseNum = courseNum;
        this.period = period;
        students = new ArrayList<Student>();

    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void addStudent(Student s){
        this.students.add(s);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getNumStudentsIP() {
        return numStudentsIP;
    }

    public int getNumStudentIC(){
        return students.size();
    }

    public void setNumStudentsIP(int numStudentsIP) {
        this.numStudentsIP = numStudentsIP;
    }
}
