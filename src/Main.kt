import kotlin.coroutines.CoroutineContext
import kotlin.math.pow

val listaPolegadasLonga = listOf("1","3","6","9","1","1,50","2","3","4","5","6","7","8","10")
val listaPolegadasCurta = listOf("3","6","9","1","1,50","2","3","4","5")
val listaColunaW_CM = listOf<Double>(2.5,7.6,15.2,22.9,30.5,45.7,61.0,91.5,122.0,152.5,183.0,213.5,244.0,305.0)
val listaColunaE = listOf<Double>(22.9,38.1,45.7,61.0,91.5,91.5,91.5,91.5,91.5,91.5,91.5,91.5,91.5,122.0)
val listVazaoMinima = listOf<Double>(0.85,1.52,2.55,3.11,4.25,11.89,17.26,36.79,62.8)
val listVazaoMaxima = listOf<Double>(53.8,110.4,251.9,455.6,696.2,936.7,1426.0,1921.0,2422.0)
val listaColunaK = listOf<Double>(0.1771,0.3812,0.5354,0.6909,1.056,1.429,2.184,2.963,3.732)
val listaColunaN = listOf<Double>(1.5447,1.53,1.53,1.522,1.538,1.55,1.5666,1.5738,1.587)

val colunaW_CM = listaPolegadasLonga.zip(listaColunaW_CM).toMap<String, Double>()
val colunaE = listaPolegadasLonga.zip(listaColunaE).toMap<String, Double>()
val colunaVazaoMinima = listaPolegadasCurta.zip(listVazaoMinima).toMap<String, Double>()
val colunaVazaoMaxima = listaPolegadasCurta.zip(listVazaoMaxima).toMap<String, Double>()
val colunaK: Map<String,Double> = listaPolegadasCurta.zip(listaColunaK).toMap<String, Double>()
val colunaN: Map<String,Double> = listaPolegadasCurta.zip(listaColunaN).toMap<String, Double>()

fun main() {
    val habitantes: Int
    val consumoAguaHabitanteDia = 160 //Dado estimado hardcoded
    habitantes = 70000

    val vasaoLitrosPorDia = habitantes * consumoAguaHabitanteDia
    val vasaoLitrosPorSegundo = vasaoLitrosPorDia/86400.0

    for(polegada in listaPolegadasCurta) {
        //TODO: Ta fora de ordem, MAS ATENCAO: pra mudar a ordem de uma das listas constantes tem que mudar de todas.
        println("para a cm: ${colunaW_CM.get(polegada)}, a vazão é de ${calcularCalhaDeParshall(vasaoLitrosPorSegundo,polegada)}%")
    }


}

fun calcularCalhaDeParshall(vasaoLitrosPorSegundo: Double,polegada: String): Int{
    val vasaoLitrosCubicosPorSegundo = vasaoLitrosPorSegundo / 1000
    val alturaDaAgua =  calcularAlturaDaAgua(vasaoLitrosCubicosPorSegundo,polegada)
    return calcularRegraDeTres(alturaDaAgua*100,polegada).toInt()
}

fun calcularAlturaDaAgua(vasaoPorSegunda:Double,polegada:String):Double{
    //Essa funcao VAI colapsar se por algum motivo alguem inserir um valor nullo na tabela
    //TODO: Colocar um exception
    val k = colunaK.get(polegada)?:0.0
    val n = colunaN.get(polegada)?:0.0
    return (vasaoPorSegunda/k).pow((1/n))
}

fun calcularRegraDeTres(alturaDaAgua:Double,polegada:String):Double{
    val e = colunaE.get(polegada)?:0.0
    return (alturaDaAgua*100)/e
}