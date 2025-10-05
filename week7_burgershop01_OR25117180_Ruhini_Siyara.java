import java.util.Scanner;

public class week7_burgershop01_OR25117180_Ruhini_Siyara{
	final static double BURGERPRICE=500.0;
	public static final int PREPARING=0;
	public static final int DELIVERED=1;
	public static final int CANCEL=2;
	static String[] orderIdArray=new String[1000];
	static String[] Customer_phoneArray=new String[1000];
	static String[] customerNameArray=new String[1000];
	static int[] Order_QuantitiesArray=new int[1000];
	static int[] Order_StatusesArray=new int[1000];
	static int orderCount=0;
	static Scanner input=new Scanner(System.in);
	static int last_order_num=0;
	public static Scanner sc=new Scanner(System.in);

	public static void main(String args[]){
		while(true){
			clearConsole();
			System.out.println("-----------------------------------------------------------------");
			System.out.println("|\t\t\t iHungry Burger \t\t\t|");
			System.out.println("-----------------------------------------------------------------");
			System.out.print("\n [1] Place Order");
			System.out.println("\t\t [2] Search Best Customer");
			System.out.print(" [3] Search Order");
			System.out.println("\t\t [4] Search Customer");
			System.out.print(" [5] View Orders ");
			System.out.println("\t\t [6] Update Order Details");
			System.out.println(" [7] Exit");
			System.out.print("\nEnter an option to continue > ");
			int op=input.nextInt();
			switch(op){
				case 1:
					clearConsole();
					placeOrder();
					break;
				case 2:
					SearchBestCustomer();
					break;
				case 3:
					Search_OrderDetails();
					break;
				case 4:
					searchCustomer();
					break;
				case 5:
					viewOrders();
					break;
				case 6:
					updateOrderDetails();
					break;
				case 7:
					exit();
					break;
				default:
					System.out.println("Invalid option...");
					input.nextLine();
			}
		}
	}

	public static void placeOrder(){
		L1:do{
			clearConsole();
			System.out.println("-------------------------------------------------");
			System.out.println("|\t\t     Place Order     \t\t|");
			System.out.println("-------------------------------------------------");
			String orderId=generateOrderId();
			
			System.out.println("\n\nORDER ID - "+orderId);
			System.out.println("======================");
			
			System.out.print("\n\nEnter Customer ID (phone no.): ");
			String Customer_phone=input.next();
			if(Customer_phone.length()==10 & Customer_phone.charAt(0)=='0'){
				String Customer_Name="";
				int search=SearchCustomer_phone(Customer_phone);
				if(search!=-1){
					Customer_Name=customerNameArray[search];
					System.out.print("Customer Name : "+Customer_Name);
					System.out.println();
				}else{
					System.out.print("Customer Name : ");
					Customer_Name=input.next();
				}
				int Quantity;
				do{
					System.out.print("Enter Burger Quantity - ");
					Quantity=input.nextInt();
					if(Quantity<=0) System.out.println("Quantity must be greater than 0. Add Quantity again");
				}while(Quantity<=0);
				double total=Quantity*BURGERPRICE;
				System.out.print("Total_Value - "+total);
				L2:do{
					System.out.print("\n\tAre you confirm order (Y/N) - ");
					char op=input.next().charAt(0);
					if(op=='Y'||op=='y'){
						if(orderCount>=1000) System.out.println("Cannot add more orders. Storage full.");
						else{
							orderIdArray[orderCount]=orderId;
							Customer_phoneArray[orderCount]=Customer_phone;
							customerNameArray[orderCount]=Customer_Name;
							Order_QuantitiesArray[orderCount]=Quantity;
							Order_StatusesArray[orderCount]=PREPARING;
							orderCount++;
							System.out.print("\n\t Your Order Is Enter To The System Sucessfully...\n");
						}
					}else if(op=='N'||op=='n') continue L1;
					else{
						System.out.println("\n\t Invalid option..Try a given option...");
						continue L2;
					}
					System.out.print("\nDo you want to place another order (Y/N)> ");
					char op1=input.next().charAt(0);
					if(op1=='Y'||op1=='y') continue L1;
					else return;
				}while(true);
			}else{
				System.out.print("Invalid input...");
				;
			}
		}while(true);
	}

