
'''
Created on 17 nov. 2017

@author: Scoican
'''
class Nota:
    
    def __init__(self,asignare,nota):
        self.__asignare= asignare
        self.__nota = nota

    def getIdStud(self):
        '''
        Metoda ce returneaza id-ul studentului ce are nota
        Output:Numar intreg
        '''
        return self.__asignare[0]

    def getLabProb(self):
        '''
        Metoda ce returneaza idul unei probleme
        output:lista cu numere intregi
        '''
        return self.__asignare[1]
    
    def getIdLab(self):
        '''
        Metoda ce returneaza laboratorul la care studentul a primit nota
        Output:Numar intreg
        '''
        return self.__asignare[1][0]
    
    def getIdProb(self):
        '''
        Metoda ce returneaza problema la care studentul a primit nota
        Output:Numar intreg
        '''
        return self.__asignare[1][1]

    def getNota(self):
        '''
        Metoda ce returneaza nota obtinuta de student
        Output:Numar rational
        '''
        return self.__nota
    

    def setIdStud(self, value):
        '''
        Metoda ce modifica id-ul studentului
        Input:Value-Numar intreg
        '''
        self.__asignare[0] = value


    def setIdLab(self, value):
        '''
        Metoda ce modifica laboratorul studentului
        Input:Value-Numar intreg
        '''
        self.__asignare[1][0] = value
    
    def setIdProb(self,value):
        '''
        Metoda ce modifica problema studentului
        Input:Value-Numar intreg
        '''
        self.__asignare[1][1] = value    

    def setNota(self, value):
        '''
        Metoda ce modifica nota studentului
        Input:Value-Numar rational
        '''
        self.__nota = value

    def __eq__(self,other):
        if isinstance(other,self.__class__):
            return self.getIdStud()==other.getIdStud() and self.getLabProb()==other.getLabProb()
        else:
            return False
    
    def __str__(self):
        return str(self.getIdStud())+" "+str(self.getIdLab())+"_"+str(self.getIdProb())+" "+str(self.getNota())
    
    def __repr__(self):
        return self.__str__()
    
    @staticmethod
    def citesteLinie(linie):
        split=linie.split(";")
        lab=split[1].split("_")
        return Nota([int(split[0]),[int(lab[0]),int(lab[1])]],float(split[2]))
    
    @staticmethod
    def scrieLinie(nota):
        return str(nota.getIdStud())+";"+str(nota.getIdLab())+"_"+str(nota.getIdProb())+";"+str(nota.getNota())+";"