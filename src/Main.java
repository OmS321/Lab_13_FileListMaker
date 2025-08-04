import java.io.*;
//import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.util.Arrays;
import java.util.Scanner;

//import static java.nio.file.StandardOpenOption.CREATE;

public class Main
{

    private static boolean canSave = false;
    private static boolean isSaved = false;

    public static void main(String[] args)
    {

        Scanner input = new Scanner(System.in);

        printMenu(); // Print the menu options
        String choice = SafeInput.getRegExString(input, "Select your desired action from above",
                "[AaDdIiVvCcMmSsOoQq]");
        ArrayList<String> myArrayList = new ArrayList<>();
        boolean done = false;

        try
        {
            do {
                switch (choice.toUpperCase()) {
                    case "A":
                        addItem(myArrayList);
                        break;

                    case "D":
                        deleteItem(myArrayList);
                        break;

                    case "I":
                        insertItem(myArrayList);
                        break;

                    case "V":
                        viewList(myArrayList);
                        break;

                    case "C":
                        clearList(myArrayList);
                        break;

                    case "M":
                        moveItem(myArrayList);
                        break;

                    case "S":
                        saveList(myArrayList);
                        break;

                    case "O":
                        openList(myArrayList);
                        break;

                    case "Q":
                        if (quitProgram()) {
                            if (isSaved || !canSave) {
                                return;
                            } else {
                                boolean wantToSave = SafeInput.getYNConfirm(input, "You have unsaved changes. Do you want to save before quitting?");
                                // Exit the program without saving
                                if (wantToSave) {
                                    saveList(myArrayList);
                                } else {
                                    System.out.println("Exiting without saving changes.");
                                }
                                return; // Exit the program after saving
                            }
                        } else {
                            printMenu(); // Print the menu again if not quitting
                        }
                        break;

                    default:
                        System.out.println("Invalid choice, please try again.");
                }

                choice = SafeInput.getRegExString(input, "Press A (add), D (delete), I (insert), V (view), C (Clear), M (Move), S (SAVE), O (OPEN) or Q (QUIT)",
                        "[AaDdIiVvCcMmSsOoQq]");

            }
            while (!done);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found. Please check the file path and try again.");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while processing the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printMenu()
    {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("V - View the list");
        System.out.println("C - Clear the list");
        System.out.println("M - Move an item");
        System.out.println("S - Save the list to a file");
        System.out.println("O - Open the list from disk");
        System.out.println("Q - Quit the program");
    }

    //static methods for the list functions

    private static void addItem(ArrayList<String> list)
    {
        Scanner addInput = new Scanner(System.in);
        System.out.println("You chose to add an item.");

        list.add(SafeInput.getNonZeroLenString(addInput, "Enter the item to add"));
        canSave = true; // Set canSave to true when an item is added
    }

    private static void deleteItem(ArrayList<String> list)
    {

        Scanner deleteInput = new Scanner(System.in);
        System.out.println("You chose to delete an item.");

        if (!list.isEmpty())
        {
            list.remove((SafeInput.getRangedInt(deleteInput, "Enter the index of the item to delete",
                    0, list.size() - 1)));
            // Reset canSave if the list is empty after deletion
            canSave = !list.isEmpty(); // Set canSave to true when an item is deleted
        }
        else
        {
            System.out.println("\nNo items to delete.");
        }
    }

    private static void insertItem(ArrayList<String> list)
    {
        System.out.println("You chose to insert an item.");
        Scanner insertInput = new Scanner(System.in);

        int index = SafeInput.getRangedInt(insertInput, "Enter the index to insert the item at",
                0, (list.size()));
        String item = SafeInput.getNonZeroLenString(insertInput, "Enter the item to add");
        list.add(index, item);
        canSave = true; // Set canSave to true when an item is inserted
    }

    private static void viewList(ArrayList<String> list)
    {
        if (list.isEmpty())
        {
            System.out.println("No items in the list to view.");
        }
        else
        {
            for (int i = 0; i < list.size(); i++)
            {
                System.out.println("Index " + i + ": " + list.get(i));
            }
        }
    }

    private static void clearList(ArrayList<String> list)
    {
        if (!list.isEmpty())
        {
            list.clear();
            System.out.println("The list has been cleared.");
        }
        else
        {
            System.out.println("\nThe list is already empty.");
        }
    }

    private static void moveItem(ArrayList<String> list)
    {
        System.out.println("You chose to move an item.");
        Scanner moveInput = new Scanner(System.in);

        if (!list.isEmpty())
        {
            int fromIndex = SafeInput.getRangedInt(moveInput, "Enter the index of the item to move",
                    0, list.size() - 1);
            int toIndex = SafeInput.getRangedInt(moveInput, "Enter the index to move the item to",
                    0, list.size() - 1);

            if (fromIndex != toIndex)
            {
                String item = list.remove(fromIndex);
                list.add(toIndex, item);
                System.out.println("Item moved from index " + fromIndex + " to index " + toIndex + ".");
                canSave = true; // Set canSave to true when an item is moved
            }
            else
            {
                System.out.println("Item not moved, source and destination indices are the same.");
            }
        }
        else
        {
            System.out.println("\nNo items to move.");
        }
    }


    private static void saveList(ArrayList<String> list) throws FileNotFoundException, IOException
    {
        if (canSave)
        {
            File directory = new File(System.getProperty("user.dir"));
            String saveName = SafeInput.getNonZeroLenString(new Scanner(System.in), "Enter the name of the file to save the list to (without extension)");
            if (saveName.contains("."))
            {
                System.out.println("Invalid file name. Please do not include a file extension.");
                return; // Exit the method if the name is invalid
            }
            Path file = Paths.get(directory.getPath() + "\\src\\" + saveName + ".txt");


            OutputStream out =
                    new BufferedOutputStream(new FileOutputStream(file.toFile()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));

            for (int i = 0; i < list.size(); i += 1)
            {
                list.set(i, list.get(i));
                writer.write(list.get(i), 0, list.get(i).length());

                if (i < list.size() - 1) writer.print(", ");
            }

            writer.println();
            writer.close();

            System.out.println("Data file has been written to: " + file.toAbsolutePath());

            isSaved = true; // Set isSaved to true after saving
            canSave = false; // Reset canSave after saving
        }
        else
        {
            System.out.println("No changes to save.");
        }
    }

    private static void openList(ArrayList<String> list) throws FileNotFoundException, IOException
    {
        JFileChooser chooser = new JFileChooser();

        Path target = Paths.get(System.getProperty("user.dir")).resolve("src");
        String rec = "";
        // set the chooser to the current directory
        chooser.setCurrentDirectory(target.toFile());

        if (!canSave)
        {
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = chooser.getSelectedFile();
                Path filePath = selectedFile.toPath();

                BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));

                System.out.println("Loading file: " + filePath.toAbsolutePath() + "...\n");

                while (reader.ready())
                {
                    rec = reader.readLine();
                }

                reader.close();

                String[] items = rec.split(",\\s*"); // Split by comma and optional space

                list.clear();
                list.addAll(Arrays.asList(items)); // Add items to the list

                System.out.println(list);
            }
        }
        else
        {
            System.out.println("No saved data to open. Please save your changes first.");
        }
    }


    // the final method to quit the program
    private static boolean quitProgram()
    {
        Scanner quitInput = new Scanner(System.in);

        System.out.println("\nYou chose to quit the program.");
        return SafeInput.getYNConfirm(quitInput, "Are you sure you want to quit?");
    }
}