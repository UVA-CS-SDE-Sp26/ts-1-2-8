/**
 * Commmand Line Utility
 * part a --> command line interface
 * checking the number of arguments the user uses
 */
public class TopSecret {
    public static void main(String[] args) {
        ProgramController controller = new ProgramController();

        //if there are no arguments, list the numbered files available to display
        if (args.length == 0){
            String result = controller.listFiles();
            System.out.println(result);
        }
        //if there is one argument, open the file at that index (use the default key)
        else if (args.length == 1){
            String result = controller.displayFile(args[0], null);
            System.out.println(result);
        }
        //if two args, display the file at the index of the first argument and the key provided by the second argument
        else if (args.length == 2){
            String result = controller.displayFile(args[0], args[1]);
            System.out.println(result);
        }
        //if an invalid argument is used by user gives an error message and directions
        else{
            System.out.println("Invalid number of arguments");
            System.out.println("Correct Format: java topsecret [fileNumber] [optionalKey]");
        }

    }
}
