#include "Sortare.h"


/**
* Sorteaza in place
* cmpf - relatia dupa care se sorteaza
*/
void sortare(VectorDinamic* v, FunctieComparare cmpF) {
	int i, j;
	for (i = 0; i < v->lungime; i++) {
		for (j = i + 1; j < v->lungime; j++) {
			Concurent* p1 = get(v, i);
			Concurent* p2 = get(v, j);
			if (cmpF(p1, p2) > 0) {
				//interschimbam
				set(v, i, p2);
				set(v, j, p1);
			}
		}
	}
}
