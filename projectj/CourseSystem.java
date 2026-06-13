import java.util.Scanner;

public class CourseSystem {

    static class Course {
        String name, id;
        Student[] students = new Student[10];
        int count = 0;

        Course(String name, String id) {
            this.name = name;
            this.id = id;
        }

        void enroll(Student s) {
            students[count++] = s;
            System.out.println(s.name + " enrolled in " + name);
        }

        void display() {
            System.out.println("\nCourse: " + name);
            System.out.println("Course ID: " + id);
            for(int i=0;i<count;i++)
                System.out.println(students[i].name + " - Progress: " + students[i].progress + "%");
        }
    }

    static class Student {
        String name, id;
        int progress = 0;

        Student(String name, String id) {
            this.name = name;
            this.id = id;
        }

        void updateProgress(int p) {
            if(p>=0 && p<=100)
                progress = p;
        }
    }

    static Course[] courses = new Course[10];
    static Student[] students = new Student[10];
    static int cCount = 0, sCount = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        courses[cCount++] = new Course("Java Programming","C101");
        students[sCount++] = new Student("Rahul","S001");

        students[0].updateProgress(80);
        courses[0].enroll(students[0]);

        int choice;

        do {
            System.out.println("\n1.Create Course");
            System.out.println("2.Add Student");
            System.out.println("3.Enroll Student");
            System.out.println("4.Show Details");
            System.out.println("5.Exit");
            System.out.print("Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:
                    System.out.print("Course Name: ");
                    String cn=sc.nextLine();
                    System.out.print("Course ID: ");
                    String ci=sc.nextLine();
                    courses[cCount++]=new Course(cn,ci);
                    break;

                case 2:
                    System.out.print("Student Name: ");
                    String sn=sc.nextLine();
                    System.out.print("Student ID: ");
                    String si=sc.nextLine();
                    students[sCount++]=new Student(sn,si);
                    break;

                case 3:
                    courses[0].enroll(students[0]);
                    break;

                case 4:
                    for(int i=0;i<cCount;i++)
                        courses[i].display();
                    break;
            }

        } while(choice!=5);

        sc.close();
    }
}