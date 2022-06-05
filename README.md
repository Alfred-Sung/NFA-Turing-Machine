# Multi-tape NFA Turing Machine
___
Fun little project creating a [non-deterministic finite automata](https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton) in plain Java

Machine state transitions are defined in `input.txt` and follow the formatting guide in `input template.txt`

## DFA Example
___
Current instructions in `input.txt` does addition between two binary numbers separated by a # character and B characters represent blank cells

Note that the machine also prints the current state that it is on within square brackets

Possible input would be `1#1`:
```
Tape 1: 1#1
----------- 
    B1#1    
     ^      
    [1]     
----------- 
   B1#1     
     ^      
    [1]     
----------- 
  B1#1      
     ^      
    [1]     
----------- 
 B1#1B      
     ^      
    [1]     
----------- 
  B1#1B     
     ^      
    [2]     
----------- 
  B1#0B     
     ^      
    [3]     
----------- 
   B1#0B    
     ^      
    [3]     
----------- 
    B1#0B   
     ^      
    [4]     
----------- 
     B0#0B  
     ^      
    [4]     
----------- 
    B10#0B  
     ^      
    [1]     
----------- 
   B10#0B   
     ^      
    [1]     
----------- 
  B10#0B    
     ^      
    [1]     
----------- 
 B10#0B     
     ^      
    [1]     
----------- 
B10#0B      
     ^      
    [1]     
----------- 
 B10#0B     
     ^      
    [2]     
----------- 
  B10#1B    
     ^      
    [2]     
----------- 
 B10#1B     
     ^      
    [5]     
----------- 
B10#BB      
     ^      
    [5]     
----------- 
 B10#BB     
     ^      
    [5]     
----------- 
  B10#BB    
     ^      
    [5]     
----------- 
   B10BBB   
     ^      
    [6]     
----------- 
    B10BBB  
     ^      
    [6]     
----------- 
     B10BBB 
     ^      
    [6]     
----------- 
    B10BBB  
     ^      
    [7]     
 Success!   
Ran turing machine in 0.2614s
1 turing instance(s) created
1 succeeded, 0 failed

Process finished with exit code 0

```

## NFA Example
___
The program also supports non-deterministic automatas; printing each possibility into separate columns in the output

Consider the following possible `input.txt` that creates all the possible binary numbers for any given number of digits
```
2
1
01#
01#B
1
2
1 -> 1 : 0 0 R
1 -> 1 : 0 1 R
1 -> 2 : B B N
```

```
Tape 1: 0000
----------- 
    B0000   
     ^      
    [1]     
----------- ----------- 
   B1000       B0000    
     ^           ^      
    [1]         [1]     
----------- ----------- ----------- ----------- 
  B1100       B0100       B1000       B0000     
     ^           ^           ^           ^      
    [1]         [1]         [1]         [1]     
----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- 
 B1110       B0110       B1010       B0010       B1100       B0100       B1000       B0000      
     ^           ^           ^           ^           ^           ^           ^           ^      
    [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]     
----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- 
B1111B      B0111B      B1011B      B0011B      B1101B      B0101B      B1001B      B0001B      B1110B      B0110B      B1010B      B0010B      B1100B      B0100B      B1000B      B0000B      
     ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^      
    [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]         [1]     
----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- ----------- 
B1111B      B0111B      B1011B      B0011B      B1101B      B0101B      B1001B      B0001B      B1110B      B0110B      B1010B      B0010B      B1100B      B0100B      B1000B      B0000B      
     ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^           ^      
    [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]         [2]     
 Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!    Success!   
Ran turing machine in 0.4626s
16 turing instance(s) created
16 succeeded, 0 failed
```
