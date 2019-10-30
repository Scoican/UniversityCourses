'''
Created on 16 nov. 2017

@author: Scoican
'''
from teste.TesteStudent import testeStudent
from teste.TesteLaborator import testeLaborator
from teste.TesteRepository import testeRepository
from teste.TesteValidator import testeValidator
from teste.TesteStudentiService import *
from teste.TesteLaboratorService import *
from teste.TesteNota import testeNota
from teste.TesteNoteService import *

def runTeste():
    '''
    Functie ce ruleaza toate testele
    '''
    testeDeleteNota()
    testeStudent()
    testeLaborator()
    testeRepository()
    testeValidator()
    testeNota()
    testeAdaugareStudent()
    testeUpdateStudent()
    testeFindStudent()
    testeDeleteStudent()
    testeCheckIdStudent()
    testeStudentiGrupa()
    testeGasireNumeStudent()
    testeGetStudenti()
    testeAdaugareLaborator()
    testeUpdateLaborator()
    testeFindLaborator()
    testeDeleteLaborator()
    testeGetLaborator()
    testeCheckLabProb()
    testeAdaugareNote()
    testeAdaugareAsignare()
    testeListaStudentiNotati()
    testeProblemeleStudentului()
    testeVerificareStudentNotat()
    testeCreareListaProblema()
    testeCalculMedie()
    testeGetNote()
    testeCheckIdNote()
    
    