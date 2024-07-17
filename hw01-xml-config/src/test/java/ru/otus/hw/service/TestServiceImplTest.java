package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @InjectMocks
    private TestServiceImpl testServiceImpl;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @Test
    void testExecuteTest() {
        List<Question> emptyListOfQuestions = new ArrayList<>();
        doReturn(emptyListOfQuestions).when(questionDao).findAll();

        testServiceImpl.executeTest();

        verify(questionDao, atMostOnce()).findAll();
        verify(ioService, atMostOnce()).printLine(anyString());
        verify(ioService, atMostOnce()).printFormattedLine(anyString());
    }
}
