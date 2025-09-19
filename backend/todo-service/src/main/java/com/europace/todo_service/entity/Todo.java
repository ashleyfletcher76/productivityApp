package com.europace.todo_service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column()
    private LocalDate finishBy;

    @Column()
    private LocalDate createdAt;

    public Todo() {}

    public Todo(
            String username, String title,
            String description,
            LocalDate finishBy
    ) {
        this.username = username;
        this.title = title;
        this.description = description;
        this.finishBy = finishBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getFinishBy() {
        return finishBy;
    }

    public void setFinishBy(LocalDate finishBy) {
        this.finishBy = finishBy;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null)
            createdAt = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
