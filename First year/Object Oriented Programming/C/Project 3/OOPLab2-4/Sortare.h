#pragma once
#include "VectorDinamic.h"



/*
Tipul functie de comparare pentru 2 elemente
returneaza 0 daca sunt egale, 1 daca o1>o2, -1 altfel
*/
typedef int(*FunctieComparare)(Concurent* o1, Concurent* o2);

/**
* Sorteaza in place
* cmpf - relatia dupa care se sorteaza
*/
void sortare(VectorDinamic * v, FunctieComparare cmpF);
