'''
Created on Jan 11, 2018

@author: Scoican
'''
def testPrim(numar):
    if numar>1:
        for contor in range(2,numar):
            if numar%contor==0:
                return 0
        return 1
    else:
       return 0

def listaPrima(numar):
    lista=[]
    for contor in range(2,numar):
        if testPrim(contor)==1:
            lista.append(contor)
    return lista