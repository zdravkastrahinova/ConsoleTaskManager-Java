import services.menuServices.MenuService;

public class Program {
	public static void main(String[] args) {
		MenuService menuService = new MenuService();
		menuService.renderMainMenu();
	}
}
