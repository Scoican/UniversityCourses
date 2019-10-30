/**
a) Sa se determine pozitiile elementului maxim dintr-o lista liniara.
De ex: poz([10,14,12,13,14], L) va produce L = [2,5].
b) Se da o lista eterogena, formata din numere intregi si liste de
numere intregi. Sa se inlocuiasca fiecare sublista cu pozitiile
elementului maxim din sublista respectiva.
De ex: [1, [2, 3], [4, 1, 4],3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] =>
[1, [2], [1, 3], 3, 6, [2], 5, [1, 2, 3], 7]
*/



%a)
%cautareMaxim(L,M,R)
%L-lista in care cautam
%M-elementul maxim curent
%R-Rezultat
%(i,i,o)

cautareMaxim([],M,M).
cautareMaxim([H|T],M,R):-H>M,!,cautareMaxim(T,H,R).
cautareMaxim([_|T],M,R):-cautareMaxim(T,M,R).

%determinarePozitie(L,E,P,C,R)
%L-lista in care cautam
%P-pozitia curenta
%E-elementul cautat
%C-variabila colectoare
%R-raspuns
%(i,i,i,i,o)

determinarePozitie([],_,_,C,C).
determinarePozitie([H|T],E,P,C,R):-
	E=:=H,
	!,
	inserare(C,P,R1),
	P1 is P+1,
	determinarePozitie(T,E,P1,R1,R).
determinarePozitie([H|T],E,P,C,R):-
	E\=H,
	P1 is P+1,
	determinarePozitie(T,E,P1,C,R).


%pozitieMaxim(L,R)
%L-lista in care cautam
%R-raspuns
%(i,o)

pozitieMaxim(L,R):-cautareMaxim(L,0,M),
	determinarePozitie(L,M,1,[],R).


%b)
%inlocuirePozMaxim(L,R)
%L-lista in care inlocuim
%R-raspuns
%(i,o)

inlocuirePozMaxim([],[]).
inlocuirePozMaxim([H|T],[R1|R]):-
	isList(H),
	!,
	pozitieMaxim(H,R1),
	inlocuirePozMaxim(T,R).
inlocuirePozMaxim([H|T],[H|R]):-
	not(isList(H)),
	inlocuirePozMaxim(T,R).