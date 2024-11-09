package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TestServiceImpl.class})
class TestServiceImplTest {

    @Autowired
    private TestServiceImpl testServiceImpl;

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private LocalizedIOService ioService;

    private static final Student TEST_STUDENT = new Student("Vasily", "Pupkin");

    @Test
    void testExecuteTest() {
        List<Question> emptyListOfQuestions = new ArrayList<>();
        doReturn(emptyListOfQuestions).when(questionDao).findAll();

        TestResult actualTestResult = testServiceImpl.executeTestFor(TEST_STUDENT);

        verify(questionDao, atMostOnce()).findAll();
        verify(ioService, times(2)).printLine(anyString());
        verify(ioService, atMostOnce()).printLineLocalized(anyString());
        verify(ioService, atMostOnce()).printFormattedLineLocalized(anyString(), anyInt(), anyString());
        assertTrue(actualTestResult.getAnsweredQuestions().isEmpty());
        assertEquals(actualTestResult.getRightAnswersCount(), 0);
    }
}