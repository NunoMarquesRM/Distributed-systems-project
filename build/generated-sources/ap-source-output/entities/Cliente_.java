package entities;

import entities.Aluguer;
import entities.Trotinete;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-13T23:39:07")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Integer> idade;
    public static volatile SingularAttribute<Cliente, String> password;
    public static volatile CollectionAttribute<Cliente, Aluguer> aluguerCollection;
    public static volatile CollectionAttribute<Cliente, Trotinete> trotineteCollection;
    public static volatile SingularAttribute<Cliente, String> nome;
    public static volatile SingularAttribute<Cliente, Integer> contato;
    public static volatile SingularAttribute<Cliente, Integer> nivel;
    public static volatile SingularAttribute<Cliente, String> email;

}