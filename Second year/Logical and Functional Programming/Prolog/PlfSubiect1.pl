%2)

%lungimeSir(L,N,R)
%L- lista pe care o numaram
%N-Lungime
%R- raspuns

lungimeSir([],N,N).
lungimeSir([_|T],N,R):-N1 is N+1,lungimeSir(T,N1,R).


%isList(H)
%h-elementul de verificat

isList([_|_]).
isList([]).


%eliminare(L,R).
%L-lista din care eliminam
%R-raspuns

eliminare([],_,[]):-!.
eliminare(L,0,L):-!.
eliminare([H|T],K,[H|R]):-not(isList(H)),
	!,
	eliminare(T,K,R).
eliminare([H|T],K,R):-isList(H),
	lungimeSir(H,0,N),
	N mod 2=:=0,
	!,
	K1 is K-1,
	eliminare(T,K1,R).
eliminare([H|T],K,[H|R]):-isList(H),
	lungimeSir(H,0,N),
	N mod 2\=0,
	!,
	eliminare(T,K,R).

%3)
%subm(L,R)
%L- lista din care obtinem submultimi
%R- rezultat
%(i,o)


subm([],[]).
subm([_|T],S):-subm(T,S).
subm([H|T],[H|S]):-subm(T,S).

%suma(L,S,Sum)
%L-lista de verificat
%S-suma de verificat
%Sum-suma curenta

suma2([],S,Sum):-S=:=Sum.
suma2([H|T],S,Sum):-Sum1 is Sum+H,suma2(T,S,Sum1).

submultimi(L,S,R):-subm(L,R),suma2(R,S,0).

submultimi2(L,S,R):-findall(R1,submultimi(L,S,R1),R).
