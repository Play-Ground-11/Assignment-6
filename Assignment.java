import java.util.Scanner;

class Assignment{


    final static String CLEAR = "\033[H\033[2J";
    final static String COLOR_BLUE_BOLD = "\033[34;1m";
    final static String COLOR_RED_BOLD = "\033[31;1m";
    final static String COLOR_GREEN_BOLD = "\033[33;1m";
    final static String RESET = "\033[0m";

    private static final String ERROR_MSG = String.format("%s%s%s",COLOR_RED_BOLD,"%s",RESET);
    private static final String SUCCESS_MSG = String.format("%s%s%s",COLOR_GREEN_BOLD,"%s",RESET);
    
    private static String[][] accounts = new String[0][];

 private static final Scanner SCANNER = new Scanner(System.in); 
    public static void main(String[] args) {

        final String DASHBOARD = "👷 Welcome to Smart Banking";
        final String CREATE_ACCOUNT = "➕ Open New Account";
        final String DEPOSITS = "➕ Deposit Money";
        final String WITHDRAWALS = "➕ Withdraw Money";
        final String TRANSFER = "➕ Transfer Money";
        final String CHECK_BALANCE = "➕ Check Acoount Balance";
        final String DELETE_ACCOUNT = "❌ Drop Exisiting Account";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String[] accountHolders = new String[0];
        String screen = DASHBOARD;

        do{
            final String APP_TITLE = String.format("%s%s%s",COLOR_BLUE_BOLD,screen,RESET);

            System.out.println(CLEAR);
            System.out.println("\t"+APP_TITLE+"\n");

            switch(screen){
                case DASHBOARD: 
                    System.out.println("\t[1]. Open New Account");
                    System.out.println("\t[2]. Deposit Money");
                    System.out.println("\t[3]. Withdraw Money");
                    System.out.println("\t[4]. Transfer Money");
                    System.out.println("\t[5]. Check Acoount Balance");
                    System.out.println("\t[6]. Drop Exisiting Account");
                    System.out.println("\t[7]. Exit\n");
                    System.out.print("\tEnter an option to continue: ");
                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch (option){
                        case 1: screen = CREATE_ACCOUNT; break;
                        case 2: screen = DEPOSITS; break;
                        case 3: screen = WITHDRAWALS; break;
                        case 4: screen = TRANSFER; break;
                        case 5: screen = CHECK_BALANCE; break;
                        case 6: screen = DELETE_ACCOUNT; break;
                        case 7: System.out.println(CLEAR); System.exit(0);
                        default: continue;
                    }
                    break;
                    
                case CREATE_ACCOUNT:
                    String id = String.format("ID: SDB-%04d",accounts.length+1);
                    System.out.printf("\t%s\n",id);
                    String name = nameValidation();
                    double deposit = depositValidation();

                    String[][] tempAccounts = new String[accounts.length+1][3];

                    for (int i = 0; i < accounts.length; i++) {
                        tempAccounts[i] = accounts[i];
                    }

                    tempAccounts[tempAccounts.length-1][0] = id; 
                    tempAccounts[tempAccounts.length-1][1] = name; 
                    tempAccounts[tempAccounts.length-1][2] = deposit+""; 

                    accounts = tempAccounts;

                    System.out.println();
                    System.out.printf(SUCCESS_MSG,String.format("%s:%s created successfully\n",id,name));
                    System.out.print("\tDo you want to add another account? (Y/N) ");
                    if (!SCANNER.nextLine().toUpperCase().strip().equals("Y"))
                    screen = DASHBOARD;
                    break;
            }
        }while(true);

    }

    private static String nameValidation(){
        boolean valid;
        String name;

        do{
            valid = true;
            System.out.print("\tEnter Account holder's Name: ");
            name = SCANNER.nextLine().strip();

            if(name.isBlank()){
                valid = false;
                System.out.printf(ERROR_MSG,"\tName can't be empty.\n");
            }else if(name.length() < 3){
                valid = false;
                System.out.printf(ERROR_MSG,"\tInvalid name.\n");
            }else{
                for (int i = 0; i < name.length(); i++) {
                    if(!Character.isLetter(name.charAt(i))){
                        valid = false;
                        System.out.printf(ERROR_MSG,"\tInvalid name.\n");
                    }
                }
            }
        }while(!valid);
        return name;
    }

    public static double depositValidation(){
        boolean valid;
        double deposit;

        do{
            valid = true;
            System.out.print("\tEnter initial deposit: ");
            deposit = SCANNER.nextDouble();
            SCANNER.nextLine();

            if(deposit < 5000){
                valid = false;
                System.out.printf(ERROR_MSG,"\tInvalid deposit.\n");
            }
        }while(!valid);

        return deposit;
    }
    
}
