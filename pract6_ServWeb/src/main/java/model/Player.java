package model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Jugador")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name = "nombre", length=50, nullable = false)
    private String name;
    @Column(nullable = false)
    private int posX;
    @Column(nullable = false)
    private int posY;

    public Player() {}

    public Player(String username) {
        this.name = username;
        this.posX = 0;
        this.posY = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
