package com.jp.trc.testing.dao;

import com.jp.trc.testing.model.tests.*;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 13.09.2020 10:43
 */
public class CSVTestDAO implements TestDAO {

    private String fileTests = CSVFiles.CSV_TESTS;
    private String fileQuestions = CSVFiles.CSV_QUESTIONS;
    private String fileAnswers = CSVFiles.CSV_ANSWERS;
    private String fileAssignments = CSVFiles.CSV_ASSIGNMENTS;
    private String fileAnswerToQuestions = CSVFiles.CSV_ANSWER_TO_QUESTIONS;

    private CSVUserDAO userDAO = new CSVUserDAO();

    /**
     * Get list all tests.
     *
     * @return List<Test> all tests.
     */
    @Override
    public List<Test> getTests() {
        List<String> lines = readFile(fileTests);
        return lines.stream().skip(1).map(line -> {
                    String[] values = line.split(";");
                    return new Test(
                            Integer.parseInt(values[0]),
                            values[1],
                            (Teacher) userDAO.getUser(Integer.parseInt(values[2])),
                            Integer.parseInt(values[3])
                    );
                }
        ).collect(Collectors.toList());
    }

    /**
     * Get list questions of all tests.
     *
     * @return List<Question> questions of all tests.
     */
    @Override
    public List<Question> getQuestions() {
        List<String> lines = readFile(fileQuestions);
        return lines.stream().skip(1).map(line -> {
                    String[] values = line.split(";");
                    return new Question(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[1]),
                            values[2]
                    );
                }
        ).collect(Collectors.toList());
    }

    /**
     * Get list all answer options on all tests.
     *
     * @return List<Answer> all answer options on all tests.
     */
    @Override
    public List<Answer> getAnswers() {
        List<String> lines = readFile(fileAnswers);
        return lines.stream().skip(1).map(line -> {
                    String[] values = line.split(";");
                    return new Answer(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[1]),
                            values[2],
                            Boolean.parseBoolean(values[3])
                    );
                }
        ).collect(Collectors.toList());
    }

    /**
     * Get the student's attitude to the test.
     *
     * @param studentId Student id who is taking the test.
     * @param testId    Test id of the test the student is taking.
     * @return Assignment the student's to the test.
     */
    @Override
    public Assignment getAssignment(int studentId, int testId) {
        List<String> lines = readFile(fileAssignments);
        for (String line : lines) {
            String[] values = line.split(";");
            if (values[0].equals(String.valueOf(studentId)) && values[1].equals(String.valueOf(testId))) {
                return new Assignment(
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        values[2].equals("null") ? null : Integer.parseInt(values[2])
                );
            }
        }
        throw new ObjectNotFoundException("Assignment not found!");
    }

    /**
     * Update test results record.
     *
     * @param assignment
     */
    @Override
    public void updateAssignment(Assignment assignment) {
        try {
            String tempFilePath = "resources/.temp.csv";
            File tempFile = new File(tempFilePath);
            tempFile.createNewFile();

            File file = new File(fileAssignments);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(";");

                if (values[0].equals(String.valueOf(assignment.getStudentId()))
                        && values[1].equals(String.valueOf(assignment.getTestId()))) {
                            StringBuilder tempLine = new StringBuilder();
                            tempLine.append("\n");
                            tempLine.append(assignment.getStudentId()).append(";");
                            tempLine.append(assignment.getTestId()).append(";");
                            tempLine.append(assignment.getResult());
                            writeStringToFile(tempFilePath, tempLine.toString());
                } else {
                    writeStringToFile(tempFilePath, values[0].equals("studentId") ? line : "\n" + line);
                }

                line = reader.readLine();
            }
            reader.close();

            file.delete();
            tempFile.renameTo(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a test by to his id.
     *
     * @param testId
     * @return
     */
    @Override
    public Test getTest(int testId) {
        try {
            FileInputStream stream = new FileInputStream (fileTests);
            InputStreamReader isr = new InputStreamReader (stream, "UTF8");
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(";");
                if (values[0].equals(String.valueOf(testId))) {
                    return new Test(
                            Integer.parseInt(values[0]),
                            String.valueOf(values[1]),
                            (Teacher) userDAO.getUser(Integer.parseInt(values[2])),
                            Integer.parseInt(values[3])
                    );
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ObjectNotFoundException("Such test not found!!!");
    }

    /**
     * Get an answer option by its id.
     *
     * @param answerId
     * @return
     * @throws ObjectNotFoundException Exception is thrown if sought-for answer not found.
     */
    @Override
    public Answer getAnswer(int answerId) throws ObjectNotFoundException {
        try {
            File file = new File(fileAnswers);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(";");
                if (values[0].equals(String.valueOf(answerId))) {
                    return new Answer(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[1]),
                            values[2],
                            Boolean.parseBoolean(values[3])
                    );
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ObjectNotFoundException("Such answer not found!!!");
    }

    /**
     * Remembers the student's answer to a question during the test.
     *
     * @param answerToQuestion student's answer to a question
     * @return true if the entry was successfully added.
     */
    @Override
    public boolean addAnswerToQuestion(AnswerToQuestion answerToQuestion) {
        StringBuilder line = new StringBuilder();
        line.append("\n");
        line.append(answerToQuestion.getStudentId()).append(";");
        line.append(answerToQuestion.getAnswerId());

        try {
            writeStringToFile(fileAnswerToQuestions, line.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Get all answers to all students' questions.
     *
     * @return Set<AnswerToQuestion> all answers to all students' questions.
     */
    @Override
    public Set<AnswerToQuestion> getAnswerToQuestions() {
        List<String> lines = readFile(fileAnswerToQuestions);
        Set<AnswerToQuestion> resultSet = new HashSet<>();
        resultSet.addAll(
                lines.stream().skip(1).map(line -> {
                            String[] values = line.split(";");
                            return new AnswerToQuestion(
                                    Integer.parseInt(values[0]),
                                    Integer.parseInt(values[1])
                            );
                        }
                ).collect(Collectors.toList())
        );
        return resultSet;
    }

    /**
     * Deletes student answers to the question that he put earlier.
     *
     * @param studentId  Student id whose answers need to delete.
     * @param questionId Question id answers to be deleted.
     */
    @Override
    public void clearAnswerToQuestion(int studentId, int questionId) {
        try {
            String tempFilePath = "resources/.temp.csv";
            File tempFile = new File(tempFilePath);
            tempFile.createNewFile();

            File file = new File(fileAnswerToQuestions);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(";");

                if (!(values[0].equals(String.valueOf(studentId))
                        && getAnswer(Integer.parseInt(values[1])).getQuestionId() == questionId)) {
                    writeStringToFile(tempFilePath, values[0].equals("studentId") ? line : "\n" + line);
                }
                line = reader.readLine();
            }
            reader.close();

            file.delete();
            tempFile.renameTo(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeStringToFile(String filePath, String line) throws IOException {
        OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(filePath, true));
        file.write(line);
        file.flush();
        file.close();
    }

    private List<String> readFile(String filePath) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines == null || lines.size() == 1) {
            throw new ObjectNotFoundException("File is empty!");
        }
        return lines;
    }
}
