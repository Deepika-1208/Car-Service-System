import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

// Vehicle Class (Superclass)
class Vehicle {
    private String registrationNumber;
    private String model;
    private int mileage;

    public Vehicle(String registrationNumber, String model, int mileage) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.mileage = mileage;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "Registration='" + registrationNumber + '\'' +
                ", Model='" + model + '\'' +
                ", Mileage=" + mileage +
                '}';
    }
}

// Car Class (Subclass of Vehicle)
class Car extends Vehicle {
    private String ownerName;
    private String phoneNumber; //  field for phone number

    public Car(String registrationNumber, String model, int mileage, String ownerName, String phoneNumber) {
        super(registrationNumber, model, mileage);  // Call to superclass constructor
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Car{" +
                "Registration='" + getRegistrationNumber() + '\'' +
                ", Model='" + getModel() + '\'' +
                ", Mileage=" + getMileage() +
                ", Owner='" + ownerName + '\'' +
                ", Phone='" + phoneNumber + '\'' +
                '}';
    }
}

// ServiceSchedule Class
class ServiceSchedule {
    private Vehicle vehicle;
    private LocalDate serviceDate;
    private String serviceDetails;

    public ServiceSchedule(Vehicle vehicle, LocalDate serviceDate, String serviceDetails) {
        this.vehicle = vehicle;
        this.serviceDate = serviceDate;
        this.serviceDetails = serviceDetails;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    // Method to calculate how many days until the next service
    public long daysUntilService(LocalDate currentDate) {
        return ChronoUnit.DAYS.between(currentDate, serviceDate);
    }

    @Override
    public String toString() {
        return "ServiceSchedule{" +
                "Vehicle=" + vehicle +
                ", ServiceDate=" + serviceDate +
                ", ServiceDetails='" + serviceDetails + '\'' +
                '}';
    }
}

// CarServiceSystem Class
public class CarServiceSystem {
    private ArrayList<ServiceSchedule> serviceSchedules = new ArrayList<>();

    // Adding a new service schedule
    public void addServiceSchedule(ServiceSchedule schedule) {
        serviceSchedules.add(schedule);
        System.out.println("Service scheduled for vehicle: " + schedule.getVehicle().getRegistrationNumber());
    }

    // Save schedules to a file
    public void saveSchedulesToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (ServiceSchedule schedule : serviceSchedules) {
            writer.write(schedule.toString() + "\n");
        }
        writer.close();
    }

    // Load schedules from a file
    public void loadSchedulesFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    // Main method
    public static void main(String[] args) throws IOException {
        CarServiceSystem system = new CarServiceSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter vehicle details:");

        System.out.print("Registration Number: ");
        String registration = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Mileage: ");
        int mileage = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Owner Name: ");
        String owner = scanner.nextLine();

        System.out.print("Phone Number: "); //  prompt for phone number
        String phoneNumber = scanner.nextLine();

        // Create a Car object (which is a Vehicle)
        Car car = new Car(registration, model, mileage, owner, phoneNumber);

        System.out.print("Enter Service Date (YYYY-MM-DD): ");
        LocalDate serviceDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Service Details: ");
        String serviceDetails = scanner.nextLine();

        // Create a service schedule
        ServiceSchedule schedule = new ServiceSchedule(car, serviceDate, serviceDetails);
        system.addServiceSchedule(schedule);

        // Calculate days until the service date
        LocalDate currentDate = LocalDate.now();
        long daysUntilService = schedule.daysUntilService(currentDate);

        // Show result to the owner
        System.out.println("\nScheduled Services:");
        System.out.println(schedule);
        System.out.println("The vehicle will be serviced in " + daysUntilService + " day(s).");

        // Save schedules to a file
        system.saveSchedulesToFile("service_schedules.txt");

        // Display all saved service schedules
        system.loadSchedulesFromFile("service_schedules.txt");
    }
}
