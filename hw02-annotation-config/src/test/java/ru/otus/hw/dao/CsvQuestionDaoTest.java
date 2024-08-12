package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
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

        List<Question> expectedQuestions = prepareQuestions();


        assertNotNull(actualQuestions);
        assertEquals(actualQuestions.size(), 3);
        assertEquals(actualQuestions, expectedQuestions);
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

    private List<Question> prepareQuestions() {
        return List.of(
                new Question("Is there life on Mars?",
                        List.of(
                                new Answer("Science doesn't know this yet", true),
                                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                                new Answer("Absolutely not", false))),
                new Question("How should resources be loaded form jar in Java?",
                        List.of(
                                new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                                new Answer("ClassLoader#geResource#getFile + FileReader", false),
                                new Answer("Wingardium Leviosa", false)
                        )),
                new Question("Which option is a good way to handle the exception?",
                        List.of(
                                new Answer("@SneakyThrow", false),
                                new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true),
                                new Answer("Ignoring exception", false)
                        )));
    }
}
