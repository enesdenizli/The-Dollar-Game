/******************************************************************************
 The program is a small game that based on graph theory. User chooses the
 amount of vertex and edges. After the initialization for each vertex with user
 prompted value, user tries to have one goal: to make all vertices a nonzero number.
 Rules are, if user chooses to give money from a vertex, it gives a dollar to each
 vertex it connected to via the edge. If the vertex chooses to take money, it takes
 a dollar from each vertex via its all edges.
 *******************************************************************************/

import java.util.*;

class Vertex {
    private char name;
    private String edges = "";
    private int dollars;

    public Vertex(char name) {
        this.name = name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public void addEdges(char edge) {
        this.edges += edge;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public char getName() {
        return this.name;
    }

    public String getEdges() {
        return this.edges;
    }

    public int getDollars() {
        return this.dollars;
    }

    // you can add a method that allows user to check if an edge exists
}

public class Main {
    // Program saves all the edges created to this string
    static String allEdges = "";
    static Scanner input = new Scanner(System.in);
    // Allows us to create a list of Vertices
    static ArrayList<Vertex> vertexArrayList = new ArrayList<Vertex>();
    // Total vertex amount
    static int vertexAmount = 0;
    // Total edge amount
    static int edgeAmount = 0;
    public static void main(String[] args) {
        menu();

        dollarAmountSetter();

        edgeChecker();

        startGame();

    }

    // This function starts the game
    public static void startGame() {
        // Variable for user choice
        String userChoice = "";
        // flag allows
        boolean flag = false;
        while(!flag){
            System.out.println("What move you want to make? (enter 'g' for give, 't' for take, 'q' for quit): ");
            userChoice = input.nextLine();
            // replaces whitespace and converts to uppercase
            userChoice = userChoice.replaceAll("\\s+","").toUpperCase();
            // based on user choice, program gets directed
            switch (userChoice) {
                case "Q":
                    System.out.println("Thanks for playing the dollar game, see you next time!");
                    System.exit(0);
                case "G":
                    // method 'give', update flag condition
                    break;
                case "T":
                    // method 'take', update flag condition
                    break;
                default:
                    System.out.println("Invalid format entry");
                    break;
            }
        }
    }

    // This function gets each characters from designatedEdge
    // and adds to each vertex if the edge doesn't exist
    public static void edgeAdderForVertices(String designatedEdge) {
        char vertex1 = designatedEdge.charAt(0);
        char vertex2 = designatedEdge.charAt(1);
        // goes through the vertexArrayList and checks if there exists
        /// an edge with the same name so that it can add to edges that
        // connects tha vertex to other vertices
        for (Vertex vertex : vertexArrayList) {
            if (vertex1 == vertex.getName()) {
                vertex.addEdges(vertex2);
            }
            if(vertex2 == vertex.getName()){
                vertex.addEdges(vertex1);
            }
        }
    }

    // This function checks if the edge is in the correct format so that it can
    // add it to the allEdges string
    public static void edgeChecker() {
        // particular edge we want to check
        String designatedEdge = "";
        boolean flag = false;
        // iterates through each edge
        for(int i = 0; i < edgeAmount; i++) {
            // flag allows the program to stay in the loop till we get the answer we want
            while(!flag) {
                System.out.println("Please enter two vertices you want to designate an edge: ");
                designatedEdge = input.nextLine();

                //removes whitespc., converts to uppercs., and sorts string alphb.
                designatedEdge = stringEditor(designatedEdge);

                // checks for the correct string length
                if(designatedEdge.length() != 2) {
                    System.out.println("Character length is not appropriate");
                }
                // checks for the valid format
                else if(designatedEdge.matches(".*\\d.*")){
                    System.out.println("Invalid format");
                }
                // checks if edge is to vertex itself
                else if(designatedEdge.charAt(0) == designatedEdge.charAt(1)) {
                    System.out.println("Vertex may not have an edge to itself");
                }
                // checks if the edge already exist
                else if(!edgeAdder(designatedEdge)) {
                    // prints an appropriate message
                }
                // if everythin is good, we can exit the loop and call the function
                // edgeAdderForVertices
                else {
                    flag = true;
                    edgeAdderForVertices(designatedEdge);
                }
            }
            flag = false;
        }
    }

    // This function adds edges to the string allEdges and
    // returns a boolean value based on the condition
    public static boolean edgeAdder(String designatedEdge) {
        // basic trigger
        int counter = 0;
        for (Vertex vertex : vertexArrayList) {
            if (vertex.getName() == designatedEdge.charAt(0)) {
                counter++;
            } else if (vertex.getName() == designatedEdge.charAt(1)) {
                counter++;
            }
        }
        // decides if the edge we wanted to add is appropriate because
        // if there is no such an vertices, program should not allow
        // the user to create such and edge
        if(counter < 2){
            System.out.println("Vertices does not match");
            return false;
        }
        // checks if the edge we want to add already exists
        if (allEdges.contains(designatedEdge)) {
            System.out.println("Sorry, the edge already exist.");
            return false;
        }
        // if all is good, we add edge to allEdges and return true
        else {
            allEdges += designatedEdge;
            return true;
        }
    }

    // This function edits the edge the user wants to create
    // It gets rid off the whitespaces, converts to uppercase and
    // sorts the string then return the string
    public static String stringEditor(String designatedEdge) {
        designatedEdge = designatedEdge.replaceAll("\\s+","");
        designatedEdge = designatedEdge.toUpperCase();
        // converts to char array to use sort function
        char[] temp = designatedEdge.toCharArray();
        Arrays.sort(temp);
        // converts back to string
        designatedEdge = new String(temp);
        return designatedEdge;
    }

    // This function allows the user to create a Vertex and
    // set the dollar amount for each vertex
    public static void dollarAmountSetter() {
        // index we iterate
        int i = 0;
        boolean flag = true;
        // iterates till we hit vertexAmount
        while(i < vertexAmount){
            // try/catch block to prevent inputmissmatch exception
            try {
                // if flag is what we want, we increase the name of current Vertex
                if(flag) {
                    // each Vertex we create will have an alphabetical ordered name
                    vertexArrayList.add(new Vertex((char) (65 + i)));
                }
                System.out.println("Please enter a dollar amount for vertex " +
                        vertexArrayList.get(i).getName());
                // gets the dollar amount for each Vertex
                vertexArrayList.get(i).setDollars(input.nextInt());
                // to empty the buffer for nextLine
                input.nextLine();
                // prints out the dollar amount for each Vertex after initialization
                System.out.println("Dollar amount for vertex " +
                        vertexArrayList.get(i).getName() + " is " +
                        vertexArrayList.get(i).getDollars());
                System.out.println();
                flag = true;
                i++;
            } catch(InputMismatchException e) {
                System.err.println("Incorrect format");
                input.next();
                flag = false;
            }
        }
    }

    // This function starts to create the board for the game which asks for
    // the vertex amount and the edge amount
    public static void menu() {
        // flag variable for while loop
        boolean isAmountCorrect = false;
        System.out.println("Welcome to the dollar game!" +
                "Give each vertex of the graph a number." +
                "The goal is to make sure that none of the vertices" +
                " have negative numbers anymore. " +
                "The moves you can make in the game are that either" +
                " a vertex can donate money (meaning for each vertex that" +
                " they’re connected to, they can give a dollar), or a " +
                "vertex can take money (meaning for each vertex that they’re " +
                "connected to, they can take a dollar). The goal of the game " +
                "is to make sure that every vertex is nonzero.");
        // checks if user entered the correct amount
        while(vertexAmount < 2 || vertexAmount > 7) {
            // try/catch block to prevent inputmissmatch exception
            try {
                System.out.println("Please enter the number of vertices you want to create" +
                        " (between 2-7, included): ");
                vertexAmount = input.nextInt();
            } catch(InputMismatchException e) {
                System.err.println("Incorrect format");
                input.next();
            }
        }
        // maximum amount of edge calculated based on a graph formula
        int maximumEdgeAmount = (vertexAmount*(vertexAmount-1))/2;
        // minimum amount of edge calculated based on a graph formula
        int minimumEdgeAmount = vertexAmount - 1;
        while(!isAmountCorrect){
            try {
                System.out.println("Please enter the number of edges you want to create: ");
                edgeAmount = input.nextInt();
                if (edgeAmount < minimumEdgeAmount) {
                    System.out.println("Minimum edge amount is below the limit");
                } else if(edgeAmount > maximumEdgeAmount){
                    System.out.println("Maximum edge amount is above the limit");
                } else {
                    isAmountCorrect = true;
                }
            } catch (InputMismatchException e) {
                System.err.println("Incorrect format");
                input.next();
            }
        }
    }
}