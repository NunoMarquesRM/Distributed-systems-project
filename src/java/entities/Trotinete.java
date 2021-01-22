/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nuno Marques
 */
@Entity
@Table(name = "TROTINETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trotinete.findAll", query = "SELECT t FROM Trotinete t")
    , @NamedQuery(name = "Trotinete.findByIdTrotinete", query = "SELECT t FROM Trotinete t WHERE t.idTrotinete = :idTrotinete")
    , @NamedQuery(name = "Trotinete.findByNome", query = "SELECT t FROM Trotinete t WHERE t.nome = :nome")
    , @NamedQuery(name = "Trotinete.findByMarca", query = "SELECT t FROM Trotinete t WHERE t.marca = :marca")
    , @NamedQuery(name = "Trotinete.findByDataRegisto", query = "SELECT t FROM Trotinete t WHERE t.dataRegisto = :dataRegisto")
    , @NamedQuery(name = "Trotinete.findByEstado", query = "SELECT t FROM Trotinete t WHERE t.estado = :estado")
    , @NamedQuery(name = "Trotinete.findByLocalizacao", query = "SELECT t FROM Trotinete t WHERE t.localizacao = :localizacao")
    , @NamedQuery(name = "Trotinete.findByNumeroDias", query = "SELECT t FROM Trotinete t WHERE t.numeroDias = :numeroDias")
    , @NamedQuery(name = "Trotinete.findByPotencia", query = "SELECT t FROM Trotinete t WHERE t.potencia = :potencia")
    , @NamedQuery(name = "Trotinete.findByVelocidadeMaxima", query = "SELECT t FROM Trotinete t WHERE t.velocidadeMaxima = :velocidadeMaxima")
    , @NamedQuery(name = "Trotinete.findByTempoDeCarga", query = "SELECT t FROM Trotinete t WHERE t.tempoDeCarga = :tempoDeCarga")
    , @NamedQuery(name = "Trotinete.findByTipo", query = "SELECT t FROM Trotinete t WHERE t.tipo = :tipo")
    , @NamedQuery(name = "Trotinete.findByAlcance", query = "SELECT t FROM Trotinete t WHERE t.alcance = :alcance")
    , @NamedQuery(name = "Trotinete.findByLitrosDeposito", query = "SELECT t FROM Trotinete t WHERE t.litrosDeposito = :litrosDeposito")
    , @NamedQuery(name = "Trotinete.findByPesoMaximo", query = "SELECT t FROM Trotinete t WHERE t.pesoMaximo = :pesoMaximo")
    , @NamedQuery(name = "Trotinete.findNumeroDias", query = "SELECT t.numeroDias FROM Trotinete t")
    , @NamedQuery(name = "Trotinete.findByEmail", query = "SELECT t FROM Trotinete t WHERE t.email = (SELECT c.email FROM Cliente c Where c.email= :email)")})
public class Trotinete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TROTINETE")
    private Integer idTrotinete;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA_REGISTO")
    @Temporal(TemporalType.DATE)
    private Date dataRegisto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LOCALIZACAO")
    private String localizacao;
    @Size(max = 50)
    @Column(name = "NUMERO_DIAS")
    private String numeroDias;
    @Size(max = 50)
    @Column(name = "POTENCIA")
    private String potencia;
    @Size(max = 50)
    @Column(name = "VELOCIDADE_MAXIMA")
    private String velocidadeMaxima;
    @Size(max = 50)
    @Column(name = "TEMPO_DE_CARGA")
    private String tempoDeCarga;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 50)
    @Column(name = "ALCANCE")
    private String alcance;
    @Size(max = 50)
    @Column(name = "LITROS_DEPOSITO")
    private String litrosDeposito;
    @Size(max = 50)
    @Column(name = "PESO_MAXIMO")
    private String pesoMaximo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTrotinete")
    private Collection<Aluguer> aluguerCollection;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Cliente email;

    public Trotinete() {
    }

    public Trotinete(Integer idTrotinete) {
        this.idTrotinete = idTrotinete;
    }

    public Trotinete(Integer idTrotinete, String nome, String marca, Date dataRegisto, String estado, String localizacao, String tipo) {
        this.idTrotinete = idTrotinete;
        this.nome = nome;
        this.marca = marca;
        this.dataRegisto = dataRegisto;
        this.estado = estado;
        this.localizacao = localizacao;
        this.tipo = tipo;
    }

    public Integer getIdTrotinete() {
        return idTrotinete;
    }

    public void setIdTrotinete(Integer idTrotinete) {
        this.idTrotinete = idTrotinete;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(String numeroDias) {
        this.numeroDias = numeroDias;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(String velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public String getTempoDeCarga() {
        return tempoDeCarga;
    }

    public void setTempoDeCarga(String tempoDeCarga) {
        this.tempoDeCarga = tempoDeCarga;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public String getLitrosDeposito() {
        return litrosDeposito;
    }

    public void setLitrosDeposito(String litrosDeposito) {
        this.litrosDeposito = litrosDeposito;
    }

    public String getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(String pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    @XmlTransient
    public Collection<Aluguer> getAluguerCollection() {
        return aluguerCollection;
    }

    public void setAluguerCollection(Collection<Aluguer> aluguerCollection) {
        this.aluguerCollection = aluguerCollection;
    }

    public Cliente getEmail() {
        return email;
    }

    public void setEmail(Cliente email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrotinete != null ? idTrotinete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trotinete)) {
            return false;
        }
        Trotinete other = (Trotinete) object;
        if ((this.idTrotinete == null && other.idTrotinete != null) || (this.idTrotinete != null && !this.idTrotinete.equals(other.idTrotinete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Trotinete[ idTrotinete=" + idTrotinete + " ]";
    }
    
}
