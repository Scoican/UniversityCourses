'''
Created on 17 nov. 2017

@author: Scoican
'''
from domain.Nota import Nota
from validare.Validator import Validator,ValidationException

class NoteController:
    
    def __init__(self,repo):
        self.__repo=repo
        self.__validator=Validator()
    
    def citesteComanda(self,text):
        '''
        Metoda ce citeste un numar float,dand ValueError in caz contrar
        output:Numar intreg
        '''
        while True:
            try:
                return float(input(text))
            except ValueError:
                print("Eroare!Va rog, introduceti o comanda valida!")
    
    def adaugareAsignare(self,idStud,idLab):
        '''
        Metoda ce asigneaza o problema unui student
        Input:idStud-numar intreg,idLab-lista de numere intregi
        '''
        nt=Nota([idStud,idLab],0)
        self.__validator.valideazaAsignare(nt)
        self.__repo.add(nt)
    
    def deleteNota(self,idStud,idLabProb):
        '''
        Metoda ce sterge o nota
        input:nota-obiect Nota
        '''
        lista=self.getNote()
        x=0
        while x<len(lista):
            if lista[x].getIdStud()==idStud and lista[x].getLabProb()==idLabProb:
                self.__repo.rem(lista[x])
            x+=1
        
    def adaugareNota(self,idStud,labProb,nota):
        '''
        Metoda ce adauga o nota unui student
        Input:idStud,nota-Numere intregi
        '''
        asignare=Nota([idStud,labProb],0)
        nt=self.__repo.find(asignare)
        nt.setNota(nota)
        self.__repo.updFile()
    
    def afisareAsignari(self,note):
        '''
        Metoda ce afiseaza lista cu studenti ce au probleme asignate
        input:Note-lista de obiecte Nota
        '''
        x=0
        while x<len(note):
            print("Studentul cu id-ul "+str(Nota.getIdStud(note[x]))+
                  " are asignata problema:"+str(Nota.getIdLab(note[x]))+
                  "_"+str(Nota.getIdProb(note[x])))
            x+=1
    
    def listaStudentiNotati(self,note):
        '''
        Metoda ce creeaza o lista cu toti studentii ce au primit note
        input:note-lista cu toate obiectele Nota
        output:lista-lista cu toti studentii ce au note
        '''
        x=0
        lista=[]
        while x<len(note):
            if note[x].getNota()!=0:
                lista.append(note[x])
            x+=1
        return lista
    
    def listaStudentiNotatiRecursiv(self,note,lista,x):
        if x==len(note):
            return lista
        if note[x].getNota()!=0:
            lista.append(note[x])
        return self.listaStudentiNotatiRecursiv(note, lista, x+1)
    
    def problemeleStudentului(self,idStud,note):
        '''
        Metoda ce creeaza o lista cu toate problemele ce ii apartin unui singur student
        input:idStud-numar intreg,note-lista cu toate obiectele nota
        output:lista-lista cu toate obiectele nota ce indeplinesc conditia
        '''
        x=0
        lista=[]
        while x<len(note):
            if note[x].getIdStud()==idStud:
                lista.append(note[x])
            x+=1
        return lista
    
    def afisareProblemeleStudentului(self,lista):
        '''
        Metoda ce afiseaza toate problemele unui student ales
        input:lista-lista cu obiecte nota
        '''
        if len(lista)==0:
            print("Studentul nu mai are probleme asignate")
        else:
            x=0
            print("Studentul are asignate problemele:")
            while x<len(lista):
                print(str(lista[x].getIdLab())+"_"+str(lista[x].getIdProb()))
                x+=1
    
    def verificareStudentNotat(self,idStud,idLabProb,lista):
        '''
        Metoda ce verifica daca un student are o nota deja asignata la o problema
        input:id stud-numar intreg, idLabProb-lista cu numere intregi,lista-lista cu obiecte Nota
        '''
        x=0
        while x<len(lista):
            if idStud==lista[x].getIdStud() and idLabProb==lista[x].getLabProb():
                if lista[x].getNota()!=0:
                    raise ValidationException("Studentul are o nota asignata!")
                break
            x+=1
    
    def afisareNote(self,note):
        '''
        Metoda ce afiseaza toti studentii cu notele obtinute
        input:note-Lista de obiecte Nota cu studentii ce au note
        '''
        x=0
        while x<len(note):
            print("Studentul cu id-ul "+str(Nota.getIdStud(note[x]))+
                  " are asignata problema "+str(Nota.getIdLab(note[x]))+
                  "_"+str(Nota.getIdProb(note[x]))+
                  " si a obtinut nota:"+str(Nota.getNota(note[x])))
            x+=1
    
    def creareListaProblema(self,idProb):
        '''
        Metoda ce creaza o lista cu toti studentii de la o problema data
        input:idProb-lista de numere intregi
        output:lista-lista cu numere intregi
        '''
        lista=[]
        note=self.getNote()
        if len(note)==0:
            print("Nu au avut loc asignari!")
        else:
            x=0
            while x<len(note):
                if note[x].getNota()!=0:
                    if note[x].getLabProb()==idProb:
                        lista.append([note[x].getIdStud(),note[x].getNota()])
                x+=1
            return lista
    
    def afisareAlfabetica(self,lista):
        '''
        Metoda ce afiseaza in ordine alfabetica o lista
        input:lista-lista cu un numar intreg si un string
        '''
        lista.sort(key=lambda student:student[0])
        if len(lista)>0:
            print("Studentii au obtinut la acest laborator urmatoarele note:")
        x=0
        while x<len(lista):
            print("Studentul "+str(lista[x][0])+" a obtinut nota "+str(lista[x][1]))
            x+=1
    
    def afisareOrdonatiNote(self,lista):
        '''
        Metoda ce afiseaza in ordine dupa note o lista
        input:lista-lista cu un numar intreg si un string
        '''
        lista.sort(key=lambda student:student[1])
        if len(lista)>0:
            print("Studentii au obtinut la acest laborator urmatoarele note:")
        x=0
        while x<len(lista):
            print("Studentul "+str(lista[x][0])+" a obtinut nota "+str(lista[x][1]))
            x+=1
     
    def calculMedie(self):
        '''
        Metoda ce calculeaza media unui student la toate problemele pe care le are
        output:lista cu numere intregi
        '''
        lista=[]
        frec=[]
        note=self.getNote()
        if len(note)==0:
            print("Nu au avut loc asignari!")
        else:
            x=0
            while x<len(note):
                nrNote=0
                sumaNote=0
                idStud=note[x].getIdStud()
                if idStud in frec:
                    x+=1
                    continue
                y=0
                while y<len(note):
                    if idStud==note[y].getIdStud():
                        nrNote+=1
                        sumaNote=sumaNote+note[y].getNota()
                    y+=1
                media=float(sumaNote/nrNote)
                lista.append([idStud,media])
                frec.append(idStud)
                x+=1
        return lista
    
    def afisareListaMedie(self,lista):
        '''
        Metoda ce afiseaza o lista cu mediile studentilor
        input:lista-lista cu un numar float si un numar intreg
        '''
        x=0
        ok=0
        while x<len(lista):
            if lista[x][1]<=5:
                if ok==0:
                    print("Studentii cu media laboratoarelor mai mica de 5 sunt:")
                    ok=1
                print("Studentul "+str(lista[x][0])+" cu media "+str("%.2f" % lista[x][1]))
            x+=1
        if ok==0:
            print("Nu exista studenti cu media mai mica de 5!")
        
    def getNote(self):
        '''       
        Metoda ce returneaza o lista cu toate obiectele clasei Nota
        Output:lista cu toate obiecetele Nota
        '''
        return self.__repo.getAll()
    
    def checkId(self,idStud):
        '''
        Metoda ce verifica daca un id este deja prezent in lista de obiecte Nota
        input:id-numar intreg
        output:1-caz adevarat;0-caz fals
        '''
        asig=[idStud,[]]
        nt=Nota(asig,0)
        if nt in self.getNote():
            return 1
        else:
            return 0
        
            