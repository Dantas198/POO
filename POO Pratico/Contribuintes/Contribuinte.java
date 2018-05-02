package Contribuintes;

import java.lang.StringBuilder;

public abstract class Contribuinte {
    private String nome;
    private int nif;
    private String email;
    private String morada;
    private String password;
    
    public Contribuinte() {
        nome = null;
        nif = 0;
        email = null;
        morada = null;
        password = null;
    }
    
    public Contribuinte(String nome, int nif, String email, String morada, String password) {
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.morada = morada;
        this.password = password;
    }
    
    public Contribuinte(Contribuinte c) {
        this.nome = c.getNome();
        this.nif= c.getNif();
        this.email = c.getEmail();
        this.morada = c.getMorada();
        this.password = c.getPassword();
    }
    
    public abstract Contribuinte clone();
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//N�o verifica a password
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

    public boolean isPassword(String pass) {
        return this.password.equals(pass);
    }
    
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
