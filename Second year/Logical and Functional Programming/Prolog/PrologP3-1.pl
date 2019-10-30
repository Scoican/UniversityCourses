divizori( 0, _, [] ) :- !.
divizori( 1, _, [] ) :- !.
divizori( N, D, [] ) :- D > N div 2,!.
divizori( N, D, L ) :- N mod D =:= 0, !,
                         D1 is D + 1,
                         divizori( N, D1, L1 ),
                         L = [D|L1].
divizori( N, D, L ) :- D1 is D + 1,
                         divizori( N, D1, L ).
