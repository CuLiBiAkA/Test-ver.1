package jm.task.core.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Data
@AllArgsConstructor
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;


    @Override
    public String toString() {
        return ">>..Юзер:"+name+" "+lastName+", отроду годиков "+age+", Порядковый номер - "+id+"..<<";

    }
}