	public static void Search_OrderDetails(){
	L1:do{
		clearConsole();
		System.out.println("---------------------------------------------------------------------");
		System.out.println("|\t\t          SEARCH ORDER DETAILS          \t\t|");
		System.out.println("---------------------------------------------------------------------\n");
		System.out.print("Enter Order ID - ");
		String orderId=input.next();
		int index=-1;
		for(int i=0;i<orderCount;i++){
			if(orderIdArray[i].length()==orderId.length()){
				boolean match=true;
				for(int j=0;j<orderId.length();j++){
					char c1=orderIdArray[i].charAt(j);
					char c2=orderId.charAt(j);
					if(c1>='a'&&c1<='z') c1=(char)(c1-32);
					if(c2>='a'&&c2<='z') c2=(char)(c2-32);
					if(c1!=c2){
						match=false;
						break;
					}
				}
				if(match){ index=i; break; }
			}
		}
		if(index == -1){
		System.out.println("\nInvalid Order ID.");
		System.out.print("Do you want to enter again? (Y/N) > ");
		char op = input.next().charAt(0);
		if(op == 'Y' || op == 'y'){
			clearConsole();
			continue L1; 
		} else if(op == 'N' || op == 'n'){
        clearConsole();
        promptReturnToMenuOrStay();
        return; 
		} else {
        System.out.println("\n\tInvalid option...");
        continue L1; 
    }
}

		System.out.println("\n----------------------------------------------------------------");
		System.out.printf("%-8s %-12s %-8s %-8s %10s %12s|\n", "OrderID", "CustomerID", "Name", "Quantity", "OrderValue", "OrderStatus");
		System.out.println("----------------------------------------------------------------");
		double value=Order_QuantitiesArray[index]*BURGERPRICE;
		String status=statusToString(Order_StatusesArray[index]);
		System.out.printf("%-8s %-12s %-8s %-8d %10.2f %12s|\n",orderIdArray[index],Customer_phoneArray[index],customerNameArray[index],Order_QuantitiesArray[index],value,status);
		System.out.println("----------------------------------------------------------------");
		System.out.println();
		L3:do{
			System.out.print("\nDo you want to search another order details (Y/N): ");
			char op=input.next().charAt(0);
			if(op=='Y'||op=='y'){
				continue L1;
			}else if(op=='N'||op=='n'){
				clearConsole();
				promptReturnToMenuOrStay();
			}else{
				System.out.println("\n\tInvalid option...\n");
				continue L3;
			}
		}while(true);
	}while(true);
}

	public static void searchCustomer(){
	L1:do{
		clearConsole();
		System.out.println("-----------------------------------------------------------------");
		System.out.println("|\t\t\tSEARCH CUSTOMER DETAILS\t\t\t|");
		System.out.println("-----------------------------------------------------------------");
		System.out.print("\n\nEnter Customer Id (phone no.) - ");
		String Customer_ID=input.next();

		boolean valid=true;
		if(Customer_ID.length()!=10 || Customer_ID.charAt(0)!='0') valid=false;
		else{
			for(int i=0;i<Customer_ID.length();i++){
				char c=Customer_ID.charAt(i);
				if(c<'0' || c>'9'){
					valid=false;
					break;
				}
			}
		}

		if(!valid){
			System.out.println("\nInvalid phone number.Enter again.");
			System.out.print("\n\tDo you want to search another customer details (Y/N): ");
			char op=input.next().charAt(0);
			if(op=='Y'||op=='y') continue L1;
			else if(op=='N'||op=='n'){
				clearConsole();
				promptReturnToMenuOrStay();
				return;
			}else{
				System.out.println("Invalid...");
				return;
			}
		}

		String name="";
		boolean found=false;
		for(int i=0;i<orderCount;i++){
			if(Customer_phoneArray[i].equals(Customer_ID)){
				name=customerNameArray[i];
				found=true;
				break;
			}
		}

		if(!found) System.out.println("\n\tThis customer ID is not added yet...\n");
		else{
			System.out.println("\n\nCustomer_ID \t- "+Customer_ID);
			System.out.println("Name \t- "+name);
			System.out.println("\nCustomer Order Details");
			System.out.println("==========================\n");
			System.out.println("--------------------------------------------");
			System.out.printf("%-10s %-15s %-8s\n","Order_ID","Order_Quantity","Total_Value");
			System.out.println("--------------------------------------------");
			for(int i=0;i<orderCount;i++){
				if(Customer_phoneArray[i].equals(Customer_ID)){
					double Order_Value=Order_QuantitiesArray[i]*BURGERPRICE;
					System.out.printf("%-10s %-15d %-15.2f\n",orderIdArray[i],Order_QuantitiesArray[i],Order_Value);
					System.out.println("--------------------------------------------\n");
				}
			}
		}

		L3:do{
			System.out.print("Do you want to search another customer details (Y/N): ");
			char op=input.next().charAt(0);
			if(op=='Y'||op=='y') continue L1;
			else if(op=='N'||op=='n'){
				clearConsole();
				promptReturnToMenuOrStay();
				return;
			}else{
				System.out.println("Invalid...");
				continue L3;
			}
		}while(true);

	}while(true);
}


