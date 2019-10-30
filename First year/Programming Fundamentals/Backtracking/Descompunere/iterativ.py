'''
Created on Jan 11, 2018

@author: Scoican
'''
from Primalitate.TestNumarPrim import *

def solutie(lista,numar):
    return sum(lista)==numar

def solutieGasita(lista):
    print(lista)
    
def consistent(numar):
    if testPrim(numar)==0:
        return False
    return True
  
def descIterativ(numar):
    lista=[-1] 
    if numar<=3:
        print("Numarul nu poate fi descompus!")
    else:
        while len(lista)>0:
            choosed = False
            while not choosed and lista[-1]<numar-1:
                lista[-1] = lista[-1]+1 
                if len(lista)>=2:
                    while lista[-1]<lista[-2]:
                        lista[-1]=lista[-1]+1
                choosed = consistent(lista[-1])
            if choosed and sum(lista)<=numar:
                if solutie(lista, numar):
                    solutieGasita(lista)
                lista.append(-1)
            else:
                lista = lista[:-1] 
                
            
            
            
            
            
            