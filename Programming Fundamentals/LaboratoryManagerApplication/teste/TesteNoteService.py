'''
Created on 18 nov. 2017

@author: Scoican
'''

from validare.Validator import ValidationException
from repository.Repository import Repository,RepositoryException
from Controller.NoteService import NoteController
from domain.Nota import Nota

def testeDeleteNota():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,1])
    ctrlNote.deleteNota(1,[1,1])
    assert len(ctrlNote.getNote())==0

def testeAdaugareAsignare():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,1])
    assert len(ctrlNote.getNote())==1
    try:
        ctrlNote.adaugareAsignare(1, [1,1])
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul a fost deja adaugat"
        
def testeAdaugareNote():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,1])
    try:
        ctrlNote.adaugareNota(2,[1,1],10)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    
def testeListaStudentiNotati():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,1])
    ctrlNote.adaugareNota(1,[1,1],10)
    ctrlNote.adaugareAsignare(2, [1,3])
    ctrlNote.adaugareNota(2,[1,3],10)
    ctrlNote.adaugareAsignare(3, [1,3])
    ctrlNote.adaugareNota(3,[1,3],10)
    note=ctrlNote.getNote()
    assert len(ctrlNote.listaStudentiNotati(note))==3
    
def testeProblemeleStudentului():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,3])
    ctrlNote.adaugareNota(1,[1,3],10)
    ctrlNote.adaugareAsignare(1, [1,4])
    ctrlNote.adaugareNota(1,[1,4],10)
    assert len(ctrlNote.problemeleStudentului(1, ctrlNote.getNote()))==2
    
def testeVerificareStudentNotat():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,1])
    ctrlNote.adaugareNota(1,[1,1],10)
    ctrlNote.adaugareAsignare(2, [1,3])
    ctrlNote.adaugareNota(2,[1,3],10)
    ctrlNote.adaugareAsignare(3, [1,3])
    ctrlNote.adaugareNota(3,[1,3],10)
    note=ctrlNote.getNote()
    try:
        ctrlNote.verificareStudentNotat(1,[1,1], ctrlNote.listaStudentiNotati(note))
        assert False
    except ValidationException as ve:
        assert str(ve)=="Studentul are o nota asignata!"
        
def testeCreareListaProblema():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,3])
    ctrlNote.adaugareNota(1,[1,3],10)
    ctrlNote.adaugareAsignare(2, [1,3])
    ctrlNote.adaugareNota(2,[1,3],10)
    assert len(ctrlNote.creareListaProblema([1,3]))==2
    
def testeCalculMedie():
    repo=Repository()
    ctrlNote=NoteController(repo)
    ctrlNote.adaugareAsignare(1, [1,1])
    ctrlNote.adaugareNota(1,[1,1],10)
    ctrlNote.adaugareAsignare(1, [1,3])
    ctrlNote.adaugareNota(1,[1,3],10)
    ctrlNote.adaugareAsignare(1, [1,4])
    ctrlNote.adaugareNota(1,[1,4],10)
    lista=ctrlNote.calculMedie()
    assert lista[0][1]==10.0
    
def testeGetNote():
    repo=Repository()
    ctrlNote=NoteController(repo)
    assert len(ctrlNote.getNote())==0
    ctrlNote.adaugareAsignare(1, [1,1])
    assert len(ctrlNote.getNote())==1
      
def testeCheckIdNote():
    repo=Repository()
    ctrlNote=NoteController(repo)
    assert ctrlNote.checkId(7)==0
    