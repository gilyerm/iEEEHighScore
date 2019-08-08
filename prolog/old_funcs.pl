:- ensure_loaded("basic_rules.pl").

%%% ------ BASICS ------ %%%

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


and(X1,X2,Y) :-
    Y is (X1 /\ X2).

xor(X1,X2,Y) :-
    Y is (X1 xor X2).

orList([],0).
orList([X|R],Res) :-
    orList(R,SubRes),
    or(X,SubRes,Res).


% bitCount5_real([X1,X2,X3,X4,X5]) :-
%     allPowsOf2([X1,X2,X3,X4,X5]),
%     noDups([X1,X2,X3,X4,X5]).

% bitCount5_real(L) :-
%     is_list(L),
%     length(L,5),
%     allPowsOf2(L),
%     noDups(L).


%%% ------ MAIN TOOLS ------ %%%

isGoodSeq([_,_,_,_],_,4).
isGoodSeq([L1,L2,L3,L4,L5|R],Cprevs,Size) :-
    Size > 4,
    bitCount5([L1,L2,L3,L4,L5]),
    or5(L1,L2,L3,L4,L5, C),
    not(contains(C,Cprevs)),
    NextSize is Size-1,
    isGoodSeq([L2,L3,L4,L5|R], [C|Cprevs], NextSize).

testGoodSeq(Seq,Size) :-
    anInputList(Seq,Size),
    isGoodSeq(Seq,[],Size).