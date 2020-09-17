package com.jp.trc.testing.dao;

import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.LoginExistsException;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;
import com.jp.trc.testing.view.menu.Filter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 04.09.2020 10:22
 */
public class CSVUserDAO implements UserDAO {

    private String fileUsers = CSVFiles.CSV_USERS;
    private String fileAssignments = CSVFiles.CSV_ASSIGNMENTS;
    private String fileGroups = CSVFiles.CSV_GROUPS;

    /**
     * Creating a user in the database
     *
     * @param user The user to add to the database.
     * @throws LoginExistsException If user exist.
     */
    @Override
    public void addUser(User user) throws LoginExistsException, IOException {
        List<String> csvFile = readFile(fileUsers);
        int userId = 1;
        if (csvFile.size() > 1) {
            String[] lastLine = csvFile.get(csvFile.size() - 1).split(";");
            userId = Integer.parseInt(lastLine[0]) + 1;
        }

        if (userExist(user.getLogin())) {
            throw new LoginExistsException("Пользователь с таким логином уже существует!");
        }

        user.setId(userId);
        StringBuilder line = new StringBuilder();

        line.append("\n");
        line.append(user.getId()).append(";");
        line.append(user.getName()).append(";");
        line.append(user.getLogin()).append(";");
        line.append(user.getPassword()).append(";");
        line.append(user.getAge()).append(";");
        String groupId = user instanceof Student
                ? String.valueOf(((Student) user).getGroupId())
                : "";
        line.append(groupId).append(";");
        line.append(user.getClass().getSimpleName());

        writeStringToFile(fileUsers, line.toString());
    }

