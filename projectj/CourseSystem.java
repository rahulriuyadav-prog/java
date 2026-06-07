import java.util.Scanner;

public class CourseSystem {
    
    private static Course[] courses = new Course[10];
    private static Student[] totalStudents = new Student[50];
    
    private static int courseCount = 0;
    private static int studentCount = 0;

    // ==========================================
    // 📚 COURSE CLASS
    // ==========================================
    static class Course {
        private String courseName;
        private String courseId;
        private String duration;
        private Student[] enrolledStudents; 
        private int enrollmentCount;

        public Course(String courseName, String courseId, String duration) {
            this.courseName = courseName;
            this.courseId = courseId;
            this.duration = duration;
            this.enrolledStudents = new Student[30]; 
            this.enrollmentCount = 0;
        }

        public String getCourseName() { 
            return courseName; 
        }
        
        public String getCourseId() { 
            return courseId; 
        }

        /**
         * Adds a verified student reference directly into the course enrollment array
         */
        public void addStudentToCourse(Student student) {
            if (enrollmentCount < enrolledStudents.length) {
                enrolledStudents[enrollmentCount] = student;
                enrollmentCount++;
                System.out.println("🎉 Successfully enrolled " + student.getStudentName() + " in " + this.courseName);
            } else {
                System.out.println("❌ Registration failed: This course has reached maximum capacity.");
            }
        }

        public void displayCourseDetails() {
            System.out.println("\n-------------------------------------------");
            System.out.println("📚 Course: " + courseName);
            System.out.println("🆔 Course ID: " + courseId);
            System.out.println("⏱️ Duration: " + duration);
            System.out.println("👥 Enrolled Roster:");
            
            if (enrollmentCount == 0) {
                System.out.println("   (No students enrolled yet)");
            } else {
                for (int i = 0; i < enrollmentCount; i++) {
                    System.out.println("   • " + enrolledStudents[i].getStudentName() + 
                                       " [ID: " + enrolledStudents[i].getStudentId() + "] " +
                                       "- Progress: " + enrolledStudents[i].getProgress() + "%");
                }
            }
            System.out.println("-------------------------------------------");
        }
    }

    // ==========================================
    // 👤 STUDENT CLASS
    // ==========================================
    static class Student {
        private String studentName;
        private String studentId;
        private int progress; 

        public Student(String studentName, String studentId) {
            this.studentName = studentName;
            this.studentId = studentId;
            this.progress = 0; 
        }

        public String getStudentName() { 
            return studentName; 
        }
        
        public String getStudentId() { 
            return studentId; 
        }
        
        public int getProgress() { 
            return progress; 
        }
        
        /**
         * Mutator method containing input validation logic to avoid corrupted states
         */
        public void updateProgress(int value) {
            if (value >= 0 && value <= 100) {
                this.progress = value;
                System.out.println("📈 Progress updated to " + value + "% for " + studentName);
            } else {
                System.out.println("❌ Error: Progress metrics must remain within a 0 to 100 percentage block.");
            }
        }

        /**
         * Entry point for handling a localized student enrollment request
         */
        public void enroll(Course course) {
            course.addStudentToCourse(this);
        }
    }

    // ==========================================
    // ⚙️ MAIN APP FLOW & MENU
    // ==========================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        courses[courseCount++] = new Course("Java Programming", "C101", "6 Weeks");
        courses[courseCount++] = new Course("Web Development", "C102", "8 Weeks");

        totalStudents[studentCount++] = new Student("Rahul", "S001");
        totalStudents[studentCount++] = new Student("Priya", "S002");

        totalStudents[0].enroll(courses[0]); 
        totalStudents[1].enroll(courses[0]); 
        totalStudents[0].updateProgress(80);
        totalStudents[1].updateProgress(65);

        int choice;
        do {
            System.out.println("\n🎓 --- ONLINE COURSE MANAGEMENT SYSTEM --- 🎓");
            System.out.println("1. Create New Course");
            System.out.println("2. Register New Student");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Update Student Progress");
            System.out.println("5. Display Course Details & Reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Input error. Please enter a valid menu number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    if (courseCount >= courses.length) {
                        System.out.println("❌ Capacity Error: Global course table is full.");
                        break;
                    }
                    System.out.print("Enter Course Name: ");
                    String cName = scanner.nextLine();
                    System.out.print("Enter Course ID: ");
                    String cId = scanner.nextLine();
                    System.out.print("Enter Duration (e.g., '4 Weeks'): ");
                    String duration = scanner.nextLine();
                    
                    courses[courseCount++] = new Course(cName, cId, duration);
                    System.out.println("✅ Course created successfully!");
                    break;

                case 2:
                    if (studentCount >= totalStudents.length) {
                        System.out.println("❌ Capacity Error: Global student index directory is full.");
                        break;
                    }
                    System.out.print("Enter Student Name: ");
                    String sName = scanner.nextLine();
                    System.out.print("Enter Student ID: ");
                    String sId = scanner.nextLine();
                    
                    totalStudents[studentCount++] = new Student(sName, sId);
                    System.out.println("✅ Student registered successfully!");
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    String enrollSId = scanner.nextLine();
                    System.out.print("Enter Course ID: ");
                    String enrollCId = scanner.nextLine();

                    Student studentToEnroll = findStudent(enrollSId);
                    Course courseToTarget = findCourse(enrollCId);

                    if (studentToEnroll != null && courseToTarget != null) {
                        studentToEnroll.enroll(courseToTarget);
                    } else {
                        System.out.println("❌ Match Error: One or both IDs provided do not exist in the system registry.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    String progressSId = scanner.nextLine();
                    System.out.print("Enter New Progress Percentage (0-100): ");
                    
                    while (!scanner.hasNextInt()) {
                        System.out.print("Please provide a whole integer percentage value: ");
                        scanner.next();
                    }
                    int newProgress = scanner.nextInt();

                    Student studentToUpdate = findStudent(progressSId);
                    if (studentToUpdate != null) {
                        studentToUpdate.updateProgress(newProgress);
                    } else {
                        System.out.println("❌ Search Error: Student ID not found.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Printing System Wide Report ---");
                    if (courseCount == 0) {
                        System.out.println("No registered courses on file.");
                    } else {
                        for (int i = 0; i < courseCount; i++) {
                            courses[i].displayCourseDetails();
                        }
                    }
                    break;

                case 6:
                    System.out.println("👋 System shutting down safely. Goodbye!");
                    break;

                default:
                    System.out.println("❌ Selection Error: Option out of range. Please choose between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }

    // ==========================================
    // 🔍 INDEX LOOKUP HELPER FUNCTIONS
    // ==========================================
    private static Student findStudent(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (totalStudents[i].getStudentId().equalsIgnoreCase(id)) {
                return totalStudents[i];
            }
        }
        return null; // Return null safely if string lookup falls through
    }

    private static Course findCourse(String id) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId().equalsIgnoreCase(id)) {
                return courses[i];
            }
        }
        return null;
    }
}