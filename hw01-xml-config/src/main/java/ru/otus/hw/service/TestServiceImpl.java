package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDao;

    private final IOService ioService;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = questionDao.findAll();

        for (int i = 0; i < questions.size(); i++) {
            ioService.printFormattedLine("Question %d: %s%n", i + 1, questions.get(i).text());
            List<Answer> answers = questions.get(i).answers();
            for (int j = 0; j < 3; j++) {
                ioService.printFormattedLine("%d. %s%n", j + 1, answers.get(j).text());
            }
        }

    }
}
