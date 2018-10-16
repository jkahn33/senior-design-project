package senior.design.group10.objects;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="test")
public class TestWrap {
    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private int id;

    @Column
    private String val;

    public TestWrap(){}

    public TestWrap(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }
}
