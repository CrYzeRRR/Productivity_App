import java.awt.*;
import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class MainMenuController implements Initializable {
    private int menuIndex = 0;

    @FXML
    private Button menuButton, accountButton;
    @FXML
    private Button homeButton;
    @FXML
    private Pane menuPane, accountPane;
    @FXML
    private Pane splashPane;
    @FXML
    private ProgressBar loadingSplashBar;
    @FXML
    private Label splashQuoteLabel;
    @FXML
    private Pane mainMenuCenterPane;
    @FXML
    private Pane tasksPane, addTaskPane, goalsPane, toDoPane, schedulePane, habitsPane, progressPane, quotesPane, settingsPane, infoPane, accountShowPane, signInPane;
    @FXML
    private Button tasksBtn, addTaskBtn, goalsBtn,toDoBtn,scheduleBtn,habitsBtn,progressBtn,quotesBtn, settingsBtn, infoBtn, accountShowButton, settingsAccButton, logOutButton;
    @FXML
    private TextField taskName;
    @FXML
    private DatePicker taskDate;
    @FXML
    private ColorPicker taskColor;

    // ####### ELEMENTS FOR TASKS PANE ########
    // Elements for the first task
    @FXML
    private Label task1Label, task1Date;
    @FXML
    private Pane task1Color;

    // Elements for the second task
    @FXML
    private Label task2Label, task2Date;
    @FXML
    private Pane task2Color;

    // Elements for the third task
    @FXML
    private Label task3Label, task3Date;
    @FXML
    private Pane task3Color;

    // Finish task buttons
    @FXML
    private Button finishTask1, finishTask2, finishTask3;

    // Next and previous tasks page
    @FXML
    private Button nextTaskPage, previousTaskPage;

    // Task number and page indicators
    @FXML
    private Label totalTaskLabel, pageTaskLabel;

    // ###### ELEMENTS FOR UNDONE HOME SCREEN #######
    @FXML
    private Label undoneTaskName1, undoneTaskName2, undoneTaskName3, undoneTaskDate1, undoneTaskDate2, undoneTaskDate3, undoneTasksLeft, taskReminderName, taskReminderDate, reminderLabel, dayQuote, dayQuoteAuthor;
    @FXML
    private Pane undoneTaskColor1, undoneTaskColor2, undoneTaskColor3;

    // ###### ELEMENTS FOR ACCOUNT PAGE ######
    @FXML
    private Label usernameLabel, fullNameLabel;
    @FXML
    private TextField usernameField, fullNameField;

    // ###### ELEMENTS FOR INFO PAGE #######
    @FXML
    private Button instagramBtn, linkedinBtn, twitterBtn;

    // Log In inputs
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;

    // Initializations for the quotes tab
    @FXML
    private Label quoteLabel, quoteAuthorLabel;

    // Stores actual username into the program
//    static final String fileName = "src/main/resources/config.properties";
//    Properties prop = new Properties();
    static final String configProp = "src/main/resources/config.properties";
    static Properties prop = new Properties();
    private String officialUsername = "UNKNOWN";
    private List<Task> taskObjList = new ArrayList<>();

    // List for storing "Account Pane"'s buttons
    List<Button> accountButtonsList = new ArrayList<>();

    // Hashmap for storing panes to corresponding buttons
    HashMap<Button, Pane> buttonsHm = new HashMap<Button, Pane>();

    // List for each task element
    List<Object>taskFields = new ArrayList<>();

    // List for Undone Tasks in home screen
    List<Object> undoneTaskFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (InputStream input = new FileInputStream(configProp)) {
            prop.load(input);
        } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("Err load: " + ex.getMessage());
    }

        // Initializing splash screen as soon as application launches
        splashPane.setVisible(true);
        final boolean[] flag = {true};
        final float[] barIndex = {0};

        // SPLASH SCREEN THREAD
        Thread splashScreen = new Thread(() -> {
            // Picking a random quote and displaying it on the splashscreen
            ArrayList<String> splashQuotes = new ArrayList<String>();
            Random random = new Random();
            // TODO : For more languages,
            // TODO : Resizeable
            /*
            TODO : Menu - Tasks, Schedule, Goals
            TODO : Dashboard - Just display info for every section, last 5 registers
            TODO : Acc - Logout ( Implementation )
            TODO : Site - App presentation, App requirements ( Java RE min. 1.8 installed ), App screenshots ( short descriptions ), Download, Contact ( Social Media, Email )
            TODO : Make app .jar to upload to Google Drive
             */


            // Adding a random quote on the splashscreen
            String splashQuoteString = randomQuotePicker.pick();
            List<String> characters = Arrays.asList("(", ")", "<", ">");
            for(String x : characters) {
                if(x.equals("<"))
                    splashQuoteString = splashQuoteString.replace(x, "\n-");
                else
                    splashQuoteString = splashQuoteString.replace(x, "");
            }
            splashQuoteLabel.setText("     "+ splashQuoteString);
            // Starting the while that updates the progress bar
            while (flag[0]) {
                try {
                    barIndex[0]++;
                    Thread.sleep(1500);
                    loadingSplashBar.setProgress(barIndex[0] / 3);
                    // If bar almost filled
                    if(barIndex[0] == 3) {
                        loadingSplashBar.setProgress(1);
                        Thread.sleep(1000);
                        flag[0] = false;
                        splashPane.setVisible(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }); splashScreen.start(); // Starting the lambda thread

        // Adding the quote of the day to main menu
        String quoteOfTheDay = randomQuotePicker.pick();
        String quoteText = quoteOfTheDay.substring(quoteOfTheDay.indexOf("<") + 1, quoteOfTheDay.indexOf(">"));
        dayQuoteAuthor.setText("- " + quoteText);
        String quoteAuth = quoteOfTheDay.substring(quoteOfTheDay.indexOf("(") + 1, quoteOfTheDay.indexOf(")"));
        dayQuote.setText("     " + quoteAuth);

        // Creating a hashmap which will later access the corresponding panes
        buttonsHm.put(tasksBtn, tasksPane);
        buttonsHm.put(goalsBtn, goalsPane);
        buttonsHm.put(toDoBtn, toDoPane);
        buttonsHm.put(scheduleBtn, schedulePane);
        buttonsHm.put(habitsBtn, habitsPane);
        buttonsHm.put(progressBtn, progressPane);
        buttonsHm.put(quotesBtn, quotesPane);
        buttonsHm.put(settingsBtn, settingsPane);
        buttonsHm.put(infoBtn, infoPane);
        buttonsHm.put(accountShowButton, accountShowPane);
        buttonsHm.put(settingsAccButton, settingsPane);
        buttonsHm.put(addTaskBtn, addTaskPane);

        // Picking a random quote from the file and inserting it in the quotes tab
        String quote = randomQuotePicker.pick();
        String quoteAuthorString = quote.substring(quote.indexOf("<") + 1, quote.indexOf(">"));
        quoteAuthorLabel.setText("- " + quoteAuthorString);
        String quoteFromString = quote.substring(quote.indexOf("(") + 1, quote.indexOf(")"));
        quoteLabel.setText("     " + quoteFromString);
        pastQuotes.add((randomQuotePicker.quotesList).indexOf(quote));
        quoteIndex++;

        // If user is not signed in, we only display the button "Sign In"
        accountButtonsList.add(accountShowButton);
        accountButtonsList.add(settingsAccButton);
        accountButtonsList.add(logOutButton);
        if (officialUsername.equals("UNKNOWN")) {
            for (Button b : accountButtonsList) {
                if (b == accountShowButton) {
                    b.setText("Sign in");
                    continue;
                }
                b.setVisible(false);
            }
        }

        // Adding all the fields that need to be changed to 'taskFields'
        taskFields.add(task1Label);
        taskFields.add(task1Date);
        taskFields.add(task1Color);
        taskFields.add(task2Label);
        taskFields.add(task2Date);
        taskFields.add(task2Color);
        taskFields.add(task3Label);
        taskFields.add(task3Date);
        taskFields.add(task3Color);

        // Adding all the fields from 'Undone Tasks'
        undoneTaskFields.add(undoneTaskName1);
        undoneTaskFields.add(undoneTaskDate1);
        undoneTaskFields.add(undoneTaskColor1);
        undoneTaskFields.add(undoneTaskName2);
        undoneTaskFields.add(undoneTaskDate2);
        undoneTaskFields.add(undoneTaskColor2);
        undoneTaskFields.add(undoneTaskName3);
        undoneTaskFields.add(undoneTaskDate3);
        undoneTaskFields.add(undoneTaskColor3);
    }


    // Whenever the home button is pressed
    @FXML
    private void setHomeScreen(ActionEvent event) {
        for(Pane pane : buttonsHm.values()) {
            pane.setVisible(false);
        }
        lastPane = null;
        mainMenuCenterPane.setVisible(true);
    }


    // When menu button clicked
    @FXML
    private void displayMenu(ActionEvent event) {
        Pane pane = null;
        if (event.getSource() == menuButton)
                pane = menuPane;
        else if (event.getSource() == accountButton)
                pane = accountPane;
        if (menuIndex % 2 == 0 || !pane.isVisible()) {
            pane.setVisible(true);
            menuIndex++;
        }
        else {
            pane.setVisible(false);
            menuIndex--;
        }
    }

    // Method for logging into account
    @FXML
    private void logIntoAccount(ActionEvent event) {
        String user = loginUsername.getText();
        String password = loginPassword.getText();
        // Getting user and password from config.properties
        if(user.equals(prop.getProperty("user_name")) && password.equals(prop.getProperty("password")) && officialUsername.equals("UNKNOWN")) {
            officialUsername = user;
            boolean flag = true;
            int taskIndex = 0;
            // Adding all the tasks according to user's account
            while(flag) {
                String task = "task" + taskIndex;
                taskIndex++;
                try {
                    String response = prop.getProperty(task);
                    System.out.println(response);
                    // In case it finds a recently deleted task, it doesn't stop, it continues to the next iteration
                    if(response.equals(".")) {
                        continue;
                    }
                    // Stopping point, when the next task's content is null
                    if (response == null) { ;
                        flag = false;
                    }
                    String taskName = response.substring(response.indexOf("<") + 1, response.indexOf(">"));
                    String taskDate = response.substring(response.indexOf("[") + 1, response.indexOf("]"));
                    String taskColor = response.substring(response.indexOf("{") + 1, response.indexOf("}"));
                    Task taskObj = new Task(taskName, taskDate, taskColor, task);
                    taskObjList.add(taskObj);
                } catch (Exception e) {
                    flag = false;
                }
            }

            // Adding random tasks to home screen when user logs in
            Random random = new Random();
            List<Task> tempTaskList = new ArrayList<>(taskObjList);
            for (int i = 0; i < 3; i++) {
                try {
                    Task randomTask = tempTaskList.get(random.nextInt(tempTaskList.size()));
                    ((Label) undoneTaskFields.get(i * 3)).setText(randomTask.getName());
                    ((Label) undoneTaskFields.get(i * 3 + 1)).setText(randomTask.getDate());
                    ((Pane) undoneTaskFields.get(i * 3 + 2)).setStyle("-fx-background-color: #" + randomTask.getColor() + ";" + "-fx-background-radius: 40");
                    tempTaskList.remove(randomTask);
                }catch (Exception e) { break; }
            }
            if (taskObjList.size() - 3 > 0)
                undoneTasksLeft.setText("And " + (taskObjList.size() - 3) + " left...");

            // Adding a random quote to 'Reminders'
            try {
                Task randomTask = taskObjList.get(random.nextInt(taskObjList.size()));
                taskReminderName.setText(randomTask.getName());
                taskReminderName.setTextFill(Color.web(randomTask.getColor()));
                taskReminderDate.setText(randomTask.getDate());
                reminderLabel.setVisible(true);
            } catch(Exception e) {}

            // Changing account names in the account page
            usernameLabel.setText(prop.getProperty("user_name"));
            fullNameLabel.setText(prop.getProperty("full_name"));

            signInPane.setVisible(false);
            mainMenuCenterPane.setVisible(true);
            accountShowButton.setText("Account");
            settingsAccButton.setVisible(true);
            logOutButton.setVisible(true);
        }
    }

    // Changing username and Full Name
    int chAccIndex = 0;
    @FXML
    private void changeAccountNames(ActionEvent event) {
        if(chAccIndex % 2 == 0) {
            usernameLabel.setVisible(false);
            fullNameLabel.setVisible(false);

            usernameField.setVisible(true);
            fullNameField.setVisible(true);
        } else {
            usernameField.setVisible(false);
            fullNameField.setVisible(false);
            String user = usernameField.getText();
            String full = fullNameField.getText();

            // TODO: Add the try-catch
            try (OutputStream output = new FileOutputStream(configProp)) {
                // Set the property value to ".". which will later tell the program this is a recently deleted task
                prop.setProperty("user_name", user);
                prop.setProperty("full_name", full);
                // Save properties to project root folder
                prop.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
                System.out.println("Err write: " + io.getMessage());
            }

            usernameLabel.setVisible(true);
            fullNameLabel.setVisible(true);
            usernameLabel.setText(prop.getProperty("user_name"));
            fullNameLabel.setText(prop.getProperty("full_name"));
        }
        chAccIndex++;

    }

    int tasksPageIndex = 0;
    int iterationIndex;
    // Changing the task page according to the specified index
    @FXML
    private void changeTasksPage(ActionEvent event) {
        // Updating 'taskPageIndex', which will be the starting point of the first task shown
        if (event.getSource() == nextTaskPage) {
            tasksPageIndex += 3;
            if(tasksPageIndex >= taskObjList.size()) {
                tasksPageIndex -= 3;
                return;
            }
        }
        else if(event.getSource() == previousTaskPage) {
            if (tasksPageIndex == 0)
                return;
            tasksPageIndex -= 3;
        }
        // Setting all other panes invisible
        try {
            for(Pane pane : buttonsHm.values()) {
                pane.setVisible(false);
            }
            mainMenuCenterPane.setVisible(false);
            tasksPane.setVisible(true);
            menuPane.setVisible(false);
        } catch (Exception e) {
            tasksPane.setVisible(true);
        }
        int elementIndex = 0;
        iterationIndex = 0;

        // Clearing the tasks before assigning new tasks
        for(int i = 0; i < 9;i += 3) {
            ((Label) taskFields.get(i)).setText("");
            ((Label) taskFields.get(i + 1)).setText("");
            ((Pane) taskFields.get(i + 2)).setStyle("-fx-background-color: None");
        }

        // Going trough all the fields
        for(Task task : taskObjList) {
            if (iterationIndex == tasksPageIndex) {
                    try {
                        // Updating fields
                        ((Label) taskFields.get(elementIndex)).setText(task.getName());
                        ((Label) taskFields.get(elementIndex + 1)).setText(task.getDate());
                        System.out.println(taskFields.get(elementIndex + 2));
                        ((Pane) taskFields.get(elementIndex + 2)).setStyle("-fx-background-color: #" + task.getColor() + ";" + "-fx-background-radius: 40");
                    } catch (Exception e) { }
                elementIndex += 3;
            }
            else {
                iterationIndex ++;
            }
        }

        totalTaskLabel.setText("Total tasks : " + taskObjList.size());
        pageTaskLabel.setText((tasksPageIndex / 3 + 1) + " / " + ((taskObjList.size() + 2) / 3));
    }


    // Adding task to config.properties
    @FXML
    private void addTaskToAccount(ActionEvent event) {
        if (!officialUsername.equals("UNKNOWN")) {
            String taskNameFinal = null;
            String taskNameString = taskName.getText();
            String taskDateString = taskDate.getValue().format(ISO_LOCAL_DATE);
            Color color= taskColor.getValue();
            String taskColorString = ColorConverter.colorToHex(color);
            // Updating 'taskObjList' in case a user adds a new task
            String finalTask ="<" + taskNameString + ">" + "[" +taskDateString + "]" + "{" + taskColorString + "}";
            boolean flag = true;
            int taskIndex = 0;
            String taskPropName = "";
            while(flag) {
                // Checking to see what is the next task number which can be assigned
                taskPropName = "task" + taskIndex;
                taskIndex++;
                try {
                    String response = prop.getProperty(taskPropName);
                    if (response == null || response.equals(".")) {
                        System.out.println("In Exception");
                        taskNameFinal = taskPropName;
                        flag = false;
                    }
                } catch (Exception e) {
                    System.out.println("In Exception");
                    taskNameFinal = taskPropName;
                    flag = false;
                }
            }
            System.out.println(taskPropName);
            Task taskObj = new Task(taskNameString, taskDateString, taskColorString, taskPropName);
            taskObjList.add(taskObj);
            // Writing the new task
            try (OutputStream output = new FileOutputStream(configProp)) {
                prop.setProperty(taskNameFinal, finalTask);
                prop.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
                System.out.println("Err write: " + io.getMessage());
            }
        } else {
            return;
        }
    }


    // Cancelling tasks insertion
    @FXML
    private void cancelTask(ActionEvent event) {
        addTaskPane.setVisible(false);
        tasksPane.setVisible(true);
    }

    // Finishing tasks
    @FXML
    private void finishTask(ActionEvent event) {
        int index = 0;
        if(event.getSource() == finishTask2)
            index = 1;
        else if(event.getSource() == finishTask3)
            index = 2;
        System.out.println("taskPageIndex : " + tasksPageIndex);
        System.out.println("index : " + index);
        System.out.println(taskObjList.get(tasksPageIndex + index).getName());
        System.out.println(taskObjList.get(tasksPageIndex + index).getPropertyName());
        ((Label) taskFields.get(index * 3)).setText("");
        ((Label) taskFields.get(index * 3 + 1)).setText("");
        ((Pane) taskFields.get(index * 3 + 2)).setStyle("-fx-background-color: None");
        try (OutputStream output = new FileOutputStream(configProp)) {
            // Set the property value to ".". which will later tell the program this is a recently deleted task
            prop.setProperty(taskObjList.get(tasksPageIndex + index).getPropertyName(), ".");

            // Save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Err write: " + io.getMessage());
        }
        taskObjList.remove(tasksPageIndex + index);
    }

    // Logout function
    @FXML
    private void logOut(ActionEvent event) {
        // Clearing officialUsername
        officialUsername = "UNKNOWN";
        // Setting the 'Sign In' button back to visible
        for (Button b : accountButtonsList) {
            if (b == accountShowButton) {
                b.setText("Sign in");
                continue;
            }
            b.setVisible(false);
        }
        // Clearing all the lists
        taskObjList.clear();

        // Setting all the other panes visible - off
        Set set = buttonsHm.entrySet();
        // Get an iterator
        Iterator it = set.iterator();
        // Display elements
        while(it.hasNext()) {
            Map.Entry me = (Map.Entry)it.next();
            ((Pane) me.getValue()).setVisible(false);
        }

        // Clearing 'Undone Tasks' when logging out
        for (int i = 0; i < 3; i++) {
            ((Label) undoneTaskFields.get(i * 3)).setText("");
            ((Label) undoneTaskFields.get(i * 3 + 1)).setText("");
            ((Pane) undoneTaskFields.get(i * 3 + 2)).setStyle("-fx-background-color: None");
        }
        undoneTasksLeft.setText("");

        // Clearing 'Reminders' when logging out
        taskReminderName.setText("");
        taskReminderDate.setText("");
        reminderLabel.setVisible(false);

        // Setting back main menu visible
        mainMenuCenterPane.setVisible(true);
        accountPane.setVisible(false);
    }


    // Handling the press of the menu buttons
    Pane lastPane = null;
    @FXML
    private void handleButtonAction (ActionEvent event){

        if (event.getSource() == accountShowButton && officialUsername.equals("UNKNOWN")) {
            signInPane.setVisible(true);
            mainMenuCenterPane.setVisible(false);
            accountPane.setVisible(false);
            lastPane = signInPane;
        }
        else {
            if (lastPane == null) {
                mainMenuCenterPane.setVisible(false);
            } else {
                lastPane.setVisible(false);
            }

            (buttonsHm.get(event.getSource())).setVisible(true);
            (buttonsHm.get(event.getSource())).setDisable(false);
            lastPane = (Pane) (buttonsHm.get(event.getSource()));
            menuPane.setVisible(false);
        }
    }

    // Getting browser to open links for social media
    @FXML
    private void openSocialMedia(ActionEvent event) {
        String urlString  = "";
        if(event.getSource() == instagramBtn)
            urlString = "https://www.instagram.com/adiolteanu_";
        if(event.getSource() == linkedinBtn)
            urlString = "https://www.linkedin.com/in/adrian-sorin-olteanu-b096b3183/";
        if(event.getSource() == twitterBtn)
            urlString = "https://twitter.com/YzeCr";
        System.out.println(urlString);
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Random quotes for the quote screen
    int quoteIndex = 0;
    RandomQuotePicker randomQuotePicker = new RandomQuotePicker();
    List<Integer> pastQuotes = new ArrayList<Integer>();
    @FXML
    private void nextRandomQuote(ActionEvent event) {
        // Getting a random quote from the file, and assigning it to 'actualQuote'
        String actualQuote;
        quoteIndex++;
        try {
            // In case we were at a previous quote, to return to the one next
            actualQuote = randomQuotePicker.quotesList.get(pastQuotes.get(quoteIndex));
        }
        catch (Exception e){
            // In the other case, we generate a new one
            actualQuote = randomQuotePicker.pick();
            pastQuotes.add((randomQuotePicker.quotesList).indexOf(actualQuote));
            try {
                // In case the new quote is the same as the old one, picking a different one
                System.out.println(randomQuotePicker.quotesList.get(pastQuotes.get(quoteIndex - 1)));
                if(randomQuotePicker.quotesList.indexOf(actualQuote) == pastQuotes.get(quoteIndex - 1)) {
                    System.out.println("ENTERED IF");
                    System.out.println("SO" + actualQuote + " == " + randomQuotePicker.quotesList.get(pastQuotes.get(quoteIndex - 1)));
                    while (randomQuotePicker.quotesList.indexOf(actualQuote) == pastQuotes.get(quoteIndex - 1)) {
                        actualQuote = randomQuotePicker.pick();
                        System.out.println("CHANGING ACTUAL QUOTE TO" + actualQuote);
                    }
                    pastQuotes.remove(pastQuotes.size() - 1);
                    pastQuotes.add((randomQuotePicker.quotesList).indexOf(actualQuote));
                }
                else
                    // If it is not the same, picking a random one
                    actualQuote = randomQuotePicker.pick();
            }
            catch (Exception e1) {
                System.out.println(e1);
            }
        }
        // Retrieving the quote body and author
        String quoteAuthorString = actualQuote.substring(actualQuote.indexOf("<") + 1, actualQuote.indexOf(">"));
        quoteAuthorLabel.setText("- " + quoteAuthorString);
        String quoteFromString = actualQuote.substring(actualQuote.indexOf("(") + 1, actualQuote.indexOf(")"));
        quoteLabel.setText("     " + quoteFromString);
        System.out.println(quoteIndex);
    }

    // Method called to display previously shown quote
    @FXML
    private void previousQuote(ActionEvent event) {
        try {
            quoteIndex--;
            int previousQuoteIndex = pastQuotes.get(quoteIndex);
            String quote = randomQuotePicker.quotesList.get(previousQuoteIndex);
            String quoteFromString = quote.substring(quote.indexOf("(") + 1, quote.indexOf(")"));
            String quoteAuthorString = quote.substring(quote.indexOf("<") + 1, quote.indexOf(">"));
            quoteAuthorLabel.setText("- " + quoteAuthorString);
            quoteLabel.setText("     " + quoteFromString);
        }
        catch (Exception e) {
            System.out.println("No previous quotes.");
            quoteIndex++;
        }
    }
}

// RandomQuotePicker class, returns a random quote ('pick()') from a text file
class RandomQuotePicker {
    public List<String> quotesList;
    Random random = new Random();
    public RandomQuotePicker() {
        try {
            String QUOTEFILE = "src/main/resources/quotes.txt";
            quotesList = Files.readAllLines(Paths.get(QUOTEFILE), StandardCharsets.UTF_8);
            System.out.println(quotesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String pick() {
        return quotesList.get(random.nextInt(quotesList.size()));
    }

}

