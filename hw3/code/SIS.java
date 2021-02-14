import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SIS {
    private static String fileSep = File.separator;
    private static String lineSep = System.lineSeparator();
    private static String space   = " ";

    private List<Student> studentList = new ArrayList<>();

    public SIS(){ processOptics(); }

    private void processOptics(){

        Consumer<String> processOptic = file -> {
            try {
                // read lines from file
                List<String> lines = Files
                        .readAllLines(Paths.get(file));

                // process first line
                String[] firstLine = lines.get(0).split(space);
                int id = Integer.parseInt(firstLine[firstLine.length -1]);

                // process second line
                String[] secondLine = lines.get(1).split(space);
                int year = Integer.parseInt(secondLine[0]);
                int courseCode = Integer.parseInt(secondLine[1]);
                int credit = Integer.parseInt(secondLine[2]);

                // process third line
                String examType = lines.get(2);

                // process forth line
                String answers = lines.get(3);
                long numberOfQuestions = answers.chars().filter(ch -> ch == 'T' || ch == 'F' || ch == 'E').count();
                long correctAnswers = answers.chars().filter(ch -> ch == 'T').count();
                double grade = 100.0 * Float.valueOf(correctAnswers) / Float.valueOf(numberOfQuestions);

                // create new Course
                Course newCourse = new Course(courseCode, year, examType, credit, grade);

                // check if student exists
                Student student = studentList.stream()
                        .filter(s -> id == s.getStudentID())
                        .findAny()
                        .orElse(null);

                // if the student exists
                if(student != null) {
                    // add new course to student
                    student.getTakenCourses().add(newCourse);
                }
                // add new student
                else {
                    String[] firstNames = Arrays.copyOfRange(firstLine, 0, firstLine.length - 1);
                    String lastName = firstLine[firstLine.length-2];
                    Student newStudent = new Student(firstNames, lastName, id);

                    // Add first course to new student
                    newStudent.getTakenCourses().add(newCourse);
                    studentList.add(newStudent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        // Read all files in input folder
        try {
            Files.walk(Paths.get("input"))
                    .filter(Files::isRegularFile)
                    .map(Path::toString).forEach(processOptic);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double getGrade(int studentID, int courseCode, int year){
        // find student with given studentID
        Student student = studentList.stream()
                .filter(s -> studentID == s.getStudentID())
                .findAny()
                .orElse(null);

        // If this student exists
        if (student != null) {
            // Find first midterm
            Course midterm1 = student.getTakenCourses().stream()
                    .filter(course -> courseCode == course.getCourseCode()
                            && year == course.getYear()
                            && course.getExamType().equals("Midterm1"))
                    .findAny()
                    .orElse(null);
            // Find second midterm
            Course midterm2 = student.getTakenCourses().stream()
                    .filter(course -> courseCode == course.getCourseCode()
                            && year == course.getYear()
                            && course.getExamType().equals("Midterm2"))
                    .findAny()
                    .orElse(null);
            // Find final exam
            Course finalExam = student.getTakenCourses().stream()
                    .filter(course -> courseCode == course.getCourseCode()
                            && year == course.getYear()
                            && course.getExamType().equals("Final"))
                    .findAny()
                    .orElse(null);
            // If they all exist
            if (finalExam != null && midterm1 != null && midterm2 != null) {
                // calculate and return final grade
                double finalGrade = (finalExam.getGrade() * 0.5) + (midterm1.getGrade() * 0.25) + (midterm2.getGrade() * 0.25);
                return finalGrade;
            }
        }

        // return 0 if something wrong
        return 0;
    }

    public void updateExam(int studentID, int courseCode, String examType, double newGrade){
        // find student with given studentID
        Student student = studentList.stream()
                .filter(s -> studentID == s.getStudentID())
                .findAny()
                .orElse(null);

        // if student exists
        if (student != null) {
            // get the course that has the same coursecode and exam type with the maximum year
            Course course = student.getTakenCourses().stream()
                    .filter(c -> courseCode == c.getCourseCode()
                            && c.getExamType().equals(examType))
                    .max(Comparator.comparingInt(Course::getYear))
                    .get();

            // update grade
            course.setGrade(newGrade);

        }
    }

    public void createTranscript(int studentID){
        // list to keep the grades for each class
        List<Double> totalNotes = new ArrayList<>();
        // list to keep the credits for each class
        List<Double> totalCredits = new ArrayList<>();
        // find student with given studentID by matching studentID
        Student student = studentList.stream()
                .filter(s -> studentID == s.getStudentID())
                .findAny()
                .orElse(null);

        // if the student exists
        if (student != null) {
            // for each year a course is taken
            student.getTakenCourses().stream()
                    .map(Course::getYear).distinct().sorted().forEach(year -> {

                System.out.println(year);
                // for each of the course taken that year sorted by course code
                student.getTakenCourses().stream()
                        .filter(c -> year == c.getYear())
                        .map(Course::getCourseCode)
                        .distinct()
                        .sorted()
                        .forEach(courseCode -> {
                            System.out.print(courseCode);
                            // get the grade of the course that year
                            double grade = getGrade(studentID, courseCode, year);
                            // convert it to letter scale and print it
                            if (grade >= 89.5) System.out.println(" AA");
                            else if (grade >= 84.5 && grade <89.5) System.out.println(" BA");
                            else if (grade >= 79.5 && grade < 84.5) System.out.println(" BB");
                            else if (grade >= 74.5 && grade < 79.5) System.out.println(" CB");
                            else if (grade >= 69.5 && grade < 74.5) System.out.println(" CC");
                            else if (grade >= 64.5 && grade < 69.5) System.out.println(" DC");
                            else if (grade >= 59.5 && grade < 64.5) System.out.println(" DD");
                            else if (grade >= 49.5 && grade < 59.5) System.out.println(" FD");
                            else if (grade < 49.5) System.out.println(" FF");
                        });

            });
        }
        // list of course codes student has taken
        List<Integer> courses = student.getTakenCourses().stream().map(Course::getCourseCode).sorted()
                .distinct().collect(Collectors.toList());

        // for each course code, find the last year the course is taken
        // and get that course entry
        courses.stream().forEach(c -> {
            Course latestCourse = student.getTakenCourses().stream().filter(c1 -> c1.getCourseCode() == c)
                    .max(Comparator.comparingInt(Course::getYear))
                    .get();

            // add the credit of the course to credit list
            totalCredits.add(Double.valueOf(latestCourse.getCredit()));
            // get the grade of the course
            double grade = getGrade(studentID,c,latestCourse.getYear());
            // convert it to 4.0 scale and add the weight of course to totalNotes list
            if (grade >= 89.5) totalNotes.add(latestCourse.getCredit()*4.0);
            else if (grade >= 84.5 && grade <89.5) totalNotes.add(latestCourse.getCredit()*3.5);
            else if (grade >= 79.5 && grade < 84.5) totalNotes.add(latestCourse.getCredit()*3.0);
            else if (grade >= 74.5 && grade < 79.5) totalNotes.add(latestCourse.getCredit()*2.5);
            else if (grade >= 69.5 && grade < 74.5) totalNotes.add(latestCourse.getCredit()*2.0);
            else if (grade >= 64.5 && grade < 69.5) totalNotes.add(latestCourse.getCredit()*1.5);
            else if (grade >= 59.5 && grade < 64.5) totalNotes.add(latestCourse.getCredit()*1.0);
            else if (grade >= 49.5 && grade < 59.5) totalNotes.add(latestCourse.getCredit()*0.5);
            else if (grade < 49.5) totalNotes.add(0.0);

        });
        // find the sum of credits
        double totalCreditValue = totalCredits.stream().reduce(0.0, Double::sum);
        // find the sum of grades
        double totalNoteValue = totalNotes.stream().reduce(0.0, Double::sum);
        // calculate gpa
        double cgpa = totalNoteValue/totalCreditValue;
        // print gpa
        System.out.println("CGPA: " + String.format("%.2f", cgpa));

    }

    public void findCourse(int courseCode){
        // find the years the course was offered
        List<Integer> yearsOffered = studentList.stream()
                .flatMap(s -> s.getTakenCourses().stream()).filter(c -> courseCode == c.getCourseCode())
                .map(Course::getYear)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        // for each year
        yearsOffered.stream().forEach(y -> {
            System.out.print(y + " ");
            // calculate the number of students taken the course that year
            long count = studentList.stream().
                    flatMap(s -> s.getTakenCourses().stream())
                    .filter(c -> courseCode == c.getCourseCode())
                    .filter(c -> y == c.getYear()).count();
            // divide by 3 as each class has 3 exams
            System.out.println(count/3);
        });
    }

    public void createHistogram(int courseCode, int year){
        // list to keep grades
        List<Double> grades = new ArrayList<>();

        // for each student
        studentList.stream().forEach(s -> {
            // check if the student has taken the course that semester
            Course course= s.getTakenCourses().stream().filter(c -> courseCode == c.getCourseCode() && year == c.getYear())
                    .findAny().orElse(null);
            // if yes, add her/his grade to grades list
            if(course != null) grades.add(getGrade(s.getStudentID(), courseCode, year));
        });

        // filter the grades according to bins and print them
        System.out.println("0-10 " + grades.stream().filter(g -> g < 10).count());
        System.out.println("10-20 " + grades.stream().filter(g -> g >= 10 && g < 20).count());
        System.out.println("20-30 " + grades.stream().filter(g -> g >= 20 && g < 30).count());
        System.out.println("30-40 " + grades.stream().filter(g -> g >= 30 && g < 40).count());
        System.out.println("40-50 " + grades.stream().filter(g -> g >= 40 && g < 50).count());
        System.out.println("50-60 " + grades.stream().filter(g -> g >= 50 && g < 60).count());
        System.out.println("60-70 " + grades.stream().filter(g -> g >= 60 && g < 70).count());
        System.out.println("70-80 " + grades.stream().filter(g -> g >= 70 && g < 80).count());
        System.out.println("80-90 " + grades.stream().filter(g -> g >= 80 && g < 90).count());
        System.out.println("90-100 " + grades.stream().filter(g -> g >= 90).count());

    }
}