'''
Created on 17 nov. 2017

@author: Scoican
'''
from validare.Validator import ValidationException
from repository.Repository import Repository, RepositoryException
from Controller.StudentService import StudentController
from domain.Student import Student


def testeAdaugareStudent():
    repo=Repository()
    ctrlStud=StudentController(repo)
    ctrlStud.adaugaStudent(1, "Gigel", 217)
    assert len(ctrlStud.getStudenti())==1
    #Add
    try:
        ctrlStud.adaugaStudent(2, "", 217)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nume invalid \n"
        
    try:
        ctrlStud.adaugaStudent(1, "Gigel", 20)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul a fost deja adaugat"

def testeUpdateStudent():
    repo=Repository()
    ctrlStud=StudentController(repo)
    ctrlStud.adaugaStudent(1, "Gigel", 217)
    ctrlStud.updateStudent(1, "Marcu", 218)
    st=Student(1,"",0)
    st1=repo.find(st)
    assert st1.getNume()=="Marcu"
    
    try:
        ctrlStud.updateStudent(3, "Marcu", 20)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
        
    try:
        ctrlStud.updateStudent(2, "", 217)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nume invalid \n"
        
def testeFindStudent():
    repo=Repository()
    ctrlStud=StudentController(repo)
    try:
        ctrlStud.findStudent(Student(3,"Gigel",20))
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"

def testeDeleteStudent():
    repo=Repository()
    ctrlStud=StudentController(repo)
    ctrlStud.adaugaStudent(1, "Gigel", 217)
    ctrlStud.deleteStudent(Student(1,"Gigel",20))
    assert len(ctrlStud.getStudenti())==0
    
    try:
        ctrlStud.deleteStudent(Student(1,"Gigel",20))
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    
def testeCheckIdStudent():
    repo=Repository()
    ctrlStud=StudentController(repo)
    assert ctrlStud.checkId(1)==0

def testeGetStudenti():
    repo=Repository()
    ctrlStud=StudentController(repo)
    assert len(ctrlStud.getStudenti())==0

def testeStudentiGrupa():
    repo=Repository()
    ctrlStud=StudentController(repo)
    ctrlStud.adaugaStudent(1, "asd", 20)
    ctrlStud.adaugaStudent(2, "asd", 20)
    ctrlStud.adaugaStudent(3, "asd", 20)
    assert len(ctrlStud.studentiGrupa(20, ctrlStud.getStudenti()))
    
def testeGasireNumeStudent():
    repo=Repository()
    ctrlStud=StudentController(repo)
    ctrlStud.adaugaStudent(1, "asd", 20)
    lista=[[1,10]]
    ctrlStud.gasireNumeStudent(lista)
    assert lista[0][0]=="asd"