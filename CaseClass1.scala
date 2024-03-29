// case class:
// 1) case classes are used to conveniently store and match on the contents of a class (a wrapper as well as a class)
// 2) Scala defines apply() method in companion object for you: construct instances of case classes without using new
//    note: Scala defines different name spaces for types and terms
//    so it is legal to use the same name for a class (types) and an object (terms)
// 3) Scala defines unapply() method for you: so it is an extractor
// syntax:
//   case class [classname]
// adding case keyword to class declaration causes compiler to add a number of useful features automatically
// 1) the keyword suggests an association with case expressions in pattern matching
// 2) compiler automatically converts the constructor arguments into immutable fields (vals)
// 3)) compiler automatically implements equals(), hashCode(), and toString() methods to the class
//     it uses the fields specified in constructor arguments, so, we no longer need our own toString() methods
// if you've made a case class:
//   ex1. case class Foo()
//   (Scala secretly creates a companion object for you, turning it into the following)
//        object Foo {
//            def apply() = new Foo
//        }
//        class Foo()
//        val foo = Foo()           // no new keyword when instantiation: val foo = new Foo
//   note: Scala defines separate namespaces for types (objects/values) and terms (expressions/methods)
//         so companion object can have the same name as the class name
//
//   ex2. trait User
//        case case FreeUser(name: String) extends User
//   (Scala secretly creates a companion object for you, turning it into the following)
//   i.e. trait User {
//            def name: String
//        }
//        object FreeUser {
//            def apply(name: String) = new FreeUser(name)
//            def unapply(user: FreeUser): Option[String] = Some(user.name)
//        }
//        class FreeUser(val name: String) extends User
//
// case object:
//   no argument in the constructor
//   it is a singleton
//   it is like a regular scala object
// 1) syntax:
//    case object [objectname]
object CaseClass {

    case class Person(name: String, age: Int) // the constructor accepts two arguments
    // the body of Person class is empty because there are no methods that we want to define
    // case class implies:
    // 1) immutable & public fields (constructor arguments)
    // 2) equals(), hashCode(), and toString() methods are automatically defined

    case object DoWork                        // the constuctor accepts no argument
    case object NoWork                        // the constuctor accepts no argument
    // case object: singleton object with syntactic suger similar to case class

    def main(args: Array[String]) {
        // example 0:
         trait User1
         case class FreeUser1(val name: String) extends User1
         // is a syntactic sugar of the following 
         trait User2 {
             def name: String
         }
         object FreeUser2 {
             def apply(name: String) = new FreeUser2(name)
             def unapply(user: FreeUser2): Option[String] = Some(user.name)
         }
         class FreeUser2(val name: String) extends User2 // if write only FreeUser2(name: String), name could be a private field
         val user1 = FreeUser1("John") // apply()
         val user2 = FreeUser2("John") // apply()
         val FreeUser1(name1) = user1  // unapply()
         val FreeUser2(name2) = user2  // unapply()
         println(name1)
         println(name2)

        // example 1: pattern matching objects of case class
        def matchPerson(person: Person): Unit = person match {
            case Person("Alice", 25) => println("value pattern matched: Person(Alice, 25)")
            case Person(  "Bob", 30) => println("value pattern matched: Person(  Bob, 30)")
            case Person(      x,  y) => println("typed pattern matched: Person(    x,  y), where (x, y) = (" + x + ", " + y + ")")
        }
        val alice   = Person("Alice",   25)       // create a new object using the case class
        val bob     = Person("Bob",     25)       // create a new object using the case class
        val charlie = Person("Charlie", 25)       // create a new object using the case class

        // 1.1) constructor parameters become immutable, public fields
        println("public fields: name = " + alice.name + ", age = " + alice.age)
        // 1.2) equals() method:
        println("alice == bob: " + (alice == bob))
        println("alice.equals(bob): " + (alice.equals(bob)))
        println("alice == Person(\"Alice\", 25): " + (alice == Person("Alice", 25)))
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

        // example 3: case class of container types
        case class StringList(data: List[String])
        case class StringMap(data: Map[String, String])
        def printStringList(strings: StringList): Unit = {
            println(strings.data)
        }
        def printStringMap(strings: StringMap): Unit = {
            println(strings.data)
        }
        printStringList(StringList(List("one", "two", "three")))                   // List(one, two, three)
        //printStringList(StringList(List(1, 2, 3)))                               // compile-time error: type mismatch
        printStringMap(StringMap(Map("one" -> "1", "two" -> "2", "three" -> "3"))) // Map(one -> 1, two -> 2, three -> 3)
        //printStringMap(StringMap(Map("one" -> 1, "two" -> 2, "three" -> 3)))     // compile-time error: type mismatch
    }
}
