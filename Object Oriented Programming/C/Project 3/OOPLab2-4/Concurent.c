#include "Concurent.h"

Concurent * creeazaConcurent(char * nume, char * prenume, float scor){
	Concurent* c = (Concurent*)malloc(sizeof(Concurent));
	c->nume = (char*)malloc(sizeof(char) * (strlen(nume) + 1));
	strcpy(c->nume, nume);
	c->prenume = (char*)malloc(sizeof(char)*(strlen(prenume) + 1));
	strcpy(c->prenume, prenume);
	c->scor = scor;
	return c;
}

void distrugeConcurent(Concurent * c)
{
	free(c->nume);
	free(c->prenume);
	free(c);
}

Concurent* copiazaConcurent(Concurent * c){
	if (c == NULL)
		return NULL;
	Concurent* copieConcurent = creeazaConcurent(getNume(c), getPrenume(c), getScor(c));
	return copieConcurent;
}


char* getNume(Concurent * p){ return p->nume; }
char* getPrenume(Concurent * p){ return p->prenume;}
float getScor(Concurent * p){return p->scor;}

void setNume(Concurent * p, char nume[30]) { strcpy(p->nume,nume); }
void setPrenume(Concurent * p, char prenume[30]) { strcpy(p->prenume,prenume);  }
void setScor(Concurent * p, float scor) { p->scor = scor; }
void toString(Concurent* c)
{
	printf("Nume:%s --- Prenume:%s --- Scor:%.2f.", getNume(c), getPrenume(c),getScor(c));
}

void testConcurent() {

	//Testam faptul ca se creaza un concurent si verificam atributele cu ajutorul functiilor getter
	Concurent* c = creeazaConcurent("Chisbac", "George", 45.5);
	toString(c);
	assert(strcmp(getNume(c), "Chisbac") == 0);
	assert(strcmp(getPrenume(c), "George") == 0);
	assert(getScor(c) == 45.5);

	//Testam faptul ca se actualizeaza in urma folosirii functiilor setter
	setNume(c, "Harjoc");   setPrenume(c, "Sergiu");     setScor(c, 100);
	assert(strcmp(getNume(c), "Harjoc") == 0);
	assert(strcmp(getPrenume(c), "Sergiu") == 0);
	assert(getScor(c) == 100);

	//Testam faptul ca functia de copiere functioneaza corect,iar apoi dealocam memoria
	Concurent* copie = copiazaConcurent(c);
	distrugeConcurent(c);
	assert(getScor(copie) == 100);
	assert(copiazaConcurent(NULL) == NULL);
	distrugeConcurent(copie);
	

}