	public static void viewOrders() {
    L1: do {
        clearConsole();
        System.out.println("---------------------------------------------------------");
        System.out.println("|\t\t       VIEW ORDER LIST       \t\t|");
        System.out.println("---------------------------------------------------------\n");
        System.out.println("[1] Delivered Orders");
        System.out.println("[2] Preparing Orders");
        System.out.println("[3] Cancel Orders");
        System.out.print("\nEnter an option to continue > ");
        int opt = input.nextInt();
        switch (opt) {
            case 1:
                viewDeliveredOption();
                break;
            case 2:
                viewPreparingOption();
                break;
            case 3:
                viewCancelOption();
                break;
            default:
                System.out.println("\n\tInvalid option...");
                L2: do {
                    System.out.print("\t\nWant to view order list again (Y/N): ");
                    char choice = input.next().charAt(0);
                    if (choice == 'Y' || choice == 'y') {
                        clearConsole();
                        continue L1;
                    } else if (choice == 'N' || choice == 'n') {
                        clearConsole();
                        promptReturnToMenuOrStay();
                        break L1;  
                    } else {
                        System.out.println("\tInvalid input. Enter Y or N.");
                        continue L2;
                    }
                } while (true);
        }
    } while (true);
}


public static void viewDeliveredOption() {
    clearConsole();
    System.out.println("-------------------------------------------------------------------");
    System.out.println("|                          DELIVERED ORDER                        |");
    System.out.println("-------------------------------------------------------------------\n");

    boolean found = false;
    System.out.println("------------------------------------------------------------");
    System.out.printf("%-10s %-15s %-10s %-10s %-15s\n", "OrderId", "CustomerID", "Name", "Quantity", "OrderValue");
    System.out.println("------------------------------------------------------------");

    for (int i = 0; i < orderCount; i++) {
        if (Order_StatusesArray[i] == DELIVERED) {
            double value = Order_QuantitiesArray[i] * BURGERPRICE;
            System.out.printf("%-10s %-15s %-10s %-10d %-15.2f\n",
                    orderIdArray[i], Customer_phoneArray[i], customerNameArray[i],
                    Order_QuantitiesArray[i], value);
            System.out.println("------------------------------------------------------------");
            found = true;
        }
    }
    if (!found) System.out.println("No delivered orders...");

    askReturnToMenu();
}

public static void viewPreparingOption() {
    clearConsole();
    System.out.println("-------------------------------------------------------------------");
    System.out.println("|                        PREPARING ORDER                          |");
    System.out.println("-------------------------------------------------------------------\n");

    boolean found = false;
    System.out.println("------------------------------------------------------------");
    System.out.printf("%-10s %-15s %-10s %-10s %-15s\n", "OrderId", "CustomerID", "Name", "Quantity", "OrderValue");
    System.out.println("------------------------------------------------------------");

    for (int i = 0; i < orderCount; i++) {
        if (Order_StatusesArray[i] == PREPARING) {
            double value = Order_QuantitiesArray[i] * BURGERPRICE;
            System.out.printf("%-10s %-15s %-10s %-10d %-15.2f\n",
                    orderIdArray[i], Customer_phoneArray[i], customerNameArray[i],
                    Order_QuantitiesArray[i], value);
            System.out.println("------------------------------------------------------------");
            found = true;
        }
    }
    if (!found) System.out.println("No preparing orders...");

    askReturnToMenu();
}

public static void viewCancelOption() {
    clearConsole();
    System.out.println("-------------------------------------------------------------------");
    System.out.println("|                          CANCEL ORDER                           |");
    System.out.println("-------------------------------------------------------------------\n");

    boolean found = false;
    System.out.println("------------------------------------------------------------");
    System.out.printf("%-10s %-15s %-10s %-10s %-15s\n", "OrderId", "CustomerID", "Name", "Quantity", "OrderValue");
    System.out.println("------------------------------------------------------------");

    for (int i = 0; i < orderCount; i++) {
        if (Order_StatusesArray[i] == CANCEL) {
            double value = Order_QuantitiesArray[i] * BURGERPRICE;
            System.out.printf("%-10s %-15s %-10s %-10d %-15.2f\n",
                    orderIdArray[i], Customer_phoneArray[i], customerNameArray[i],
                    Order_QuantitiesArray[i], value);
            System.out.println("------------------------------------------------------------");
            found = true;
        }
    }
    if (!found) System.out.println("No cancelled orders...");

    askReturnToMenu();
}


public static void askReturnToMenu() {
    while (true) {
        System.out.print("\nDo you want to go to home page (Y/N): ");
        char ch = input.next().charAt(0);
        if (ch == 'Y' || ch == 'y') {
            clearConsole();
            promptReturnToMenuOrStay();
            break;
        } else if (ch == 'N' || ch == 'n') {
            System.out.println("Staying here...");
            break; 
        } else {
            System.out.println("Invalid input. Enter Y or N.");
        }
    }
}


