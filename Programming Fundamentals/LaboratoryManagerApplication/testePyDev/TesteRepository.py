'''
Created on Jan 7, 2018

@author: Scoican
'''
from repository.Repository import *
import unittest
from testePyDev.TesteStudent import TestClasaStudenti
from domain.Student import Student


class TestRepository(TestClasaStudenti):
    
    def setUp(self):
        TestClasaStudenti.setUp(self)
        self.numeFis="studenti.txt"
        self._repo=FileRepository(self.numeFis,Student.citesteLinie,Student.scrieLinie)
        TestClasaStudenti.testStudent(self)
        
    def tearDown(self):
        with open(self.numeFis,"w") as f:
            f.write("")
    
    def testAdd(self):
        student=Student(2,"gogu",3)
        self._repo.add(student)
        self.assertEqual(len(self._repo.getAll()),1)
        
    def testRem(self):
        student=Student(2,"gogu",3)
        self._repo.add(student)
        self.assertEqual(len(self._repo.getAll()),1)
        self._repo.rem(student)
        self.assertEqual(len(self._repo.getAll()),0)
    
    def testUpd(self):
        student=Student(2,"gogu",3)
        altStudent=Student(2,"gigel",3)
        self._repo.add(student)
        self._repo.upd(altStudent)
        self.assertEqual(student,altStudent)
        
if __name__=="__main__":
        unittest.main()
    