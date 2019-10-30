%a.Sa se intercaleze un element pe pozitia a n-a a unei liste.
%b.Definiti un predicat care intoarce cel mai mare divizor comun al
%numerelor dintr-o lista.


%intercalareN(L,E,N,P,R)
%L-lista in care intercalam
%E-elementul intercalat
%N-pozitia unde intercalam
%P-pozitia curenta
%R-rezultat
%(i,i,o)


intercalareN([],_,_,_,[]).
intercalareN([H|T],E,N,N,[E,H|T]):-!.
intercalareN([H|T],E,N,P,[H|R]):-P<N,P1 is P+1,intercalareN(T,E,N,P1,R).

%cmmdcLista(L,C,R)
%L-lista de calculat
%C-cmmdc curent
%R-rezultat
%(i,i,o)

cmmdcLista([],C,C).
cmmdcLista([H|T],C,R):-cmmdc(H,C,R1),cmmdcLista(T,R1,R).
