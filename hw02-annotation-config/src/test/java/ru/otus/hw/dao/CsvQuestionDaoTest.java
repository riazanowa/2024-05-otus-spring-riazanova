package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {

    public static final String FILE_NAME = "questions.csv";

    public static final String WRONG_FILE_NAME = "non-existent.csv";

    @InjectMocks
    private CsvQuestionDao dao;

    @Mock
    private TestFileNameProvider fileNameProvider;

    @Test
    public void testFindAll_whenTestFileIsCorrect_ReturnListOfQuestions() {
        when(fileNameProvider.getTestFileName()).thenReturn(FILE_NAME);

        List<Question> actualQuestions = dao.findAll();

        assertNotNull(actualQuestions);
        assertEquals(actualQuestions.size(), 3);
    }

    @Test
    public void testFindAll_whenTestFileIsNotCorrect_ExceptionThrown() {
        when(fileNameProvider.getTestFileName()).thenReturn(WRONG_FILE_NAME);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.findAll();
        });

        String expectedMessage = "File not found: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
