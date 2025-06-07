package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.label.AddLabelForm;
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
   * 全てのLabelを取得する。
   */
  public Iterable<Label> getAllLabels() {
    return labelRepository.findAll();
  }

  /**
   * Labelを追加する。
   */
  public Label addLabel(AddLabelForm addLabelForm) {
    Label addLabel = new Label(addLabelForm);
    return labelRepository.save(addLabel);
  }

  /**
   * Labelを更新する。
   */
  public Label updateLabel(Long updateLabelId, UpdateLabelForm updateLabelForm)
      throws RuntimeException {
    Label updateLabel = labelRepository.findById(updateLabelId)
        .orElseThrow(() -> new RuntimeException("更新対象のLabelが見つかりませんでした。"));
    // フォームの値でLabelの値を更新する
    updateLabel.updateWithForm(updateLabelForm);
    return labelRepository.save(updateLabel);
  }

  /**
   * Labelを削除する。
   */
  public Long deleteLabel(Long deleteLabelId) throws RuntimeException {
    Label deleteLabel = labelRepository.findById(deleteLabelId)
        .orElseThrow(() -> new RuntimeException("更新対象のLabelが見つかりませんでした。"));
    labelRepository.delete(deleteLabel);
    return deleteLabelId;
  }
}
