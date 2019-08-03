
powOf2(X) :-
    contains(X,[1,2,4,8,16,32,64,128,256,512]).

contains(X,[X|_]).
contains(X,[_|R]) :-
    contains(X,R).

anInputList([],0).
anInputList([X|R],N) :-
    N > 0,
    powOf2(X),
    M is N-1,   % plus(N,-1,M),
    anInputList(R,M).

uptoInputList(L,N) :-
    N >= 0,
    (
        (
            M is N-1,  % plus(N,-1,M),
            uptoInputList(L,M)
        )
        |
        anInputList(L,N)
    ).

or(X1,X2,Y) :-
    Y is (X1 \/ X2).

and(X1,X2,Y) :-
    Y is (X1 /\ X2).

xor(X1,X2,Y) :-
    Y is (X1 xor X2).

bitCount5(L) :-
    % is_list(L),
    % length(L,5),
    % allPowsOf2(L),
    noDups(L).

noDups([]).
noDups([X|R]) :-
    not(contains(X,R)),
    noDups(R).

allPowsOf2([]).
allPowsOf2([X|R]) :-
    powOf2(X),
    allPowsOf2(R).

orList([],0).
orList([X|R],Res) :-
    orList(R,SubRes),
    or(X,SubRes,Res).

or5(L1,L2,L3,L4,L5,Res) :-
    Res is (L1 \/ L2 \/ L3 \/ L4 \/ L5).

% set_prolog_flag(answer_write_options,[max_depth(0)]).

isGoodSeq([_,_,_,_],_,4).
%isGoodSeq(Inp,Cprevs,Seq,Count) :-
isGoodSeq([L1,L2,L3,L4,L5|R],Cprevs,Count) :-
    %Count > 4,
    bitCount5([L1,L2,L3,L4,L5]),
    or5(L1,L2,L3,L4,L5, C),
    not(contains(C,Cprevs
        )),
    NextCount is Count-1,
    isGoodSeq([L2,L3,L4,L5|R], [C|Cprevs], NextCount).

testGoodSeq(Seq,Size) :-
    % Size is 5,
    anInputList(Seq,Size),
    isGoodSeq(Seq,[],Size).

buildUp4(L1,L2,L3,L4) :-
    powOf2(L1),
    powOf2(L2),
    L2 \= L1,
    powOf2(L3),
    L3 \= L2,
    L3 \= L1,
    powOf2(L4),
    L4 \= L3,
    L4 \= L2,
    L4 \= L1.

extendGoodSeq([L1,L2,L3,L4,L5|R],Cprevs,Count) :-
    % print(bla),
    Count > 4,
    powOf2(L5), % printf('%s\n', L5),
    %bitCount5([L5,L1,L2,L3,L4]),
    not(contains(L5,[L1,L2,L3,L4])),
    or5(L1,L2,L3,L4,L5, C),
    not(contains(C,Cprevs)),
    NextCount is Count-1,
    extendGoodSeq([L2,L3,L4,L5|R], [C|Cprevs], NextCount).    

extendGoodSeq(_,_,4).

testExGoodSeq([L1,L2,L3,L4|R],Size) :-
    Size >= 5,
    buildUp4(L1,L2,L3,L4),
    extendGoodSeq([L1,L2,L3,L4|R],[],Size).
    % isGoodSeq(Seq,[],Size).

% set_prolog_flag(answer_write_options,[max_depth(0)]).

multiExGoodSeq([Start|R],Size,Start) :-
    testExGoodSeq([Start|R],Size).

runMain1(Seq,Size) :-
    first_solution(Seq, [
            multiExGoodSeq(Seq,Size,1), 
            multiExGoodSeq(Seq,Size,2), 
            multiExGoodSeq(Seq,Size,4), 
            multiExGoodSeq(Seq,Size,8),
            multiExGoodSeq(Seq,Size,16), 
            multiExGoodSeq(Seq,Size,32), 
            multiExGoodSeq(Seq,Size,64),
            multiExGoodSeq(Seq,Size,128), 
            multiExGoodSeq(Seq,Size,256), 
            multiExGoodSeq(Seq,Size,512)
        ],
        []
    ).

multiExGoodSeq2([Start|R],Size,Starts) :-
    contains(Start,Starts),
    testExGoodSeq([Start|R],Size).

runMain2(Seq,Size) :-
    first_solution(Seq, [
            multiExGoodSeq2(Seq,Size,[1,2]), 
            multiExGoodSeq2(Seq,Size,[4,8]), 
            multiExGoodSeq2(Seq,Size,[16,32]),
            multiExGoodSeq2(Seq,Size,[64,128]),
            multiExGoodSeq2(Seq,Size,[256,512])
        ],
        []
    ).
