//The solution can be compiled at https://www.programiz.com/csharp-programming/online-compiler/
using System;

public class Wildlife
{
    public static void Main(string[] args)
    {
        var cat = new Cat();
        cat.Say();
        cat.HasPart();
        cat.HasHabitat();
        Console.WriteLine();
        
        var bear = new Bear();
        bear.Say();
        bear.HasPart();
        bear.HasHabitat();
        Console.WriteLine();
        
        var whale = new Whale();
        whale.Say();
        whale.HasPart();
        whale.HasHabitat();
        Console.WriteLine();
        
        var fish = new Fish();
        fish.Say();
        fish.HasPart();
        fish.HasHabitat();
        Console.WriteLine();
    }
    
    #region Animal parts interfaces
    interface IAnimalPart
    {
        bool IsHasPart { get; }
    }

    interface ISpineAnimalPart : IAnimalPart
    {
        bool IsHasSpinePart { get; }
    }

    interface IFurAnimalPart : IAnimalPart
    {
        bool IsHasFurPart { get; }
    }
    #endregion

    #region Animal habitats interfaces
    interface IHabitat
    {
        bool IsHasHabitat { get; }
    }

    interface IWaterHabitat : IHabitat
    {
        bool IsHasWaterHabitat { get; }
    }
    #endregion

    #region Abstracts
    public abstract class Animal
    {
        public abstract void Say();

        public abstract void HasPart();

        public virtual void HasHabitat()
        {
            Console.WriteLine($"Animal has no available habitatas");
        }
    }

    public abstract class Mammal : Animal, ISpineAnimalPart
    {
        public bool IsHasSpinePart => true;

        public bool IsHasPart => true;

        public override void Say()
        {
            Console.WriteLine("Animal makes a sound");
        }

        public override void HasPart()
        {
            Console.WriteLine($"Is has any parts: {this.IsHasPart}");
            Console.WriteLine($"Spine part: {this.IsHasPart}");
        }

        public override void HasHabitat()
        {
            base.HasHabitat();
        }
    }
    #endregion

    #region Entities
    public class Cat : Mammal, IFurAnimalPart
    {
        public bool IsHasFurPart => true;

        public override void Say()
        {
            Console.WriteLine("The cat say mow!");
        }

        public override void HasPart()
        {
            base.HasPart();
            Console.WriteLine($"Fur part: {this.IsHasFurPart}");
        }
    }

    public class Bear : Mammal, IFurAnimalPart
    {
        public bool IsHasFurPart => true;

        public override void Say()
        {
            Console.WriteLine("The bear say wraar!");
        }

        public override void HasPart()
        {
            base.HasPart();
            Console.WriteLine($"Fur part: {this.IsHasFurPart}");
        }
    }

    public class Whale : Mammal, IWaterHabitat
    {
        public bool IsHasHabitat => true;

        public bool IsHasWaterHabitat => true;

        public override void Say()
        {
            Console.WriteLine("Whale makes an noise");
        }

        public override void HasHabitat()
        {
            Console.WriteLine($"Is has habitat: {IsHasHabitat}");
            Console.WriteLine($"Is water habitat: {IsHasWaterHabitat}");
        }
    }

    public class Fish : Animal, IWaterHabitat
    {
        public bool IsHasWaterHabitat => true;

        public bool IsHasHabitat => true;

        public override void Say()
        {
            Console.WriteLine("Fish makes an bolp");
        }

        public override void HasPart()
        {
            Console.WriteLine($"Fish has no available parts");
        }

        public override void HasHabitat()
        {
            Console.WriteLine($"Is has habitat: {IsHasHabitat}");
            Console.WriteLine($"Is water habitat: {IsHasWaterHabitat}");
        }
    }
    #endregion
}