	public static void updateOrderDetails(){
		L1:do{
			clearConsole();
			System.out.println("-----------------------------------------------------------");
			System.out.println("|                  UPDATE ORDER DETAILS                   |");
			System.out.println("-----------------------------------------------------------");
			System.out.println();
			System.out.print("Enter order Id - ");
			String orderId=input.next();
			
			int index=-1;
		for(int i=0;i<orderCount;i++){
			if(orderIdArray[i].length()==orderId.length()){
				boolean match=true;
				for(int j=0;j<orderId.length();j++){
					char c1=orderIdArray[i].charAt(j);
					char c2=orderId.charAt(j);
					if(c1>='a'&&c1<='z') c1=(char)(c1-32);
					if(c2>='a'&&c2<='z') c2=(char)(c2-32);
					if(c1!=c2){
						match=false;
						break;
					}
				}
				if(match){ index=i; break; }
			}
		}
		if(index == -1){
		System.out.println("\nInvalid Order ID.");
		L2:do{
		System.out.print("Do you want to update another order details (Y/N)> ");
		char op = input.next().charAt(0);
		if(op == 'Y' || op == 'y'){
			clearConsole();
			continue L1; 
		} else if(op == 'N' || op == 'n'){
        clearConsole();
        promptReturnToMenuOrStay();
        return; 
		} else {
        System.out.println("\n\tInvalid option...");
        continue L1; 
    }

}while(true);	
}	

			if(Order_StatusesArray[index]==DELIVERED||Order_StatusesArray[index]==CANCEL){
				System.out.println("\nThis Order cannot be updated..Already "+Order_StatusesArray[index] );
				L3:do{
					System.out.print("\nDo you want to update another order details (Y/N): ");
					char ch=input.next().charAt(0);
			if(ch=='N'||ch=='n') {
			clearConsole();
			promptReturnToMenuOrStay();
			}else if(ch=='Y'||ch=='y') {
					continue L1;
				}
				}while(true);
			}
			
			System.out.println("\nOrderID        -"+orderIdArray[index]);
			System.out.println("CustomerID     - "+Customer_phoneArray[index]);
			System.out.println("Name           - "+customerNameArray[index]);
			System.out.println("Quantity       - "+Order_QuantitiesArray[index]);
			System.out.println("OrderValue     - "+(Order_QuantitiesArray[index]*BURGERPRICE));
			System.out.println("Order_Statuses -  Preparing");
			System.out.println();
			System.out.println("What do you want to update ?");
			System.out.println("\t(01) Quantity");
			System.out.println("\t(02) Status");
			System.out.print("\nEnter your option - ");
			int update_Option=input.nextInt();
			if(update_Option==1){
				clearConsole();
				
				System.out.println("Quantity Update");
				System.out.println("=================\n");
				
				System.out.println("OrderID     - "+orderId);
				System.out.println("CustomerID  - "+Customer_phoneArray[index]);
				System.out.println("Name        - "+customerNameArray[index]);
				int Quantity;
				 do{
					System.out.print("\nEnter your quantity update value - ");
					Quantity=input.nextInt();
					if(Quantity<=0) System.out.println("\n\tInput only positive number...");
				}while(Quantity<=0);
				Order_QuantitiesArray[index]=Quantity;
				System.out.println("\n\tupdate order quantity success full...");
				System.out.println("\tnew order quantity - "+Quantity);
				System.out.println("new order value - "+(Quantity*BURGERPRICE));
				System.out.println();
			}else if(update_Option==2){
				clearConsole();
				
				System.out.println("Status Update");
				System.out.println("=================\n");
				System.out.println("OrderID     - "+orderId);
				System.out.println("CustomerID  - "+Customer_phoneArray[index]);
				System.out.println("Name        - "+customerNameArray[index]);
				System.out.println("\n\t(0)Cancel");
				System.out.println("\t(1)Preparing");
				System.out.println("\t(2)Delivered");
				
				int statusOp;
				do{
					System.out.print("\nEnter new order status - ");
					statusOp=input.nextInt();
					if(statusOp!=0&&statusOp!=1&&statusOp!=2) System.out.println("Invalid option...");
				}while(statusOp!=0&&statusOp!=1&&statusOp!=2);
				if (statusOp == 0) {
				Order_StatusesArray[index] = CANCEL;
					} else if (statusOp == 1) {
						Order_StatusesArray[index] = PREPARING;
					} else if (statusOp == 2) {
						Order_StatusesArray[index] = DELIVERED;
					} else {
					System.out.println("Place an order first"); 
					}

				System.out.println("\t\nupdate order status successfully...");
				System.out.println("\nnew order status - "+statusToString(Order_StatusesArray[index]));
				System.out.println();
			}else continue L1;
			L5:do{
				System.out.print("\nDo you want to update another order details (Y/N): ");
				char ch=input.next().charAt(0);
				if(ch=='Y'||ch=='y') continue L1;
				if(ch=='N'||ch=='n') return;
				continue L5;
			}while(true);
		}while(true);
	}

