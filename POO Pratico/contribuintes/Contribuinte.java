package contribuintes;

import java.io.Serializable;
import java.lang.StringBuilder;

import moradas.Morada;

public abstract class Contribuinte implements Serializable {
    private String nome;
    private int nif;
    private String email;
    private Morada morada;
    private String password;
    
    /**
     * Construtor vazio de Contribuinte
     */
    public Contribuinte() {
        nome = null;
        nif = 0;
        email = null;
        morada = null;
        password = null;
    }
    
    /**
     * Construtor parametrizado de Contribuinte
     */
    public Contribuinte(String nome, int nif, String email, Morada morada, String password) {
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.morada = morada;
        this.password = password;
    }
    
    /**
     * Construtor cópia de Contribuinte 
     */
    public Contribuinte(Contribuinte c) {
        this.nome = c.getNome();
        this.nif= c.getNif();
        this.email = c.getEmail();
        this.morada = c.getMorada();
        this.password = c.getPassword();
    }
    
    /**
     * Clone abstrato de Contribuinte
     */
    public abstract Contribuinte clone();
    
    /**
     * Devolve o nome do contribuinte
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Atualiza o nome do contribuinte
     */
    public void setNome(String nome) {
        this.nome = nome;
        }
    
    /**
     *Devolve o nif 
     */
    public int getNif() {
        return nif;
    }
    
    /**
     * Atualiza o nif
     */
    public void setNif(int nif) {
        this.nif = nif;
    }
    
    /**
     * Devolve o email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Atualiza o email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Devolve a morada
     */
    public Morada getMorada() {
        return this.morada.clone();
    }
    
    /**
     * Atualiza a morada
     */
    public void setMorada(Morada morada) {
        this.morada = morada.clone();
    }
    
    /**
     * Devolve a password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Atualiza a password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    //N�o verifica a password
    /**
     * Metodo de igualdade de Contribuinte
     */
    public boolean equals(Contribuinte c) {
        if(this == c)
            return true;
        if (c==null || this.getClass() != c.getClass())
            return false;
        return this.nome.equals(c.getNome()) &&
                this.nif == c.getNif() &&
                this.email.equals(c.getEmail()) &&
                this.morada.equals(c.getMorada());
    }
    
    /**
     * Verifica se a password esta correta
     */
    public boolean isPassword(String pass) {
        return this.password.equals(pass);
    }
    
    /**
     * Metodo para converter as variaveis de Contribuintes em string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("Nome: ").append(this.getNome().toString());
        sb.append("NIF: ").append(this.getNif());
        sb.append("Email: ").append(this.getEmail());
        sb.append("Morada: ").append(this.getMorada());
        sb.append("}\n");
        return sb.toString();
    }
}
