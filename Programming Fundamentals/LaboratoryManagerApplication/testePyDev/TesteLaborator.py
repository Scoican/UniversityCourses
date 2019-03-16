'''
Created on Jan 7, 2018

@author: Scoican
'''
from domain.Laborator import Laborator
import unittest

class TestClasaLaboratoare(unittest.TestCase):
    
    def setUp(self):
        self._labProb=[1,1]
        self._desc="Descriere"
        self._dline=[1,1]
    
    def tearDown(self):
        pass
    
    def testLaborator(self):
        self._laborator=Laborator(self._labProb,self._desc,self._dline)
        self._altLaborator=Laborator([1,1],"Descriere",[1,1])
        self.assertEqual(self._altLaborator,self._laborator)
        self.assertEqual(self._laborator.getNrLab(),1)
        self.assertEqual(self._laborator.getNrProba(),1)
        self.assertEqual(self._laborator.getDesc(),"Descriere")
        self.assertEqual(self._laborator.getZiDeadline(),1)
        self.assertEqual(self._laborator.getLunaDeadline(),1)
        self._laborator.setDesc("Abecedar")
        self._laborator.setZiDeadline(2)
        self._laborator.setLunaDeadline(2)
        self.assertEqual(self._laborator.getDesc(),"Abecedar")
        self.assertEqual(self._laborator.getZiDeadline(),2)
        self.assertEqual(self._laborator.getLunaDeadline(),2)
        linieTest=self._laborator.citesteLinie("1_1;Abecedar;2/2")
        self.assertEqual(linieTest, self._laborator)
        self.assertEqual(self._laborator.scrieLinie(self._laborator),"1_1;Abecedar;2/2;")
        
if __name__=="__main__":
    unittest.main()