'''
Created on 12 nov. 2017

@author: Scoican
'''

class RepositoryException(Exception):
    '''
    Clasa ce ajuta la afisarea de erori
    '''
    pass

class Repository:
    '''
    Clasa ce efectueaza operatii CRUD
    '''
    def __init__(self):
        self._elems = []
        
    def add(self,elem):
        '''
        Metoda ce adauga un element la o lista
        Input:elem-obiect
        '''
        if elem in self._elems:
            raise RepositoryException("Elementul a fost deja adaugat")
        self._elems.append(elem)
        
    def rem(self,elem):
        '''
        Metoda ce sterge un element din lista
        Input:elem-obiect
        '''
        if elem not in self._elems:
            raise RepositoryException("Elementul nu se afla in lista")
        self._elems.remove(elem)
    
    def upd(self,elem):
        '''
        Metoda ce modifica un element din lista
        Input:elem-obiect
        '''
        if elem not in self._elems:
            raise RepositoryException("Elementul nu se afla in lista")
        idx = self._elems.index(elem) 
        self._elems[idx]=elem

    def find(self,elem):
        '''
        Metoda ce gaseste un element din lista
        Input:elem-obiect
        '''
        if elem not in self._elems:
            raise RepositoryException("Elementul nu se afla in lista")
        idx = self._elems.index(elem) 
        return self._elems[idx]
    
    def __len__(self):
        return len(self._elems)
    
    def getAll(self):
        return self._elems[:]
    
    
    
    
class FileRepository(Repository):
    
    def __citireFisier(self):
        """
        Functie ce citeste din fisier
        """
        with open(self.numeFis,"r") as file:
            for line in file.readlines():
                line=line.strip()
                if len(line)>0:
                    obj=self.__citesteLinie(line)
                    self._elems.append(obj)

    
    def __init__(self,numeFis,_linie_citita,_linie_scrisa):
        self.__citesteLinie = _linie_citita
        self.__scrieLinie = _linie_scrisa
        Repository.__init__(self)
        self.numeFis = numeFis
        self.__citireFisier()
        
    def __scriereFisier(self):
        """
        Functie ce scrie in fisier
        """
        with open(self.numeFis,"w") as file:
            for obj in self._elems:
                line=self.__scrieLinie(obj)
                file.write(line+"\n")

    
    def __adaugareElemFisier(self,elem):
        with open(self.numeFis,"a") as f:
            line=self.__scrieLinie(elem)
            f.write(line+"\n")
    
    def add(self,elem):
        """
        adaugare in fisier
        """
        Repository.add(self, elem)
        self.__adaugareElemFisier(elem)
    
    def rem(self,elem):
        """
        stergere element din fisier
        """
        Repository.rem(self,elem)
        self.__scriereFisier()
        
    def upd(self,elem):
        """
        update in fisier
        """
        Repository.upd(self, elem)
        self.__scriereFisier()     
        
    def find(self,elem):
        """
        cautare in fisier
        """
        return Repository.find(self, elem)
        
    def updFile(self):
        self.__scriereFisier()