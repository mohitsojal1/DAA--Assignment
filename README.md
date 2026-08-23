# DAA--Assignment
# DAA — Assignment

Java implementations of core algorithms from the **Design and Analysis of Algorithms (DAA)** course. Each assignment applies a classic sorting algorithm to a real-ish dataset via a small command-line program.

## 📁 Repository Structure

```
DAA--Assignment/
├── Assignment-1/
│   ├── MergeSort.java            # Merge sort on social media usage data
│   ├── social_media_usage.csv    # Sample dataset
│   └── Output.png                # Sample run output
├── Assignment-2/
│   ├── QuickSort.java            # Quick sort on movie dataset (StreamFlix)
│   └── Output.txt                # Sample run output
├── Assignment3.java               # (In progress)
└── README.md
```

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

## 📌 Assignment 3

Work in progress — file currently empty.

## 🛠 Requirements

- Java (JDK 8+)
- No external dependencies — just `javac`/`java` from the command line

## 👤 Author

Mohit Sojal

## 📄 License

Add a license (e.g. MIT) if you'd like others to reuse this code.
