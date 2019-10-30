'''
Created on Jan 7, 2018

@author: Scoican
'''
import unittest
from domain.Laborator import Laborator
from validare.Validator import *
from repository.Repository import *
from Controller.LaboratorService import *


class TesteServiceStudenti(unittest.TestCase):
    
    def setUp(self):
        self._labServ=LaboratorController(Repository())
                
    def testAdd(self):
        self._labServ.adaugaLaborator([1,1],"prob",[1,1])
        self._labServ.adaugaLaborator([1,2],"prob",[1,1])
        self.assertEqual(len(self._labServ.getLaborator()),2)
        
    def testRem(self):
        self._labServ.adaugaLaborator([1,1],"prob",[1,1])
        self._labServ.adaugaLaborator([1,2],"prob",[1,1])
        lab=Laborator([1,1],"prob",[1,1])
        self._labServ.deleteLaborator(lab)
        self.assertEqual(len(self._labServ.getLaborator()),1)
    
    def testUpd(self):
        lab=Laborator([1,1],"prob",[1,1])
        self._labServ.adaugaLaborator([1,1],"prob",[1,1])
        self._labServ.updateLaborator([1,1],"prob123",[1,1])
        self.assertEqual(lab,Laborator([1,1],"prob123",[1,1]))
    
    def testCheckId(self):
        self.assertTrue(self._labServ.checkLabProb([1,1])==0)
          
if __name__=="__main__":
    unittest.main()
