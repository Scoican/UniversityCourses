'''
Created on 18 nov. 2017

@author: Scoican
'''
from domain.Nota import Nota

def testeNota():
    '''
    Functie ce testeaza clasa Nota
    '''
    nt=Nota([1,[1,3]],10)
    assert nt.getIdStud()==1
    assert nt.getIdLab()==1
    assert nt.getIdProb()==3
    assert nt.getNota()==10
    nt.setIdStud(2)
    nt.setIdProb(2)
    nt.setIdLab(4)
    nt.setNota(9)
    assert nt.getIdStud()==2
    assert nt.getIdLab()==4
    assert nt.getIdProb()==2
    assert nt.getNota()==9