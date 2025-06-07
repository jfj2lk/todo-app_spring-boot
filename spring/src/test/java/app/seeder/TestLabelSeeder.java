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

  /**
   * テスト用のLabelの初期データを作成する。
   */
  public void seedInitialLabel() {
    List<Label> labels = List.of(
        new Label("label1"),
        new Label("label2"),
        new Label("label3"),
        new Label("label4"));

    labelRepository.saveAll(labels);
  }
}
