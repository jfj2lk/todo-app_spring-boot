package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.form.label.AddLabelForm;
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
}
