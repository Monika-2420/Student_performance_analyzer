import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    record Student(
            int id,
            String name,
            String department,
            int mark1,
            int mark2,
            int mark3,
            LocalDate joiningDate
    ) {

        int total() {
            return mark1 + mark2 + mark3;
        }

        double average() {
            return total() / 3.0;
        }

        String grade() {
            int avg = (int) average();

            if (avg >= 90)
                return "A+";
            else if (avg >= 80)
                return "A";
            else if (avg >= 70)
                return "B";
            else if (avg >= 60)
                return "C";
            else if (avg >= 50)
                return "D";
            else
                return "F";
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Student> students = new ArrayList<>();

        System.out.println("*******************************");
        System.out.println("   STUDENT PERFORMANCE SYSTEM");
        System.out.println("*******************************");


        // ENTER NUMBER OF STUDENTS

        System.out.print("\nEnter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();


        // ENTER STUDENT DETAILS


        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter details for Student " + i);

            System.out.print("ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Department: ");
            String department = sc.nextLine();

            System.out.print("Mark 1: ");
            int mark1 = sc.nextInt();

            System.out.print("Mark 2: ");
            int mark2 = sc.nextInt();

            System.out.print("Mark 3: ");
            int mark3 = sc.nextInt();

            sc.nextLine();

            System.out.print("Joining date (yyyy-mm-dd): ");
            String date = sc.nextLine();

            LocalDate joiningDate =
                    LocalDate.parse(date);

            students.add(
                    new Student(
                            id,
                            name,
                            department,
                            mark1,
                            mark2,
                            mark3,
                            joiningDate
                    )
            );
        }

        // --------------------------------
        // MENU
        // --------------------------------

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("             MENU");
            System.out.println("================================");

            System.out.println("1. Display all students");
            System.out.println("2. Search student by ID");
            System.out.println("3. Find highest scorer");
            System.out.println("4. Students above 80 average");
            System.out.println("5. Department-wise student count");
            System.out.println("6. Complete student report");
            System.out.println("7. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            // OPTION 1

            if (choice == 1) {

                System.out.println("\n--- ALL STUDENTS ---");

                students.forEach(student ->
                        System.out.println(
                                student.id() + " | " +
                                student.name() + " | " +
                                student.department()
                        )
                );
            }

            // OPTION 2

            else if (choice == 2) {

                System.out.print("\nEnter student ID: ");
                int id = sc.nextInt();

                Optional<Student> result =
                        students.stream()
                                .filter(student ->
                                        student.id() == id)
                                .findFirst();

                if (result.isPresent()) {

                    Student student = result.get();

                    System.out.println("\nStudent Found");
                    System.out.println("Name       : " + student.name());
                    System.out.println("Department : " + student.department());
                    System.out.println("Mark 1     : " + student.mark1());
                    System.out.println("Mark 2     : " + student.mark2());
                    System.out.println("Mark 3     : " + student.mark3());
                    System.out.println("Total      : " + student.total());
                    System.out.println("Average    : " + student.average());
                    System.out.println("Grade      : " + student.grade());

                } else {

                    System.out.println("Student not found.");
                }
            }

            // OPTION 3

            else if (choice == 3) {

                Optional<Student> highest =
                        students.stream()
                                .max(
                                        Comparator.comparingDouble(
                                                Student::average
                                        )
                                );

                if (highest.isPresent()) {

                    Student student = highest.get();

                    System.out.println("\n--- HIGHEST SCORER ---");

                    System.out.println(
                            student.name() +
                            " - " +
                            student.average()
                    );
                }
            }


            // OPTION 4
            else if (choice == 4) {

                System.out.println(
                        "\n--- STUDENTS ABOVE 80 ---"
                );

                students.stream()
                        .filter(student ->
                                student.average() > 80)
                        .forEach(student ->
                                System.out.println(
                                        student.name() +
                                        " - " +
                                        student.average()
                                )
                        );
            }

            // OPTION 5


            else if (choice == 5) {

                Map<String, Long> count =
                        students.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                Student::department,
                                                Collectors.counting()
                                        )
                                );

                System.out.println(
                        "\n--- DEPARTMENT COUNT ---"
                );

                count.forEach((department, total) ->
                        System.out.println(
                                department +
                                " : " +
                                total +
                                " students"
                        )
                );
            }

            // OPTION 6

            else if (choice == 6) {

                System.out.println(
                        "\n--- COMPLETE REPORT ---"
                );

                students.forEach(student ->

                        System.out.println(
                                student.name() +
                                " | Total: " +
                                student.total() +
                                " | Average: " +
                                String.format(
                                        "%.2f",
                                        student.average()
                                ) +
                                " | Grade: " +
                                student.grade()
                        )
                );

                LocalDateTime now =
                        LocalDateTime.now();

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "dd-MM-yyyy hh:mm a"
                        );

                System.out.println(
                        "\nReport generated: " +
                        now.format(formatter)
                );
            }

            // OPTION 7

            else if (choice == 7) {

                System.out.println(
                        "\nThank you! Program ended."
                );
            }

            else {

                System.out.println(
                        "\nInvalid choice. Try again."
                );
            }

        } while (choice != 7);

        sc.close();
    }
}