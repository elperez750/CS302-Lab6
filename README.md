# Hash Table Problems

Two linear-time algorithms using hash maps for frequency counting and duplicate detection.

---

## Problem 1 — Largest Element Appearing At Least M Times

### Problem Statement
Implement a function that takes a reference to an integer array `arr` and an integer `m`. Return the largest element in the array that appears **at least m times**. The algorithm must run in **O(n)** time.

### Approach

The solution uses a classic **two-pass hash map** strategy:

**Pass 1 — Build frequency map**
Loop through `arr` and count how many times each element appears, storing results in a hash map where the key is the element and the value is its count.

**Pass 2 — Find the largest qualifying element**
Loop through `arr` again. For each element, check if its count in the map is `>= m`. If so, compare it against a running maximum and update if it's larger.

By the end of the second pass, the running maximum holds the answer.

### Why Two Passes?
The key insight is that you **cannot** reliably track the max during the first pass — you don't have complete frequency information yet. The second pass is safe because the full frequency picture is already built.

### Complexity
| | Complexity |
|---|---|
| Time | O(n) |
| Space | O(n) |

---

## Problem 2 — Duplicate Within Distance M

### Problem Statement
Given an integer array `arr` and an integer `m`, determine whether there exist two **distinct indices** `i` and `j` such that:
- `arr[i] == arr[j]`
- `|i - j| <= m`

The algorithm must use a **hash table**, run in **O(n)** time, and return the indices `i` and `j`. You may assume such a pair always exists.

### Approach

Use a hash map where the **key is the element value** and the **value is its most recently seen index**.

Loop through `arr` with index `i`:
1. If `arr[i]` is already in the map, check if `i - map[arr[i]] <= m`.
2. If yes — you found your pair. Return both indices.
3. If no — update the map with the current index (more recent is better for future comparisons).
4. If `arr[i]` is not in the map yet, store `arr[i] -> i`.

### Why Update to the Most Recent Index?
If a value appeared earlier but its stored index is too far back, keeping a more recent index gives future occurrences a better chance of being within distance `m`. Discarding the older index is always safe.

### Complexity
| | Complexity |
|---|---|
| Time | O(n) |
| Space | O(n) |

---

## Key Takeaway

Both problems follow the same core pattern — **trade O(n) space for O(n) time** by using a hash map to avoid nested loops. The hash map does the heavy lifting so you never need to sort or use nested iteration.
