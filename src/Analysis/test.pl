
sorted([]).
sorted([_]).

sorted([A,B|R]) :-
    A < B,
    sorted([B|R]).

mysort([],[]).
mysort([A],[A]).

mysort([A,B|R],[A|S]) :-
    A < B,
    mysort([B|R],S).

mysort([A,B|R],[B|S]) :-
    A > B,
    mysort([A|R],S).

