:- ensure_loaded("basic_rules.pl").
% set_prolog_flag(answer_write_options,[max_depth(0)]).


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

extendGoodSeq([L1,L2,L3,L4,L5|R],Cprevs,Size) :-
    Size > 4,
    powOf2(L5),
    %bitCount5([L5,L1,L2,L3,L4]),
    not(contains(L5,[L1,L2,L3,L4])), % buildUp4(L1,L2,L3,L4),
    or5(L1,L2,L3,L4,L5, C),
    not(contains(C,Cprevs)),
    NextSize is Size-1,
    extendGoodSeq([L2,L3,L4,L5|R], [C|Cprevs], NextSize).    

extendGoodSeq([_,_,_,_],_,4).
% version for outputing C values when reaching SIZE
% extendGoodSeq([_,_,_,_],Cprevs,4) :-
%     print_message(informational, Cprevs).

testExGoodSeq([L1,L2,L3,L4|R],Size) :-
    Size > 4,
    buildUp4(L1,L2,L3,L4),
    extendGoodSeq([L1,L2,L3,L4|R],[],Size).

startSeqOption([],0).
startSeqOption([X|R],Depth) :-
    Depth > 0,
    powOf2(X),
    Depth1 is Depth-1,
    startSeqOption(R,Depth1),
    noDups([X|R]).

optionForNext5([_,L2,L3,L4,L5], Nexts, [L2,L3,L4,L5,P]) :-
    contains(P,Nexts).

nextPossible1(N,Lasts) :-
    powOf2(N),
    not(contains(N,Lasts)).

nextPossible5([L1,L2,L3,L4,L5], Nexts) :-
    findall(Nx,
            nextPossible1(Nx,[L1,L2,L3,L4,L5]),
            NextOpts),
    sort(NextOpts, Nexts).

% nextPossible5([L1,L2,L3,L4,L5], [N1,N2,N3,N4,N5]) :-
%     % nextPossible1(N1, [L1,L2,L3,L4,L5 ]),
%     % nextPossible1(N2, [L1,L2,L3,L4,L5, N1]),
%     % nextPossible1(N3, [L1,L2,L3,L4,L5, N1,N2]),
%     % nextPossible1(N4, [L1,L2,L3,L4,L5, N1,N2,N3]),
%     % nextPossible1(N5, [L1,L2,L3,L4,L5, N1,N2,N3,N4]),
%     % noDups([N1,N2,N3,N4,N5]),
%     sorted([N1,N2,N3,N4,N5]).

recNextOptions(_,[],0).
recNextOptions([L1,L2,L3,L4,L5], [P|ResNexts], Depth) :-
    Depth > 0,
    nextPossible5([L1,L2,L3,L4,L5], Nexts),
    contains(P,Nexts),
    Depth1 is Depth-1,
    recNextOptions([L2,L3,L4,L5,P], ResNexts, Depth1).
recNextOptions(Lasts, [Next|ResNexts], Depth) :-
    Depth > 0,
    length(Lasts, LastsLen), LastsLen < 5,
    nextPossible1(Next, Lasts),
    Depth1 is Depth-1,
    concatTo(LastsNext, Lasts, [Next]),
    recNextOptions(LastsNext, ResNexts, Depth1).

% last5([X1,X2,X3,X4,X5],[X1,X2,X3,X4,X5]).
% last5([_|R],L):-
%     length([_|R], Size),
%     Size > 5,
%     last5(R,L).

tailOf([_|R],Tail,WantedLen) :-
    length([_|R], Size), Size > WantedLen,
    tailOf(R,Tail,WantedLen).
tailOf(Tail,Tail,WantedLen) :-
    length(Tail, Size), Size =< WantedLen.

