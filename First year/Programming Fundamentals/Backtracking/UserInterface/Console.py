'''
Created on Jan 11, 2018

@author: Scoican
'''
from Descompunere.iterativ import *
from Descompunere.recursiv import descRecursiv

def citireNumar():
    while True:
        try:
            return int(input("Introduceti numarul dorit:"))
        except ValueError:
            print("Va rugam introduceti un numar intreg!")

def run():
    print("1.Iterativ")
    print("2.Recursiv")
    while True:
        cmd=input("Introduceti comanda:")
        if cmd=="1":
            descIterativ(citireNumar())
        elif cmd=="2":
            descRecursiv([],citireNumar())
        else:
            print("Comanda invalida")