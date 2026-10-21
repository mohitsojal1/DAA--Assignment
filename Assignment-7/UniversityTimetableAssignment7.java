import java.util.*;

public class UniversityTimetableAssignment7 {

    static class Room {
        String name;
        int capacity;

        Room(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int studentCount = sc.nextInt();

        if (studentCount < 0) {
            System.out.println("Error: Number of students cannot be negative.");
            sc.close();
            return;
        }

        Map<String, Set<String>> studentCourses = new LinkedHashMap<>();
        Set<String> allCourses = new TreeSet<>();

        for (int i = 1; i <= studentCount; i++) {
            System.out.print("Enter number of courses for student S" + i + ": ");
            int count = sc.nextInt();

            if (count < 0) {
                System.out.println("Error: Course count cannot be negative.");
                sc.close();
                return;
            }

            Set<String> enrolled = new LinkedHashSet<>();
            System.out.print("Enter course names: ");

            for (int j = 0; j < count; j++) {
                String course = sc.next();
                enrolled.add(course);
                allCourses.add(course);
            }

            studentCourses.put("S" + i, enrolled);
        }

        System.out.print("Enter number of rooms: ");
        int roomCount = sc.nextInt();

        if (roomCount < 0) {
            System.out.println("Error: Number of rooms cannot be negative.");
            sc.close();
            return;
        }

        List<Room> rooms = new ArrayList<>();

        for (int i = 1; i <= roomCount; i++) {
            System.out.print(
                    "Enter room name and capacity for room " + i + ": ");

            String name = sc.next();
            int capacity = sc.nextInt();

            if (capacity < 0) {
                System.out.println(
                        "Error: Room capacity cannot be negative.");
                sc.close();
                return;
            }

            rooms.add(new Room(name, capacity));
        }

        if (allCourses.isEmpty()) {
            System.out.println("No courses were entered.");
            sc.close();
            return;
        }

        Map<String, Set<String>> graph = new TreeMap<>();
        Map<String, Integer> enrollmentCount = new HashMap<>();

        for (String course : allCourses) {
            graph.put(course, new TreeSet<>());
            enrollmentCount.put(course, 0);
        }

        // Build the conflict graph.
        for (Set<String> enrolled : studentCourses.values()) {

            for (String course : enrolled) {
                enrollmentCount.put(
                        course, enrollmentCount.get(course) + 1);
            }

            List<String> list = new ArrayList<>(enrolled);

            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    String c1 = list.get(i);
                    String c2 = list.get(j);

                    graph.get(c1).add(c2);
                    graph.get(c2).add(c1);
                }
            }
        }

        // Welsh-Powell: sort courses by descending degree.
        List<String> order = new ArrayList<>(allCourses);

        order.sort((a, b) -> {
            int degreeCompare = Integer.compare(
                    graph.get(b).size(), graph.get(a).size());

            return degreeCompare != 0
                    ? degreeCompare
                    : a.compareTo(b);
        });

        // Assign the smallest available colour to each course.
        Map<String, Integer> color = new HashMap<>();

        for (String course : order) {
            Set<Integer> usedColors = new HashSet<>();

            for (String neighbor : graph.get(course)) {
                if (color.containsKey(neighbor)) {
                    usedColors.add(color.get(neighbor));
                }
            }

            int assignedColor = 1;

            while (usedColors.contains(assignedColor)) {
                assignedColor++;
            }

            color.put(course, assignedColor);
        }

        int slotCount = 0;

        for (int assignedColor : color.values()) {
            slotCount = Math.max(slotCount, assignedColor);
        }

        // Sort rooms by ascending capacity.
        List<Room> roomsByCapacity = new ArrayList<>(rooms);

        roomsByCapacity.sort(
                Comparator.comparingInt((Room r) -> r.capacity)
                        .thenComparing(r -> r.name));

        Map<String, String> assignedRoom = new HashMap<>();
        boolean allocationFailed = false;

        // Allocate rooms independently for each time slot.
        for (int slot = 1; slot <= slotCount; slot++) {
            List<String> exams = new ArrayList<>();

            for (String course : allCourses) {
                if (color.get(course) == slot) {
                    exams.add(course);
                }
            }

            // Schedule exams with more students first.
            exams.sort((a, b) -> {
                int compare = Integer.compare(
                        enrollmentCount.get(b),
                        enrollmentCount.get(a));

                return compare != 0 ? compare : a.compareTo(b);
            });

            Set<String> occupiedRooms = new HashSet<>();

            for (String course : exams) {
                Room chosen = null;

                for (Room room : roomsByCapacity) {
                    if (!occupiedRooms.contains(room.name)
                            && room.capacity >= enrollmentCount.get(course)) {
                        chosen = room;
                        break;
                    }
                }

                if (chosen == null) {
                    allocationFailed = true;
                    assignedRoom.put(course, "NO ROOM");
                } else {
                    assignedRoom.put(course, chosen.name);
                    occupiedRooms.add(chosen.name);
                }
            }
        }

        // Display the final timetable.
        System.out.println(
                "\n========== UNIVERSITY EXAM TIMETABLE ==========");
        System.out.println(
                "Conflict Graph: Courses sharing students cannot share a time slot.");
        System.out.println("Coloring Algorithm: Welsh-Powell");
        System.out.println("Total Courses: " + allCourses.size());
        System.out.println("Total Exam Slots Used: " + slotCount);

        System.out.println("\nCourse Conflicts:");

        for (String course : allCourses) {
            System.out.println(course + " -> " + graph.get(course));
        }

        System.out.println("\nFinal Timetable:");

        System.out.printf("%-10s %-12s %-10s %-12s%n",
                "Course", "Time Slot", "Students", "Room");

        for (String course : allCourses) {
            System.out.printf("%-10s %-12s %-10d %-12s%n",
                    course,
                    "Slot " + color.get(course),
                    enrollmentCount.get(course),
                    assignedRoom.get(course));
        }

        System.out.println(
                "\nRoom Allocation Policy: Smallest available room that fits the exam.");

        if (allocationFailed) {
            System.out.println(
                    "Warning: One or more exams could not be assigned a suitable room.");
        } else {
            System.out.println(
                    "Room Allocation Status: All exams assigned successfully.");
        }

        System.out.println("================================================");

        sc.close();
    }
}
