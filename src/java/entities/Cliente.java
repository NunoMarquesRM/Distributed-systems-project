/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nuno Marques
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome")
    , @NamedQuery(name = "Cliente.findByIdade", query = "SELECT c FROM Cliente c WHERE c.idade = :idade")
    , @NamedQuery(name = "Cliente.findByContato", query = "SELECT c FROM Cliente c WHERE c.contato = :contato")
    , @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email")
    , @NamedQuery(name = "Cliente.findByPassword", query = "SELECT c FROM Cliente c WHERE c.password = :password")
    , @NamedQuery(name = "Cliente.findByNivel", query = "SELECT c FROM Cliente c WHERE c.nivel = :nivel")
    , @NamedQuery(name = "Cliente.login", query = "SELECT c FROM Cliente c WHERE c.email = :email AND c.password = :password")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDADE")
    private int idade;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTATO")
    private int contato;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NIVEL")
    private int nivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private Collection<Aluguer> aluguerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private Collection<Trotinete> trotineteCollection;

    public Cliente() {
    }

    public Cliente(String email) {
        this.email = email;
    }

    public Cliente(String email, String nome, int idade, int contato, String password, int nivel) {
        this.email = email;
        this.nome = nome;
        this.idade = idade;
        this.contato = contato;
        this.password = password;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getContato() {
        return contato;
    }

    public void setContato(int contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @XmlTransient
    public Collection<Aluguer> getAluguerCollection() {
        return aluguerCollection;
    }

    public void setAluguerCollection(Collection<Aluguer> aluguerCollection) {
        this.aluguerCollection = aluguerCollection;
    }

    @XmlTransient
    public Collection<Trotinete> getTrotineteCollection() {
        return trotineteCollection;
    }

    public void setTrotineteCollection(Collection<Trotinete> trotineteCollection) {
        this.trotineteCollection = trotineteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cliente[ email=" + email + " ]";
    }
    
}
