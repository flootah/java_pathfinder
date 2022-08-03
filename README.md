# Optimal Pathfinder

Given a board of numbers, find the most optimal path from top left to buttom right.
  - can only use moves going down, right, and diagonal
  - each area has a point value
  - reaching the bottom right of the board with the most points is most optimal
  
## Example Solution
```
Entered Board:
   1  -2   3   4
   5   6  -7   8
  -9  10 -11  12
  
Optimal Route:
1 -> -2 -> 3 -> 4 -> 8 -> 12

for 26 points
 ```
 
## Input

```
Choose an input method:
a: File entry
b: Manual entry
c: Random Matrices
```
Users have three ways to input boards:
  - boards can be input through manual entry, row by row
  - boards can be input via a file, with the board already laid out.
  - random boards can be generated, with the user only needing to specify the number of rows and columns
  
## Example Output:
```
Choose an input method:
a: File entry
b: Manual entry
c: Random Matrices
a
--------------------------------------------------
enter number of rows in the board
maximum supported is 99
3
--------------------------------------------------
enter number of columns in the board
maximum supported is 99
4
--------------------------------------------------
Enter the name of the file containing the board.
Please include the extension.
Type 'back' to return to input method selection.
board.txt
--------------------------------------------------
Entered Board:
   1  -2   3   4
   5   6  -7   8
  -9  10 -11  12
Augmented Board:
   1  -1   2   6
   6  12   5  14
  -3  22  11  26
Maximum:
26
Route:
1 -> -2 -> 3 -> 4 -> 8 -> 12
Exiting...
```
