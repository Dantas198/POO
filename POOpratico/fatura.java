
/**
 * Write a description of class Fatura here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.time.LocalDate;
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
    
    /**
     * Construtor de objetos cujo argumento são as variáveis.
     */
    public Fatura(int nifEmitente, String nomeEmitente, LocalDate dataDespesa, int nifCliente, String descricaoDespesa, 
                  String atividadeEconomica, float valorDespesa, boolean pendente)
    {
        this.setNifCliente(nifEmitente);
        this.setNomeEmitente(nomeEmitente);
        this.setDataDespesa(dataDespesa);
        this.setNifCliente(nifCliente);
        this.setDescricaoDespesa(descricaoDespesa);
        this.setAtividadeEconomica(atividadeEconomica);
        this.setValorDespesa(valorDespesa);
        this.setPendente(pendente);
    
    }
    /**
     * Construtor cópia de objetos.
     */
    public Fatura(Fatura f){
        this.setNifCliente(f.getNifEmitente());
        this.setNomeEmitente(f.getNomeEmitente());
        this.setDataDespesa(f.getDataDespesa());
        this.setNifCliente(f.getNifCliente());
        this.setDescricaoDespesa(f.getDescricaoDespesa());
        this.setAtividadeEconomica(f.getAtividadeEconomica());
        this.setValorDespesa(f.getValorDespesa());
        this.setPendente(f.getPendente());
    }
    
    /**
     * Método clone de objetos da classe Fatura.
     */
    public Fatura clone(){
        return new Fatura(this);
    }
    
    /**
    * Verifica se dois objetos são iguais.
    */
    public boolean equals (Object o){
        if(o==this) return true;
        if(o==null || (o.getClass() != this.getClass())) return false;
       
        Fatura f = (Fatura) o;
        return this.nifCliente == f.getNifCliente() && 
               this.nomeEmitente == f.getNomeEmitente() &&
               this.dataDespesa == f.getDataDespesa() &&
               this.nifEmitente == f.getNifEmitente() &&
               this.descricaoDespesa == f.getDescricaoDespesa() &&
               this.atividadeEconomica == f.getAtividadeEconomica() &&
               this.valorDespesa == f.getValorDespesa() &&
               this.pendente == f.getPendente();
    }
   
    /**
     * Converte as variáveis em String.
     */
    public String toString(){
    StringBuilder sb = new StringBuilder("Fatura...NifCliente: ");
        sb.append(this.nifCliente);
        sb.append(", NomeEmitente: ");
        sb.append(this.nomeEmitente);
        sb.append(", DatadaDespesa: ");
        sb.append(this.dataDespesa);
        sb.append(", NifEmitente: ");
        sb.append(this.nifEmitente);
        sb.append(" ,DescriçãoDespesa: ");
        sb.append(this.descricaoDespesa);
        sb.append(" ,AtividadeEconomica: ");
        sb.append(this.atividadeEconomica);
        sb.append(" ,ValorDespesa: ");
        sb.append(this.valorDespesa);
        sb.append(" ,Pendente: ");
        sb.append(this.pendente);
       
        return sb.toString();
    }
    
    /**
     * Altera o pendente.
     * @param p.
     */
    public void setPendente(boolean p){
        this.pendente = p;
    }
    
    /**
     * Altera o valor da despesa.
     * @param v.
     */
    public void setValorDespesa(float v){
        this.valorDespesa = v;
    }
    
    /**
     * Altera a descrição da despesa.
     * @param a.
     */
    public void setAtividadeEconomica(String a){
        this.atividadeEconomica = a;
    }
    
    /**
     * Altera a descrição da despesa.
     * @param d.
     */
    public void setDescricaoDespesa(String d){
        this.descricaoDespesa = d;
    }
    
    /**
     * Altera o nif do cliente.
     * @param n
     */
    public void setNifCliente(int n){
        this.nifCliente = n;
    }
    
    /**
     * Altera a data da despesa.
     * @para l.
     */
    public void setDataDespesa(LocalDate l){
        this.dataDespesa = l;
    }
    
    /**
     * Altera o nome emitente.
     * @param n.
     */
    public void setNomeEmitente(String n){
        this.nomeEmitente = n;
    }
    
    /**
     * Altera o valor do nif emitente.
     * @param n
     */
    public void setNifEmitente(int n){
        this.nifEmitente = n; 
    }
    
    /**
     * Retorna o nif emitente.
     * @return nifEmitente.
     */
    public int getNifEmitente(){
        return this.nifEmitente;
    }
    
    /**
     * Retorna o nome do emitente.
     * @return nomeEmitente.
     */
    public String getNomeEmitente(){
        return this.nomeEmitente;
    }
    
    /**
     * Retorna a data da despesa.
     * @return dataDespesa.
     */
    public LocalDate getDataDespesa(){
        return this.dataDespesa;
    }
    
    /**
     * Retorna o nif do cliente.
     * @return nifCliente.
     */
    public int getNifCliente(){
        return this.nifCliente;
    }
    
    /**
     * Retorna a descrição da despesa.
     * @return descricaoDespesa.
     */
    public String getDescricaoDespesa(){
        return this.descricaoDespesa;
    }
    
    /**
     * Retorna a atividade Economica.
     * @return atividadeEconomica.
     */
    public String getAtividadeEconomica(){
        return this.atividadeEconomica;
    }
    
    /**
     * Retorna o valor economico.
     * @return valorEconomica.
     */
    public float getValorDespesa(){
        return this.valorDespesa;
    }
    
    /**
     * Retorna o pendente.
     * @return pendente.
     */
    public boolean getPendente(){
        return this.pendente;
    }
    
    
}

