package ru.artgin.game.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.artgin.game.enums.Type;
import ru.artgin.game.enums.Move;
import ru.artgin.game.enums.Result;

import javax.persistence.*;


/**
 *It is the model class for table Statistic
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    @Enumerated(EnumType.STRING)
    private Move userMove;

    @Column
    @Enumerated(EnumType.STRING)
    private Move compMove;

    @ManyToOne
    private User user;

}
