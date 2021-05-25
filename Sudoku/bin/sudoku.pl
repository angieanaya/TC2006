% Constraint Logic Programming for solving a sudoku puzzle

:- use_module(library(clpfd)).	% Finite domain constraints
:- dynamic puzzle/2. % allows for dynamically set facts

% SUDOKU LATIN SQUARES
% base case 
subsquares([],[],[]).
% recursive call
subsquares([E1,E2,E3|L1],[E4,E5,E6|L2],[E7,E8,E9|L3]) :-
    	% each subsquare must have one instance of the integers
    	all_distinct([E1, E2, E3, E4, E5, E6, E7, E8, E9]),
    	% defines the other subsquares from the remainders of this lists
    	subsquares(L1,L2,L3).

% SUDOKU SOLVER USING CONSTRAINT PROP AND SEARCH
sudoku(Rows) :-
    % DEFINING A LATIN SQUARE
    % square has 9 rows
    length(Rows, 9),
    % each row has 9 elements
    maplist(same_length(Rows), Rows),
    append(Rows, Elements),
    % the domain of all elements are integers from 1 to 9
    Elements ins 1..9,
    % each row has only one instance of each integer
    maplist(all_distinct, Rows),
    transpose(Rows, Columns),
    % each column has only one instance of each integer
    maplist(all_distinct, Columns),
    % defining sudoku latin squares
    Rows = [R1,R2,R3,R4,R5,R6,R7,R8,R9],
    subsquares(R1,R2,R3),
    subsquares(R4,R5,R6),
    subsquares(R7,R8,R9),
    % adding search to the algorithm
    % ff stands for first fail labeling strategy
    maplist(labeling([ff]), Rows).

% SUDOKU SOLVER USING ONLY CONSTRAINT PROPAGATION
constraintP(Rows) :-
    % DEFINING A LATIN SQUARE
    % square has 9 rows
    length(Rows, 9),
    % each row has 9 elements
    maplist(same_length(Rows), Rows),
    append(Rows, Elements),
    % elements are integers between 1 and 9
    Elements ins 1..9,
    % each row has only one instance of each integer
    maplist(all_distinct, Rows),
    transpose(Rows, Columns),
    % each column has only one instance of each integer
    maplist(all_distinct, Columns),
    % defining sudoku latin squares
    Rows = [R1,R2,R3,R4,R5,R6,R7,R8,R9],
    subsquares(R1,R2,R3),
    subsquares(R4,R5,R6),
    subsquares(R7,R8,R9).

% EASY PUZZLE
puzzle(1,  [[_,_,4,_,7,_,_,9,1],
            [_,3,_,_,_,8,_,_,_],
            [_,6,7,_,_,_,3,_,5],
            [_,4,_,9,3,_,_,_,_],
            [_,_,1,_,_,_,9,_,_], 
            [_,_,_,8,_,4,_,3,6],
            [4,7,_,3,5,_,6,1,2],
            [9,2,_,7,8,1,4,_,3],
            [5,1,_,_,_,6,_,_,_]]).

% MEDIUM PUZZLE
puzzle(2,  [[4,9,_,1,_,_,_,3,_],
            [_,_,_,3,_,_,7,_,_],
            [7,_,_,2,_,_,1,_,_],
            [_,_,1,6,_,2,9,_,4],
            [_,_,_,_,_,_,2,_,_], 
            [8,_,_,_,_,_,_,1,3],
            [_,6,_,_,_,_,_,_,8],
            [_,_,_,_,_,_,_,_,_],
            [_,7,4,9,2,_,5,_,_]]).

% HARDEST PUZZLE EVER
puzzle(3, 	[[8,_,_,_,_,_,_,_,_],
            [_,_,3,6,_,_,_,_,_],
            [_,7,_,_,9,_,2,_,_],
            [_,5,_,_,_,7,_,_,_],
            [_,_,_,_,4,5,7,_,_],
            [_,_,_,1,_,_,_,3,_],
            [_,_,1,_,_,_,_,6,8],
            [_,_,8,5,_,_,_,1,_],
            [_,9,_,_,_,_,4,_,_]]).

/** <examples> Your example queries go here, e.g.
?- puzzle(1,Rows), sudoku(Rows), maplist(portray_clause, Rows).
?- puzzle(2,Rows), sudoku(Rows), maplist(portray_clause, Rows).
?- puzzle(3,Rows), sudoku(Rows), maplist(portray_clause, Rows).
?- puzzle(3,Rows), constraintP(Rows), maplist(portray_clause, Rows).
*/
