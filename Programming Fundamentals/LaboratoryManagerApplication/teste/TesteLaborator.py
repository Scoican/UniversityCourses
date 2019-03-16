'''
Created on 16 nov. 2017

@author: Scoican
'''
from domain.Laborator import Laborator 
def testeLaborator():
    '''
    Functei ce testeaza clasa Laborator
    '''
    lab=Laborator([1,3],"Descriere",[1,12])
    assert lab.getNrLab()==1
    assert lab.getNrProba()==3
    assert lab.getDesc()=="Descriere"
    assert lab.getZiDeadline()==1
    assert lab.getLunaDeadline()==12
    lab.setDesc("Mesaj")
    lab.setZiDeadline(3)
    lab.setLunaDeadline(11)
    assert lab.getDesc()=="Mesaj"
    assert lab.getZiDeadline()==3
    assert lab.getLunaDeadline()==11