package ecommerce;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        CartManager cartManager = new CartManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    productManager.listProducts();
                    break;
                case 2:
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    cartManager.addToCart(productId, quantity);
                    break;
                case 3:
                    cartManager.viewCart();
                    break;
                case 4:
                    System.out.println("Thank you for shopping!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
