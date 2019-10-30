'''
Created on 12 nov. 2017

@author: Scoican
'''
from repository.Repository import RepositoryException
from validare.Validator import Validator,ValidationException
from domain.Student import Student
from domain.Laborator import Laborator
from domain.Nota import Nota


class Comanda:
    '''
    Clasa ce apeleaza metodele necesare de rezolvare a unei cerinte
    '''
    def __init__(self,numeCmd,functCmd):
        self.__numeCmd=numeCmd
        self.__functCmd=functCmd
        
    def execute(self):
        '''
        Metoda ce apeleaza o alta metoda data
        '''
        self.__functCmd()
        
class Console:
    '''
    Clasa ce serveste ca UserInterface
    '''
    def __init__(self,ctrlStud,ctrlLab,ctrlNote):
        self.__ctrlStud = ctrlStud
        self.__ctrlLab=ctrlLab
        self.__ctrlNote=ctrlNote
    
    
    def __citesteComanda(self,text):
        '''
        Metoda ce citeste un numar,dand ValueError in caz contrar
        output:Numar intreg
        '''
        while True:
            try:
                return int(input(text))
            except ValueError:
                print("Eroare!Va rog, introduceti o comanda valida!")
    
    def __uiAddStudent(self):
        '''
        Metoda de ui ce adauga un student
        '''
        idStud=self.__citesteComanda("Introduceti un id pentru student:")
        Validator.valideazaExistentaStudent(self.__ctrlStud.checkId(id))
        nume=self.__ctrlStud.citesteNume("Introduceti numele studentului:")
        grup=self.__citesteComanda("Introduceti grupa in care se afla studentul:")
        self.__ctrlStud.adaugaStudent(idStud,nume,grup)
        print("Student adaugat cu succes!")
    
    def __uiRemStudent(self):
        '''
        Metoda de ui ce sterge un student
        '''
        Validator.valideazaLungimeStudenti(len(self.__ctrlStud.getStudenti()))
        idStud=self.__citesteComanda("Introduceti un id pentru studentul dorit:")
        st=Student(idStud,"",0)
        self.__ctrlStud.deleteStudent(st)
        self.__ctrlNote.deleteNota(idStud,0)
        print("Student sters cu succes!")
    
    def __uiUpdateStudent(self):
        '''
        Metoda de ui ce modifica datele unui student
        '''
        Validator.valideazaLungimeStudenti(len(self.__ctrlStud.getStudenti()))
        idStud=self.__citesteComanda("Introduceti un id pentru studentul dorit:")
        Validator.valideazaInexistentaStudent(self.__ctrlStud.checkId(idStud))
        print("Introduceti date noi:")
        nume=self.__ctrlStud.citesteNume("Introduceti numele studentului:")
        grup=self.__citesteComanda("Introduceti grupa in care se afla studentul:")
        self.__ctrlStud.updateStudent(idStud,nume,grup)
        print("Datele studentului schimbate cu succes!")
         
    def __uiPrintStudenti(self):
        '''
        Metoda de ui ce printeaza o lista cu studentii adaugati
        '''
        studenti=self.__ctrlStud.getStudenti()
        Validator.valideazaLungimeStudenti(len(studenti))
        self.__ctrlStud.printStudenti(studenti)
    
    def __uiFindStudent(self):
        '''
        Metoda de ui ce gaseste un student
        '''
        Validator.valideazaLungimeStudenti(len(self.__ctrlStud.getStudenti()))
        idStud=self.__citesteComanda("Introduceti id-ul studentului cautat:")
        st=Student(idStud,"",0)
        self.__ctrlStud.findStudent(st)
    
    def __uiAfisareGrupa(self):
        '''
        Metoda de ui ce afiseaza studentii dintr-o grupa
        '''
        studenti=[]
        studenti=self.__ctrlStud.getStudenti()
        Validator.valideazaLungimeStudenti(len(studenti))
        grupa=self.__citesteComanda("Va rog introduceti grupa dorita:")
        #grupaStudenti=self.__ctrlStud.studentiGrupa(grupa,studenti)
        lista=self.__ctrlStud.studentiGrupaRecursiv(grupa,studenti,[],0)
        self.__ctrlStud.afisareGrupa(lista,grupa)
    
    def __uiAsignareProblema(self):
        '''
        Metoda de ui ce asigneaza unui student o problema
        '''
        self.__uiPrintStudenti()
        self.__uiPrintLaborator()
        idStud=self.__citesteComanda("Alegeti un student:")
        Validator.valideazaInexistentaStudent(self.__ctrlStud.checkId(idStud))
        nrLab=self.__citesteComanda("Introduceti numarul de laborator:")
        nrProb=self.__citesteComanda("Alegeti problema ce doriti sa o asignati:")
        Validator.valideazaInexistentaProblema(self.__ctrlLab.checkLabProb([nrLab,nrProb]))
        self.__ctrlNote.adaugareAsignare(idStud,[nrLab,nrProb])
        print("Asignare reusita!")
    
    def __uiPrintListaAsig(self):
        '''
        Metoda de ui ce afiseaza lista de studenti cu problemele repartizate
        '''
        asig=self.__ctrlNote.getNote()
        Validator.valideazaLungimeAsignari(len(asig))
        print("Lista de asignari probleme:")
        self.__ctrlNote.afisareAsignari(asig)
           
    def __uiAsignareNota(self):
        '''
        Metoda de ui ce asigneaza unui student o nota
        '''
        note=self.__ctrlNote.getNote()
        Validator.valideazaLungimeAsignari(len(note))
        idStud=self.__citesteComanda("Introduceti studentul dorit:")
        lista=self.__ctrlNote.problemeleStudentului(idStud,note)
        self.__ctrlNote.afisareProblemeleStudentului(lista)
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei:")
        labProb=[nrLab,nrProb]
        Validator.valideazaExistentaProblema(labProb)
        self.__ctrlNote.verificareStudentNotat(idStud,labProb,lista)
        nota=self.__ctrlNote.citesteComanda("Introduceti nota dorita:")
        Validator.valideazaNota(nota)
        self.__ctrlNote.adaugareNota(idStud,labProb,nota)
        
    def __uiPrintNote(self):
        '''
        Metoda de ui ce afiseaza lista de studenti cu notele obtinute la problemele asignate
        '''
        note=self.__ctrlNote.getNote()
        Validator.valideazaLungimeAsignari(len(note))
        lista=self.__ctrlNote.listaStudentiNotatiRecursiv(note,[],0)
        Validator.valideazaLungimeStudentiNotati(len(lista))
        self.__ctrlNote.afisareNote(lista)
       
    def __uiAddLaborator(self):
        '''
        Metoda de ui ce adauga un laborator
        '''
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei:")
        labProb=[nrLab,nrProb]
        Validator.valideazaExistentaProblema(labProb)
        desc=input("Introduceti o descriere:")
        desc=desc.replace("  ","")
        zi=self.__citesteComanda("Introduceti zi limita:")
        luna=self.__citesteComanda("Introduceti luna limita:")
        deadline=[zi,luna]
        self.__ctrlLab.adaugaLaborator(labProb,desc,deadline)
        print("Laborator adaugat cu succes!")
    
    def __uiPrintLaborator(self):
        '''
        Metoda de ui ce printeaza lista de laboratoare
        '''
        lab=self.__ctrlLab.getLaborator()
        Validator.valideazaLungimeProbleme(len(lab))
        self.__ctrlLab.printLaborator(lab)
                
    def __uiRemLaborator(self):
        '''
        Metoda de ui ce sterge o problema de laborator din lista
        '''
        Validator.valideazaLungimeProbleme(len(self.__ctrlLab.getLaborator()))
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului dorit:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei dorite:")
        labProb=[nrLab,nrProb]
        lab=Laborator(labProb," ",0)
        self.__ctrlLab.deleteLaborator(lab)
        self.__ctrlNote.deleteNota(0,labProb)
        print("Problema stersa cu succes!")
    
    def __uiUpdateLaborator(self):
        '''
        Metoda de ui ce modifica datele unei probleme
        '''
        Validator.valideazaLungimeProbleme(len(self.__ctrlLab.getLaborator()))
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului dorit:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei dorite:")
        labProb=[nrLab,nrProb]
        Validator.valideazaInexistentaProblema(labProb)
        desc=input("Introduceti o descriere noua:")
        zi=self.__citesteComanda("Introduceti ziua de predare:")
        luna= self.__citesteComanda("Introduceti luna de predare:")
        deadline=[zi,luna]
        self.__ctrlLab.updateLaborator(labProb,desc,deadline)
        print("Problema a fost modificata cu succes!")
    
    def __uiFindLaborator(self):
        '''
        Metoda de ui ce gaseste un laborator
        '''
        Validator.valideazaLungimeProbleme(len(self.__ctrlLab.getLaborator()))
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului dorit:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei dorite:")
        labProb=[nrLab,nrProb]
        lab=Laborator(labProb,"",[])
        self.__ctrlLab.findLaborator(lab)
    
        
    def __afisareMeniu(self):
        '''
        Metoda ce afiseaza comenzile si mesajele din meniul principal
        '''
        print("Bun venit !")
        print("Acesta este meniul de comenzi:")
        print("1.Meniu studenti")
        print("2.Meniu probleme")
        print("3.Meniu laborator")
        print("4.Statistici")
        print("5.Adaugare date testare")
        print("0.Inchidere aplicatie")
    
    def __afisareMeniuSecundar(self,comenzi,x):
        '''
        Metoda ce afiseaza comenzile din meniul secundar
        '''
        print("Alegeti comanda dorita:")
        for y in comenzi:
            print(comenzi[y][1])
        print(str(x)+".Revenire meniu principal")
        print("0.Iesire aplicatie")
        
    def __uiComenziSecundare(self,comenzi):
        '''
        Asemanator run,metoda ce printeaza comenzile dorite,dar din meniul secundar
        '''
        x=len(comenzi)+1
        ok=0
        while True:
            if ok==0:
                self.__afisareMeniuSecundar(comenzi,x)
                ok=1
            cmd=self.__citesteComanda("Introduceti comanda dorita:")
            if cmd==0:
                return
            if cmd==x:
                self.run()
                return
            if cmd in comenzi:
                try:
                    comenzi[cmd][0].execute()
                except RepositoryException as re:
                    print(re)
                except ValidationException as ve:
                    print(ve)
            else:
                print("Comanda invalida")
                
    def __uiDateTestare(self):
        '''
        Metoda ce adauga niste elemente ce ajuta la testare
        '''
        numarStudenti=self.__citesteComanda("Cati studenti doriti:")
        numarLaboratoare=self.__citesteComanda("Cate probleme doriti:")
        x=0
        while x<numarStudenti:
            st=self.__ctrlStud.randomStudenti()
            try:
                self.__ctrlStud.adaugaStudent(st.getId(),st.getNume(),st.getGrup())
            except RepositoryException as re:
                print(re)
            x+=1
        x=0
        while x<numarLaboratoare:
            lab=self.__ctrlLab.randomLaborator()
            try:
                self.__ctrlLab.adaugaLaborator([lab.getNrLab(),lab.getNrProba()],lab.getDesc(),[lab.getZiDeadline(),lab.getLunaDeadline()])
            except RepositoryException as re:
                print(re)
            x+=1
        print("Elemente adaugate")
        self.__ctrlStud.adaugaStudent(1,"Gigel",217)
        self.__ctrlStud.adaugaStudent(2,"Mirel",217)
        self.__ctrlLab.adaugaLaborator([1,1],"Descriere",[1,1])
        self.__ctrlLab.adaugaLaborator([1,2],"Descriere",[1,1])
        self.__ctrlLab.adaugaLaborator([1,3],"Descriere",[1,1])
        self.__ctrlNote.adaugareAsignare(1,[1,1])
        self.__ctrlNote.adaugareAsignare(1,[1,2])
        self.__ctrlNote.adaugareAsignare(1,[1,3])
        self.__ctrlNote.adaugareAsignare(2,[1,1])
        self.__ctrlNote.adaugareNota(1,[1,1],5)
        self.__ctrlNote.adaugareNota(1,[1,2],4)
        self.__ctrlNote.adaugareNota(1,[1,3],2)
        self.__ctrlNote.adaugareNota(2,[1,1],9)
        
    def __uiAflabeticNume(self):
        '''
        Metoda de ui ce afiseaza studentii de la o problema anume,in ordine aflabetica dupa nume
        '''
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei:")
        Validator.valideazaInexistentaProblema(self.__ctrlLab.checkLabProb([nrLab,nrProb]))
        idProb=[nrLab,nrProb]
        listaStudentiProblema=self.__ctrlNote.creareListaProblema(idProb)
        listaStudentiProblema=self.__ctrlStud.gasireNumeStudent(listaStudentiProblema)
        self.__ctrlNote.afisareAlfabetica(listaStudentiProblema)
    
    def __uiNota5(self):
        '''
        Metoda de ui ce afiseaza toti studentii cu media de la laboratoare mai mica de 5
        '''
        listaMedie=self.__ctrlNote.calculMedie()
        listaMedie=self.__ctrlStud.gasireNumeStudent(listaMedie)
        self.__ctrlNote.afisareListaMedie(listaMedie)
    
    
    def __uiOrdonatiNote(self):
        '''
        Metoda de ui ce afiseaza studentii de la o problema anume,in orindea notelor
        '''
        nrLab=self.__citesteComanda("Introduceti numarul laboratorului:")
        nrProb=self.__citesteComanda("Introduceti numarul problemei:")
        Validator.valideazaInexistentaProblema(self.__ctrlLab.checkLabProb([nrLab,nrProb]))
        idProb=[nrLab,nrProb]
        listaStudentiProblema=self.__ctrlNote.creareListaProblema(idProb)
        listaStudentiProblema=self.__ctrlStud.gasireNumeStudent(listaStudentiProblema)
        self.__ctrlNote.afisareOrdonatiNote(listaStudentiProblema)
    
    
    def __uiSortare(self):
        mod=input("Modul de sortare dorit:")
        key=input("In functie de ce doriti sa fie sortati:")
        if self.__ctrlStud.sortare(key,mod)!=[]:
            self.__ctrlStud.printStudenti(self.__ctrlStud.sortare(key,mod))
    
    
    def run(self):
        '''
        Metoda ce apeleaza comenzile dorite din meniul principal
        '''
        ok=0
        comenziStud={1:[Comanda(1,self.__uiAddStudent),"1.Adauga un student"],
                     2:[Comanda(2,self.__uiPrintStudenti),"2.Printeaza studenti"],
                     3:[Comanda(3,self.__uiRemStudent),"3.Sterge un student"],
                     4:[Comanda(4,self.__uiUpdateStudent),"4.Modificare date student"],
                     5:[Comanda(5,self.__uiFindStudent),"5.Gaseste student"],
                     6:[Comanda(6,self.__uiAfisareGrupa),"6.Afiseaza studentii dintr-o grupa"],
                     7:[Comanda(7,self.__uiSortare),"7.Sortare Studenti"]}
        
        comenziLab={1:[Comanda(1,self.__uiAddLaborator),"1.Adauga o problema"],
                    2:[Comanda(2,self.__uiPrintLaborator),"2.Printeaza problemele"],
                    3:[Comanda(3,self.__uiRemLaborator),"3.Sterge problema"],
                    4:[Comanda(4,self.__uiUpdateLaborator),"4.Modifica date problema"],
                    5:[Comanda(5,self.__uiFindLaborator),"5.Gaseste problema"]}
        
        comenziNote={1:[Comanda(1,self.__uiAsignareProblema),"1.Asignare problema unui student"],
                     2:[Comanda(2,self.__uiPrintListaAsig),"2.Afiseaza lista studentilor si a problemelor asignate"],
                     3:[Comanda(3,self.__uiAsignareNota),"3.Asignare nota pentru un student"],
                     4:[Comanda(4,self.__uiPrintNote),"4.Afiseaza lista studentilor si notele la laborator"]}
        
        comenziStatistici={1:[Comanda(1,self.__uiAflabeticNume),"1.Afiseaza studentii cu notele obtinute la un laborator ales,alfabetic dupa nume"],
                           2:[Comanda(2,self.__uiOrdonatiNote),"2.Afiseaza studentii cu notele obtinute la un laborator ales,ordonati dupa nota"],
                           3:[Comanda(3,self.__uiNota5),"3.Afiseaza toti studentii cu media notelor la laboratoare mai mica de 5"]}
                    
        while True:
            if ok==0:
                self.__afisareMeniu()
                ok=1
            cmd = self.__citesteComanda("Introduceti comanda dorita:")
            if cmd==0:
                return
            elif cmd==1:
                try:
                    self.__uiComenziSecundare(comenziStud)
                    return
                except RepositoryException as re:
                    print(re)
                except ValidationException as ve:
                    print(ve)
            elif cmd==2:
                try:
                    self.__uiComenziSecundare(comenziLab)
                    return
                except RepositoryException as re:
                    print(re)
                except ValidationException as ve:
                    print(ve)
            elif cmd==3:
                try:
                    self.__uiComenziSecundare(comenziNote)
                    return
                except RepositoryException as re:
                    print(re)
                except ValidationException as ve:
                    print(ve)
            elif cmd==4:
                if(self.__ctrlNote.getNote()!=[]):
                    try:
                        self.__uiComenziSecundare(comenziStatistici)
                        return
                    except RepositoryException as re:
                        print(re)
                    except ValidationException as ve:
                        print(ve)
                else:
                    print("Nu exista informatie suficienta pentru creare de statistici!")
            elif cmd==5:
                try:
                    self.__uiDateTestare()
                except RepositoryException as re:
                    print(re)
                except ValidationException as ve:
                    print(ve)
            else:
                print("Comanda invalida")
                