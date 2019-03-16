'''
Created on Jan 11, 2018

@author: Scoican
'''
from Primalitate.TestNumarPrim import testPrim


def solutie(lista,numar):
    return sum(lista)==numar

def solutieGasita(lista):
    print(lista)
    
def consistent(numar):
    if testPrim(numar)==0:
        return False
    return True
    
def descRecursiv(lista,numar):
    if numar<=3:
        print("Numarul nu poate fi descompus!")
        return
    lista.append(0)
    for i in range(0,numar):
        lista[-1] = i
        if len(lista)>=2:
            if lista[-1]<lista[-2]:
                continue
        choosed = consistent(lista[-1])
        if choosed and sum(lista)<=numar:
            if solutie(lista, numar):
                solutieGasita(lista)
            descRecursiv(lista,numar) 
    lista.pop()
    
    
    
    