using System;
using System.Collections.Generic;

public class HelloWorld
{
    public static void Main(string[] args)
    {
                    var vehicles = new List<Vehicle>()
            {
                new Plane(), new Helicopter(), new Boat(), new Tanker(), new Truck(), new Taxi()
            };

            vehicles.ForEach(v =>
            {
                v.HasParts();
                Console.WriteLine();
            });
    }
    #region Part interfaces
    interface IPart
    {
        bool HasPart { get; }
    }

    interface IWheelPart : IPart
    {
        bool HasWheelPart { get; }
    }

    interface IPropellerPart : IPart
    {
        bool HasPropeller { get; }
    }

    interface IWingsPart : IPart
    {
        bool HasWings { get; }
    }

    interface ITransportation : IPart
    {
        bool HasTransportation { get; }
    }
    #endregion

    #region Abstracts
    public abstract class Vehicle : IPart
    {
        public bool HasPart => true;

        public abstract void HasParts();
    }
    #endregion

    #region Entities
    public class Plane : Vehicle, IWheelPart, IPropellerPart, IWingsPart, ITransportation
    {
        public bool HasWheelPart => true;

        public bool HasPropeller => true;

        public bool HasWings => true;

        public bool HasTransportation => true;

        public override void HasParts()
        {
            Console.WriteLine($"Plane");
            Console.WriteLine($"Has wheel: {HasWheelPart}");
            Console.WriteLine($"Has propeller: {HasPropeller}");
            Console.WriteLine($"Has wings: {HasWings}");
            Console.WriteLine($"Is transport: {HasTransportation}");
        }
    }

    public class Helicopter : Vehicle, IWheelPart, IPropellerPart, ITransportation
    {
        public bool HasWheelPart => true;

        public bool HasPropeller => true;

        public bool HasTransportation => true;

        public override void HasParts()
        {
            Console.WriteLine($"Helicopter");
            Console.WriteLine($"Has wheel: {HasWheelPart}");
            Console.WriteLine($"Has propeller: {HasPropeller}");
            Console.WriteLine($"Is transport: {HasTransportation}");
        }
    }

    public class Boat : Vehicle, IPropellerPart, ITransportation
    {
        public bool HasPropeller => true;

        public bool HasTransportation => true;

        public override void HasParts()
        {
            Console.WriteLine($"Boat");
            Console.WriteLine($"Has propeller: {HasPropeller}");
            Console.WriteLine($"Is transport: {HasTransportation}");
        }
    }

    public class Tanker : Boat
    {
        public override void HasParts()
        {
            Console.WriteLine($"Tanker");
            Console.WriteLine($"Has propeller: {HasPropeller}");
            Console.WriteLine($"Is transport: {HasTransportation}");
        }
    }

    public class Truck : Vehicle, IWheelPart, ITransportation
    {
        public bool HasWheelPart => true;

        public bool HasTransportation => true;

        public override void HasParts()
        {
            Console.WriteLine($"Truck");
            Console.WriteLine($"Has wheel: {HasWheelPart}");
            Console.WriteLine($"Is transport: {HasTransportation}");
        }
    }

    public class Taxi : Vehicle, IWheelPart
    {
        public bool HasWheelPart => true;

        public override void HasParts()
        {
            Console.WriteLine($"Taxi");
            Console.WriteLine($"Has wheel: {HasWheelPart}");
        }
    }
    #endregion
}