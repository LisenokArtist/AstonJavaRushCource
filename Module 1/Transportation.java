/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/
import java.util.*;
public class Main
{
	public static void main(String[] args) {
        List<Vehicle> vehicles = Arrays.asList(
            new Plane(),
            new Helicopter(),
            new Boat(),
            new Tanker(),
            new Truck(),
            new Taxi()
        );

        vehicles.forEach(v -> {
            v.hasParts();
            System.out.println();
        });
	}
	
    // Parts interfaces
	interface IPart {
        boolean hasPart();
    }

    interface IWheelPart extends IPart {
        boolean hasWheelPart();
    }

    interface IPropellerPart extends IPart {
        boolean hasPropeller();
    }

    interface IWingsPart extends IPart {
        boolean hasWings();
    }

    interface ITransportation extends IPart {
        boolean hasTransportation();
    }

    // Abstracts
    public static abstract class Vehicle implements IPart {
        @Override
        public boolean hasPart() {
            return true;
        }

        public abstract void hasParts();
    }

    // Entities
    public static class Plane extends Vehicle implements IWheelPart, IPropellerPart, IWingsPart, ITransportation {
        @Override
        public boolean hasWheelPart() { return true; }

        @Override
        public boolean hasPropeller() { return true; }

        @Override
        public boolean hasWings() { return true; }

        @Override
        public boolean hasTransportation() { return true; }

        @Override
        public void hasParts() {
            System.out.println("Plane");
            System.out.println("Has wheel: " + hasWheelPart());
            System.out.println("Has propeller: " + hasPropeller());
            System.out.println("Has wings: " + hasWings());
            System.out.println("Is transport: " + hasTransportation());
        }
    }

    public static class Helicopter extends Vehicle implements IWheelPart, IPropellerPart, ITransportation {
        @Override
        public boolean hasWheelPart() { return true; }

        @Override
        public boolean hasPropeller() { return true; }

        @Override
        public boolean hasTransportation() { return true; }

        @Override
        public void hasParts() {
            System.out.println("Helicopter");
            System.out.println("Has wheel: " + hasWheelPart());
            System.out.println("Has propeller: " + hasPropeller());
            System.out.println("Is transport: " + hasTransportation());
        }
    }

    public static class Boat extends Vehicle implements IPropellerPart, ITransportation {
        @Override
        public boolean hasPropeller() { return true; }

        @Override
        public boolean hasTransportation() { return true; }

        @Override
        public void hasParts() {
            System.out.println("Boat");
            System.out.println("Has propeller: " + hasPropeller());
            System.out.println("Is transport: " + hasTransportation());
        }
    }

    public static class Tanker extends Boat {
        @Override
        public void hasParts() {
            System.out.println("Tanker");
            System.out.println("Has propeller: " + hasPropeller());
            System.out.println("Is transport: " + hasTransportation());
        }
    }

    public static class Truck extends Vehicle implements IWheelPart, ITransportation {
        @Override
        public boolean hasWheelPart() { return true; }

        @Override
        public boolean hasTransportation() { return true; }

        @Override
        public void hasParts() {
            System.out.println("Truck");
            System.out.println("Has wheel: " + hasWheelPart());
            System.out.println("Is transport: " + hasTransportation());
        }
    }

    public static class Taxi extends Vehicle implements IWheelPart {
        @Override
        public boolean hasWheelPart() { return true; }

        @Override
        public void hasParts() {
            System.out.println("Taxi");
            System.out.println("Has wheel: " + hasWheelPart());
        }
    }
}