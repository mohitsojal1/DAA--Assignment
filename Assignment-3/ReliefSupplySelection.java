import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Item {
    String name;
    double weight;
    double value;
    double ratio;
    boolean divisible;

    Item(String name, double weight, double value, boolean divisible) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.divisible = divisible;
        this.ratio = value / weight;
    }
}

public class ReliefSupplySelection {

    public static void main(String[] args) {

        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("Medicine", 10, 100, false));
        items.add(new Item("Drinking Water", 20, 120, true));
        items.add(new Item("Food", 30, 150, true));
        items.add(new Item("Medical Kit", 15, 180, false));
        items.add(new Item("Blankets", 25, 100, true));

        double W = 50;
        double remainingWeight = W;
        double totalValue = 0;

        // Sort according to value/weight ratio
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                return Double.compare(b.ratio, a.ratio);
            }
        });

        System.out.println("RELIEF SUPPLY SELECTION");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-20s %-12s %-12s %-12s %-20s%n",
                "Item", "Weight", "Value", "Ratio", "Selected");
        System.out.println("-------------------------------------------------------------");

        for (Item item : items) {

            if (remainingWeight <= 0)
                break;

            // Take complete item
            if (item.weight <= remainingWeight) {

                remainingWeight -= item.weight;
                totalValue += item.value;

                System.out.printf("%-20s %-12.2f %-12.2f %-12.2f %-20.2f%n",
                        item.name,
                        item.weight,
                        item.value,
                        item.ratio,
                        item.weight);
            }

            // Take fraction if item is divisible
            else if (item.divisible) {

                double fraction = remainingWeight / item.weight;
                double selectedValue = fraction * item.value;

                totalValue += selectedValue;

                System.out.printf("%-20s %-12.2f %-12.2f %-12.2f %-20s%n",
                        item.name,
                        remainingWeight,
                        selectedValue,
                        item.ratio,
                        String.format("%.2f kg (fraction)", remainingWeight));

                remainingWeight = 0;
            }

            // Cannot divide this item
            else {
                continue;
            }
        }

        System.out.println("-------------------------------------------------------------");

        System.out.printf("Boat Capacity: %.2f kg%n", W);
        System.out.printf("Remaining Capacity: %.2f kg%n", remainingWeight);
        System.out.printf("Maximum Utility Value: %.2f%n", totalValue);
    }
}