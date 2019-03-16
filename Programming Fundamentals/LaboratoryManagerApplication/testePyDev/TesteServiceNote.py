'''
Created on Jan 7, 2018

@author: Scoican
'''
import unittest
from domain.Nota import Nota
from validare.Validator import *
from repository.Repository import *
from Controller.NoteService import *


class TesteServiceStudenti(unittest.TestCase):
    
    def setUp(self):
        self._noteServ=NoteController(Repository())
                
    def testAddAsig(self):
        self._noteServ.adaugareAsignare(1,[1,1])
        self._noteServ.adaugareAsignare(2,[1,1])
        self.assertEqual(len(self._noteServ.getNote()),2)
        
    def testDelete(self):
        self._noteServ.adaugareAsignare(1,[1,1])
        self._noteServ.adaugareAsignare(2,[1,1])
        self.assertEqual(len(self._noteServ.getNote()),2)
        self._noteServ.deleteNota(2,[1,1])
        self.assertEqual(len(self._noteServ.getNote()),1)
    
    def testProblemeleStudentului(self):
        self._noteServ.adaugareAsignare(1,[1,1])
        self._noteServ.adaugareAsignare(1,[1,2])
        self._noteServ.adaugareAsignare(1,[1,3])
        self._noteServ.adaugareAsignare(1,[1,4])
        self.assertEqual(len(self._noteServ.problemeleStudentului(1,self._noteServ.getNote())),4)
    
    def testCheckId(self):
        self.assertTrue(self._noteServ.checkId(1)==0)   
       
if __name__=="__main__":
    unittest.main()