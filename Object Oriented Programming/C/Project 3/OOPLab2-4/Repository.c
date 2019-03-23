#include "Repository.h"
#include <assert.h>
#include <string.h>

ConcurentRepo * creeazaRepo(){
	ConcurentRepo* repo = (ConcurentRepo*)malloc(sizeof(ConcurentRepo));
	repo->concurenti = creeazaVDinamic(distrugeConcurent);
	return repo;
}


void distrugeRepo(ConcurentRepo * r){
	if (r == NULL)
		return;
	destroyList(r->concurenti);
	free(r);
}


int adaugaConcurent(ConcurentRepo* r, Concurent* p) {
	if (r == NULL)
		return 0;
	if (cauta(r, getNume(p), getPrenume(p)) != NULL)
		return 0;
	Concurent* copie = copiazaConcurent(p);
	return adauga(r->concurenti, copie);
}


int getLungimeRepo(ConcurentRepo * r){
	if (r == NULL)
		return -1;
	return getLungime(r->concurenti);
}


Concurent * pozConcurent(ConcurentRepo * r, int pos){
	if (r == NULL)
		return NULL;
	if (pos < 0 || pos >= getLungime(r->concurenti))
		return NULL;
	return get(r->concurenti, pos);
}


Concurent* cauta(ConcurentRepo * r, char * nume, char * prenume){
	if (r == NULL)
		return NULL;
	int poz = cautaPozitie(r, nume, prenume);
	if (poz == -1)
		return NULL;
	return get(r->concurenti, poz);
}


int cautaPozitie(ConcurentRepo* r, char* nume, char* prenume) {
	if (r == NULL)
		return -1;
	if (getLungimeRepo(r) == 0)
		return -1;
	for (int i = 0; i < getLungimeRepo(r); i++) {
		Concurent* concurent = get(r->concurenti, i);
		if ( (strcmp(concurent->nume, nume) == 0) && (strcmp(concurent->prenume, prenume) == 0))
			return i;
	}
	return -1;
}


float stergeConcurent(ConcurentRepo* r, char* nume, char* prenume) {
	float rez=1;
	if (r == NULL)
		rez = 0;
	int poz = cautaPozitie(r, nume, prenume);
	if (poz == -1)
		rez = 0;
	if (rez != 0) {
		Concurent* conc = get(r->concurenti, poz);
		rez = getScor(conc);
		distrugeConcurent(conc);
		sterge(r->concurenti, poz);
	}
	return rez;
}


float actualizeaza(ConcurentRepo * r, char * nume, char * prenume, float scor){
	float rez=0;
	Concurent* c = cauta(r, nume, prenume);
	if (c != NULL) {
		rez = getScor(c);
		setScor(c, scor);
	}
	return rez;
}


void testeConcurentRepo() {
	//Cream un repository gol
	ConcurentRepo* repo = creeazaRepo();
	assert(getLungimeRepo(repo) == 0);

	//adaugam un Concurent in repo si verificam ca se schimba lungimea repo-ului
	Concurent* p = creeazaConcurent("Muntean", "Sergiu", 22.5);
	Concurent* p2 = creeazaConcurent("Muntean2", "Sergiu2", 45);
	assert(adaugaConcurent(repo, p) == 1);
	assert(getLungimeRepo(repo) == 1);
	assert(adaugaConcurent(repo, p) == 0); //nu putem adauga acelasi concurent de 2 ori
	assert(adaugaConcurent(repo, p2) == 1);
	assert(getLungimeRepo(repo) == 2);

	assert(cauta(repo, "Eminescu", "Gheorghe") == NULL);
	assert(getScor(cauta(repo, "Muntean", "Sergiu")) == 22.5);
	assert(cautaPozitie(repo, "Eminescu", "Ghita") == -1);
	assert(cautaPozitie(repo, "Muntean2", "Sergiu2") == 1);

	Concurent* p3 = creeazaConcurent("Herlea", "Tudor", 100);
	assert(adaugaConcurent(repo, p3) == 1);
	assert(strcmp(getNume(pozConcurent(repo, 0)), "Muntean") == 0);
	assert(strcmp(getPrenume(pozConcurent(repo, 1)), "Sergiu2") == 0);
	assert(getScor(pozConcurent(repo,2))==100);

	assert(stergeConcurent(repo, "Herlea", "Tudor") == 100);
	assert(actualizeaza(repo, "Muntean", "Sergiu", 10) == 22.5);
	assert(actualizeaza(repo, "Munn", "mimi", 222) == 0);
	assert(getScor(pozConcurent(repo, 0)) == 10);
	assert(stergeConcurent(repo, "Hihi", "Haha") == 0);
	assert(getLungimeRepo(repo) == 2);
	assert(adaugaConcurent(NULL, p) == 0);
	assert(cauta(NULL, "aaa", "aaa") == NULL);
	distrugeConcurent(p);
	assert(pozConcurent(NULL, 1) == NULL);
	assert(pozConcurent(repo, -4) == NULL);
	distrugeConcurent(p2);
	distrugeConcurent(p3);
	distrugeRepo(repo);
	assert(getLungimeRepo(NULL) == -1);
	assert(cautaPozitie(NULL,"aa","aa") == -1);
	assert(stergeConcurent(NULL, "aaa", "Bb") == 0);
	distrugeRepo(NULL);


}



