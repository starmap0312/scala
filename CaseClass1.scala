// 1) case class: it take arguments, so each instance of that case class can be different based on the arguments
// syntax:
//   case class [classname]
// adding case keyword to class declaration causes compiler to add a number of useful features automatically
// a) the keyword suggests an association with case expressions in pattern matching
// b) compiler automatically converts the constructor arguments into immutable fields (vals)
// c) compiler automatically implements equals(), hashCode(), and toString() methods to the clas
//    it uses the fields specified in constructor arguments
//    so, we no longer need our own toString() methods
// 2) case object: no argument in the constructor, so it is a singleton, like a regular scala object is
// syntax:
//   case object [objectname]

object CaseClass {

    case class Person(name: String, age: Int)
    // the constructor accepts two arguments
    // the body of Person class is empty because there are no methods that we want to define
    // note: immutable fields (constructor arguments) and equals(), hashCode(), and toString() methods are defined automatically

    case object DoWork
    case object NoWork
    // the constuctor accepts no argument

    def main(args: Array[String]) {
        // example 1: pattern matching objects of case class
        val alice = new Person("Alice", 25)       // create a new object using the case class
        val bob = new Person("Bob", 32)           // create a new object using the case class
        val charlie = new Person("Charlie", 32)   // create a new object using the case class
        for (person <- List(alice, bob, charlie)) {
            person match {
                case Person("Alice", 25) => println("Hi Alice!")
                case Person("Bob", 32)   => println("Hi Bob!")
                case Person(name, age)   => println("Hi name: " + name + ", age: " + age )
            }
        }

        // example 2: pattern matching case object (singleton)
        for (work <- List(DoWork, NoWork)) {
            work match {
                case DoWork => println("DoWork")
                case NoWork => println("NoWork")
            }
        }
    }
}
