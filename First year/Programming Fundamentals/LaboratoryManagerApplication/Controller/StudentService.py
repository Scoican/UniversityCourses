'''
Created on 12 nov. 2017

@author: Scoican
'''
from domain.Student import Student
from validare.Validator import Validator
import random,string
from Sortari.InsertionSort import insertion
from Sortari.CombSort import combSort

class StudentController:
    '''
    Clasa ce se ocupa cu controlul multimii de studenti
    '''
    def __init__(self,repo):
        self.__repo=repo
        self.__validator=Validator()
    
    def __testnumers(self,nume):
        '''
        Metoda ce verifica daca exista cifre intr-un string
        output:True-in cazul in care exista,False-in cazul in care nu exista
        '''
        return any(char.isdigit()for char in nume)
    
    def citesteNume(self,text):
        '''
        Metoda ce citeste un nume, fara cifre
        output:nume-string
        '''
        while True:
            nume=input(text)
            nume=nume.replace("  ","")
            if self.__testnumers(nume):
                print("Eroare!Va rog,introduceti o comanda valida!")
                return 0
            else:
                return nume
    
    def adaugaStudent(self,idStud,nume,grup):
        '''
        Metoda ce adauga un Student la lista
        input:id,grup:nr intreg,nume:string
        '''
        st=Student(idStud,nume,grup)
        self.__validator.valideazaStudent(st)
        self.__repo.add(st)
    
    def updateStudent(self,idStud,nume,grup):
        '''
        Metoda ce modifica datele unui Student din lista
        input:id,grup:nr intreg,nume:string
        '''
        st=Student(idStud,nume,grup)
        self.__validator.valideazaStudent(st)
        self.__repo.upd(st)
        
    def deleteStudent(self,st):
        '''
        Metoda ce sterge un student din lista
        input: st-obiect student cu un id
        '''
        self.__validator.valideazaId(st)
        self.__repo.rem(st)
    
    def findStudent(self,st):
        '''
        Metoda ce gaseste un student din lista
        input: st-obiect student cu un id
        '''
        self.__validator.valideazaId(st)
        st=self.__repo.find(st)
        print("ID:"+str(Student.getId(st))+
              " Nume:"+str(Student.getNume(st))+
              " Grupa:"+str(Student.getGrup(st)))
    
    def studentiGrupa(self,grupa,studenti):
        '''
        Metoda ce gaseste toti studentii dintr-o grupa data
        input:grupa:nr intreg,studenti:lista de obiecte Student
        output:grupaStudenti:lista de obiecte student cu aceasi grupa
        '''
        x=0
        grupaStudenti=[]
        while x<len(self.__repo):
            if Student.getGrup(studenti[x])==grupa:
                grupaStudenti.append(studenti[x])
            x+=1
        return grupaStudenti
    
    def studentiGrupaRecursiv(self,grupa,studenti,grupaStudenti,x):
        if x==len(self.__repo):
            return grupaStudenti
        if Student.getGrup(studenti[x])==grupa:
            grupaStudenti.append(studenti[x])
        return self.studentiGrupaRecursiv(grupa, studenti, grupaStudenti, x+1)
        
    
    def printStudenti(self,studenti):
        '''
        Metoda ce afiseaza toti studentii cu toate datele lor
        input:studenti-Lista de obiecte Student
        '''
        x=0
        print("Lista de studenti este:")
        while x < len(studenti):
            print("ID:"+str(Student.getId(studenti[x]))+
                  " Nume:"+Student.getNume(studenti[x])+
                " Grupa:"+str(Student.getGrup(studenti[x])))
            x+=1
    
    def afisareGrupa(self,studenti,grupa):
        '''
        Metoda ce afiseaza toti studentii dintr-o grupa data
        input:Studenti:lista de obiecte student cu aceasi grupa,grupa:nr intreg
        '''
        x=0
        ok=0
        while x<len(studenti):
            if ok==0:
                print("Studentii din grupa "+str(grupa)+" sunt:")
                ok=1
            print("ID:"+str(Student.getId(studenti[x]))+" Nume:"+str(Student.getNume(studenti[x])))   
            x+=1
        if ok==0:
            print("Grupa nu are studenti")
             
    def checkId(self,idStud):
        '''
        Metoda ce verifica daca un id este deja prezent in lista de obiecte
        input:id-numar intreg
        output:1-caz adevarat;0-caz fals
        '''
        st=Student(idStud," ",0)
        if st in self.getStudenti():
            return 1
        else:
            return 0
        
    def getStudenti(self):
        '''
        Metoda ce returneaza o lista cu toate obiectele Studenti
        output:Lista cu obiecte student 
        '''
        return self.__repo.getAll()
    
    def gasireNumeStudent(self,lista):
        '''
        Metoda ce gaseste numele unui student dupa id, si schimba id-ul cu numele
        input:lista-lista cu numere intregi
        output:lista-lista cu un string si un numar intreg
        '''
        if len(lista)==0:
            print("Nici un student nu are note la problema aleasa")
        else:
            studenti=self.getStudenti()
            x=0
            while x<len(lista):
                y=0
                while y<len(studenti):
                    if lista[x][0]==studenti[y].getId():
                        lista[x][0]=studenti[y].getNume()
                    y+=1
                x+=1
        return lista
    
    def sortare(self,key,mod):
        lista=self.__repo.getAll()
        if mod=="insert":
            if key=="id":
                self.__repo._elems=insertion(lista,lambda x:x.getId(), reverse=False)
                self.__repo.updFile()
                return self.__repo._elems
            elif key=="nume":
                self.__repo._elems=insertion(lista,lambda x:x.getNume(), reverse=False)
                self.__repo.updFile()
                return self.__repo._elems
            elif key=="grupa":
                self.__repo._elems=insertion(lista,lambda x:x.getGrup(), reverse=False)
                self.__repo.updFile()
                return self.__repo._elems
            else:
                print("Preferinta invalida!")
                return []
        elif mod=="comb":
            if key=="id":
                self.__repo._elems=combSort(lista,lambda x:x.getId(), reverse=False)
                self.__repo.updFile()
                return self.__repo._elems
            elif key=="nume":
                self.__repo._elems=combSort(lista,lambda x:x.getNume(), reverse=False)
                self.__repo.updFile()
                return self.__repo._elems
            elif key=="grupa":
                self.__repo._elems=combSort(lista,lambda x:x.getGrup(), reverse=False)
                self.__repo.updFile()
                return self.__repo._elems
            else:
                print("Preferinta invalida!")
                return []
        else:
            print("Metoda inexistenta")
            return []
            
    def __cuvantRandom(self,lungime):
        litere = string.ascii_lowercase
        return ''.join(random.choice(litere) for i in range(lungime))
    
    def __literaRandom(self,lungime):
        litere = string.ascii_uppercase
        return ''.join(random.choice(litere) for i in range(lungime))
    
    def randomStudenti(self):
        id=random.randint(1,100)
        nume=self.__literaRandom(1)+self.__cuvantRandom(5)
        grupa=random.randint(1,200)
        return Student(id,nume,grupa)
    
    
    
    