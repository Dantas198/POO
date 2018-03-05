import java.time.LocalDate;
/**
 * Abstract class Test - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public class Fatura
{
    // instance variables - replace the example below with your own
    private int nifEmitente;
    private String nomeEmitente; // Designação do emitente
    private LocalDate dataDespesa;
    private int nifCliente;
    private String descricaoDespesa; //alterar depois
    private String atividadeEconomica; //criar classe
    private float valorDespesa;
    
    private boolean pendente;//true por default, true se pendente false se tivermos atividadeEconomica
}
