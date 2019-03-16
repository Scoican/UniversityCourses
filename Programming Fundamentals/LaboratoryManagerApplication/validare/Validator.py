'''
Created on 12 nov. 2017

@author: Scoican
'''
class ValidationException(Exception):
    pass

class Validator:
    '''
    Clasa ce ajuta la validarea datelor din alte clase
    '''
    
    @staticmethod
    def valideazaId(student):
        '''
        Metoda ce valideaza id-ul unui student
        input:student-obiect Student cu un id(numar intreg)
        '''
        erori=""
        if student.getId()<0:
            erori+="Student invalid \n"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod        
    def valideazaStudent(student):
        '''
        Metoda ce valideaza toate datele unui obiect student
        input:student-obiect Student cu un id(numar intreg),nume(string),grupa(numar intreg)
        '''
        erori=""
        if student.getId()<0:
            erori+="Student invalid \n"
        if student.getNume()==""or student.getNume()==" ":
            erori+="Nume invalid \n"
        if student.getGrup()<0:
            erori+="Grupa invalida \n"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaLabProb(laborator):
        '''
        Metoda ce valideaza id-ul unui laborator
        input:laborator:obiect Laboator cu un labProb(lista numere intregi)
        '''
        erori=""
        if laborator.getNrLab()<=0:
            erori+="Laborator invalid \n"
        if laborator.getNrProba()<=0:
            erori+="Problema invalida \n"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod    
    def valideazaLaborator(laborator):
        '''
        Metoda ce valideaza toate datele unui obiect Laborator
        input:laborator:obiect Laborator cu un labProb(lista numere intregi),desc(string),deadline(lista numere intregi)
        '''
        erori=""
        if laborator.getNrLab()<=0:
            erori+="Laborator invalid \n"
        if laborator.getNrProba()<=0:
            erori+="Problema invalida \n"
        if laborator.getDesc()=="" or laborator.getDesc()==" ":
            erori+="Descriere invalida \n"
        if laborator.getZiDeadline()<1 or laborator.getZiDeadline()>31:
            erori+="Zi invalida \n"
        if laborator.getLunaDeadline()<1 or laborator.getLunaDeadline()>12:
            erori+="Luna invalida \n"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaAsignare(nota):
        '''
        Metoda ce valideaza toate datele unui obiect Nota necesare pentru asignare de problema
        input:nota:obiect Nota cu un idStud(numar intreg),idLab(numar intreg),idProb(numar intreg)
        '''
        erori=""
        if nota.getIdStud()<=0:
            erori+="Id student invalid! \n"
        if nota.getIdLab()<=0:
            erori+="Laborator invalid! \n"
        if nota.getIdProb()<=0:
            erori+="Problema invalida! \n"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaInexistentaStudent(st):
        '''
        Metoda ce valideaza daca un student exista sau nu in listele programului
        input:St-numar intreg
        '''
        erori=""
        if st==0:
            erori+="Studentul nu exista!"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod    
    def valideazaExistentaStudent(st):
        '''
        Metoda ce valideaza daca un student exista sau nu in listele programului
        input:St-numar intreg
        '''
        erori=""
        if st==1:
            erori+="Studentul este deja in lista"
        if len(erori)>0:
            raise ValidationException(erori)    
        
    @staticmethod
    def valideazaInexistentaProblema(lab):
        '''
        Metoda ce valideaza daca un laborator exista sau nu in listele programului
        input:lab-numar intreg
        '''
        erori=""
        if lab==0:
            erori+="Problema nu exista!"
        if len(erori)>0:
            raise ValidationException(erori)
    
    @staticmethod
    def valideazaExistentaProblema(lab):
        '''
        Metoda ce valideaza daca un laborator exista sau nu in listele programului
        input:lab-numar intreg
        '''
        erori=""
        if lab==1:
            erori+="Problema este deja in lista"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaStudentAsignat(st):
        '''
        Metoda ce valideaza daca un student are deja o problema asignata
        Input:st-numar intreg
        '''
        erori=""
        if st==1:
            erori+="Studentul are deja o problema asignata!"
        if len(erori)>0:
            raise ValidationException(erori)
    
    @staticmethod
    def valideazaLungimeAsignari(asig):
        '''
        Metoda ce valideaza daca au avut loc sau nu asignari
        Input:asig-numar intreg
        '''
        erori=""
        if asig==0:
            erori+="Nu au avut loc asignari de probleme"
        if len(erori)>0:
            raise ValidationException(erori)
    
    @staticmethod
    def valideazaLungimeStudenti(st):
        '''
        Metoda ce valideaza daca exista sau nu studenti
        input:st-numar intreg
        '''
        erori=""
        if st==0:
            erori+="Nu exista studenti in lista"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaLungimeProbleme(lab):
        '''
        Metoda ce valideaza daca exista sau nu laboratoare
        input:lab-numar intreg
        '''
        erori=""
        if lab==0:
            erori+="Nu exista probleme in lista"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaLungimeStudentiNotati(lista):
        '''
        Metoda ce valideaza daca exista sau nu studenti ce au note
        input:lista-numar intreg
        '''
        erori=""
        if lista==0:
            erori+="Nu exista studenti ce au primit note"
        if len(erori)>0:
            raise ValidationException(erori)
        
    @staticmethod
    def valideazaNota(nota):
        '''
        Metoda ce valideaza daca o nota este corecta
        input:nota-numar intreg
        '''
        erori=""
        if nota<1 or nota>10:
            erori+="Nota invalida"
        if len(erori)>0:
            raise ValidationException(erori)

        
    
    
        