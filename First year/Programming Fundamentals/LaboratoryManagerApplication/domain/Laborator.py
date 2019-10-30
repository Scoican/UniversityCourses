'''
Created on 12 nov. 2017

@author: Scoican
'''
class Laborator():
    
    def __init__(self,labProb,desc,dline):
        self.__labProb=labProb
        self.__desc=desc
        self.__dline=dline
    
    def getNrLab(self):
        '''
        Metoda ce returneaza valoarea numarului de laborator
        Output:NrLab=nr intreg
        '''
        return int(self.__labProb[0])
    
    def getNrProba(self):
        '''
        Metoda ce returneaza valoarea numarului de problema
        Output:NrProb=nr intreg
        '''
        return int(self.__labProb[1])
    
    def getDesc(self):
        '''
        Metoda ce returneaza o descriere drept string
        Output:Desc=string
        '''
        return self.__desc
    
    def getZiDeadline(self):
        '''
        Metoda ce returneaza valoarea zilei de deadline
        Output:ZiDeadLine=nr intreg
        '''
        return int(self.__dline[0])
    
    def getLunaDeadline(self):
        '''
        Metoda ce returneaza valoarea lunii de deadline
        Output:LunaDeadLine=nr intreg
        '''
        return int(self.__dline[1])
        
    def setDesc(self,desc):
        '''
        Metoda ce seteaza/schimba valoarea variabilei desc
        Input:desc=string
        '''
        self.__desc=desc
    
    def setZiDeadline(self,zi):
        '''
        Metoda ce seteaza/schimba valoarea variabilei ZiDeadline
        Input:zi=nr intreg
        '''
        self.__dline[0]=str(zi)
    
    def setLunaDeadline(self,luna):
        '''
        Metoda ce seteaza/schimba valoarea variabilei LunaDeadline
        Input:luna=nr intreg
        '''
        self.__dline[1]=str(luna)   
        
    def __eq__(self,other):
        if isinstance(other, self.__class__):
            return self.__labProb[0]==other.__labProb[0] and self.__labProb[1]==other.__labProb[1]
        else:
            return False
    
    def __str__(self):
        return str(self.getNrLab())+"_"+str(self.getNrProba())+" "+self.getDesc()+" "+str(self.getZiDeadline())+"/"+str(self.getLunaDeadline())
    
    def __repr__(self):
        return self.__str__()
    
    @staticmethod
    def citesteLinie(linie):
        split=linie.split(";")
        lab=split[0].split("_")
        dline=split[2].split("/")
        return Laborator([int(lab[0]),int(lab[1])],split[1],[int(dline[0]),int(dline[1])])
    
    @staticmethod
    def scrieLinie(lab):
        return str(lab.getNrLab())+"_"+str(lab.getNrProba())+";"+lab.getDesc()+";"+str(lab.getZiDeadline())+"/"+str(lab.getLunaDeadline())+";"