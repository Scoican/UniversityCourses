'''
Created on 17 nov. 2017

@author: Scoican
'''
from repository.Repository import Repository, RepositoryException
from Controller.LaboratorService import LaboratorController
from domain.Laborator import Laborator

def testeAdaugareLaborator():
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    ctrlLab.adaugaLaborator([1,3], "desc", [1,3])
    assert len(ctrlLab.getLaborator())==1
    try:
        ctrlLab.adaugaLaborator([1,3], "desc", [1,3])
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul a fost deja adaugat"
        
def testeUpdateLaborator():
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    ctrlLab.adaugaLaborator([1,3], "desc", [1,3])
    ctrlLab.updateLaborator([1,3], "Gigel", [1,5])
    try:
        ctrlLab.updateLaborator([5,5],"Desc",[1,5])
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    lab=Laborator([1,3],"",[])
    lab1=repo.find(lab)
    assert lab1.getDesc()=="Gigel"
    
def testeFindLaborator():
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    try:
        ctrlLab.findLaborator(Laborator([5,6],"Desc",[1,5]))
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    
def testeDeleteLaborator():
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    lab=Laborator([1,3],"",[])
    try:
        ctrlLab.deleteLaborator(lab)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"

def testeGetLaborator():
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    assert len(ctrlLab.getLaborator())==0
    
def testeCheckLabProb():
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    labProb=[1,5]    
    assert ctrlLab.checkLabProb(labProb)==0

def testeControllerLaborator():
    '''
    Functie ce testeaza clasa de control a Laboratorului
    '''
    repo=Repository()
    ctrlLab=LaboratorController(repo)
    
    #PrintLaborator
    assert len(ctrlLab.getLaborator())==0
    
    #Add
    ctrlLab.adaugaLaborator([1,3], "desc", [1,3])
    assert len(ctrlLab.getLaborator())==1
    try:
        ctrlLab.adaugaLaborator([1,3], "desc", [1,3])
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul a fost deja adaugat"
        
    #uptdate
    ctrlLab.updateLaborator([1,3], "Gigel", [1,5])
    try:
        ctrlLab.updateLaborator([5,5],"Desc",[1,5])
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    lab=Laborator([1,3],"",[])
    lab1=repo.find(lab)
    assert lab1.getDesc()=="Gigel"
    
    #find
    try:
        ctrlLab.findLaborator(Laborator([5,6],"Desc",[1,5]))
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    
    #delete
    ctrlLab.deleteLaborator(lab)
    assert len(ctrlLab.getLaborator())==0
    try:
        ctrlLab.deleteLaborator(lab)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
        
    #checkLabProb
    labProb=[1,5]    
    assert ctrlLab.checkLabProb(labProb)==0
    
        
    
    