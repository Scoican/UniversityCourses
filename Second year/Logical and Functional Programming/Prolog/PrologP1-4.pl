%a.Sa se scrie un predicat care substituie intr-o lista un element
%printr-o alta lista.

%substituire(L,E,M,R)
%L-lista
%E-elementul inlocuit
%M-lista cu care se inlocuieste
%R-rezultat
%(i,i,i,o)

substituire([],_,_,[]).
substituire([H|T],H,L,[L|R]):-!,substituire(T,H,L,R).
substituire([H|T],E,L,[H|R]):-substituire(T,E,L,R).

%b.Sa se elimine elementul de pe pozitia a n-a a
%unei liste liniare.

%eliminarePozitie(L,P,N,R)
%L-lista din care se elimina
%P-Pozitia curenta
%N-pozitia data
%R-lista rezultata
%(i,i,i,o)

eliminarePozitie([],_,_,[]).
eliminarePozitie([_|T],P,P,T).
eliminarePozitie([H|T],P,N,[H|R]):-P1 is P+1,eliminarePozitie(T,P1,N,R).

elim(L,N,R):-eliminarePozitie(L,1,N,R).