	public static void SearchBestCustomer(){
		clearConsole();
		System.out.println("-----------------------------------------------------------------");
		System.out.println("|\t\t             BEST COSTOMER             \t\t|");
		System.out.println("-----------------------------------------------------------------");
		if(orderCount==0){
			System.out.println("\n\tNo orders available yet.");
			input.nextLine();
			return;
		}
		String[] CustomerArray=new String[orderCount];
		String[] Customer_Names=new String[orderCount];
		double[] totalAmount=new double[orderCount];
		int uniqueCount=0;
		for(int i=0;i<orderCount;i++){
			boolean exists=false;
			for(int j=0;j<uniqueCount;j++){
				if(Customer_phoneArray[i].equals(CustomerArray[j])){
					exists=true;
					break;
				}
			}
			if(!exists){
				CustomerArray[uniqueCount]=Customer_phoneArray[i];
				Customer_Names[uniqueCount]=customerNameArray[i];
				uniqueCount++;
			}
		}
		for(int i=0;i<uniqueCount;i++){
			double total=0;
			for(int j=0;j<orderCount;j++){
				if(Customer_phoneArray[j].equals(CustomerArray[i])) total+=Order_QuantitiesArray[j]*BURGERPRICE;
			}
			totalAmount[i]=total;
		}
		for(int i=0;i<uniqueCount-1;i++){
			for(int j=i+1;j<uniqueCount;j++){
				if(totalAmount[i]<totalAmount[j]){
					double tempAmount=totalAmount[i];
					totalAmount[i]=totalAmount[j];
					totalAmount[j]=tempAmount;
					String tempId=CustomerArray[i];
					CustomerArray[i]=CustomerArray[j];
					CustomerArray[j]=tempId;
					String tempName=Customer_Names[i];
					Customer_Names[i]=Customer_Names[j];
					Customer_Names[j]=tempName;
				}
			}
		}
		System.out.println("\n\n------------------------------------------");
		System.out.printf("%-15s %-15s %8s\n", "CustomerID", "Name", "Total ");
		System.out.println("------------------------------------------");
		for(int i=0;i<uniqueCount;i++){
			System.out.printf("%-15s %-15s %8.2f\n",  CustomerArray[i],Customer_Names[i],totalAmount[i]);
			System.out.println("------------------------------------------");
		}
		L4:do{ System.out.print("\nDo you want to go back to main menu? (Y/N)> ");
		char op=input.next().charAt(0);
		if(op=='Y'|op=='y'){
			clearConsole();
			promptReturnToMenuOrStay();
		}else if(op=='N'|op=='n'){
			System.out.println("Stay here...");
			clearConsole();
			SearchBestCustomer();
			
		}else{		
			System.out.println("Invalid option..try Y/N..");
			
			continue L4;
			}
		}while(true);
	}

