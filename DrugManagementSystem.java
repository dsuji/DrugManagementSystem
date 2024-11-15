import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Drug {
    String name, code, expiryDate;
    int quantity;
    double price;

    Drug(String name, String code, int quantity, double price, String expiryDate) {
        this.name = name; this.code = code; this.quantity = quantity;
        this.price = price; this.expiryDate = expiryDate;
    }

    void reduceQuantity() { quantity--; }
    @Override
    public String toString() {
        return "Drug: " + name + ", Code: " + code + ", Qty: " + quantity + 
               ", Price: $" + price + ", Expiry: " + expiryDate;
    }
}

class Patient {
    String name, id;
    List<Drug> prescribedDrugs = new ArrayList<>();

    Patient(String name, String id) { this.name = name; this.id = id; }
    void addDrug(Drug drug) { prescribedDrugs.add(drug); }

    @Override
    public String toString() {
        return "Patient: " + name + ", ID: " + id + ", Drugs: " + prescribedDrugs;
    }
}

class DrugInventory {
    List<Drug> drugs = new ArrayList<>();
    
    void addDrug(Drug drug) { drugs.add(drug); }
    
    Drug findDrug(String code) {
        return drugs.stream().filter(d -> d.code.equals(code) && d.quantity > 0).findFirst().orElse(null);
    }
    void displayInventory() { drugs.forEach(System.out::println); }
}

public class DrugManagementSystem {
    public static void main(String[] args) {
        DrugInventory inventory = new DrugInventory();
        List<Patient> patients = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Drug  2. Show Inventory  3. Add Patient  4. Prescribe Drug  5. Show Patients  0. Exit");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.print("Enter drug name, code, quantity, price, expiry date: ");
                    inventory.addDrug(new Drug(scanner.next(), scanner.next(), scanner.nextInt(), scanner.nextDouble(), scanner.next()));
                }
                case 2 -> inventory.displayInventory();
                case 3 -> {
                    System.out.print("Enter patient name, ID: ");
                    patients.add(new Patient(scanner.next(), scanner.next()));
                }
                case 4 -> {
                    System.out.print("Enter patient ID, drug code: ");
                    Patient patient = patients.stream().filter(p -> p.id.equals(scanner.next())).findFirst().orElse(null);
                    Drug drug = inventory.findDrug(scanner.next());
                    if (patient != null && drug != null) {
                        patient.addDrug(drug); drug.reduceQuantity();
                    } else System.out.println("Patient or drug not found, or drug out of stock.");
                }
                case 5 -> patients.forEach(System.out::println);
                case 0 -> { scanner.close(); return; }
            }
        }
    }
}
