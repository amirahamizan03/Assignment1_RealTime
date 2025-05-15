package week2_3;

public class Multithreading
{
    public static void main(String [] args ){
        MultithreadingThing myThing = new MultithreadingThing(1);
        MultithreadingThing myThing2 = new MultithreadingThing(2);
        MultithreadingThing myThing3= new MultithreadingThing(3);


        myThing.run();
        myThing2.run();
        myThing3.run();
    }
}
