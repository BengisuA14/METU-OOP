public class Main {
    public static void main ( String [] args ) {
        SIS sis = new SIS();

        /*System.out.println(sis.getGrade(9858516, 2300105, 20101));
        sis.updateExam(9858516, 2300105, "Final", 100);
        System.out.println(sis.getGrade(9858516, 2300105, 20101));*/
        sis.createTranscript(5021430);
        sis.findCourse(5710336);
        sis.createHistogram(5710336,20222);
    }
}
