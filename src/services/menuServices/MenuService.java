package services.menuServices;

import java.util.Scanner;

public class MenuService {
    private static Scanner sc;

    public void renderMainMenu() {
        System.out.println();
        System.out.println("Choose option: ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");

        sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());
        switch (option) {
            case 1:
                UsersMenuService.renderUserLogin();
                break;
            case 2:
                UsersMenuService.renderUserRegister();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                renderMainMenu();
                break;
        }
    }
}
