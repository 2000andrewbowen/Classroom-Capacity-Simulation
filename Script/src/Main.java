import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;


public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Student.Communities> cohortA = new ArrayList<Student.Communities>(){
            {
                add(Student.Communities.JOHNPAUL);
                add(Student.Communities.CATHERINEOFSIENA);
            }
        };

        ArrayList<Student.Communities> cohortB = new ArrayList<Student.Communities>(){
            {
                add(Student.Communities.JUANDIEGO);
                add(Student.Communities.MOTHERTERESA);
                add(Student.Communities.PADREPIO);
            }
        };

        File inputFolder = new File("C:/Users/2000a/OneDrive/Documents/Scripts for Mom/Communities");
        File[] listOfFiles = inputFolder.listFiles();
        File outputFolder = new File("C:/Users/2000a/OneDrive/Documents/Scripts for Mom/Output");

        ArrayList<Class> classes = new ArrayList<Class>();
        ArrayList<Student> students = new ArrayList<Student>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String filePath = inputFolder.getAbsolutePath() + "/" + file.getName();
                BufferedReader readbuffer = new BufferedReader(new FileReader(filePath));
                String strRead;
                strRead = readbuffer.readLine();

                String communityName = strRead.replace(",", "");
                while ((strRead=readbuffer.readLine())!=null) {
                    String splitarray[] = strRead.split("\t");
                    String name = splitarray[0] + " " + splitarray[1];
                    students.add(new Student(name, splitarray[1], communityName));
                }
            }
        }

        BufferedReader readOnline = new BufferedReader(new FileReader("C:/Users/2000a/OneDrive/Documents/Scripts for Mom/onlineOnly.txt"));
        String input;

        while ((input=readOnline.readLine())!=null) {
            //System.out.println(input);
            for(Student s: students){
                if(s.getName().equals(input)){
                    s.setOnline(true);
                    //System.out.println("Found one");
                    break;
                }
            }
        }

        BufferedReader inpersonOnly = new BufferedReader(new FileReader("C:/Users/2000a/OneDrive/Documents/Scripts for Mom/inpersonOnly.txt"));
        input = "";

        while ((input=inpersonOnly.readLine())!=null) {
            //System.out.println(input);
            for(Student s: students){
                if(s.getName().equals(input)){
                    s.setInperson(true);
                    //System.out.println("Found one");
                    break;
                }
            }
        }

        BufferedReader alwaysInClass = new BufferedReader(new FileReader("C:/Users/2000a/OneDrive/Documents/Scripts for Mom/alwaysInClass.txt"));
        input = "";

        while ((input=alwaysInClass.readLine())!=null) {
            //System.out.println(input);
            for(Student s: students){
                if(s.getName().equals(input)){
                    s.setAlwaysInClass(true);
                    //System.out.println("Found one");
                    break;
                }
            }
        }


        //System.out.println(students.toString());
        BufferedReader readbuffer = new BufferedReader(new FileReader("C:/Users/2000a/OneDrive/Documents/Scripts for Mom/Schedules.txt"));
        String strRead;
        readbuffer.readLine();
        while ((strRead=readbuffer.readLine())!=null) {
            String splitarray[] = strRead.split("\t");

            String studentNameRaw[] = splitarray[0].split(" ");
            StringBuilder nameBuilderS = new StringBuilder();
            nameBuilderS.append(studentNameRaw[1]);
            nameBuilderS.append(' ');
            nameBuilderS.append(studentNameRaw[0].substring(0, studentNameRaw[0].length() - 1));
            String studentName =  nameBuilderS.toString().replaceAll("\"", "");

            String courseNum = splitarray[1].trim();
            int period = getPeriod(splitarray[2]);

            String teacherNameRaw[] = splitarray[3].split(" ");
            StringBuilder nameBuilderT = new StringBuilder();
            nameBuilderT.append(teacherNameRaw[1]);
            nameBuilderT.append(' ');
            nameBuilderT.append(teacherNameRaw[0].substring(0, teacherNameRaw[0].length() - 1));
            String teacherName =  nameBuilderT.toString().replaceAll("\"", "");

            //System.out.println(teacherName + " " + courseNum + " " + getPeriodExpression(period) + " " + studentName);

            boolean classFound = false;
            int currentC = -1;
            for(Class c : classes){
                if(c.getCourseNum().equals(courseNum) && c.getPeriod() == period && c.getTeacherName().equals(teacherName)){
                    classFound = true;
                    currentC = classes.indexOf(c);
                    break;
                }
            }

            if(!classFound){
                classes.add(new Class(teacherName,courseNum,period));
                currentC = classes.size() - 1;
            }

            boolean studentFound = false;
            int currentS = -1;
            for(Student s : students){
                if(s.getName().equals(studentName)){
                    studentFound = true;
                    currentS = students.indexOf(s);
                    break;
                }
            }

            if(!studentFound){
                students.add(new Student(studentName, " "," "));
                currentS = students.size() - 1;
            }

            classes.get(currentC).addStudent(students.get(currentS));

        }

        for(Class c: classes){
            int i = 0;
            for(Student s: c.getStudents()){
                if(c.getPeriod() >= -2 && c.getPeriod() <= 2){
                    if(cohortA.contains(s.getCommunity()) && !s.isOnline()){
                        i++;
                    }
                }else{
                    if(cohortB.contains(s.getCommunity()) && !s.isOnline()){
                        i++;
                    }
                }
            }

            c.setNumStudentsIP(i);
        }

        String outputFilePath = outputFolder.getAbsolutePath() + "/" + "ClassroomNumbers.csv";

        try (PrintWriter writer = new PrintWriter(new File(outputFilePath))) {

            StringBuilder sb = new StringBuilder();
            sb.append("Teacher");
            sb.append(',');
            sb.append("Course #");
            sb.append(',');
            sb.append("Period");
            sb.append(',');
            sb.append("Students in Person");
            sb.append(',');
            sb.append("Total Students");
            sb.append('\n');

            for(Class c: classes){
                sb.append(c.getTeacherName());
                sb.append(',');
                sb.append(c.getCourseNum());
                sb.append(',');
                sb.append(getPeriodExpression(c.getPeriod()));
                sb.append(',');
                sb.append(c.getNumStudentsIP());
                sb.append(',');
                sb.append(c.getNumStudentIC());
                sb.append('\n');
            }

            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for(Class c: classes){
            outputFilePath = "C:/Users/2000a/OneDrive/Documents/Scripts for Mom/Output/Teacher Lists/" + c.getTeacherName() + "_" + c.getCourseNum() + "_" + getPeriodExpression(c.getPeriod()) + ".txt";
            ArrayList<Student> cohortAList = new ArrayList<Student>();
            ArrayList<Student> cohortBList = new ArrayList<Student>();
            ArrayList<Student> onlineList = new ArrayList<Student>();
            ArrayList<Student> inpersonList = new ArrayList<Student>();
            ArrayList<Student> alwaysInClassList = new ArrayList<Student>();

            try (PrintWriter writer = new PrintWriter(new File(outputFilePath))) {

                for(Student s: c.getStudents()){
                    if(s.isOnline()){
                        onlineList.add(s);
                    }else if(s.isInperson()) {
                        inpersonList.add(s);
                    }else if (s.isAlwaysInClass()){
                        alwaysInClassList.add(s);
                    }else if(cohortA.contains(s.getCommunity())){
                        cohortAList.add(s);
                    }else{
                        cohortBList.add(s);
                    }
                }
                cohortAList.sort(Comparator.comparing(Student::getLastName));
                cohortBList.sort(Comparator.comparing(Student::getLastName));
                StringBuilder sb = new StringBuilder();

                sb.append(c.getTeacherName() + " " + getPeriodExpression(c.getPeriod()) + " " + c.getCourseNum() +"\n\n");
                sb.append(String.format("\t%-40s %-40s", "Navy", "Silver"));
                sb.append("\n");

                int i = 0;

                if(cohortAList.size() == cohortBList.size()){
                    for(i = 0; i < cohortAList.size(); i++){
                        sb.append(String.format("%-40s %-40s", cohortAList.get(i).getName(), cohortBList.get(i).getName()));
                        sb.append("\n");
                    }
                }else if(cohortAList.size() < cohortBList.size()){
                    for(i = 0; i < cohortAList.size(); i++){
                        sb.append(String.format("%-40s %-40s", cohortAList.get(i).getName(), cohortBList.get(i).getName()));
                        sb.append("\n");
                    }
                    for(i = i; i < cohortBList.size(); i++){
                        sb.append(String.format("%-40s %-40s", "",cohortBList.get(i).getName()));
                        sb.append("\n");
                    }
                }else{
                    for(i = 0; i < cohortBList.size(); i++){
                        sb.append(String.format("%-40s %-40s", cohortAList.get(i).getName(), cohortBList.get(i).getName()));
                        sb.append("\n");
                    }
                    for(i = i; i < cohortAList.size(); i++){
                        sb.append(String.format("%-40s %-40s", cohortAList.get(i).getName(),""));
                        sb.append("\n");
                    }
                }

                sb.append("\n");

                if(!onlineList.isEmpty()){
                    sb.append("\t\t\t\tOnline Only Students\n");
                    for(Student s: onlineList){
                        sb.append(String.format("\t\t\t%s\n", s.getName()));
                    }
                }

                sb.append("\n\n");

                if(!inpersonList.isEmpty()){
                    sb.append("\t\t\t\tOther Onsite Learners\n");
                    for(Student s: inpersonList){
                        sb.append(String.format("\t\t\t%s\n", s.getName()));
                    }
                }

                sb.append("\n\n");

                if(!alwaysInClassList.isEmpty()){
                    sb.append("\t\t\t\tRequired to be in Classroom\n");
                    for(Student s: alwaysInClassList){
                        sb.append(String.format("\t\t\t%s\n", s.getName()));
                    }
                }

                writer.write(sb.toString());

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static int getPeriod(String periodExpression) {
        int period = 0;
        switch (periodExpression){
            case "1(A)":
                period = 1;
                break;
            case "1(B)":
                period = -1;
                break;
            case "2(A)":
                period = 2;
                break;
            case "2(B)":
                period = -2;
                break;
            case "3(A)":
                period = 3;
                break;
            case "3(B)":
                period = -3;
                break;
            case "4(A)":
                period = 4;
                break;
            case "4(B)":
                period = -4;
                break;

        }
        return period;
    }

    private static String getPeriodExpression(int period){
        String expression = "";

        switch (period){
            case 1:
                expression = "1(A)";
                break;
            case -1:
                expression = "1(B)";
                break;
            case 2:
                expression = "2(A)";
                break;
            case -2:
                expression = "2(B)";
                break;
            case 3:
                expression = "3(A)";
                break;
            case -3:
                expression = "3(B)";
                break;
            case 4:
                expression = "4(A)";
                break;
            case -4:
                expression = "4(B)";
                break;
        }

        return expression;
    }

}
