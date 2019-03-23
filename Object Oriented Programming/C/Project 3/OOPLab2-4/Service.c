#include "Service.h"


Service * creeazaService(ConcurentRepo * repo)
{
	Service* srv = (Service*)malloc(sizeof(Service));
	srv->repo = repo;
	srv->undoList = creeazaVDinamic(destroyList);
	return srv;
}

void distrugeService(Service * srv)
{
	distrugeRepo(srv->repo);
	destroyList(srv->undoList);
	free(srv);
}

ConcurentRepo* getRepo(Service* srv) {
	return srv->repo;
}

int adaugaService(Service * srv, char * nume, char * prenume, float scor)
{
	int rez=0;
	if (strcmp(validare(nume, prenume, scor), "Datele sunt ok") == 0) {
		Concurent* c = creeazaConcurent(nume, prenume, scor);
		VectorDinamic* toUndo = copyList(srv->repo->concurenti, copiazaConcurent);
		adauga(srv->undoList, toUndo);
		rez = adaugaConcurent(srv->repo, c);
		distrugeConcurent(c);
	}
	return rez;

}

float stergeService(Service * srv, char * nume, char * prenume)
{
	float rez = 0;
	if (strcmp(validare(nume, prenume, 50), "Datele sunt ok") == 0) {
		VectorDinamic* toUndo = copyList(srv->repo->concurenti, copiazaConcurent);
		adauga(srv->undoList, toUndo);
		rez = stergeConcurent(srv->repo, nume, prenume);
	}
	return rez;
}

float actualizeazaService(Service* srv, char* nume, char*prenume, float scor) {
	float rez = 0;
	if (strcmp(validare(nume, prenume, scor), "Datele sunt ok") == 0) {
		VectorDinamic* toUndo = copyList(srv->repo->concurenti, copiazaConcurent);
		adauga(srv->undoList, toUndo);
		rez = actualizeaza(srv->repo, nume, prenume, scor);
	}
	return rez;
}

ConcurentRepo* filtreazaDupaScor(Service* srv, float score) {
	ConcurentRepo* rep = creeazaRepo();
	for (int i = 0; i < getLungimeRepo(srv->repo); i++) {
		Concurent* cnt = pozConcurent(srv->repo, i);
		if (getScor(cnt) <= score) {
			Concurent* concurentNou = creeazaConcurent(cnt->nume, cnt->prenume, cnt->scor);
			adaugaConcurent(rep, concurentNou);
			distrugeConcurent(concurentNou);
		}
	}
	return rep;
}

//FUNCTIILE DE SORTARE
int cmpNume(Concurent* c1, Concurent* c2) {
	return strcmp(getNume(c1), getNume(c2));
}

int cmpScor(Concurent* c1, Concurent* c2) {
	if (c1->scor <= c2->scor)
		return 1;
	return -1;
}

ConcurentRepo* sortareNume(Service* srv) {
	sortare((srv->repo)->concurenti, cmpNume);
	return srv->repo;
}

ConcurentRepo* sortareScor(Service* srv) {
	sortare((srv->repo)->concurenti, cmpScor);
	return srv->repo;
}

//Added by scoican
int undo(Service * srv)
{
	if (getLungime(srv->undoList) == 0) {
		return 1;//no more undo
	}
	VectorDinamic* l = removeLast(srv->undoList);
	destroyList(srv->repo->concurenti);
	srv->repo->concurenti = l;
	return 0;
}

void testeService() {
	ConcurentRepo* repo = creeazaRepo();
	Service* srv = creeazaService(repo);
	assert(getLungimeRepo(getRepo(srv)) == 0);
	
	assert(undo(srv) == 1);

	//testam adaugare,stergere,actualizare
	assert(adaugaService(srv, "a", "bababa", 100)==1);
	assert(adaugaService(srv, "asaa2", "binasai", 260)==0);
    assert(adaugaService(srv, "Sada", "nono", 55)==1);
	assert(getLungimeRepo(getRepo(srv)) == 2);

	assert(undo(srv) == 0);
	assert(getLungimeRepo(getRepo(srv)) == 1);
	assert(adaugaService(srv, "Sada", "nono", 55) == 1);
	//avem 2 concurenti in acest moment
	assert(stergeService(srv, "aaa", "fff") == 0); //nu avem concurent cu numele-prenumele acesta
	assert(getLungimeRepo(getRepo(srv)) == 2);

	assert(actualizeazaService(srv, "a", "bababa", 99) == 100);  //se reuseste actualizarea
	assert(actualizeazaService(srv, "adsda", "babsdasaaba", 99) == 0);  //actualizare esuata(nume ,prenume inexistente in lista)
	assert(actualizeazaService(srv, "a", "bababa", 299) == 0); //scor invalid


	assert(stergeService(srv, "a", "bababa") == 99); //se reuseste stergerea
	assert(getLungimeRepo(getRepo(srv)) == 1);
	assert(stergeService(srv, "aaaaaaaaaaaaaaaaaaaaaaaaaaa ", "fff") == 0);
	//in acest moment avem doar pe ("sada,"nono",55) in service
	assert(strcmp(getNume(pozConcurent(srv->repo, 0)), "Sada") == 0);
	assert(adaugaService(srv, "Harjoc", "Meme", 100) == 1);
	assert(adaugaService(srv, "Danciu", "Darius", 60) == 1);
	assert(adaugaService(srv, "Avram", "Dan", 75) == 1);
	assert(getLungimeRepo(getRepo(srv)) == 4);
	
	//test filtrare
	ConcurentRepo* r1= filtreazaDupaScor(srv, 61);
	ConcurentRepo* r2 = filtreazaDupaScor(srv, 90);
	assert(getLungimeRepo(r1) == 2); //55 si 60 sunt mai mici decat 61
	assert(getLungimeRepo(r2) == 3); //aici avem si pe cel cu 75pct
	distrugeRepo(r1); distrugeRepo(r2);
	//teste sortari 
	
	sortareNume(srv); //Avram-Danciu-Harjoc-Sada
	assert(strcmp(getNume(pozConcurent(srv->repo, 0)), "Avram") == 0);
	assert(strcmp(getPrenume(pozConcurent(srv->repo, 1)), "Darius") == 0);
	assert(getScor(pozConcurent(srv->repo, 3)) == 55);

	sortareScor(srv); //Harjoc,Avram,Danciu,Sada
	assert(strcmp(getNume(pozConcurent(srv->repo, 0)), "Harjoc") == 0);
	assert(strcmp(getPrenume(pozConcurent(srv->repo, 1)), "Dan") == 0);
	assert(getScor(pozConcurent(srv->repo, 2)) == 60);



	distrugeService(srv);
}


