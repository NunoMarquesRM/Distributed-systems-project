/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nuno Marques
 */
@Entity
@Table(name = "ALUGUER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluguer.findAll", query = "SELECT a FROM Aluguer a")
    , @NamedQuery(name = "Aluguer.findByIdAluguer", query = "SELECT a FROM Aluguer a WHERE a.idAluguer = :idAluguer")
    , @NamedQuery(name = "Aluguer.findByDataInicio", query = "SELECT a FROM Aluguer a WHERE a.dataInicio = :dataInicio")
    , @NamedQuery(name = "Aluguer.findByDataFim", query = "SELECT a FROM Aluguer a WHERE a.dataFim = :dataFim")
    , @NamedQuery(name = "Aluguer.findByEmail", query = "SELECT a FROM Aluguer a WHERE a.email = (SELECT c.email FROM Cliente c Where c.email= :email)")})

public class Aluguer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ALUGUER")
    private Integer idAluguer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Cliente email;
    @JoinColumn(name = "ID_TROTINETE", referencedColumnName = "ID_TROTINETE")
    @ManyToOne(optional = false)
    private Trotinete idTrotinete;

    public Aluguer() {
    }

    public Aluguer(Integer idAluguer) {
        this.idAluguer = idAluguer;
    }

    public Aluguer(Integer idAluguer, Date dataInicio, Date dataFim) {
        this.idAluguer = idAluguer;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Integer getIdAluguer() {
        return idAluguer;
    }

    public void setIdAluguer(Integer idAluguer) {
        this.idAluguer = idAluguer;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Cliente getEmail() {
        return email;
    }

    public void setEmail(Cliente email) {
        this.email = email;
    }

    public Trotinete getIdTrotinete() {
        return idTrotinete;
    }

    public void setIdTrotinete(Trotinete idTrotinete) {
        this.idTrotinete = idTrotinete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAluguer != null ? idAluguer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluguer)) {
            return false;
        }
        Aluguer other = (Aluguer) object;
        if ((this.idAluguer == null && other.idAluguer != null) || (this.idAluguer != null && !this.idAluguer.equals(other.idAluguer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Aluguer[ idAluguer=" + idAluguer + " ]";
    }
    
}
