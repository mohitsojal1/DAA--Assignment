import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DisasterReliefAssignment6 {

static class Item {
    String name;
    int weight;
    int utility;

    Item(String name, int weight, int utility) {
        this.name = name;
        this.weight = weight;
        this.utility = utility;
    }
}

static void knapsack(Item[] items, int capacity) {
    int n = items.length;

    // DP table: dp[i][w] stores maximum utility
    // using the first i items with capacity w.
    int[][] dp = new int[n + 1][capacity + 1];

    // Build the DP table.
    for (int i = 1; i <= n; i++) {
        for (int w = 0; w <= capacity; w++) {

            if (items[i - 1].weight <= w) {
                int include = items[i - 1].utility
                        + dp[i - 1][w - items[i - 1].weight];

                int exclude = dp[i - 1][w];

                dp[i][w] = Math.max(include, exclude);
            } else {
                dp[i][w] = dp[i - 1][w];
            }
        }
    }

    // Maximum achievable utility.
    int maxUtility = dp[n][capacity];

    // Trace back to identify selected items.
    List<Item> selectedItems = new ArrayList<>();
    int remainingCapacity = capacity;

    for (int i = n; i > 0; i--) {
        if (dp[i][remainingCapacity]
                != dp[i - 1][remainingCapacity]) {

            selectedItems.add(items[i - 1]);
            remainingCapacity -= items[i - 1].weight;
        }
    }

    Collections.reverse(selectedItems);

    // Display results.
    System.out.println("\n========== DISASTER RELIEF RESOURCE ALLOCATION ==========");
    System.out.println("Truck Capacity: " + capacity + " kg");

    System.out.println("\nAvailable Relief Items:");
    System.out.printf("%-5s %-20s %-12s %-12s%n",
            "No.", "Item", "Weight (kg)", "Utility");

    for (int i = 0; i < n; i++) {
        System.out.printf("%-5d %-20s %-12d %-12d%n",
                i + 1, items[i].name,
                items[i].weight, items[i].utility);
    }

    System.out.println("\nSelected Relief Items:");
    System.out.printf("%-5s %-20s %-12s %-12s%n",
            "No.", "Item", "Weight (kg)", "Utility");

    int totalWeight = 0;
    int totalUtility = 0;

    if (selectedItems.isEmpty()) {
        System.out.println("No items selected.");
    } else {
        for (int i = 0; i < selectedItems.size(); i++) {
            Item item = selectedItems.get(i);

            System.out.printf("%-5d %-20s %-12d %-12d%n",
                    i + 1, item.name,
                    item.weight, item.utility);

            totalWeight += item.weight;
            totalUtility += item.utility;
        }
    }

    System.out.println("\n==================== FINAL RESULT ====================");
    System.out.println("Maximum Utility       : " + maxUtility);
    System.out.println("Total Weight Carried  : " + totalWeight + " kg");
    System.out.println("Remaining Capacity    : "
            + (capacity - totalWeight) + " kg");

    System.out.println("\nTime Complexity       : O(N * W)");
    System.out.println("Space Complexity      : O(N * W)");
    System.out.println("======================================================");
}

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter the number of relief items: ");
    int n = sc.nextInt();

    System.out.print("Enter the truck capacity (kg): ");
    int capacity = sc.nextInt();

    if (n < 0 || capacity < 0) {
        System.out.println(
                "Error: Number of items and capacity cannot be negative.");
        sc.close();
        return;
    }

    Item[] items = new Item[n];

    System.out.println("\nEnter details for each item:");

    for (int i = 0; i < n; i++) {
        System.out.println("\nItem " + (i + 1));

        System.out.print("Enter item name (one word): ");
        String name = sc.next();

        System.out.print("Enter weight (kg): ");
        int weight = sc.nextInt();

        System.out.print("Enter utility value: ");
        int utility = sc.nextInt();

        if (weight <= 0 || utility < 0) {
            System.out.println(
                    "Error: Weight must be positive and utility cannot be negative.");
            sc.close();
            return;
        }

        items[i] = new Item(name, weight, utility);
    }

    knapsack(items, capacity);
    sc.close();
}

}
