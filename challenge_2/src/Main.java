import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    private static List<Product> productList;

    public static void main(String[] args) {

        productList = new ArrayList<>();

        productList.add(new Product("Laptop", 999.99, 5));
        productList.add(new Product("Smart phone", 499.99, 10));
        productList.add(new Product("Tablet", 299.99, 0));
        productList.add(new Product("Smartwatch", 199.99, 3));

        //Question 2.1
        System.out.println(allProductsInStock());
        System.out.println(mostExpensiveProduct());
        System.out.println(isProductInStock("Laptop"));

        //Question 2.2
        findMissingNumber();


    }

    private static Double allProductsInStock() {
        double result = 0;
        for(int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getQuantity() > 0) {
                result += productList.get(i).getPrice() * productList.get(i).getQuantity();
            }
        }
        return result;
    }

    private static String mostExpensiveProduct() {
        Product mostExpensiveProduct = productList.get(0);

        for(int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getPrice() > mostExpensiveProduct.getPrice()) {
                mostExpensiveProduct = productList.get(i);
            }
        }
        return "The most expensive product: " + mostExpensiveProduct.getName();
    }

    private static boolean isProductInStock(String productName) {
        for(int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getName().equals(productName)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private static void findMissingNumber() {
        int example[] = {3, 7, 1, 2, 6, 4};

        int n = 7;

        int expectedSum = n * (n + 1) / 2;

        int actualSum = 0;
        for (int num : example) {
            actualSum += num;
        }

        int missingNumber = expectedSum - actualSum;

        System.out.println("Missing number: " + missingNumber);
    }


}