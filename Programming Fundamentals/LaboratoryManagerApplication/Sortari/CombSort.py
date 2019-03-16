'''
Created on Jan 11, 2018

@author: Scoican
'''
def gapCalculator(gap):
 
    gap = (gap * 10)/13
    if gap < 1:
        return 1
    return int(gap)

def combSort(lista,key=lambda x:x,reverse=False):
    lungime=len(lista)
    gap=lungime
    while gap!=1:
        gap=gapCalculator(gap)
        if reverse==False:
            for i in range(0,lungime-gap):
                if key(lista[i])>key(lista[i+gap]):
                    lista[i],lista[i+gap]=lista[i+gap],lista[i]
        else:
            for i in range(0,lungime-gap):
                if key(lista[i])<key(lista[i+gap]):
                    lista[i],lista[i+gap]=lista[i+gap],lista[i]
    return lista