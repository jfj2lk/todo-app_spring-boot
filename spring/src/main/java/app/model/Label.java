package app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import app.form.label.CreateLabelForm;
import app.form.label.UpdateLabelForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("labels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    @Id
    private Long id;
    private Long userId;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Label追加フォームの値でLabelオブジェクトを作成する。
     */
    public Label(CreateLabelForm addLabelForm, Long userId) {
        this.userId = userId;
        this.name = addLabelForm.getName();
    }

    /**
     * Label更新フォームの値でLabelオブジェクトを更新する。
     */
    public void updateWithForm(UpdateLabelForm updateLabelForm) {
        this.name = updateLabelForm.getName();
    }
}
