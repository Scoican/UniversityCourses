'''
Created on 17 nov. 2017

@author: Scoican
'''
from domain.Student import Student
from validare.Validator import Validator,ValidationException
from domain.Laborator import Laborator
from domain.Nota import Nota

def testeValidator():
    '''
    Functei ce testeaza clasa Validator
    '''
    valid=Validator()
    st=Student(-10,"",-100)
    try:
        valid.valideazaId(st)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Student invalid \n"
        
    try:
        valid.valideazaStudent(st)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Student invalid \nNume invalid \nGrupa invalida \n"
        
    lab=Laborator([0,0],"",[0,0])
    try:
        valid.valideazaLaborator(lab)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Laborator invalid \nProblema invalida \nDescriere invalida \nZi invalida \nLuna invalida \n"
    
    try:
        valid.valideazaLabProb(lab)    
        assert False
    except ValidationException as ve:
        assert str(ve)=="Laborator invalid \nProblema invalida \n"
    
    asig=Nota([0,[0,0]],0)
    try:
        valid.valideazaAsignare(asig)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Id student invalid! \nLaborator invalid! \nProblema invalida! \n"
        
    try:
        valid.valideazaInexistentaStudent(0)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Studentul nu exista!"
        
    try:
        valid.valideazaExistentaStudent(1)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Studentul este deja in lista"
        
    try:
        valid.valideazaExistentaProblema(1)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Problema este deja in lista" 
    
    try:
        valid.valideazaInexistentaProblema(0)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Problema nu exista!"
    
    try:
        valid.valideazaStudentAsignat(1)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Studentul are deja o problema asignata!"
        
    try:
        valid.valideazaLungimeAsignari(0)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nu au avut loc asignari de probleme"
    
    try:
        valid.valideazaLungimeProbleme(0)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nu exista probleme in lista"
        
    try:
        valid.valideazaLungimeStudenti(0)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nu exista studenti in lista"
        
    try:
        valid.valideazaLungimeStudentiNotati(0)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nu exista studenti ce au primit note"
    
    try:
        valid.valideazaNota(11)
        assert False
    except ValidationException as ve:
        assert str(ve)=="Nota invalida"