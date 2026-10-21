# DAA — Assignment

Java implementations of core algorithms from the **Design and Analysis of Algorithms (DAA)** course. Each assignment applies classic algorithmic paradigms (Divide and Conquer, Greedy, Dynamic Programming, Backtracking/Graph Coloring, and Branch and Bound) to real-world problem scenarios.

## 📁 Repository Structure

```
DAA--Assignment/
├── Assignment-1/
│   ├── MergeSort.java                    # Merge sort on social media usage data
│   ├── social_media_usage.csv            # Sample dataset
│   └── Output.png                        # Sample run output
├── Assignment-2/
│   ├── QuickSort.java                    # Quick sort on movie dataset (StreamFlix)
│   └── Output.txt                        # Sample run output
├── Assignment-3/
│   ├── ReliefSupplySelection.java        # Fractional Knapsack (Disaster Relief)
│   ├── DAA_Assign-3.docx.pdf             # Assignment report
│   └── output_assign_3.png               # Sample output
├── Assignment-4/
│   ├── DijkstraAlgo.java                 # Dijkstra's Algorithm (Emergency Hospital Routing)
│   ├── DAA Assign-4.pdf                  # Assignment report
│   └── Output_Assign-4.png               # Sample output
├── Assignment-5/
│   ├── MultistageGraphAssignment5.java   # Multistage Graph (Dynamic Programming)
│   ├── DAA Assign-5.pdf                  # Assignment report
│   └── Output_Assign-5.png               # Sample output
├── Assignment-6/
│   ├── DisasterReliefAssignment6.java    # 0/1 Knapsack Problem (Dynamic Programming)
│   ├── DAA Assign-6.pdf                  # Assignment report
│   └── Output_Assign-6.png               # Sample output
├── Assignment-7/
│   ├── UniversityTimetableAssignment7.java # Graph Coloring (Timetable & Room Scheduling)
│   ├── DAA Assign-7.pdf                  # Assignment report
│   └── Output_Assign_7.png               # Sample output
├── Assignment-8/
│   ├── TSPBranchAndBoundAssignment8.java # TSP (Branch and Bound / Matrix Reduction)
│   ├── DAA Assign-8.pdf                  # Assignment report
│   └── Output_Assign-8.png               # Sample output
└── README.md
```

---

## 📌 Assignment 1 — Merge Sort
`MergeSort.java` reads `social_media_usage.csv` and sorts one selected column using merge sort.

**Columns available to sort by:**
1. Daily Minutes Spent
2. Posts Per Day
3. Likes Per Day
4. Follows Per Day

**Run it:**
```bash
cd Assignment-1
javac MergeSort.java
java MergeSort
```
You'll be prompted to choose which column to sort. Make sure `social_media_usage.csv` is in the same directory when you run it.

---

## 📌 Assignment 2 — Quick Sort
`QuickSort.java` implements a mini "StreamFlix" movie recommendation sorter. It reads a CSV of movies (`title,year,rating,watchtime`) and sorts them using quick sort.

**Sort options:**
1. IMDB Rating
2. Release Year
3. Watch Time Popularity

**Run it:**
```bash
cd Assignment-2
javac QuickSort.java
java QuickSort
```
You'll be prompted for a CSV filename, a sort key, and how many top results to display. See `Output.txt` for a sample run.

---

## 📌 Assignment 3 — Fractional Knapsack (Greedy Approach)
`ReliefSupplySelection.java` implements the Fractional Knapsack algorithm using a greedy approach to select relief supplies (e.g., medicine, food, water, medical kits, blankets) to maximize utility under a boat capacity constraint.

**Run it:**
```bash
cd Assignment-3
javac ReliefSupplySelection.java
java ReliefSupplySelection
```

---

## 📌 Assignment 4 — Dijkstra's Shortest Path Algorithm
`DijkstraAlgo.java` models a city traffic network with hospital nodes. It computes the shortest travel times and paths for ambulances to reach emergency hospital destinations and dynamically adapts to traffic congestion edge weight updates.

**Run it:**
```bash
cd Assignment-4
javac DijkstraAlgo.java
java DijkstraAlgo
```

---

## 📌 Assignment 5 — Multistage Graph (Dynamic Programming)
`MultistageGraphAssignment5.java` solves the shortest path problem across multiple stages using dynamic programming (backward formulation), determining the optimal path and minimum cost from source stage to destination stage.

**Run it:**
```bash
cd Assignment-5
javac MultistageGraphAssignment5.java
java MultistageGraphAssignment5
```

---

## 📌 Assignment 6 — 0/1 Knapsack Problem (Dynamic Programming)
`DisasterReliefAssignment6.java` solves the 0/1 Knapsack problem using dynamic programming to optimize disaster relief resource allocation when items cannot be divided. It builds a DP table and backtracks to determine the selected items and total utility.

**Run it:**
```bash
cd Assignment-6
javac DisasterReliefAssignment6.java
java DisasterReliefAssignment6
```

---

## 📌 Assignment 7 — Graph Coloring (Timetable Scheduling)
`UniversityTimetableAssignment7.java` constructs a conflict graph from student course registrations and applies graph coloring (largest degree first) to compute conflict-free exam time slots and assign suitable examination rooms based on enrollment capacity.

**Run it:**
```bash
cd Assignment-7
javac UniversityTimetableAssignment7.java
java UniversityTimetableAssignment7
```

---

## 📌 Assignment 8 — Traveling Salesperson Problem (Branch and Bound)
`TSPBranchAndBoundAssignment8.java` finds the optimal Traveling Salesperson tour using Least Cost Branch and Bound (LCBB) with row and column matrix reductions to calculate lower bounds at each state space tree node.

**Run it:**
```bash
cd Assignment-8
javac TSPBranchAndBoundAssignment8.java
java TSPBranchAndBoundAssignment8
```
