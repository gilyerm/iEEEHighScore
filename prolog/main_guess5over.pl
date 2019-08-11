:-  ensure_loaded("main_tools.pl").

extendGuessBy(Guess,Depth,Prfxs):-
    tailOf(Guess,Tail,5),
    findall(Prf,
        (
            recNextOptions(Tail,Next,Depth),
            concatTo(Prf,Guess,Next),
            isValidGuess(Guess)
        ),
        Prfxs).

genRunables_Guess5Over(_,_,[],[]).
genRunables_Guess5Over(Seq,Size,[Prf|PrfxsR],
        [threadQuery_Guess5Over(Seq,Size,Prf)|Rest]) :-
    genRunables_Guess5Over(Seq,Size,PrfxsR,Rest).

threadQuery_Guess5Over(Seq,Size,Prf) :-
    (
        % length(Seq,Size),
        prefixOf(Seq,Prf),
        testExGoodSeq(Seq,Size)
    ).

isValidGuess(Guess) :-
    is_list(Guess),
    length(Guess,Len),
    testExGoodSeq(Guess,Len).

mainMulti_Guess5Over(Seq,Size,Guess,Depth):-
    length(Guess,GLen), /* GLen > 4, */
    GLen + Depth =< Size,
    extendGuessBy(Guess,Depth,Prfxs),
    % include(isValidGuess(), Prfxs, GoodPrfxs),
    length(Seq,Size),
    % genRunables_Guess5Over(Seq,Size,GoodPrfxs,Runnables),
    genRunables_Guess5Over(Seq,Size,Prfxs,Runnables),
    %% log
        % length(Prfxs,PrefxLen),print_message(informational, PrefxLen),
        % length(GoodPrfxs,GoodLen),print_message(informational, GoodLen),
        % length(Runnables,RnblLen),print_message(informational, RnblLen),
        % !,fail,
    first_solution(Seq,Runnables,[on_fail(continue)]).

myMain(Guess,Depth,Seq,Size) :-
    mainMulti_Guess5Over(Seq,Size,Guess,Depth).

% main(Seq,Size,Guess,Depth):- 
%     mainMulti_Guess5Over(Seq,Size,Guess,Depth).


% ourGuess(
%     [4,1,2,8,16,32,4,1,2,8,64,16,4,1,2,128,8,16,4,1,256,2,8,16,4,512,1,2,8,32,64,4,1,2,128,32,8,4,1,256,64,2,8,4,128,256,1,2,8,512,16,32,1,2,64,128,8,16,1,32,64,4,128,2,16,32,1,256,4,2,512,8,32,64,1,128,16,2,256,4,32,8,16,64,2,256,1,32,8,128,512,2,1,32,256,128,4,2,64,512,1,8,4,128,64,16,1,256,4,128,64,8,32,2,256,16,64,4,8,512,2,128,16,32,8,4,512,1,256,16,8,32,64,128,256,1,2,512,16,64,4,32,2,256,512,8,16,4,128,256,32,8,16,512,64,1,4,32,256,512,8,1,64,128,256,512,2,8,64,128,256,16,2,32,512,4,1,16,128,32]
% ).

% genRunables_Guess5Over(15,[4,1,2,8,16,32,4,1,2,8,64],3,[],Runbls).

% gen(_,_,[],[]).
% gen(Seq,Size,[X|R],[multiExGoodSeq(Seq,Size,X)|Rest]) :-
%     gen(Seq,Size,R,Rest).

% runrunmain(Seq,Size,Opt) :-
%     gen(Seq,Size,Opt,L),
%     first_solution(Seq,L,[on_fail(continue)]).