cmb([H|_],1,[H]).
cmb([H|T],K,S):-K>1,
	K1 is K-1,
	cmb(T,K1,S1),
	S =[H|S1].
cmb([_|T],K,S):-cmb(T,K,S).


insert(E,L,[E|L]).
insert(E,[H|T],[H|L]):-insert(E,T,L).

perm([],[]).
perm([H|T],S):-
	perm(T,S1),
	insert(H,S1,S).

verifica([],Pr,Pr).
verifica([H|T],Pr,P):-Pr1 is H*Pr,verifica(T,Pr1,P).

aranj(L,K,P,R):-cmb(L,K,R1),perm(R1,R),verifica(R,1,Pr),Pr=:=P.
aranjament(L,K,P,R):-findall(S,aranj(L,K,P,S),R).


f(X,N,R):-R is X**N.
