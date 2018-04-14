// 1) implicit parameters
// 2) higher-order List functions
// 3) reduction on List
//    if the operator is associative and commutative, the foldLeft and foldRight are equivalent
//    ex. (a + b)        = (b + a)       ... commutative
//        ((a + b) + c)) = (a + (b + c)) ... associative
//    there may be a difference in efficiency
// 4) reasoning about concat
