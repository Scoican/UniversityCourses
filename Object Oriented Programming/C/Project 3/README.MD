Fiecare studnet primeste aplicatia unui coleg.

Faceti code review pe aplicatia primita (CodeReviewTemplate.odt)



Adaugati in aplicatia primita urmatoarele



1 Undo multiplu 

   - utilizatorul poate face undo la operatiile adaugamodificasterge

   - utilizatorul poate face undo de mai multe ori.

2 O noua varianta de filtrare



 

Obs Functionalitata undo se implementeaza folosind o lista de liste.

In proiect trebuie sa existe un modul (si doar unu singur) care implementeaza conceptul de lista folosind structura de vector dinamic.

  Implementarea de voastra de lista trebuie sa functioneze si in cazul in care vreau lista de liste. Practic lista o sa contina elemente de tip void.

Acordati atentie gestiunii memoriei (alocaredelocare, cum facem copierea listei, etc). Este important ca lista depusa in lista de undo sa fie o lista total independenta de lista curenta de obiecte (din repo daca aveti repo). 

   

Cerinte obligatorii (nu se preia laboratorul daca nu sunt satisfacute toate)

   1 un singur modul care implementeaza lista folosind structura vector dinamic folosit si la undo si la lista de obiecte (void)

   2 Test Code coverage 100% pe toate modulele in afara de UI

   3 Fara warninguri (in Visual Studio warning level W4, treat warning as errors)

   4 Fara memory leak.



Timp de lucru 1 saptamana.


