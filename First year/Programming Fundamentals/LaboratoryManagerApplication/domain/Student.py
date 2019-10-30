'''
Created on 12 nov. 2017

@author: Scoican
'''
class Student():
    
    def __init__(self,id,nume,grup):
        self.__id = id
        self.__nume = nume
        self.__grup = grup
    
    def getId(self):
        '''
        Metoda ce returneaza id-ul unui student
        output:id:numar intreg
        '''
        return self.__id
    
    def getNume(self):
        '''
        Metoda ce returneaza numele unui student
        output:nume:string
        '''
        return self.__nume
    
    def getGrup(self):
        '''
        Metoda ce returneaza grupa unui student
        output:grup:numarintreg
        '''
        return self.__grup
    
    def setNume(self,nume):
        '''
        Metoda ce modifica numele unui student
        input:nume-strign
        '''
        self.__nume=nume
    
    def setGrup(self,grup):
        '''
        Metoda ce modifica grupa unui student
        input:grup:numar intreg
        '''
        self.__grup=grup
        
    def __eq__(self,other):
        if isinstance(other, self.__class__):
            return self.__id==other.__id
        else:
            return False
    
    def __str__(self):
        return str(self.__id)+" "+self.__nume+" "+str(self.__grup)
    
    def __repr__(self):
        return self.__str__()
    
    @staticmethod
    def citesteLinie(linie):
        split=linie.split(";")
        return Student(int(split[0]),split[1],int(split[2]))
    
    @staticmethod
    def scrieLinie(student):
        return str(student.getId())+";"+student.getNume()+";"+str(student.getGrup())+";"