    /**
     * Get a user from the database by his id.
     *
     * @param userId User id.
     * @return User from database.
     */
    @Override
    public User getUser(int userId) {
        String line = "";
        try {
            FileInputStream stream = new FileInputStream (fileUsers);
            InputStreamReader isr = new InputStreamReader (stream);
            BufferedReader reader = new BufferedReader(isr);
            String lineUser = reader.readLine();
            while (lineUser != null) {
                if (lineUser.split(";")[0].equals(String.valueOf(userId))) {
                    line = lineUser;
                    break;
                }
                lineUser = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line.length() == 0) {
            throw new ObjectNotFoundException("User not found!");
        }

        String[] values = line.split(";");

        if (values[7].equals("Student")) {
            Student student = new Student(
                    Integer.parseInt(values[0]),
                    Integer.parseInt(values[5]),
                    values[1],
                    values[2],
                    values[3],
                    Integer.parseInt(values[4])
            );
            student.setRating(Double.parseDouble(values[6]));
            return student;
        }
        User resultUser = null;
        try {
            resultUser = (User) create(
                    Class.forName("com.jp.trc.testing.model.users." + values[7]),
                    Integer.parseInt(values[0]),
                    values[1],
                    values[2],
                    values[3],
                    Integer.parseInt(values[4])
            );
        } catch (ClassNotFoundException | InstantiationException
                | InvocationTargetException | NoSuchMethodException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resultUser;
    }

    /**
     * Get a group from the database by his id.
     * @param groupId Group id.
     * @return Group from database.
     */
    public Group getGroup(int groupId) {
        String line = "";
        List<String> lines = readFile(fileGroups);
        if (lines == null || lines.size() == 1) {
            throw new ObjectNotFoundException("File is empty!");
        }

        for (String lineUser : lines) {
            if (lineUser.split(";")[0].equals(String.valueOf(groupId))) {
                line = lineUser;
                break;
            }
        }
        if (line.length() == 0) {
            throw new ObjectNotFoundException("Group not found!");
        }

        String[] values = line.split(";");
        return new Group(Integer.parseInt(values[0]), values[1]);
    }

    /**
     * Updates the user in the database.
     *
     * @param user User.
     */
    @Override
    public void updateUser(User user) {
        try {
            String tempFilePath = "resources/.temp.csv";
            File tempFile = new File(tempFilePath);
            tempFile.createNewFile();

            File file = new File(fileUsers);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String lineUser = reader.readLine();
            while (lineUser != null) {
                String[] values = lineUser.split(";");

                if (values[0].equals(String.valueOf(user.getId()))) {
                    StringBuilder updateLine = new StringBuilder();

                    updateLine.append("\n");
                    updateLine.append(user.getId()).append(";");
                    updateLine.append(user.getName()).append(";");
                    updateLine.append(user.getLogin()).append(";");
                    updateLine.append(user.getPassword()).append(";");
                    updateLine.append(user.getAge()).append(";");
                    String groupId = user instanceof Student
                            ? String.valueOf(((Student) user).getGroupId())
                            : "";
                    updateLine.append(groupId).append(";");
                    String rating = user instanceof Student
                            ? String.valueOf(((Student) user).getRating())
                            : "0";
                    updateLine.append(rating).append(";");
                    updateLine.append(user.getClass().getSimpleName());

                    writeStringToFile(tempFilePath, updateLine.toString());
                } else {
                    writeStringToFile(tempFilePath, values[0].equals("id") ? lineUser : "\n" + lineUser);
                }
                lineUser = reader.readLine();
            }
            reader.close();

            file.delete();
            tempFile.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the user in the database.
     *
     * @param userId User id.
     */
    @Override
    public void deleteUser(int userId) {
        try {
            String tempFilePath = "resources/.temp.csv";
            File tempFile = new File(tempFilePath);
            tempFile.createNewFile();

            File file = new File(fileUsers);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(";");

                if (!values[0].equals(String.valueOf(userId))) {
                    writeStringToFile(tempFilePath, line);
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
     * Get all assignments.
     * @return List<Assignment> all tests assigned to students.
     */
    @Override
    public List<Assignment> getAssignments() {
        List<String> lines = readFile(fileAssignments);

        return (List<Assignment>) lines.stream()
                .skip(1)
                .map(line -> new Assignment(
                        Integer.parseInt(line.split(";")[0]),
                        Integer.parseInt(line.split(";")[1]),
                        line.split(";")[2].equals("null")
                                ? null
                                : Integer.parseInt(line.split(";")[2])
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get all groups.
     * @param filter Filter for paging, search and ordering.
     * @return List<Groups> all student groups.
     */
    @Override
    public List<Group> getGroups(Filter filter) {
        List<String> lines = readFile(fileGroups);

        if (filter.getLimit() == 0) {
            return (List<Group>) lines.stream()
                    .skip(1)
                    .map(line -> new Group(
                            Integer.parseInt(line.split(";")[0]),
                            line.split(";")[1]
                    ))
                    .sorted(filter.getComparator())
                    .collect(Collectors.toList());
        } else {
            return (List<Group>) lines.stream()
                    .skip(1)
                    .map(line -> new Group(
                            Integer.parseInt(line.split(";")[0]),
                            line.split(";")[1]
                    ))
                    .sorted(filter.getComparator())
                    .skip(filter.getOffset())
                    .limit(filter.getLimit())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Gets all users.
     *
     * @return List<User> All users from the database.
     */
    @Override
    public Map<String, User> getUsers() {
        HashMap<String, User> users = new HashMap<>();

        try {
            File file = new File(fileUsers);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String lineUser = reader.readLine();
            while (lineUser != null) {
                String[] values = lineUser.split(";");
                if (!values[0].equals("id")) {
                    users.put(values[2], getUser(Integer.parseInt(values[0])));
                }
                lineUser = reader.readLine();
            }
        } catch (IOException e) {
                e.printStackTrace();
            }

        if (users.size() == 0) {
            throw new ObjectNotFoundException("Users not found!");
        }

        return users;
    }

    /**
     * Deleting all users.
     */
    @Override
    public void deleteUsers() {
        try {
            FileInputStream stream = new FileInputStream ("resources/temp.csv");
            InputStreamReader isr = new InputStreamReader (stream);
            BufferedReader reader = new BufferedReader(isr);
            String lineUser = reader.readLine();
            clearFile(fileUsers);
            writeStringToFile(fileUsers, "id;name;login;password;age;groupId;type");
            while (lineUser != null) {
                if (lineUser.split(";")[6].equals("Admin")) {
                    writeStringToFile(fileUsers, lineUser);
                }
                lineUser = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Does such a user exist in the database.
     * @param login Login to check.
     * @return true if login is finded.
     */
    public boolean userExist(String login) {
        try {
            FileInputStream stream = new FileInputStream (fileUsers);
            InputStreamReader isr = new InputStreamReader (stream);
            BufferedReader reader = new BufferedReader(isr);
            String lineUser = reader.readLine();
            while (lineUser != null) {
                if (lineUser.split(";")[2].equals(login)) {
                    return true;
                }
                lineUser = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            FileInputStream stream = new FileInputStream (filePath);
            InputStreamReader isr = new InputStreamReader (stream, "UTF8");
            BufferedReader reader = new BufferedReader(isr);
            String lineUser = reader.readLine();
            while (lineUser != null) {
                lines.add(lineUser);
                lineUser = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lines == null || lines.size() == 1) {
            throw new ObjectNotFoundException("File is empty!");
        }
        return lines;
    }

    private static void clearFile(String filePath) throws IOException {
        OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(filePath));
        file.write("");
        file.flush();
        file.close();
    }

    private static void writeStringToFile(String filePath, String line) throws IOException {
        OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(filePath, true));
        file.write(line);
        file.flush();
        file.close();
    }

    private static void writeObjectToFile(String filePath, User user) throws IOException {
        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filePath, true));
        file.writeObject(user);
        file.flush();
        file.close();
    }

    private static <T> T create(Class<T> clazz, int id, String name, String login,
                                                String password, int age) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T result = clazz.getConstructor(
                int.class,
                String.class,
                String.class,
                String.class,
                int.class
        ).newInstance(id, name, login, password, age);

        return result;
    }
}
