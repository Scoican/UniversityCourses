
'''
Created on 12 nov. 2017

@author: Scoican
'''
from domain.Laborator import Laborator
from validare.Validator import Validator
import random,string
class LaboratorController:
    '''
    Clasa ce se ocupa cu controlul multimii de laboratoare si probleme
    '''
    def __init__(self,repo):
        self.__repo=repo
        self.__validator=Validator()
    
    def adaugaLaborator(self,labProb,desc,dline):
        '''
        Metoda ce adauga un Laborator la lista
        input:labProb,dline=lista cu nr intregi,desc=string,
        '''
        lab=Laborator(labProb,desc,dline)
        self.__validator.valideazaLaborator(lab)
        self.__repo.add(lab)
    
    def updateLaborator(self,labProb,desc,dline):
        '''
        Metoda ce modifica datele unui Laborator din lista
        input:labProb,dline=lista cu nr intregi,desc=string,
        '''
        lab=Laborator(labProb,desc,dline)
        self.__validator.valideazaLaborator(lab)
        self.__repo.upd(lab)
        
    def deleteLaborator(self,lab):
        '''
        Metoda ce sterge un laborator din lista
        input:Lab-obiect laborator ce contine doar id-ul unui lab
        '''
        self.__validator.valideazaLabProb(lab)
        self.__repo.rem(lab)
    
    def findLaborator(self,lab):
        '''
        Metoda ce gaseste un laborator din lista
        input:Lab-obiect laborator ce contine doar id-ul unui lab
        '''
        self.__validator.valideazaLabProb(lab)
        lab=self.__repo.find(lab)
        print("Problema:"+str(Laborator.getNrLab(lab))+"_"+str(Laborator.getNrProba(lab))+" "+
              " Descriere:"+Laborator.getDesc(lab)+
              " Deadline:"+str(Laborator.getZiDeadline(lab))+"/"+str(Laborator.getLunaDeadline(lab)))
    
    def printLaborator(self,lab):
        '''
        Metoda ce afiseaza toate probleme cu toate datele lor
        input:lab-Lista de obiecte Laborator
        '''
        x=0
        print("Lista de laboratoare si probleme este:")
        while x<len(lab):
            print("Problema:"+str(Laborator.getNrLab(lab[x]))+"_"+str(Laborator.getNrProba(lab[x]))+
                  " Descriere:"+Laborator.getDesc(lab[x])+
                " Deadline:"+str(Laborator.getZiDeadline(lab[x]))+"/"+str(Laborator.getLunaDeadline(lab[x])))
            x+=1
            
    def getLaborator(self):
        '''
        Metoda ce returneaza o lista cu toate obiectele Laborator
        output:Lista cu obiecte
        '''
        return self.__repo.getAll()
    
    def checkLabProb(self,labProb):
        '''
        Metoda ce verifica daca id-ul unui laborator/problema este deja prezent in lista
        output:1-caz adevarat,0-caz fals
        input:labProb-lista cu numere intregi
        '''
        lab=Laborator(labProb," ",0)
        if lab in self.getLaborator():
            return 1
        else:
            return 0
        
    def __cuvantRandom(self,lungime):
        litere = string.ascii_lowercase
        return ''.join(random.choice(litere) for i in range(lungime))
    
    def __literaRandom(self,lungime):
        litere = string.ascii_uppercase
        return ''.join(random.choice(litere) for i in range(lungime))
    
    def randomLaborator(self):
        nrLab=random.randint(1,14)
        nrProb=random.randint(1,6)
        descriere=self.__literaRandom(1)+self.__cuvantRandom(random.randint(1,8))+" "+self.__cuvantRandom(random.randint(1,8))+" "+self.__cuvantRandom(random.randint(1,8))
        ziDeadline=random.randint(1,30)
        lunaDealine=random.randint(1,12)
        return Laborator([nrLab,nrProb],descriere,[ziDeadline,lunaDealine])