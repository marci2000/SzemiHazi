// Nev: Tasnadi Marton
// Azonosito: tmim1967
// Csoport: 534/1

package service;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServiceMockTest {

    @Mock
    StudentXMLRepository studentXMLRepository;

    @Mock
    HomeworkXMLRepository homeworkXMLRepository;

    @Mock
    GradeXMLRepository gradeXMLRepository;

    static public Service service;

    @BeforeEach
    public void config() {
        MockitoAnnotations.initMocks(this);
        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @Test
    public void saveStudent() {
        Student student = new Student("3", "Hanna", 531);
        when(studentXMLRepository.save(student)).thenReturn(null);
        int result = service.saveStudent(student.getID(), student.getName(), student.getGroup());
        assertEquals(1, result);
        verify(studentXMLRepository).save(any(Student.class));
    }

    @ParameterizedTest
    @ValueSource(strings = "1")
    void updateStudent(String id) {
        Student student = new Student(id, "Hanna", 531);
        when(studentXMLRepository.update(student)).thenReturn(null);
        int result = service.updateStudent(id, student.getName(), student.getGroup());
        assertEquals(0, result);
        verify(studentXMLRepository).update(any(Student.class));
    }

    @Test
    void deleteStudent() {
        when(studentXMLRepository.delete(any(String.class))).thenReturn(new Student("3", "Hanna", 531));
        int result = service.deleteStudent("2");
        assertEquals(1, result);
        verify(studentXMLRepository).delete(any(String.class));
    }
}
