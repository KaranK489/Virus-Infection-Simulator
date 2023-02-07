package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class Controller {

    //various FXML features, such as a gridpane, a listview, labels, a pie chart, buttons, a line chart, rectangles, and number axes.
    @FXML
    GridPane gPane;

    @FXML
    ListView<String> lstEvents;

    @FXML
    Label lblHospitalStatus, lblQuarantineStatus, lblKey, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, lbl13, lbl14, lbl15, lbl16, lbl17, lbl18, lblInfectionRate;

    @FXML
    PieChart pieChart;

    @FXML
    Button btnStart, btnMoreSpeed, btnLessSpeed, btnVirus, btnQuarantine, btnHospital, btnMutate, btnVaccine;

    @FXML
    LineChart lineChart;

    @FXML
    Rectangle rt1, rt2, rt3, rt4, rt5, rt6, rt7, rt8, rt9, rt10, rt11, rt12, rt13, rt14, rt15, rt16, rt17, rt18;

    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;

    //XY Chart Series', used for the line chart.
    XYChart.Series seriesHealthy = new XYChart.Series();
    XYChart.Series seriesInfected = new XYChart.Series();

    boolean vaccineDistribution;
    boolean virusExists = false;
    int infectionRate = 60;
    boolean hospitalOpen = false;
    boolean quarantine;
    int peopleHealthyNum = 0;
    int peopleInfectedNum = 0;
    int row = 30;
    int col = 75;
    int chartCounter = 0;
    int speed = 1000000000;
    int years = 0;
    Button[][] btn = new Button[row][col];
    int[][] spotsTaken = new int[row][col];
    ArrayList<Person> people = new ArrayList<>();
    ArrayList<Person> peopleInfectedTempInfo = new ArrayList<>();
    ArrayList<Virus> peopleInfected = new ArrayList<>();
    ArrayList<Vaccine> vaccines = new ArrayList<>();
    //initializes by hiding everything except for start button, and disabling vaccine button.
    @FXML
    public void initialize() {
        btnVaccine.setDisable(true);
        setFXMLFeatures(false);
    }
    //handles start function, by initializing the pictures/people, the gird pane, buttons and FXML features, and running the start method.
    @FXML
    private void handleStart() {
        setPictures();
        gPane.setGridLinesVisible(true);
        gPane.setVisible(true);
        start();
        btnStart.setDisable(true);
        setFXMLFeatures(true);
    }
    //sets various FXML features to false/true based on parameter.
    public void setFXMLFeatures(boolean a){
        for (Button button : Arrays.asList(btnMoreSpeed, btnLessSpeed, btnVirus, btnQuarantine, btnHospital, btnMutate, btnVaccine)) {
            button.setVisible(a);
        }
        for (Label label : Arrays.asList(lblKey, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, lbl13, lbl14, lbl15, lbl16, lbl17, lbl18)) {
            label.setVisible(a);
        }
        for (Rectangle rectangle : Arrays.asList(rt1, rt2, rt3, rt4, rt5, rt6, rt7, rt8, rt9, rt10, rt11, rt12, rt13, rt14, rt15, rt16, rt17, rt18)) {
            rectangle.setVisible(a);
        }
        pieChart.setVisible(a);
        lineChart.setVisible(a);
        lstEvents.setVisible(a);
    }
    //sets the "pictures," or spots on the grid (the hospital, people, etc.).
    public void setPictures() {
        //loop to initialize 100 people.
        int a = 0;
        while (a < 100) {
            int randRow = (int) (Math.random() * (30));
            int randCol = (int) (Math.random() * (75));
            if (!((randRow >= 10 && randRow <= 19) && (randCol >= 30 && randCol <= 44)) && !((randRow >= 27 && randRow <= 29) && (randCol >= 36 && randCol <= 38))) {
                people.add(new Person(randRow, randCol));
                spotsTaken[randRow][randCol] = 1;
                a++;
            }
        }
        //loop to initialize buttons and add it to the gridpane.
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j] = new Button();
                btn[i][j].setPrefSize(25, 25);
                gPane.add(btn[i][j], j, i);
            }
        }
        //loop to initialize hospital area.
        for (int v = 10; v < 20; v++) {
            for (int n = 30; n < 45; n++) {
                btn[v][n].setStyle("-fx-background-color:#757575");
                spotsTaken[v][n] = 2;
            }
        }
        //loop to initialize vaccine distribution center.
        for (int v = 27; v < 30; v++) {
            for (int n = 36; n < 39; n++) {
                btn[v][n].setStyle("-fx-background-color:#000cff");
                spotsTaken[v][n] = 4;
            }
        }
        //runs updateScreen which updates colors of squares.
        updateScreen();
    }
    //updates colors of squares on grid based on what it is representing.
    @FXML
    private void updateScreen() {
        //loop that runs through all buttons in the grid.
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                //sets different colors based on spotsTaken 2d array (if no one is in the spot, set to white, if it is a hospital, set it to gray, etc.).
                if (spotsTaken[i][j] == 0) {
                    btn[i][j].setStyle("-fx-background-color:#ffffff");
                } else if (spotsTaken[i][j] == 2) {
                    btn[i][j].setStyle("-fx-background-color:#757575");
                } else if (spotsTaken[i][j] == 3) {
                    btn[i][j].setStyle("-fx-background-color:#ff0000");
                } else if (spotsTaken[i][j] == 4) {
                    btn[i][j].setStyle("-fx-background-color:#000cff");
                } else if (spotsTaken[i][j] == 5) {
                    btn[i][j].setStyle("-fx-background-color:#00ff2f");
                } else if (spotsTaken[i][j] == 1) {
                    //loop that runs through people arraylist.
                    for (int k = 0; k < people.size(); k++) {
                        if (k == 0) {
                            //loop to set red squares based on infected people locations.
                            for (Virus m : peopleInfected) {
                                if ((m.getRow() == i) && (m.getCol() == j)) {
                                    btn[i][j].setStyle("-fx-background-color:#ff0000");
                                }
                            }
                        }
                        //set of if statements which sets the colors of the squares based on the person's age and gender.
                        if ((people.get(k).getRow() == i) && (people.get(k).getCol() == j)) {
                            if (people.get(k).getAge() >= 0 && people.get(k).getAge() <= 12) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#6ab5fc");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#ffa6e9");
                                }
                            } else if (people.get(k).getAge() >= 13 && people.get(k).getAge() <= 21) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#e49974");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#ff7f7f");
                                }
                            } else if (people.get(k).getAge() >= 22 && people.get(k).getAge() <= 30) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#00a2bb");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#720707");
                                }
                            } else if (people.get(k).getAge() >= 31 && people.get(k).getAge() <= 45) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#c4ff15");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#95029d");
                                }
                            } else if (people.get(k).getAge() >= 46 && people.get(k).getAge() <= 60) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#c9beff");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#27079e");
                                }
                            } else if (people.get(k).getAge() >= 61 && people.get(k).getAge() <= 75) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#e543ff");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#0a6b01");
                                }
                            } else if (people.get(k).getAge() >= 76) {
                                if (people.get(k).getGender().equals("male")) {
                                    btn[i][j].setStyle("-fx-background-color:#f2ff00");
                                } else {
                                    btn[i][j].setStyle("-fx-background-color:#97ffed");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //start function to run actual animation.
    public void start() {
        //animation timer
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //loop to run the animation through all the people in people arraylist
                for (int i = 0; i < people.size(); i++) {
                    //sets an arraylist to get the neighbors of the person.
                    ArrayList<Integer> allNeighbors = people.get(i).checkNeighbor(people);
                    //runs every 'speed' seconds, which can be changed while run.
                    if (now - people.get(i).getStartTime() > (speed)) {
                        //enables/disables hospital button based on infected people count.
                        if (peopleInfected.size() > 0 && hospitalOpen) {
                            btnHospital.setDisable(true);
                        } else if (peopleInfected.size() == 0 && hospitalOpen) {
                            btnHospital.setDisable(false);
                        }
                        //displays infection rate.
                        lblInfectionRate.setText("Infection Rate: " + infectionRate);
                        //increases age of people by 1.
                        people.get(i).setAge(people.get(i).getAge() + 1);
                        //runs change location function for every person, only if they are not quarantining.
                        if (!people.get(i).getQuarantine()) {
                            people.get(i).changeLocation(spotsTaken);
                        }
                        //various if statements to lower the reproduction cooldown and set the reproduction rate based on the person's age and gender.
                        if (people.get(i).getAge() >= 14) {
                            if (people.get(i).getReproductionCooldown() != 0) {
                                people.get(i).setReproductionCooldown(people.get(i).getReproductionCooldown() - 1);
                            }
                            if (people.get(i).getAge() <= 30) {
                                people.get(i).setReproductionRate(70);
                            }
                            if (people.get(i).getAge() <= 40 && people.get(i).getAge() > 30) {
                                people.get(i).setReproductionRate(50);
                            }
                            if (people.get(i).getAge() <= 50 && people.get(i).getAge() > 40) {
                                people.get(i).setReproductionRate(30);
                            }
                            if (people.get(i).getAge() > 50) {
                                if (people.get(i).getGender().equals("male")) {
                                    people.get(i).setReproductionRate(20);
                                } else {
                                    people.get(i).setReproductionRate(0);
                                }
                            }
                        }
                        //if the virus exists,
                        if (virusExists) {
                            if (i == 0) {
                                //infects healthy people based on locations of infected people.
                                infectPeople();
                                //runs loop through infected people and changes their locations.
                                for (int d = 0; d < peopleInfected.size(); d++) {
                                    peopleInfected.get(d).changeLocation(spotsTaken, hospitalOpen, peopleInfected, people, peopleInfectedTempInfo, lstEvents);
                                }
                            }
                            //running 3 times,
                            if (i < 3) {
                                //if vaccines are being distributed,
                                if (vaccineDistribution) {
                                    //runs loop through all vaccine distributors and changes their locations.
                                    for (Vaccine vaccine : vaccines) {
                                        vaccine.changeLocation(spotsTaken, people, lstEvents);
                                    }
                                }
                            }
                        }
                        //various if statements to set a person's death rate based on their age.
                        if (people.get(i).getAge() >= 50 && people.get(i).getAge() < 60) {
                            people.get(i).setDeathRate(1);
                        }
                        if (people.get(i).getAge() >= 60 && people.get(i).getAge() < 70) {
                            people.get(i).setDeathRate(3.5);
                        }
                        if (people.get(i).getAge() >= 60 && people.get(i).getAge() < 70) {
                            people.get(i).setDeathRate(8.5);
                        }
                        if (people.get(i).getAge() >= 70) {
                            people.get(i).setDeathRate(25);
                        }
                        //resets the timer.
                        people.get(i).resetStartTime();
                        //loop that runs if the person 14 years or older.
                        if (people.get(i).getAge() >= 14) {
                            //if there are people/neighbors next to the person.
                            if (allNeighbors.size() > 0) {
                                //senses one of the neighbors.
                                int neighbor1 = allNeighbors.get(0);
                                //attempts to reproduce with the neighbor if the conditions are met.
                                if (!(people.get(i).getGender().equals(people.get(neighbor1).getGender())) && people.get(neighbor1).getAge() >= 14) {
                                    reproduce(people.get(i), people.get(neighbor1));
                                }
                            }
                        }
                        //runs death method.
                        death(i);
                        //runs one time, and updates numbers of people who are healthy and people who are vaccinated (for the line chart), and runs the handle chart method.
                        if (i == 0) {
                            peopleHealthyNum = people.size();
                            peopleInfectedNum = peopleInfected.size();
                            handleChart();
                        }
                    }
                }
                //updates the squares.
                updateScreen();
            }
        }.start();
    }
    //reproduce method.
    public void reproduce(Person person1, Person person2) {
        //variables for baby positions and reproduction rates.
        int babyRow;
        int babyCol;
        double totalReproductionRate = person1.getReproductionRate() + person2.getReproductionRate();
        double randomReproductionRate = (Math.random() * (201));
        //if various conditions are met for reproduction,
        if ((randomReproductionRate < totalReproductionRate) && person1.getReproductionCooldown() == 0 && person2.getReproductionCooldown() == 0 && person1.getReproductionRate() != 0 && person2.getReproductionRate() != 0) {
            //sets baby row to certain values if the people reproducing are near the edge.
            if (person1.getRow() == 0 || person2.getRow() == 0) {
                babyRow = 1;
            } else if (person1.getRow() == 29 || person2.getRow() == 29) {
                babyRow = 28;
            } else {
                //if parents are not near the edge, it sets the baby row to a random spot near them.
                if (Math.random() > .5) {
                    babyRow = person1.getRow() + 1;
                } else {
                    babyRow = person1.getRow() - 1;
                }
            }
            //sets baby column to certain values if the people reproducing are near the edge.
            if (person1.getCol() == 0 || person2.getCol() == 0) {
                babyCol = 1;
            } else if (person1.getCol() == 74 || person2.getCol() == 74) {
                babyCol = 73;
            } else {
                //if parents are not near the edge, it sets the baby column to a random spot near them.
                if (Math.random() > .5) {
                    babyCol = person1.getCol() + 1;
                } else {
                    babyCol = person1.getCol() - 1;
                }
            }
            //if the spot is empty,
            if (spotsTaken[babyRow][babyCol] == 0) {
                //baby added to people arraylist.
                people.add(new Person(babyRow, babyCol));
                //sets baby quarantine to true if quarantine is in place.
                if (quarantine) {
                    people.get(people.size() - 1).setQuarantine(true);
                }
                //adds birth event to listview, sets the spots taken to 1 since the baby is there, sets parents' reproduction cooldown to 3.
                lstEvents.getItems().add(0, "A person has been born.");
                spotsTaken[babyRow][babyCol] = 1;
                person1.setReproductionCooldown(3);
                person2.setReproductionCooldown(3);
            }
        }
    }
    //death method which controls dying of people.
    public void death(int i) {
        //random death double.
        double randDeathNum = (Math.random() * (101));
        //if various conditions are met to make person die,
        if (randDeathNum < people.get(i).getDeathRate()) {
            //sets the spots taken to 0 since no one is there anymore.
            spotsTaken[people.get(i).getRow()][people.get(i).getCol()] = 0;
            //adds death event to listview.
            lstEvents.getItems().add(0, "A person has died (natural).");
            //removes person from people arraylist.
            people.remove(i);
        }
    }
    //handles virus button clicked.
    @FXML
    private void handleVirus() {
        //sets virus exists boolean to true.
        virusExists = true;
        //infects random person in people arraylist, adds person to peopleInfected arraylist, adds person's temporary info to peopleInfectedTempInfo arraylist, removes person from people arraylist.
        int random = (int) (Math.random() * ((people.size() - 1) + 1));
        peopleInfected.add(new Virus(people.get(random).getRow(), people.get(random).getCol(), peopleInfectedTempInfo.size(), people.get(random).getAge(), people.get(random).getGender()));
        peopleInfectedTempInfo.add(people.get(random));
        people.remove(people.get(random));
        //enables vaccine button.
        btnVaccine.setDisable(false);
        //adds infected event to listview.
        lstEvents.getItems().add(0, "A person has been infected.");
    }
    //infects people.
    public void infectPeople() {
        //creates arraylist to manage infected people.
        ArrayList<Integer> peopleToRemove = new ArrayList<>();
        ArrayList<Integer> neighborsOfInfectedPeople = new ArrayList<>();
        //runs through infected people arraylist.
        for (Virus virus : peopleInfected) {
            //sets temp arraylist to the infected person's neighbors.
            ArrayList<Integer> temp = virus.checkNeighbor(people);
            //runs through all the infected person's neighbors.
            for (Integer integer : temp) {
                //if arraylist doesn't contain neighbor, and the infected person isn't in the hospital, adds neighbor to neighbors of infected people arraylist.
                if (!neighborsOfInfectedPeople.contains(integer) && !virus.getInHospital()) {
                    neighborsOfInfectedPeople.add(integer);
                }
            }
        }
        //runs through all neighbors in neighbors of infected person array list.
        for (Integer neighborsOfInfectedPerson : neighborsOfInfectedPeople) {
            //sets a random infection rate.
            double randomInfectionRate = (Math.random() * (101));
            //decreases chances of neighbor getting infected if they are quarantining and/or are vaccinated.
            if (people.get(neighborsOfInfectedPerson).getQuarantine()) {
                randomInfectionRate += 20;
            }
            if (people.get(neighborsOfInfectedPerson).getVaccinated()) {
                randomInfectionRate += 50;
            }
            //if the condition is met,
            if ((randomInfectionRate < infectionRate)) {
                //adds infected event to listview.
                lstEvents.getItems().add("A person has been infected.");
                //infects neighbor, adds person to peopleInfected arraylist, adds person's temporary info to peopleInfectedTempInfo arraylist, adds person to people to remove arraylist.
                peopleInfected.add(new Virus(people.get(neighborsOfInfectedPerson).getRow(), people.get(neighborsOfInfectedPerson).getCol(), peopleInfectedTempInfo.size(), people.get(neighborsOfInfectedPerson).getAge(), people.get(neighborsOfInfectedPerson).getGender()));
                peopleInfectedTempInfo.add(people.get(neighborsOfInfectedPerson));
                peopleToRemove.add(neighborsOfInfectedPerson);
            }
        }
        //sorts out people to remove arraylist in greatest to least order.
        Collections.sort(peopleToRemove);
        Collections.reverse(peopleToRemove);
        //removes people in people to remove arraylist from people arraylist.
        for (Integer integer : peopleToRemove) {
            people.remove(people.get(integer));
        }
    }
    //handles increase speed button click by increasing speed by dividing speed integer by 2.
    @FXML
    private void increaseSpeed() {
        speed /= 2;
    }
    //handles decrease speed button click by decreasing speed by multiplying speed integer by 2.
    @FXML
    private void decreaseSpeed() {
        speed *= 2;
    }
    //handles quarantine button click.
    @FXML
    private void handleQuarantine() {
        //if quarantine is on, it turns it off, displays it, and sets quarantine attribute to false for all people.
        if (quarantine) {
            lblQuarantineStatus.setText("Quarantine period off.");
            quarantine = false;
            for (Person b : people) {
                b.setQuarantine(false);
            }
        } else {
            //if quarantine is off, it turns it on, displays it, and sets quarantine attribute to true for most people.
            lblQuarantineStatus.setText("Quarantine period on.");
            quarantine = true;
            for (Person b : people) {
                if (Math.random() < .85) {
                    b.setQuarantine(true);
                }
            }
        }
    }
    //handles hospital button click.
    @FXML
    private void handleHospital() {
        //sets hospital to open if closed, and closed if open.
        hospitalOpen = !hospitalOpen;
        //displays status of whether hospital is closed or open.
        if (hospitalOpen) {
            lblHospitalStatus.setText("Hospital is open.");
        } else {
            lblHospitalStatus.setText("Hospital is closed.");
        }
    }
    //handles mutate button click by increasing virus infection rate by 10.
    @FXML
    private void handleMutate() {
        infectionRate += 10;
    }
    //handles vaccine button click.
    @FXML
    private void handleVaccine() {
        //if virus exists,
        if (virusExists) {
            //sets vaccine distribution boolean to true.
            vaccineDistribution = true;
            //loop that runs through vaccine distribution center spots, adds new vaccine distributors in those spots, sets those spots in spots taken array to 5.
            for (int i = 27; i < 30; i++) {
                for (int j = 36; j < 39; j++) {
                    vaccines.add(new Vaccine(i, j));
                    spotsTaken[i][j] = 5;
                }
            }
        }
    }
    //handles pie and line chart.
    @FXML
    private void handleChart() {
        //various variables for gender and age.
        int male1 = 0;
        int male2 = 0;
        int male3 = 0;
        int male4 = 0;
        int male5 = 0;
        int male6 = 0;
        int male7 = 0;
        int female1 = 0;
        int female2 = 0;
        int female3 = 0;
        int female4 = 0;
        int female5 = 0;
        int female6 = 0;
        int female7 = 0;
        //loop running through people array list, adding to each integer listed above based on the person's age and gender.
        for (Person a : people) {
            if (a.getGender().equals("male")) {
                if (a.getAge() <= 12) {
                    male1++;
                } else if (a.getAge() <= 21) {
                    male2++;
                } else if (a.getAge() <= 30) {
                    male3++;
                } else if (a.getAge() <= 45) {
                    male4++;
                } else if (a.getAge() <= 60) {
                    male5++;
                } else if (a.getAge() <= 75) {
                    male6++;
                } else {
                    male7++;
                }
            } else {
                if (a.getAge() <= 12) {
                    female1++;
                } else if (a.getAge() <= 21) {
                    female2++;
                } else if (a.getAge() <= 30) {
                    female3++;
                } else if (a.getAge() <= 45) {
                    female4++;
                } else if (a.getAge() <= 60) {
                    female5++;
                } else if (a.getAge() <= 75) {
                    female6++;
                } else {
                    female7++;
                }
            }
        }
        //loop running through infected people array list, adding to each integer listed above based on the person's age and gender.
        for (Virus b : peopleInfected) {
            if (b.getGender().equals("male")) {
                if (b.getGender().equals("male")) {
                    if (b.getAge() <= 12) {
                        male1++;
                    } else if (b.getAge() <= 21) {
                        male2++;
                    } else if (b.getAge() <= 30) {
                        male3++;
                    } else if (b.getAge() <= 45) {
                        male4++;
                    } else if (b.getAge() <= 60) {
                        male5++;
                    } else if (b.getAge() <= 75) {
                        male6++;
                    } else {
                        male7++;
                    }
                } else {
                    if (b.getAge() <= 12) {
                        female1++;
                    } else if (b.getAge() <= 21) {
                        female2++;
                    } else if (b.getAge() <= 30) {
                        female3++;
                    } else if (b.getAge() <= 45) {
                        female4++;
                    } else if (b.getAge() <= 60) {
                        female5++;
                    } else if (b.getAge() <= 75) {
                        female6++;
                    } else {
                        female7++;
                    }
                }
            }
        }
        //adds ages and gender groups of people to pie chart.
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Male ages 0-12", male1),
                new PieChart.Data("Male ages 13-21", male2),
                new PieChart.Data("Male ages 22-30", male3),
                new PieChart.Data("Male ages 31-45", male4),
                new PieChart.Data("Male ages 46-60", male5),
                new PieChart.Data("Male ages 61-75", male6),
                new PieChart.Data("Female ages 76+", male7),
                new PieChart.Data("Female ages 0-12", female1),
                new PieChart.Data("Female ages 13-21", female2),
                new PieChart.Data("Female ages 22-30", female3),
                new PieChart.Data("Female ages 31-45", female4),
                new PieChart.Data("Female ages 46-60", female5),
                new PieChart.Data("Female ages 61-75", female6),
                new PieChart.Data("Female ages 76+", female7)
        );
        //sets pie chart to data above.
        pieChart.setData(list);
        //clears current data in line chart.
        lineChart.getData().clear();
        //sets upper bound for x axis to constantly increasing variable chartCounter.
        xAxis.setUpperBound(chartCounter);
        //adds updated population numbers to both series.
        seriesHealthy.getData().add(new XYChart.Data(chartCounter, peopleHealthyNum));
        seriesInfected.getData().add(new XYChart.Data(chartCounter, peopleInfectedNum));
        //sets name for both series based on what they represent.
        seriesHealthy.setName("Healthy People");
        seriesInfected.setName("Infected People");
        //increases chartCounter integer by 1.
        chartCounter++;
        //adds both series to line chart.
        lineChart.getData().add(seriesHealthy);
        lineChart.getData().add(seriesInfected);
    }
}