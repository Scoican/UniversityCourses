#pragma once
#include "Concurent.h"
#include <stdlib.h>
#define CAPACITY 10
typedef void* TElement;

/*
function type for realeasing an element
*/
typedef void(*DestroyFct) (TElemenet);

/*
  function type for copy an element
*/
typedef TElement(*CopyFct) (TElement);

typedef struct {
	TElement* elemente;
	int lungime;                      //lungimea vectorului
	int capacitate;					  // capacitatea vectorului
	DestroyFct dfnc;//used on destroy to release elements
}VectorDinamic;


//Creeaza un vector dinamic de elemente generice cu o capacitate data
//capacity-integer,capacitatea maxima a vectorului
//Returneaza un pointer la vectorul dinamic creat
VectorDinamic* creeazaVDinamic(DestroyFct f);                       //testat

//Se dealoca memoria vectorului dinamic primit ca parametru
void destroyList(VectorDinamic* l);
//void distrugeVDinamic(VectorDinamic* arr);                         //testat


//Adauga elementul t in vector
//arr-pointer la un vector dinamic
//returneaza 1 - daca s-a realizat cu succes adaugare
//			 0- altfel
int adauga(VectorDinamic* arr, TElement t);                         //testat

//executa operatia de stergere de pe pozitia pos(int)
//arr-pointer la un vector dinamic
//pos>0 & pos<lungime vector
void sterge(VectorDinamic* arr, int pos);                        //testat

//Returneaza lungimea vectorului dinamic primit ca parametru
//arr-pointer la un vector dinamic
//output-int
int getLungime(VectorDinamic* arr);                        //testat

//returneaza capacitatea curenta a vectorului dat ca parametru
int getCapacitate(VectorDinamic* arr);                     //testat

//dubleaza capacitatea vectorului dinamic 
//arr-pointer la un vector dinamic
void resize(VectorDinamic* arr);                            //testat

//Returneaza elementul de pe pozitia pos (daca exista)
//arr-pointer la un vector dinamic
TElement get(VectorDinamic* arr, int pos);                  //testat

//Adauga elementul pe pozitia data
//poz>0 & poz<= lungime
TElement set(VectorDinamic* arr, int poz, TElement p);         //testat

//Added by Scoican

//Function that returns a pointer to a copy of a given vector
VectorDinamic* copyList(VectorDinamic* l, CopyFct cf);

//Function that removes the last element from the Vector
TElement removeLast(VectorDinamic* l);

// Teste
void testeVectorDinamic();




