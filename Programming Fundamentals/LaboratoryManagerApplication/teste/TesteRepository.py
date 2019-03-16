'''
Created on 16 nov. 2017

@author: Scoican
'''
from domain.Student import Student
from repository.Repository import Repository, RepositoryException

def testeRepository():
    '''
    Functei ce testeaza clasa Repository
    '''
    st=Student(1,"Gelu",217)
    repo=Repository()
    assert len(repo)==0
    repo.add(st)
    assert len(repo)==1
    try:
        repo.add(st)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul a fost deja adaugat"
    st1=Student(1,"Mirel",218)
    repo.upd(st1)
    st=repo.find(Student(1,"",0))
    assert st.getNume()=="Mirel"
    st1=Student(2,"",0)
    try:
        repo.upd(st1)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    st2=repo.find(Student(1,"",0))
    assert st2.getGrup()==218
    try:
        repo.find(Student(4,"",0))
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    repo.rem(st)
    assert len(repo)==0
    try:
        repo.rem(st)
        assert False
    except RepositoryException as re:
        assert str(re)=="Elementul nu se afla in lista"
    
    assert len(repo.getAll())==0
    
    