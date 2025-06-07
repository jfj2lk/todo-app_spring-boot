package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.label.AddLabelForm;
import app.form.label.UpdateLabelForm;
import app.model.Label;
import app.repository.LabelRepository;
import app.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelService {
  private final LabelRepository labelRepository;
  private final SecurityUtils securityUtils;

  /**
   * 全てのLabelを取得する。
   */
  public Iterable<Label> getAllLabels() {
    Long loginUserId = securityUtils.getCurrentUserId();
    return labelRepository.findAllByUserId(loginUserId);
  }

  /**
   * Labelを追加する。
   */
  public Label addLabel(AddLabelForm addLabelForm) {
    Long loginUserId = securityUtils.getCurrentUserId();
    Label addLabel = new Label(addLabelForm, loginUserId);
    return labelRepository.save(addLabel);
  }

  /**
   * Labelを更新する。
   */
  public Label updateLabel(Long updateLabelId, UpdateLabelForm updateLabelForm)
      throws RuntimeException {
    Long loginUserId = securityUtils.getCurrentUserId();
    Label updateLabel = labelRepository.findByIdAndUserId(updateLabelId, loginUserId)
        .orElseThrow(() -> new RuntimeException("更新対象のLabelが見つかりませんでした。"));
    // フォームの値でLabelの値を更新する
    updateLabel.updateWithForm(updateLabelForm);
    return labelRepository.save(updateLabel);
  }

  /**
   * Labelを削除する。
   */
  public Long deleteLabel(Long deleteLabelId) throws RuntimeException {
    Long loginUserId = securityUtils.getCurrentUserId();
    labelRepository.deleteByIdAndUserId(deleteLabelId, loginUserId);
    return deleteLabelId;
  }
}
