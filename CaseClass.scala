// Case class 
// Syntax
//   case class [classname]
// adding case keyword to class declaration causes compiler to add a number of useful features automatically
// 1) the keyword suggests an association with case expressions in pattern matching
// 2) compiler automatically converts the constructor arguments into immutable fields (vals)
// 3) compiler automatically implements equals(), hashCode(), and toString() methods to the clas
//    it uses the fields specified in constructor arguments
//    so, we no longer need our own toString() methods

object CaseClass {

    case class Person(name: String, age: Int)
    // the body of Person class is empty because there are no methods that we want to define
    // note: immutable fields (constructor arguments) and equals(), hashCode(), and toString() methods are defined automatically

    def main(args: Array[String]) {
        val alice = new Person("Alice", 25)
        val bob = new Person("Bob", 32)
        val charlie = new Person("Charlie", 32)
        for (person <- List(alice, bob, charlie)) {
            person match {
                case Person("Alice", 25) => println("Hi Alice!")
                case Person("Bob", 32) => println("Hi Bob!")
                case Person(name, age) => println("Hi name: " + name + ", age: " + age )
            }
        }
    }
}
