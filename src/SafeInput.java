import java.util.Scanner;

public class SafeInput
{
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString; // Set this to zero length. Loop runs until it isn't
        do
        {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }
        while(retString.length() == 0);

        return retString;
    }


    /**
     * Gets an integer from the user.
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a valid integer input from the user
     */
    public static int getInt(Scanner pipe, String prompt)
    {
        int retI = 0;
        boolean validInput = false;
        String trash;
        
        do
        {
            System.out.print("\n" + prompt + ": "); // show prompt add space

            if (pipe.hasNextInt())
            {
                retI = pipe.nextInt();
                pipe.nextLine(); // Clear the newline character from the input buffer
                validInput = true;
            }
            else
            {
                trash = pipe.nextLine(); // Read the invalid input
                System.out.println("You must enter an integer, not: " + trash);
            }
        }
        while (!validInput);

        return retI;
    }



    public static double getDouble(Scanner pipe, String prompt)
    {
        double retD = 0.0;
        boolean validInput = false;
        String trash;

        do
        {
            System.out.print("\n" + prompt + ": "); // show prompt add space

            if (pipe.hasNextDouble())
            {
                retD = pipe.nextDouble(); // Read the next line
                pipe.nextLine(); // Clear the newline character from the input buffer
                validInput = true;
            }
            else
            {
                trash = pipe.nextLine(); // Read the invalid input
                System.out.println("You must enter a double not: " + trash);
            }
        }
        while (!validInput);

        return retD;
    }


    /**
     * Gets an integer from the user within a specified range.
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low lower bound of the range (inclusive)
     * @param high upper bound of the range (inclusive)
     * @return a valid integer input from the user within the specified range
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int retRI = 0; // "RI" stands for "Ranged Integer"
        boolean validInput = false;
        String trash;

        do
        {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: "); // show prompt add space

            if (pipe.hasNextInt())
            {
                retRI = pipe.nextInt(); // Read the next line
                pipe.nextLine(); // Clear the newline character from the input buffer

                if (retRI < low || retRI > high)
                {
                    System.out.println("You must enter a value in range [" + low + " - " + high + "] not: " + retRI);
                }
                else
                {
                    validInput = true;
                }
            }
            else
            {
                trash = pipe.next(); // Read the invalid input
                System.out.println("You must enter an integer not: " + trash);
                pipe.nextLine();
            }
        }
        while (!validInput);

        return retRI;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double retRD = 0.0; // "RD" means "Ranged Double"
        boolean validInput = false;
        String trash;

        do
        {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: "); // show prompt add space

            if (pipe.hasNextDouble())
            {
                retRD = pipe.nextDouble(); // Read the next line
                pipe.nextLine(); // Clear the newline character from the input buffer

                if (retRD < low || retRD > high)
                {
                    System.out.println("You must enter a value in range [" + low + " - " + high + "] not : " + retRD);
                }
                else
                {
                    validInput = true;
                }
            }
            else
            {
                trash = pipe.nextLine(); // Read the invalid input
                System.out.println("You must enter a double not: " + trash);
            }
        }
        while (!validInput);

        return retRD;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        String response;
        do
        {
            System.out.print("\n" + prompt + " [Y/N]: "); // show prompt add space
            response = pipe.nextLine().trim().toUpperCase(); // Read the next line and trim spaces

            if (response.equals("Y"))
            {
                return true; // User confirmed with 'Y'
            }
            else if (response.equals("N"))
            {
                return false; // User declined with 'N'
            }
            else
            {
                System.out.println("Please enter 'Y' for Yes or 'N' for No.");
            }
        }
        while (true);
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        String value;
        boolean gotValue = false;

        do
        {
            //show the prompt
            System.out.print("\n" + prompt + ": "); // show prompt add space
            // input the value
            value = pipe.nextLine(); // Read the next line and trim spaces
            // check if the value matches the regex
            if (value.matches(regEx))
            {
                gotValue = true; // String matches the regex!
            }
            else
            {
                System.out.println("Invalid input: " + value);
            }
        }
        while (!gotValue);

        return value;
    }

    public static void prettyHeader(String msg)
    {
        String message = msg; //Message Centered Here
        int spaceCount = (54 - message.length()) / 2;

        for (int i = 0; i < 60; i++)
        {
            System.out.print("*");
        }

        System.out.println();
        System.out.print("***");

        for (int i = 0; i < spaceCount; i++)
        {
            System.out.print(" ");
        }

        System.out.print(message);

        for (int i = 0; i < spaceCount; i++)
        {
            System.out.print(" ");
        }
        if ((54 - message.length()) % 2 != 0)
        {
            System.out.print(" ");
        }
        System.out.print("***");

        System.out.println();
        for (int i = 0; i < 60; i++)
        {
            System.out.print("*");
        }

    }
}

// this is the version of SafeInput used for lab 13