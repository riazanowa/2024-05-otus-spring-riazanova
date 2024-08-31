package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (int i = 0; i < questions.size(); i++) {
            ioService.printFormattedLineLocalized("TestService.question.number", i + 1,
                    questions.get(i).text());
            List<Answer> answers = questions.get(i).answers();
            for (int j = 0; j < 3; j++) {
                ioService.printFormattedLine("%d. %s%n", j + 1, answers.get(j).text());
            }
            int answer = ioService.readIntForRangeWithPromptLocalized(1, 3,
                    "TestService.write.answer", "TestService.error.message");
            var isAnswerValid = answers.get(answer - 1).isCorrect();
            testResult.applyAnswer(questions.get(i), isAnswerValid);
        }

        return testResult;
    }

}
