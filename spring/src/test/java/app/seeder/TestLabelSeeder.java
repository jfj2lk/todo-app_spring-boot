package app.seeder;

import java.util.List;

import org.springframework.stereotype.Component;

import app.model.Label;
import app.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestLabelSeeder {
  private final LabelRepository labelRepository;
  private List<Label> labels;

  /**
   * テスト用のLabelの初期データを作成する。
   */
  public void seedInitialLabel() {
    labels = List.of(
        new Label(1L, "label1"),
        new Label(1L, "label2"),
        new Label(2L, "label3"),
        new Label(2L, "label4"));

    labelRepository.saveAll(labels);
  }
}
