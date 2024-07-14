package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    private TestServiceImpl testServiceImpl;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @BeforeEach
    public void init() {
        testServiceImpl = new TestServiceImpl(questionDao, ioService);
    }

    @Test
    void testExecuteTest() {
        verify(questionDao, atMostOnce()).findAll();
        verifyNoInteractions(ioService);
    }
}
