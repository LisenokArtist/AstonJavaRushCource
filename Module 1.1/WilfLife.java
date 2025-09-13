/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/

public class Main
{
	public static void main(String[] args) {
		Cat cat = new Cat();
        cat.say();
        cat.hasPart();
        cat.hasHabitat();
        System.out.println();

        Bear bear = new Bear();
        bear.say();
        bear.hasPart();
        bear.hasHabitat();
        System.out.println();

        Whale whale = new Whale();
        whale.say();
        whale.hasPart();
        whale.hasHabitat();
        System.out.println();

        Fish fish = new Fish();
        fish.say();
        fish.hasPart();
        fish.hasHabitat();
        System.out.println();
	}
    
	// Animal parts interfaces
    interface IAnimalPart {
        boolean isHasPart();
    }

    interface ISpineAnimalPart extends IAnimalPart {
        boolean isHasSpinePart();
    }

    interface IFurAnimalPart extends IAnimalPart {
        boolean isHasFurPart();
    }

    // Animal habitats interfaces
    interface IHabitat {
        boolean isHasHabitat();
    }

    interface IWaterHabitat extends IHabitat {
        boolean isHasWaterHabitat();
    }

    // Abstracts
    public static abstract class Animal {
        public abstract void say();

        public abstract void hasPart();

        public void hasHabitat() {
            System.out.println("Animal has no available habitatas");
        }
    }

    public static abstract class Mammal extends Animal implements ISpineAnimalPart {
        @Override
        public boolean isHasSpinePart() {
            return true;
        }

        @Override
        public boolean isHasPart() {
            return true;
        }

        @Override
        public void say() {
            System.out.println("Animal makes a sound");
        }

        @Override
        public void hasPart() {
            System.out.println("Is has any parts: " + this.isHasPart());
            System.out.println("Spine part: " + this.isHasSpinePart());
        }

        @Override
        public void hasHabitat() {
            super.hasHabitat();
        }
    }

    // Entities
    public static class Cat extends Mammal implements IFurAnimalPart {
        @Override
        public boolean isHasFurPart() {
            return true;
        }

        @Override
        public void say() {
            System.out.println("The cat say mow!");
        }

        @Override
        public void hasPart() {
            super.hasPart();
            System.out.println("Fur part: " + this.isHasFurPart());
        }
    }

    public static class Bear extends Mammal implements IFurAnimalPart {
        @Override
        public boolean isHasFurPart() {
            return true;
        }

        @Override
        public void say() {
            System.out.println("The bear say wraar!");
        }

        @Override
        public void hasPart() {
            super.hasPart();
            System.out.println("Fur part: " + this.isHasFurPart());
        }
    }

    public static class Whale extends Mammal implements IWaterHabitat {
        @Override
        public boolean isHasHabitat() {
            return true;
        }

        @Override
        public boolean isHasWaterHabitat() {
            return true;
        }

        @Override
        public void say() {
            System.out.println("Whale makes an noise");
        }

        @Override
        public void hasHabitat() {
            System.out.println("Is has habitat: " + isHasHabitat());
            System.out.println("Is water habitat: " + isHasWaterHabitat());
        }
    }

    public static class Fish extends Animal implements IWaterHabitat {
        @Override
        public boolean isHasWaterHabitat() {
            return true;
        }

        @Override
        public boolean isHasHabitat() {
            return true;
        }

        @Override
        public void say() {
            System.out.println("Fish makes an bolp");
        }

        @Override
        public void hasPart() {
            System.out.println("Fish has no available parts");
        }

        @Override
        public void hasHabitat() {
            System.out.println("Is has habitat: " + isHasHabitat());
            System.out.println("Is water habitat: " + isHasWaterHabitat());
        }
    }
}