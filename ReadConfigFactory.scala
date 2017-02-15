import com.typesafe.config.ConfigFactory
 
val value = ConfigFactory.load().getString("my.secret.value")
println(s"My secret value is $value")
