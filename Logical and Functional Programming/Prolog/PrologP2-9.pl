/**
a) Dandu-se o lista liniara numerica, sa se stearga toate secventele de
valori consecutive.
Ex: sterg([1, 2, 4, 6, 7, 8, 10], L) va produce L=[4,10].
b) Se da o lista eterogena, formata din numere intregi si liste de numere
intregi. Din fiecare sublista sa se stearga toate secventele de valori
consecutive.
De ex:[1, [2, 3, 5], 9, [1, 2, 4, 3, 4, 5, 7, 9], 11, [5, 8, 2], 7] =>
[1, [5], 9, [4, 7, 9], 11, [5, 8, 2], 7]
*/


%a)
%consecutivAux(L,P,R)
%L-lista liniara numerica
%P-elementul anterior
%R-raspuns
%(i,i,o)

consecutivAux([],_,[]).
consecutivAux([H],P,[H]):-
        not(H=:=P+1),
	!.
consecutivAux([_],_,[]).
consecutivAux([H1,H2|T],_,R):-
	H1=:=H2-1,
	consecutivAux(T,H2,R),
	!.
consecutivAux([H1,H2|T],P,[H1|R]):-
	not(H1 =:= H2 - 1),
	not(P =:= H1 - 1),
	consecutivAux([H2|T],H1,R),
	!.
consecutivAux([H1, H2|T], _, R):-
	consecutivAux([H2|T], H1, R),
	!.

%consecutiv(L,R)
%L-lista in care eliminam
%R-raspuns
%(i,o)

consecutiv([H|T],R):-consecutivAux(T,H,R).


%b)
%consecutivLista(L,R)
%L-lista in care calculam
%R-rezultat
%(i,o)

consecutivLista([],[]).
consecutivLista([H|T],[H1|R]):-
	isList(H),
	!,
	consecutiv(H,H1),
	consecutivLista(T,R).
consecutivLista([H|T],[H|R]):-
	not(isList(H)),
	!,
	consecutivLista(T,R).