	public static int SearchCustomer_phone(String Customer_ID){
		for(int i=0;i<orderCount;i++){
			if(Customer_ID.equals(Customer_phoneArray[i])) return i;
		}
		return -1;
	}
	
	public static void MainMenuAsking1(){
		System.out.print("\nDo you want to go back to main menu? (Y/N)> ");
		char op=input.next().charAt(0);
		if(op=='Y'|op=='y'){
			clearConsole();
			promptReturnToMenuOrStay();
		}else if(op=='N'|op=='n'){
			clearConsole();
			SearchBestCustomer();
			
		}else{		
			System.out.println("Invalid option..try Y/N..");
			 MainMenuAsking1();
		}
	}
	
	public static void MainMenuAsking2(){
		System.out.print("\nDo you want to go back to main menu? (Y/N)> ");
		char op=input.next().charAt(0);
		if(op=='Y'|op=='y'){
			clearConsole();
			promptReturnToMenuOrStay();
		}else if(op=='N'|op=='n'){
			System.out.print("Stay here...");
			clearConsole();
			placeOrder();
		}else{		
			System.out.println("Invalid option..try Y/N..");
			 MainMenuAsking2();
			
		}
	}

	public static void promptReturnToMenuOrStay(){
		System.out.println("-----------------------------------------------------------------");
		System.out.println("|\t\t\t iHungry Burger \t\t\t|");
		System.out.println("-----------------------------------------------------------------");
		System.out.print("\n [1] Place Order");
		System.out.println("\t\t [2] Search Best Customer");
		System.out.print(" [3] Search Order");
		System.out.println("\t\t [4] Search Customer");
		System.out.print(" [5] View Orders ");
		System.out.println("\t\t [6] Update Order Details");
		System.out.println(" [7] Exit");
		System.out.print("\nEnter an option to continue > ");
		int op=input.nextInt();
		switch(op){
			case 1: clearConsole(); placeOrder(); break;
			case 2: SearchBestCustomer(); break;
			case 3: Search_OrderDetails(); break;
			case 4: searchCustomer(); break;
			case 5: viewOrders(); break;
			case 6: updateOrderDetails(); break;
			case 7: exit(); break;
			default: System.out.println("Invalid option..."); input.nextLine();
		}
	}

	public static String generateOrderId(){
		if(orderCount==0){
			last_order_num=1;
			return "B0001";
		}
		last_order_num++;
		return String.format("B%04d",last_order_num);
	}

	public static String statusToString(int s){
		if(s==PREPARING) return "PREPARING";
		if(s==DELIVERED) return "DELIVERED";
		if(s==CANCEL) return "CANCELLED";
		return "UNKNOWN";
	}

	public final static void clearConsole(){
		try{
			final String os=System.getProperty("os.name");
			if(os.contains("Windows")) new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			else{ System.out.print("\033[H\033[2J"); System.out.flush(); }
		}catch(Exception e){}
	}

	public static void exit(){
		clearConsole();
		System.out.println("-----------------------------------------------------------------");
		System.out.println("|\t\t\t iHungry Burger \t\t\t|");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("\nYou left the program...\n \tSee you again...");
		input.close();
		System.exit(0);
	}
}
