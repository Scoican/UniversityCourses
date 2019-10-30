'''
Created on 12 nov. 2017

@author: Scoican
'''
from teste.AllTeste import runTeste
from repository.Repository import Repository, FileRepository
from Controller.StudentService import StudentController
from Controller.LaboratorService import LaboratorController
from Controller.NoteService import NoteController 
from ui.Console import Console
from domain.Student import Student
from domain.Laborator import Laborator
from domain.Nota import Nota

if __name__=='__main__':
    #runTeste()
    fisierStudenti="studenti.txt"
    fisierLaboratoare="laboratoare.txt"
    fisierNote="note.txt"
    #repoStud=Repository()
    #repoLab=Repository()
    #repoNote=Repository()
    repoStud=FileRepository(fisierStudenti,Student.citesteLinie,Student.scrieLinie)
    repoLab=FileRepository(fisierLaboratoare,Laborator.citesteLinie,Laborator.scrieLinie)
    repoNote=FileRepository(fisierNote,Nota.citesteLinie,Nota.scrieLinie)
    ctrlStud = StudentController(repoStud)
    ctrlLab = LaboratorController(repoLab)
    ctrlNote= NoteController(repoNote)
    cons = Console(ctrlStud,ctrlLab,ctrlNote)
    cons.run()