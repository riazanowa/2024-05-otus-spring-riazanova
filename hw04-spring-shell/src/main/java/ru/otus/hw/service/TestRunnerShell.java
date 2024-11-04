package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.domain.Student;

@ShellComponent(value = "Test Runner Commands")
@RequiredArgsConstructor
public class TestRunnerShell {

    private final LocalizedIOService ioService;
    private final TestRunnerService testRunnerService;
    private Student student;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(String firstName, String lastName) {
        this.student = new Student(firstName, lastName);
        return ioService.getMessage("TestRunnerShell.welcome.message", firstName, lastName);
    }

    @ShellMethod(value = "Execute test", key = {"start", "test"})
    @ShellMethodAvailability(value = "isNameAvailable")
    public void executeTest() {
        testRunnerService.run(student);
    }

    private Availability isNameAvailable() {
        return student == null ?
                Availability.unavailable(ioService.getMessage("TestRunnerShell.reason"))
                : Availability.available();
    }
}
