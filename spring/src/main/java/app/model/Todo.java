package app.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = true, length = 255)
    private String desc;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    /**
     * 指定した名前、説明を持つTodoオブジェクトを作成する
     */
    public Todo(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * 指定されたID、名前、説明を持つTodoオブジェクトを作成する
     */
    public Todo(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
