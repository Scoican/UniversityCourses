'''
Created on Jan 7, 2018

@author: Scoican
'''
from domain.Student import Student
import unittest

class TestClasaStudenti(unittest.TestCase):
    
    def setUp(self):
        self._id = 1
        self._nume= "Gigel"
        self._grupa = 217
        self._altId = 2
        self._altNume = "Aigel"
        self._altaGrupa = 218
    
    def tearDown(self):
        pass
    
    def testStudent(self):
        self._student=Student(self._id,self._nume,self._grupa)
        self._altStudent=Student(1,"Gigel",217)
        self.assertEqual(self._altStudent,self._student)
        
if __name__=="__main__":
    unittest.main()