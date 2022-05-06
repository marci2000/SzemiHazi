// Nev: Tasnadi Marton
// Azonosito: tmim1967
// Csoport: 534/1

package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.Assert.*;

class ServiceTest {
    Service service;

    @BeforeEach
    void setup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        service.saveStudent("18", "Hanna", 531);

    }

    @Test
    void saveStudent() {
        Iterable<Student> allStudents = service.findAllStudents();
        int nrOfStudentsBefore = 0;
        int maxID = 0;
        for (Student i : allStudents) {
            nrOfStudentsBefore++;
            if (Integer.parseInt(i.getID()) > maxID) {
                maxID = Integer.parseInt(i.getID());
            }
        }
        maxID++;
        service.saveStudent(String.valueOf(maxID), "Hanna", 531);
        allStudents = service.findAllStudents();
        int nrOfStudentsAfter = 0;
        for (Student i : allStudents) {
            nrOfStudentsAfter++;
        }
        assertEquals(nrOfStudentsAfter, nrOfStudentsBefore+1);
    }

    @ParameterizedTest
    @ValueSource(strings = "18")
    void updateStudent(String id) {
        int result = service.updateStudent(id, "Uj nev", 532);
        assertTrue("Unsuccessful update.", result == 1);
    }

    @Test
    void deleteStudent() {
        Iterable<Student> allStudents = service.findAllStudents();
        int nrOfStudentsBefore = 0;
        int maxID = 0;
        for (Student i : allStudents) {
            nrOfStudentsBefore++;
            if (Integer.parseInt(i.getID()) > maxID) {
                maxID = Integer.parseInt(i.getID());
            }
        }

        service.deleteStudent(String.valueOf(maxID));

        allStudents = service.findAllStudents();
        int nrOfStudentsAfter = 0;
        for (Student i : allStudents) {
            nrOfStudentsAfter++;
        }

        assertEquals(nrOfStudentsAfter, nrOfStudentsBefore-1);
    }

    @Test
    void deleteHomework() {
        Iterable<Homework> allHomeworks = service.findAllHomework();
        int nrOfHomeworksBefore = 0;
        int maxID = 0;
        for (Homework i : allHomeworks) {
            nrOfHomeworksBefore++;
            if (Integer.parseInt(i.getID()) > maxID) {
                maxID = Integer.parseInt(i.getID());
            }
        }

        service.deleteHomework(String.valueOf(maxID));

        allHomeworks = service.findAllHomework();
        int nrOfHomeworksAfter = 0;
        for (Homework i : allHomeworks) {
            nrOfHomeworksAfter++;
        }

        assertNotEquals(nrOfHomeworksAfter, nrOfHomeworksBefore);
    }
}