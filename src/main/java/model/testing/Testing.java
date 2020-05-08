package model.testing;

import model.users.Student;
import model.users.Teacher;

import java.util.List;

public class Testing {
    private int id;
    private String title;
    private List<Question> questions;
    private Teacher author;
    private List<Student> students;

    public Testing(int id, String title, List<Question> questions, Teacher author) {
        this.id = id;
        this.title = title;
        this.questions = questions;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Teacher getAuthor() {
        return author;
    }

    public void setAuthor(Teacher author) {
        this.author = author;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return String.format("%-20s\t|\t%-20s", title, author.getName());
    }
}
