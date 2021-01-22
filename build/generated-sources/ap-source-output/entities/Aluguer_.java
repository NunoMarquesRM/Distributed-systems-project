package entities;

import entities.Cliente;
import entities.Trotinete;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-13T23:39:07")
@StaticMetamodel(Aluguer.class)
public class Aluguer_ { 

    public static volatile SingularAttribute<Aluguer, Integer> idAluguer;
    public static volatile SingularAttribute<Aluguer, Date> dataFim;
    public static volatile SingularAttribute<Aluguer, Date> dataInicio;
    public static volatile SingularAttribute<Aluguer, Trotinete> idTrotinete;
    public static volatile SingularAttribute<Aluguer, Cliente> email;

}