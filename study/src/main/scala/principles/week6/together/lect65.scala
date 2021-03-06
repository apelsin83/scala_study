import scala.io.Source


object lect65 {
  val in = Source.fromURL("http://lamp.efpl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords")

  val words = in.getLines.toList filter (word => word forall (chr => chr.isLetter))

  val mnem = Map(
    '2' -> "ABC", '3' -> "DEF", '4' -> "GHI",
    '5' -> "JKL", '6' -> "MNO", '7' -> "PQRS",
    '8' -> "TUV", '9' -> "WXYZ" 
  )

  /* INVERT THE MNEM MAP TO GIVE A MAP FROM CHARS A-Z TO 2-9 */

  val charCode: Map[Char, Char] = 
    for ((digit, str) <- mnem; ltr <- str) yield ltr -> digit

  /* Maps a word to the digit string it can represent, e.g "java" -> "5282" */
  def wordCode(word: String): String = 
    word.toUpperCase map charCode

  val wordsForNum: Map[String, Seq[String]] = 
    words groupBy wordCode withDefaultValue Seq()
  def encode(number: String): Set[List[String]] = 
    if (number.isEmpty) Set(List())
    else {
      for {
        split <- 1 to number.length
        word <- wordsForNum(number take split)
        rest <- encode(number drop split)
      } yield word :: rest
    }.toSet
  /**
  * A map from digit strings to the words that represent them,
  * e.g. "5282" -> List("Java", "Kata", "Lava")
  * Note: A missing number should map to empty set e.g. "1111" -> List()
  **/
  def translate(number: String): Set[ String] = 
    encode(number) map (_ mkString " ")

  def main(args: Array[String]): Unit = {
    println(charCode)
    println(wordCode("Java"))

  }

}