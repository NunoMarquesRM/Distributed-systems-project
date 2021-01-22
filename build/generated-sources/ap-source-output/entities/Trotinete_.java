package entities;

import entities.Aluguer;
import entities.Cliente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-13T23:39:07")
@StaticMetamodel(Trotinete.class)
public class Trotinete_ { 

    public static volatile SingularAttribute<Trotinete, String> litrosDeposito;
    public static volatile SingularAttribute<Trotinete, String> estado;
    public static volatile SingularAttribute<Trotinete, String> tempoDeCarga;
    public static volatile SingularAttribute<Trotinete, String> tipo;
    public static volatile CollectionAttribute<Trotinete, Aluguer> aluguerCollection;
    public static volatile SingularAttribute<Trotinete, String> localizacao;
    public static volatile SingularAttribute<Trotinete, String> velocidadeMaxima;
    public static volatile SingularAttribute<Trotinete, String> pesoMaximo;
    public static volatile SingularAttribute<Trotinete, String> nome;
    public static volatile SingularAttribute<Trotinete, Integer> idTrotinete;
    public static volatile SingularAttribute<Trotinete, String> alcance;
    public static volatile SingularAttribute<Trotinete, String> marca;
    public static volatile SingularAttribute<Trotinete, String> potencia;
    public static volatile SingularAttribute<Trotinete, Date> dataRegisto;
    public static volatile SingularAttribute<Trotinete, String> numeroDias;
    public static volatile SingularAttribute<Trotinete, Cliente> email;

}