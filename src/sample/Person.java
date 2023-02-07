package sample;

import java.util.ArrayList;

public class Person {
    //various variables for attributes of the person, such as row, column, age, death rate, etc.
    private int row;
    private int col;
    private long startTime;
    private int age = 0;
    private int reproductionRate = 0;
    private int reproductionCooldown = 0;
    private double deathRate = 0.1;
    private final String gender;
    private boolean vaccinated = false;
    private boolean quarantine = false;
    //constructor to initialize and create a person.
    public Person(int r, int c) {
        //sets row and column to r and c parameters.
        this.row = r;
        this.col = c;
        //sets start time to system nano time.
        this.startTime = System.nanoTime();
        //sets random gender.
        if (Math.random() > .5) {
            this.gender = "male";
        } else {
            this.gender = "female";
        }
    }
    //changes location of person.
    public void changeLocation(int[][] spotsTaken) {
        //timeout and check variables.
        int timeout = 0;
        boolean check = false;
        //loop running while check is false and while timeout is less than 1000.
        while (!check && timeout < 1000) {
            //temp row and column variables
            int tempRow = row;
            int tempCol = col;
            //changes temp row and column to respective positions if person is next to hospital or vaccine distribution center.
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
                //changes temp row to respective position if person is on edge of grid.
                if (tempRow == 29) {
                    tempRow--;
                } else if (tempRow == 0) {
                    tempRow++;
                } else {
                    //randomly increases or decreases person's row (slightly adjusted to bring player towards middle).
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
                //changes temp column to respective position if person is on edge of grid.
                if (tempCol == 74) {
                    tempCol--;
                } else if (tempCol == 0) {
                    tempCol++;
                } else {
                    //randomly increases or decreases person's column (slightly adjusted to bring player towards middle).
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
            //if the spot at tempRow and tempCol has no one in it,
            if (spotsTaken[tempRow][tempCol] == 0) {
                //sets check to true to stop loop.
                check = true;
                //sets spot in spots taken to 1 since the person is there now.
                spotsTaken[tempRow][tempCol] = 1;
                //sets old position to 0 in spots taken since no one is there now.
                spotsTaken[row][col] = 0;
                //updates row and column to match tempRow and tempCol.
                row = tempRow;
                col = tempCol;
            }
            //increases timeout by 1 (used to prevent infinite looping and freezing of program).
            timeout++;
        }
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
    //various accessors and mutators to change and access attributes of people in person class.
    public void resetStartTime() {
        startTime = System.nanoTime();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setRow(int i) {
        this.row = i;
    }

    public int getRow() {
        return this.row;
    }

    public void setCol(int i) {
        this.col = i;
    }

    public int getCol() {
        return this.col;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public int getAge() {
        return this.age;
    }

    public void setReproductionRate(int i) {
        this.reproductionRate = i;
    }

    public int getReproductionRate() {
        return this.reproductionRate;
    }

    public void setReproductionCooldown(int i) {
        this.reproductionCooldown = i;
    }

    public int getReproductionCooldown() {
        return this.reproductionCooldown;
    }

    public void setDeathRate(double i) {
        this.deathRate = i;
    }

    public double getDeathRate() {
        return this.deathRate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setVaccinated(boolean i) {
        this.vaccinated = i;
    }

    public boolean getVaccinated() {
        return this.vaccinated;
    }

    public void setQuarantine(boolean i) {
        this.quarantine = i;
    }

    public boolean getQuarantine() {
        return this.quarantine;
    }
}

