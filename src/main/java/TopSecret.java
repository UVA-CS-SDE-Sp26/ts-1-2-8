/**
 * Command Line Interface
 * Part A
 * Creates Command Line interface and checks arguments to display correct output after program runs.
 */
public class TopSecret {

    private ProgramController controller;

    public TopSecret{
        this.controller = new ProgramController();
    }

    public void logic(String[] args){
        //if there are no arguments, list the numbered files available to display
        if (args.length == 0){
            String output = controller.listFiles();
            System.out.println(output);
        }
        //if there is one argument, open the file at that index (use the default key)
        else if (args.length == 1){
            String output = controller.displayFile(args[0], null);
            System.out.println(output);
        }
        //if two args, display the file at the index of the first argument and the key provided by the second argument
        else if (args.length == 2){
            String output = controller.displayFile(args[0], args[1]);
            System.out.println(output);
        }
        //if an invalid argument is used by user gives an error message and directions
        else{
            System.out.println("Invalid number of Arguments");
            System.out.println("Desired Format: java topsecret [file number] [key file name]");
        }
    }

    public static void main(String[] args) {
        new TopSecret.logic(args);
    }
}
