package ua.ivan.provider.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "balance")
    private int balance;

    @ManyToMany
    @JoinTable(
            name = "subscribe",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_package"))
    private List<Packages> packages;

    public void addUserPackage(Packages userPackage) {
        this.packages.add(userPackage);
        userPackage.getUsers().add(this);
    }

    public void removeUserPackage(Packages userPackage) {
        this.packages.remove(userPackage);
        userPackage.getUsers().remove(this);

    }
}
