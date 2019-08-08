:- set_prolog_flag(answer_write_options,[max_depth(0)]).


powOf2(X) :-
    contains(X,[1,2,4,8,16,32,64,128,256,512]).

contains(X,[X|_]).
contains(X,[_|R]) :-
    contains(X,R).

anInputList([],0).
anInputList([X|R],N) :-
    N > 0,
    powOf2(X),
    M is N-1,
    anInputList(R,M).

or(X1,X2,Y) :-
    Y is (X1 \/ X2).


bitCount5([X1,X2,X3,X4,X5]) :-
    % allPowsOf2([X1,X2,X3,X4,X5]),
    noDups([X1,X2,X3,X4,X5]).

noDups([]).
noDups([X|R]) :-
    not(contains(X,R)),
    noDups(R).

allPowsOf2([]).
allPowsOf2([X|R]) :-
    powOf2(X),
    allPowsOf2(R).

or5(L1,L2,L3,L4,L5,Res) :-
    Res is (L1 \/ L2 \/ L3 \/ L4 \/ L5).


% prefixOf([_|_],[]).
% prefixOf([X|R1],[X|R2]) :-
%     prefixOf(R1,R2).
prefixOf([X|_],[X]).
prefixOf([X|R1],[X|R2]) :-
    prefixOf(R1,R2).

% concatTo(Target,Src1,Src2).  % Target = Src1+Src2.
concatTo([],[],[]).            % Stop.
concatTo([X|R],[X|R_S1],S2) :- % S1 not empty
    concatTo(R,R_S1,S2).
concatTo([X|R],[],[X|R_S2]) :- % S1 empty, S2 not empty
    concatTo(R,[],R_S2).

sorted([]).
sorted([_]).
sorted([X,Y|R]) :-
    X < Y,
    sorted([Y|R]).