package app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.exception.ModelNotFoundException;
import app.form.label.CreateLabelForm;
import app.form.label.UpdateLabelForm;
import app.model.Label;
import app.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    /**
     * Label取得。
     */
    public Label get(Long labelId, Long userId) {
        return labelRepository
                .findByIdAndUserId(labelId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたLabelが見つかりません。"));
    }

    /**
     * 全てのLabel取得。
     */
    public List<Label> getAll(Long userId) {
        return labelRepository.findAllByUserId(userId);
    }

    /**
     * Label作成。
     */
    public Label create(Long userId, CreateLabelForm createLabelForm) {
        Label createLabel = new Label(createLabelForm, userId);
        return labelRepository.save(createLabel);
    }

    /**
     * Label更新。
     */
    public Label update(Long labelId, Long userId, UpdateLabelForm updateLabelForm) {
        Label updateLabel = labelRepository
                .findByIdAndUserId(labelId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたLabelが見つかりません。"));
        updateLabel.updateWithForm(updateLabelForm);
        return labelRepository.save(updateLabel);
    }

    /**
     * Label削除。
     */
    public Long delete(Long labelId, Long userId) {
        Label deleteLabel = labelRepository
                .findByIdAndUserId(labelId, userId)
                .orElseThrow(() -> new ModelNotFoundException("指定されたLabelが見つかりません。"));
        labelRepository.delete(deleteLabel);
        return labelId;
    }
}
