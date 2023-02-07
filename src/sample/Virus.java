package sample;

import java.util.ArrayList;

import javafx.scene.control.ListView;

public class Virus {
    //various variables for attributes of the infected person, such as row, column, age, in hospital, etc.
    private int row;
    private int col;
    private boolean inHospital = false;
    private final int spotInPeopleTempInfo;
    private boolean outsideHospital = false;
    private int age;
    private boolean cured = false;
    private boolean death = false;
    private int inHospitalTime;
    private String gender;
    //constructor to initialize and create an infected person.
    public Virus(int r, int c, int spot, int age, String gender) {
        //sets row, column, spotInPeopleTempInfo, age, and gender to corresponding parameters.
        this.row = r;
        this.col = c;
        this.spotInPeopleTempInfo = spot;
        this.age = age;
        this.gender = gender;
    }
    //changes location of infected person.
    public void changeLocation(int[][] spotsTaken, boolean hospitalOpen, ArrayList<Virus> peopleInfected, ArrayList<Person> people, ArrayList<Person> peopleInfectedTempInfo, ListView<String> lstEvents) {
        //increases age by 1.
        age++;
        //timeout and check variables.
        int timeout = 0;
        boolean check = false;
        //increases inHospitalTime by 1 if infected person is in hospital.
        if (inHospital) {
            inHospitalTime++;
        }
        //loop running while check is false and while timeout is less than 1000.
        while (!check && timeout < 1000) {
            //temp row and column variables
            int tempRow = row;
            int tempCol = col;
            //if the hospital is open,
            if (hospitalOpen) {
                //if infected person is not in the hospital,
                if (!inHospital) {
                    //if infected person is directly outside the hospital, sets outsideHospital attribute to true,
                    if ((tempRow >= 9 && tempRow <= 20 && (tempCol == 29 || tempCol == 45)) || (tempCol >= 29 && tempCol <= 45) && (tempRow == 9 || tempRow == 20)) {
                        outsideHospital = true;
                    } else {
                        //moves the person towards the hospital using this slightly complex method which moves the people so that they don't get clustered all in one line while moving to hospital.
                        if (tempRow > 19) {
                            tempRow--;
                        } else if (tempRow < 10) {
                            tempRow++;
                        } else {
                            //method to ensure efficient movement.
                            if (tempCol < 29) {
                                if (spotsTaken[tempRow][tempCol + 1] == 1) {
                                    if (spotsTaken[tempRow - 1][tempCol] == 0) {
                                        tempRow = tempRow - 1;
                                    } else if (spotsTaken[tempRow + 1][tempCol] == 0) {
                                        tempRow = tempRow + 1;
                                    }
                                }
                            } else if (tempCol > 45) {
                                if (spotsTaken[tempRow][tempCol - 1] == 1) {
                                    if (spotsTaken[tempRow - 1][tempCol] == 0) {
                                        tempRow = tempRow - 1;
                                    } else if (spotsTaken[tempRow + 1][tempCol] == 0) {
                                        tempRow = tempRow + 1;
                                    }
                                }
                            }
                        }
                        if (tempCol > 44) {
                            tempCol--;
                        } else if (tempCol < 30) {
                            tempCol++;
                        } else {
                            //method to ensure efficient movement.
                            if (tempRow < 9) {
                                if (spotsTaken[tempRow + 1][tempCol] == 1) {
                                    if (spotsTaken[tempRow][tempCol - 1] == 0) {
                                        tempCol = tempCol - 1;
                                    } else if (spotsTaken[tempRow][tempCol + 1] == 0) {
                                        tempCol = tempCol + 1;
                                    }
                                }
                            } else if (tempRow > 20) {
                                if (spotsTaken[tempRow - 1][tempCol] == 1) {
                                    if (spotsTaken[tempRow][tempCol - 1] == 0) {
                                        tempCol = tempCol - 1;
                                    } else if (spotsTaken[tempRow][tempCol + 1] == 0) {
                                        tempCol = tempCol + 1;
                                    }
                                }
                            }


                        }

                    }
                    //if infected person is in the hospital,
                } else {
                    //various set of statements to control how long the person stays in hospital, and their chances of being cured or dying.
                    if (age < 30) {
                        //if person has been in hospital for 3 movement phases in the program (this number increases as the person's age increases, so the person stays in the hospital longer if they are older),
                        if (inHospitalTime > 3) {
                            //whether the infected person is cured or dies depends on their age.
                            if (Math.random() < .9) {
                                cured = true;
                            } else {
                                if (Math.random() < .02) {
                                    death = true;
                                }
                            }
                        }
                    } else if (age < 50) {
                        if (inHospitalTime > 5) {
                            if (Math.random() < .75) {
                                cured = true;
                            } else {
                                if (Math.random() < .1) {
                                    death = true;
                                }
                            }
                        }
                    } else if (age < 60) {
                        if (inHospitalTime > 8) {
                            if (Math.random() < .5) {
                                cured = true;
                            } else {
                                if (Math.random() < .4) {
                                    death = true;
                                }
                            }
                        }
                    } else {
                        if (inHospitalTime > 12) {
                            if (Math.random() < .3) {
                                cured = true;
                            } else {
                                if (Math.random() < .7) {
                                    death = true;
                                }
                            }
                        }
                    }
                }
                //if the hospital is closed,
            } else {
                //changes temp row and column to respective positions if infected person is next to hospital or vaccine distribution center.
                if (tempRow == 9 && tempCol >= 30 && tempCol <= 44) {
                    tempRow--;
                } else if (tempRow == 20 && tempCol >= 30 && tempCol <= 44) {
                    tempRow++;
                } else if (tempCol == 29 && tempRow >= 10 && tempRow <= 19) {
                    tempCol--;
                } else if (tempCol == 45 && tempRow >= 10 && tempRow <= 19) {
                    tempCol++;
                } else if (tempRow == 26 && tempCol >= 36 && tempCol <= 38) {
                    tempRow--;
                } else if (tempCol == 35 && tempRow >= 27 && tempRow <= 29) {
                    tempCol--;
                } else if (tempCol == 39 && tempRow >= 27 && tempRow <= 29) {
                    tempCol++;
                } else {
                    //changes temp row to respective position if infected person is on edge of grid.
                    if (tempRow == 29) {
                        tempRow--;
                    } else if (tempRow == 0) {
                        tempRow++;
                    } else {
                        //randomly increases or decreases infected person's row (slightly adjusted to bring infected person towards middle).
                        if (row < 15) {
                            if (Math.random() > .45) {
                                tempRow++;
                            } else {
                                tempRow--;
                            }
                        } else {
                            if (Math.random() > .55) {
                                tempRow++;
                            } else {
                                tempRow--;
                            }
                        }
                    }
                    //changes temp column to respective position if infected person is on edge of grid.
                    if (tempCol == 74) {
                        tempCol--;
                    } else if (tempCol == 0) {
                        tempCol++;
                    } else {
                        //randomly increases or decreases infected person's column (slightly adjusted to bring infected person towards middle).
                        if (col < 32) {
                            if (Math.random() > .45) {
                                tempCol++;
                            } else {
                                tempCol--;
                            }
                        } else {
                            if (Math.random() > .55) {
                                tempCol++;
                            } else {
                                tempCol--;
                            }
                        }
                    }
                }
            }
            //if the spot at tempRow and tempCol has no one in it,
            if (spotsTaken[tempRow][tempCol] == 0) {
                //sets check to true to stop loop.
                check = true;
                //sets spot in spots taken to 1 since the infected person is there now.
                spotsTaken[tempRow][tempCol] = 1;
                //sets old position to 0 in spots taken since no one is there now.
                spotsTaken[row][col] = 0;
                //updates row and column to match tempRow and tempCol.
                row = tempRow;
                col = tempCol;
                //else, if the hospital is open and the infected person is outside the hospital, waiting to get in,
            } else if (hospitalOpen && outsideHospital) {
                //sets check to true to stop loop.
                check = true;
                //runs through all hospital spots.
                for (int i = 10; i < 20; i++) {
                    for (int j = 30; j < 45; j++) {
                        //if a spot in the hospital is empty,
                        if (spotsTaken[i][j] == 2) {
                            //sets tempRow and tempCol to that spot.
                            tempRow = i;
                            tempCol = j;
                            //sets the spot in spots taken array to 3, since that spot is now taken in the hospital.
                            spotsTaken[tempRow][tempCol] = 3;
                            //sets old position to 0 in spots taken since no one is there now.
                            spotsTaken[row][col] = 0;
                            //updates row and column to match tempRow and tempCol.
                            row = tempRow;
                            col = tempCol;
                            //sets inHospital to true and outsideHospital to false.
                            inHospital = true;
                            outsideHospital = false;
                            //sets i to 20 and j to 45 to terminate the loop.
                            i = 20;
                            j = 45;
                        }
                    }
                }
                //else, if infected person is in the hospital, but has died,
            } else if (inHospital && death) {
                //sets check to true to stop loop.
                check = true;
                //sets the spot in spots taken array to 2, since that spot is now available in the hospital.
                spotsTaken[tempRow][tempCol] = 2;
                //removes infected person from peopleInfected arraylist, since it is now dead.
                peopleInfected.remove(this);
                //adds death from virus event to listview.
                lstEvents.getItems().add(0, "A person has died (virus).");
                //else, if the infected person is in the hospital, but has been cured,
            } else if (inHospital && cured) {
                //sets check to true to stop loop.
                check = true;
                //sets the spot in spots taken array to 2, since that spot is now available in the hospital.
                spotsTaken[tempRow][tempCol] = 2;
                //adds person from corresponding spot back in peopleInfectedTempInfo back to people array, since they are no longer infected.
                people.add(peopleInfectedTempInfo.get(spotInPeopleTempInfo));
                //removes person from peopleInfected arraylist, since they are no longer infected.
                peopleInfected.remove(this);
                //sets age of person added to people arraylist to its updated age.
                people.get(people.size() - 1).setAge(age);
                //adds cured event to listview.
                lstEvents.getItems().add(0, "A person has been cured.");
                //searches for a spot based on the region the person is in in the hospital (if in top left of hospital, searches for empty spot in top left of grid, etc.).
                if (row <= 15 && col <= 37) {
                    //as an example, if the person is in the top left of the hospital, it will search through top left of grid for empty spots with this double loop.
                    for (int i = 15; i > -1; i--) {
                        for (int j = 37; j > -1; j--) {
                            //if no one is in the spot,
                            if (spotsTaken[i][j] == 0) {
                                //sets tempRow to i and tempCol to j, as that is the new spot.
                                tempRow = i;
                                tempCol = j;
                            }
                        }
                    }
                } else if (row >= 15 && col <= 37) {
                    for (int i = 15; i < 30; i++) {
                        for (int j = 37; j > -1; j--) {
                            if (spotsTaken[i][j] == 0) {
                                tempRow = i;
                                tempCol = j;
                            }
                        }
                    }
                } else if (row <= 15) {
                    for (int i = 15; i > -1; i--) {
                        for (int j = 37; j < 75; j++) {
                            if (spotsTaken[i][j] == 0) {
                                tempRow = i;
                                tempCol = j;
                            }
                        }
                    }
                } else {
                    for (int i = 15; i < 30; i++) {
                        for (int j = 37; j < 75; j++) {
                            if (spotsTaken[i][j] == 0) {
                                tempRow = i;
                                tempCol = j;
                            }
                        }
                    }
                }
                //sets new row and column in new person in people array list (person who has just been cured) to tempRow and tempCol.
                people.get(people.size() - 1).setRow(tempRow);
                people.get(people.size() - 1).setCol(tempCol);
            }
            //increases timeout by 1 (used to prevent infinite looping and freezing of program).
            timeout++;
        }
        //sets inHospital to the boolean based on the certain conditions to update whether the infected person is in the hospital or not.
        inHospital = (row >= 10 && row <= 19 && col >= 30 && col <= 44);
    }
    //checks neighbors of person.
    public ArrayList<Integer> checkNeighbor(ArrayList<Person> tempPeople) {
        //arraylist for neighbors.
        ArrayList<Integer> neighbors = new ArrayList<>();
        //runs through all people in tempPeople.
        for (int i = 0; i < tempPeople.size(); i++) {
            //if person in tempPeople is next to person,
            if (tempPeople.get(i).getRow() >= row - 1 && tempPeople.get(i).getRow() <= row + 1 && tempPeople.get(i).getCol() >= col - 1 && tempPeople.get(i).getCol() <= col + 1) {
                //adds the person in tempPeople to neighbors arraylist.
                neighbors.add(i);
            }
        }
        //returns neighbors arraylist.
        return neighbors;
    }
    //various accessors and mutators to change and access attributes of people in virus class.
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean getInHospital() {
        return this.inHospital;
    }

    public String getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }
}