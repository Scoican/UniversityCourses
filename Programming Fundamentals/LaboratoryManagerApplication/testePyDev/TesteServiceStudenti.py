'''
Created on Jan 7, 2018

@author: Scoican
'''
import unittest
from domain.Student import Student
from validare.Validator import *
from repository.Repository import *
from Controller.StudentService import *


class TesteServiceStudenti(unittest.TestCase):
    
    def setUp(self):
        self._studServ=StudentController(Repository())
                
    def testAdd(self):
        self._studServ.adaugaStudent(1, "gelu", 2)
        self._studServ.adaugaStudent(2, "gelu", 2)
        self.assertEqual(len(self._studServ.getStudenti()),2)
        
    def testRem(self):
        self._studServ.adaugaStudent(1, "gelu", 2)
        self._studServ.adaugaStudent(2, "gelu", 2)
        st=Student(1,"test",11)
        self._studServ.deleteStudent(st)
        self.assertEqual(len(self._studServ.getStudenti()),1)
    
    def testUpd(self):
        st=Student(1, "gelu", 2)
        self._studServ.adaugaStudent(1, "gelu", 2)
        self._studServ.updateStudent(1,"test",11)
        self.assertEqual(st,Student(1,"test",11))
    
    def testCheckId(self):
        self.assertTrue(self._studServ.checkId(1)==0)  
        
if __name__=="__main__":
    unittest.main()
