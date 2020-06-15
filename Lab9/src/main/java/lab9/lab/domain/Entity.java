package lab9.lab.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Entity<ID extends Serializable> implements Serializable {

    @Id
    private ID id;

    public ID getId(){
        return this.id;
    }

    public void setId(ID new_id){
        this.id=new_id;
    }

    @Override
    public String toString()
    {
        return "Entity{ "+"ID="+id+" }";
    }

}
