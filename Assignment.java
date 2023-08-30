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

    private static final String DASHBOARD = "👷 Welcome to Smart Banking";
    private static final String CREATE_ACCOUNT = "➕ Open New Account";
    private static final String DEPOSITS = "➕ Deposit Money";
    private static final String WITHDRAWALS = "➕ Withdraw Money";
    private static final String TRANSFER = "➕ Transfer Money";
    private static final String CHECK_BALANCE = "➕ Check Acoount Balance";
    private static final String DELETE_ACCOUNT = "❌ Drop Exisiting Account";

    private static String screen = DASHBOARD;

 private static final Scanner SCANNER = new Scanner(System.in); 
    public static void main(String[] args) {
        mainLoop:
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
                        default: continue mainLoop;
                    }
                    break;
                    
                case CREATE_ACCOUNT:
                    String id = String.format("SDB-%05d",accounts.length+1);
                    System.out.printf("\tID: %s\n",id);
                    String name = nameValidation();
                    double deposit = initialDepositValidation();

                    String[][] tempAccounts = new String[accounts.length+1][3];

                    for (int i = 0; i < accounts.length; i++) {
                        tempAccounts[i] = accounts[i];
                    }

                    tempAccounts[tempAccounts.length-1][0] = id; 
                    tempAccounts[tempAccounts.length-1][1] = name; 
                    tempAccounts[tempAccounts.length-1][2] = deposit+""; 

                    accounts = tempAccounts;

                    System.out.println();
                    System.out.printf(SUCCESS_MSG,String.format("\t%s:%s created successfully\n",id,name));
                    System.out.print("\tDo you want to add another account? (Y/N) ");
                    if (!SCANNER.nextLine().toUpperCase().strip().equals("Y"))
                    screen = DASHBOARD;
                    break;
                case DEPOSITS:
                    String accountNo = idValidation();
                    System.out.printf("\tA/C No: %s\n",accountNo);

                    Double currentBalance = Double.valueOf(getCurrentAccountBalance(accountNo));
                    System.out.printf("\tCurrent Balance: Rs %s\n",currentBalance);
                    Double newBalance = depositValidation(accountNo);
                    System.out.printf("\tNew Balance: Rs %s\n",newBalance);

                    System.out.println();
                    System.out.print("\tDo you want to continue? (Y/N) ");
                    if (!SCANNER.nextLine().toUpperCase().strip().equals("Y"))
                    screen = DASHBOARD;
                    break;
                case WITHDRAWALS:
                    
            }
        }while(true);

    }

    public static String idValidation(){
        String id;
        boolean continueInput = false;
         String exitId = null;
        do{
            System.out.print("\tEnter A/C No: ");
            id = SCANNER.nextLine().strip();

            if(id.isBlank()){
                System.out.printf(ERROR_MSG,"\tId cant't be empty\n");

                System.out.print("\tDo you want to try again? (Y/N) ");
                if(SCANNER.nextLine().equalsIgnoreCase("Y")) continueInput = true;
                else{
                    continueInput = false;
                    screen = DASHBOARD;
                }
                
            }else if(!id.startsWith("SDB-") || id.length() != 9){
                System.out.printf(ERROR_MSG, "\tInavalid id format\n");

                System.out.print("\tDo you want to try again? (Y/N) ");
                if(SCANNER.nextLine().equalsIgnoreCase("Y")) continueInput = true;
                else{
                    continueInput = false;
                    screen = DASHBOARD;
                }
                
            }else{
                String number = id.substring(5);
                for (int i = 0; i < number.length(); i++) {
                    if(!Character.isDigit(number.charAt(i))){
                        System.out.printf(ERROR_MSG,"\tInvalid id format.\n");

                        System.out.print("\tDo you want to try again? (Y/N) ");
                        if(SCANNER.nextLine().equalsIgnoreCase("Y")) continueInput = true;
                        else{
                            continueInput = false;
                            screen = DASHBOARD;
                        }
                    }
                }

                for (int i = 0; i < accounts.length; i++) {
                    if(accounts[i][0].equals(id)){
                        continueInput = false;
                        exitId = id;
                    }
                }
                if(exitId == null){
                    System.out.printf(ERROR_MSG,"\tAccount number not found!\n");

                    System.out.print("\tDo you want to try again? (Y/N) ");
                    if(SCANNER.nextLine().equalsIgnoreCase("Y")) continueInput = true;
                    else{
                        continueInput = false;
                        screen = DASHBOARD;
                    }
                }       
            }
        }while(continueInput);
        return exitId;
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

    public static double initialDepositValidation(){
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

    
    
    private static String getCurrentAccountBalance(String number){
        String currentBalance = null;
         for (int i = 0; i < accounts.length; i++) {
            if(accounts[i][0].equals(number)){
                currentBalance = accounts[i][2];
            }
         }
         return currentBalance;
    }

    private static Double depositValidation(String number){
        boolean valid;
        Double deposit;

        do{
            valid = true;
            System.out.print("\tEnter amount to deposit: ");
            deposit = SCANNER.nextDouble();
            SCANNER.nextLine();

            if(deposit < 500){
                valid = false;
                System.out.printf(ERROR_MSG,"\tInsufficient amount.\n");
            }
        }while(!valid);

        for (int i = 0; i < accounts.length; i++) {
            if(accounts[i][0].equals(number)){
                deposit += Double.valueOf(accounts[i][2]);
            }
        }

        return deposit;
    }
}
