
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

extendGoodSeq([_,_,_,_],_,4).

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
% 4,1,2,8,16,32,4,1,2,8,64,16,4,1,2,128,8,16,4,1,256,2,8,16,4,512,1,2,8,32,64,4,1,2,128,32,8,4,1,256,64,2,8,4,128,256,1,2,8,512,16,32,1,2,64,128,8,16,1,32,64,4,128,2,16,32,1,256,4,2,512,8,32,64,1,128,16,2,256,4,32,8,16,64,2,256,1,32,8,128,512,2,1,32,256,128,4,2,64,512,1,8,4,128,64,16,1,256,4,128,64,8,32,2,256,16,64,4,8,512,2,128,16,32,8,4,512,1,256,16,8,32,64,128,256,1,2,512,16,64,4,32,2,256,512,8,16,4,128,256,32,8,16,512,64,1,4,32,256,512,8,1,64,128,256,512,2,8,64,128,256,16,2,32,512,4,1,16,128,32

runMain3(Seq,Size,Start) :-
    prefixOf(Seq,Start),
    testExGoodSeq(Seq,Size).

% prefixOf([_|_],[]).
% prefixOf([X|R1],[X|R2]) :-
%     prefixOf(R1,R2).

runMain3H(St,Sq,Sz) :-
    runMain3(Sq,Sz,St).

prefixOf([X|_],[X]).
prefixOf([X|R1],[X|R2]) :-
    prefixOf(R1,R2).

ourGuess(
    [4,1,2,8,16,32,4,1,2,8,64,16,4,1,2,128,8,16,4,1,256,2,8,16,4,512,1,2,8,32,64,4,1,2,128,32,8,4,1,256,64,2,8,4,128,256,1,2,8,512,16,32,1,2,64,128,8,16,1,32,64,4,128,2,16,32,1,256,4,2,512,8,32,64,1,128,16,2,256,4,32,8,16,64,2,256,1,32,8,128,512,2,1,32,256,128,4,2,64,512,1,8,4,128,64,16,1,256,4,128,64,8,32,2,256,16,64,4,8,512,2,128,16,32,8,4,512,1,256,16,8,32,64,128,256,1,2,512,16,64,4,32,2,256,512,8,16,4,128,256,32,8,16,512,64,1,4,32,256,512,8,1,64,128,256,512,2,8,64,128,256,16,2,32,512,4,1,16,128,32]
).

% concatTo(Target,Src1,Src2).  % Target = Src1+Src2.
concatTo([],[],[]).            % Stop.
concatTo([X|R],[X|R_S1],S2) :- % S1 not empty
    concatTo(R,R_S1,S2).
concatTo([X|R],[],[X|R_S2]) :- % S1 empty, S2 not empty
    concatTo(R,[],R_S2).

multiExGoodSeq_N1(Seq,Size,Extra) :-
    ourGuess(Guess),
    %Start|_ is Seq,
    concatTo(Start,Guess,Extra),
    length(Seq,Size),
    concatTo(Seq,Start,_),
    testExGoodSeq(Seq,Size).

runMain_N1(Seq,Size) :-
    first_solution(Seq, [
            multiExGoodSeq_N1(Seq,Size, [   2,   4,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   4,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   4,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   4, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   4, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   8,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   8,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   8,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   8, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,   8, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,  64,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,  64,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,  64,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,  64, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   2,  64, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 256,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 256,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 256,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 256,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 256, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 512,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 512,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 512,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 512,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   2, 512, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   2,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   2,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   2,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   2, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   2, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   4,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   4,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   4,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   4, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,   4, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,  64,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,  64,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,  64,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,  64, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [   8,  64, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 256,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 256,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 256,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 256,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 256, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 512,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 512,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 512,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 512,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [   8, 512, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   2,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   2,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   2,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   2, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   2, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   4,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   4,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   4,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   4, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   4, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   8,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   8,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   8,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   8, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [  64,   8, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 256,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 256,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 256,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 256,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 256, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 512,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 512,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 512,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 512,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [  64, 512, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   2,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   2,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   2,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   2,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   2, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   4,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   4,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   4,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   4,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   4, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   8,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   8,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   8,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   8,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,   8, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,  64,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,  64,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,  64,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,  64,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256,  64, 512 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256, 512,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256, 512,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256, 512,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256, 512,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 256, 512,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   2,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   2,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   2,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   2,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   2, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   4,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   4,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   4,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   4,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   4, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   8,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   8,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   8,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   8,  64 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,   8, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,  64,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,  64,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,  64,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,  64,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512,  64, 256 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512, 256,   1 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512, 256,   2 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512, 256,   4 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512, 256,   8 ]),
            multiExGoodSeq_N1(Seq,Size, [ 512, 256,  64 ])
        ],
        [
            on_fail(continue)
        ]
    ).

sorted([]).
sorted([_]).
sorted([X,Y|R]) :-
    X < Y,
    sorted([Y|R]).

nextPossible1([L1,L2,L3,L4,L5], N) :-
    powOf2(N),
    not(contains(N,[L1,L2,L3,L4,L5])).
nextPossible5([L1,L2,L3,L4,L5], [N1,N2,N3,N4,N5]) :-
    nextPossible1([L1,L2,L3,L4,L5], N1),
    nextPossible1([L1,L2,L3,L4,L5], N2),
    nextPossible1([L1,L2,L3,L4,L5], N3),
    nextPossible1([L1,L2,L3,L4,L5], N4),
    nextPossible1([L1,L2,L3,L4,L5], N5),
    noDups([N1,N2,N3,N4,N5]),
    sorted([N1,N2,N3,N4,N5]).

optionForNext5([_,L2,L3,L4,L5], Nexts, [L2,L3,L4,L5,P]) :-
    contains(P,Nexts).

recNextOptions(_,[],0).
recNextOptions([L1,L2,L3,L4,L5], [P|ResNexts], Depth) :-
    Depth > 0,
    nextPossible5([L1,L2,L3,L4,L5], Nexts),
    contains(P,Nexts),
    Depth1 is Depth-1,
    recNextOptions([L2,L3,L4,L5,P], ResNexts, Depth1).
