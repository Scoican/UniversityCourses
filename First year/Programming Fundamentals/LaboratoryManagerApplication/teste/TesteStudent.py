'''
Created on 16 nov. 2017

@author: Scoican
'''

from domain.Student import Student

def testeStudent():
    '''
    Functei ce testeaza clasa Student
    '''
    st= Student(1,"Ion",217)
    assert st.getId()==1
    assert st.getNume()=="Ion"
    assert st.getGrup()==217
    st.setNume("Gelu")
    st.setGrup(218)
    assert st.getNume()=="Gelu"
    assert st.getGrup()==218