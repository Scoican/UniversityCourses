#include "VectorDinamic.h"
#include <assert.h>


VectorDinamic* creeazaVDinamic(DestroyFct f){
	VectorDinamic* vd = (VectorDinamic*)malloc(sizeof(VectorDinamic));
	vd->lungime = 0;
	vd->capacitate = 2;
	vd->elemente = (TElement*)malloc(vd->capacitate*sizeof(TElement));
	vd->dfnc = f;
	return vd;
}

int adauga(VectorDinamic * arr, TElement t)
{
	if (arr == NULL)
		return 0;
	if (getLungime(arr) == getCapacitate(arr)) 
		resize(arr);
	arr->elemente[arr->lungime] = t;
	arr->lungime++;
	return 1;
}

void sterge(VectorDinamic * arr, int pos)
{
	if (arr == NULL)
		return;
	if (pos < 0 || pos >= arr->lungime)
		return;
	for (int i = pos; i < arr->lungime - 1; i++)
		arr->elemente[i] = arr->elemente[i + 1];
	arr->lungime--;
}

int getLungime(VectorDinamic * arr){return arr->lungime; }

int getCapacitate(VectorDinamic* arr) { return arr->capacitate; }

void resize(VectorDinamic* arr)
{

	arr->capacitate *= 2;
	TElement* aux = (TElement*)malloc(arr->capacitate * sizeof(TElement));
	for (int i = 0; i < arr->lungime; i++) {
		aux[i] = arr->elemente[i];
	}

	//dealocam memoria ocupata de vector
	free(arr->elemente);
	arr->elemente = aux;

}

TElement get(VectorDinamic * arr, int pos)
{
	return arr->elemente[pos];
}

TElement set(VectorDinamic * arr, int poz, TElement p)
{
	if (poz < 0 || poz >= arr->lungime)
		return NULL;
	TElement rez = arr->elemente[poz];
	arr->elemente[poz] =p;
	return rez;
}

//Added by Scoican
VectorDinamic* copyList(VectorDinamic* l, CopyFct cf) {
	VectorDinamic* rez = creeazaVDinamic(l->dfnc);
	for (int i = 0; i < l->lungime; i++) {
		TElement p = get(l, i);
		adauga(rez, cf(p));
	}
	return rez;
}

TElement removeLast(VectorDinamic* l) {
	TElement rez = l->elemente[l->lungime - 1];
	l->lungime -= 1;
	return rez;
}

void destroyList(VectorDinamic* l) {
	//dealocate pets
	for (int i = 0; i < l->lungime; i++) {
		l->dfnc(l->elemente[i]);
	}
	free(l->elemente);
	free(l);
}

void testeVectorDinamic() {
	//testeaza creearea vectorului
	VectorDinamic* v = creeazaVDinamic(distrugeConcurent);
	assert(getLungime(v) == 0);
	assert(getCapacitate(v) == 2);
	sterge(NULL, 2);
	sterge(v, -4);
	
	//Testam adaugarea,getLungime,getCapacitate
	Concurent* c = creeazaConcurent("Maniu", "Mihai", 48);	
	assert(adauga(NULL, c) == 0);
	assert(set(v, -4, c) == NULL);
	Concurent* c2 = creeazaConcurent("Maniu2", "Mihai2", 50);
	assert(adauga(v, c) == 1);
	assert(adauga(v, c2) == 1);
	assert(getLungime(v) == getCapacitate(v)); //s-a atins capacitatea vectorului 
	Concurent* c3 = creeazaConcurent("Maniu3", "Mihai3", 52);
	assert(adauga(v, c3) == 1);  //vectorul se dubleaza pentru a permite introducerea unui nou concurent
	assert(getCapacitate(v) == 4); //capacitatea=2*2 => capacitate=4
	assert(getLungime(v) == 3);

	//Avem 3 concurenti in vector(capacitate:4) cu scorurile 48,50,52
	//testam stergerea,get
	sterge(v, 0);                    //stergem primul concurent 
	assert(getScor(get(v,0))== 50);
	assert(getScor(get(v, 1)) == 52);
	assert(getLungime(v) == 2);

	assert(c3==set(v, 1, c)); //inlocuim pe Maniu3 cu Maniu
	assert(getScor(get(v, 1)) == 48);
	distrugeConcurent(c3);

	assert(getLungime(v) == 2);
	removeLast(v);
	assert(getLungime(v) == 1);
	distrugeConcurent(c);


	//distrugeConcurent(c);
	//test distruge vector
	destroyList(v);

}


