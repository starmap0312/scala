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

    case class Person(name: String, age: Int) // the constructor accepts two arguments
    // the body of Person class is empty because there are no methods that we want to define
    // case class implies:
    // 1) immutable fields (constructor arguments)
    // 2) equals(), hashCode(), and toString() methods are automatically defined

    case object DoWork                        // the constuctor accepts no argument
    case object NoWork                        // the constuctor accepts no argument
    // case object: singleton object with syntactic suger similar to case class

    def main(args: Array[String]) {
        // example 1: pattern matching objects of case class
        def matchPerson(person: Person): Unit = person match {
            case Person("Alice", 25) => println("value pattern matched: Person(Alice, 25)")
            case Person(  "Bob", 30) => println("value pattern matched: Person(  Bob, 30)")
            case Person(      x,  y) => println("typed pattern matched: Person(    x,  y), where (x, y) = (" + x + ", " + y + ")")
        }
        val alice   = new Person("Alice",   25)       // create a new object using the case class
        val bob     = new Person("Bob",     25)       // create a new object using the case class
        val charlie = new Person("Charlie", 25)       // create a new object using the case class
        for (person <- List(alice, bob, charlie)) {
            matchPerson(person)
        }

        // example 2: pattern matching case object (singleton)
        // no function defined, simply using pattern matching expression
        for (work <- List(DoWork, NoWork)) {
            work match {
                case DoWork => println("value pattern matched: DoWork")
                case NoWork => println("value pattern matched: NoWork")
            }
        }
    }
}
