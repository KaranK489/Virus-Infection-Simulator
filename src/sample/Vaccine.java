package sample;

import javafx.scene.control.ListView;
import java.util.ArrayList;

public class Vaccine {
    //row and column variables of the vaccine distributor.
    private int row;
    private int col;
    //constructor to initialize and create a vaccine distributor.
    public Vaccine(int r, int c) {
        //sets row and column to r and c parameters.
        this.row = r;
        this.col = c;
    }
    //changes location of vaccine distributor.
    public void changeLocation(int[][] spotsTaken, ArrayList<Person> people, ListView<String> lstEvents) {
        //timeout and check variables.
        boolean check = false;
        int timeout = 0;
        //loop running while check is false and while timeout is less than 1000.
        while (!check && timeout < 1000) {
            //temp row and column variables
            int tempRow = row;
            int tempCol = col;
            //neighbors arraylist set to the check neighbor function for all the people.
            ArrayList<Integer> neighbors = checkNeighbor(people);
            //loop running through neighbors arraylist.
            for (int a = 0; a < neighbors.size(); a++) {
                //sets the person's vaccinated attribute to true, since the vaccine distributor is close to it.
                people.get(checkNeighbor(people).get(a)).setVaccinated(true);
                //adds vaccinated event to listview.
                lstEvents.getItems().add(0, "A person has been vaccinated.");
            }
            //changes temp row and column to respective positions if vaccine distributor is next to hospital or vaccine distribution center.
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
                //changes temp row to respective position if vaccine distributor is on edge of grid.
                if (tempRow == 29) {
                    tempRow--;
                } else if (tempRow == 0) {
                    tempRow++;
                } else {
                    //randomly increases or decreases vaccine distributor's row (slightly adjusted to bring vaccine distributor towards middle).
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
                //changes temp column to respective position if vaccine distributor is on edge of grid.
                if (tempCol == 74) {
                    tempCol--;
                } else if (tempCol == 0) {
                    tempCol++;
                } else {
                    //randomly increases or decreases vaccine distributor's column (slightly adjusted to bring vaccine distributor towards middle).
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
            //if the vaccine distributor is still in the vaccine distribution center,
            if (row >= 27 && row <= 29 && col >= 36 && col <= 38) {
                //sets check to true to stop loop.
                check = true;
                //sets spot in spots taken to 5 since the vaccine distributor is there now.
                spotsTaken[tempRow][tempCol] = 5;
                //sets old spot in spots taken to 4 since the vaccine distributor is not there anymore, but the vaccine distribution center is..
                spotsTaken[row][col] = 4;
                //updates row and column to match tempRow and tempCol.
                row = tempRow;
                col = tempCol;
            //else, if the vaccine distributor is not in the vaccine distribution center, and the spot selected is open,
            } else if (spotsTaken[tempRow][tempCol] == 0) {
                //sets check to true to stop loop.
                check = true;
                //sets spot in spots taken to 5 since the vaccine distributor is there now.
                spotsTaken[tempRow][tempCol] = 5;
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
    //checks neighbors of vaccine distributor.
    public ArrayList<Integer> checkNeighbor(ArrayList<Person> tempPeople) {
        //arraylist for neighbors.
        ArrayList<Integer> neighbors = new ArrayList<>();
        //runs through all people in tempPeople.
        for (int i = 0; i < tempPeople.size(); i++) {
            //if person in tempPeople is close to vaccine distributor,
            if ((tempPeople.get(i).getRow() >= row - 2) && (tempPeople.get(i).getRow() <= row + 2) && (tempPeople.get(i).getCol() >= col - 2) && (tempPeople.get(i).getCol() <= col + 2)) {
                //adds the person in tempPeople to neighbors arraylist.
                neighbors.add(i);
            }
        }
        //returns neighbors arraylist.
        return neighbors;
    }
}