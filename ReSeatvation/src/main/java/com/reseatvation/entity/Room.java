package com.reseatvation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int rowsCount;

    @Column(nullable = false)
    private int seatsPerRow;

    protected Room() {
    }

    public Room(String name, int rowsCount, int seatsPerRow) {
        this.name = name;
        this.rowsCount = rowsCount;
        this.seatsPerRow = seatsPerRow;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }
}
