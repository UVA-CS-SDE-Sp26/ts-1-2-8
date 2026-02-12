/**
 * Command Line Interface
 * Part A
 * Creates Command Line interface and checks arguments to display correct output after program runs.
 */
public class TopSecret {

    private ProgramController controller;

    public TopSecret(){
        this.controller = new ProgramController();
    }

    public TopSecret(ProgramController controller) {
        this.controller = controller;
    }

    public String logic(String[] args){
        //if there are no arguments, list the numbered files available to display
        if (args.length == 0){
            return controller.listFiles();
        }
        //if there is one argument, open the file at that index (use the default key)
        else if (args.length == 1){
            return controller.displayFile(args[0], null);
        }
        //if two args, display the file at the index of the first argument and the key provided by the second argument
        else if (args.length == 2){
            return controller.displayFile(args[0], args[1]);
        }
        //if an invalid argument is used by user gives an error message and directions
        else{
            return "Invalid number of Arguments\nDesired Format: java topsecret [file number] [key file name]";
        }
    }

    public static void main(String[] args) {
        String output = new TopSecret().logic(args);
        System.out.println(output);
    }
}
