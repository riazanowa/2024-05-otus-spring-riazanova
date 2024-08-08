package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (int i = 0; i < questions.size(); i++) {
            ioService.printFormattedLine("Question %d: %s%n", i + 1, questions.get(i).text());
            List<Answer> answers = questions.get(i).answers();
            for (int j = 0; j < 3; j++) {
                ioService.printFormattedLine("%d. %s%n", j + 1, answers.get(j).text());
            }
            int answer = ioService.readIntForRangeWithPrompt(1, 3, "Write a number of the answer.",
                    "Entered number is out of range.");
            var isAnswerValid = answers.get(answer - 1).isCorrect();
            testResult.applyAnswer(questions.get(i), isAnswerValid);
        }
        return testResult;
    }
}
