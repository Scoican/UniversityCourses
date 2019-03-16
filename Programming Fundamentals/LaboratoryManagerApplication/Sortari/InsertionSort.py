def insertion(lista,key=None,reverse=False):
    
    if key==None:
        key=lambda x:x
    
    for i in range(1,len(lista)):
        poz = i-1
        elem = lista[i]
        if reverse==False:
            while poz>=0 and key(elem)<key(lista[poz]):
                lista[poz+1] = lista[poz]
                poz = poz-1
            lista[poz+1] = elem
        else:
            while poz>=0 and key(elem)>key(lista[poz]):
                lista[poz+1] = lista[poz]
                poz = poz-1
            lista[poz+1] = elem
    return lista