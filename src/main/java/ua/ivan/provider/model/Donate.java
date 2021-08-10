package ua.ivan.provider.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "donate")
public class Donate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sum")
    private Long sum;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User userId;
}
