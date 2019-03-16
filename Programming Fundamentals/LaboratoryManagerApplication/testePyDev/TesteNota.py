'''
Created on Jan 7, 2018

@author: Scoican
'''
from domain.Nota import Nota
import unittest

class TestClasaNote(unittest.TestCase):
    
    def setUp(self):
        self._asignare=[1,[1,1]]
        self._nota=10
        
    def tearDown(self):
        pass
    
    def testNota(self):
        self._nota=Nota(self._asignare,self._nota)
        self._altaNota=Nota([1,[1,1]],10)
        self.assertEqual(self._nota, self._altaNota)
        
if __name__=="__main__":
    unittest.main()